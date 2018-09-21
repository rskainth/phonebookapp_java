package phoneBook.DataAccess;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import phoneBook.Contracts.BusinessObjects.IGroupXContact;
import phoneBook.Contracts.BusinessObjects.IPhoneBook;
import phoneBook.Contracts.BusinessObjects.IPhoneBookXContact;
import phoneBook.Contracts.DataAccess.IPhoneBookDA;
import phoneBook.Contracts.ObjectFactory.IBusinessObjectFactory;
import phoneBook.Entities.GroupXContact;
import phoneBook.Entities.PhoneBook;
import phoneBook.Entities.PhoneBookXContact;
import phoneBook.Repository.PhoneBookRepository;;

@Component
public class PhoneBookDA implements IPhoneBookDA {
	
	@Autowired
	private PhoneBookRepository repository;
	
	@Autowired
	IBusinessObjectFactory<IPhoneBook> businessObjectFactory;
	
	@Autowired
	IBusinessObjectFactory<IPhoneBookXContact> phoneBookXContactObjectFactory;

	@Override
	public IPhoneBook Add(IPhoneBook obj) {
		
		ModelMapper modelMapper = new ModelMapper();
		PhoneBook entity = modelMapper.map(obj, PhoneBook.class);
		
		repository.save(entity);
		
		obj.setId(entity.getId());
		
		return obj;
		
	}

	@Override
	public Boolean Update(IPhoneBook obj) {
		
		try
		{
			PhoneBook entity = repository.findById(obj.getId()).get();
		
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.map(obj, entity);
		
			repository.save(entity);
			
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		
	}

	@Override
	public Boolean Delete(Integer id) {
		
		try
		{
			repository.deleteById(id);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
		
	}

	@Override
	public IPhoneBook GetDetails(Integer id) {
	
		PhoneBook entity = repository.findById(id).get();
		return ToBusinessObject(entity);
		
	}

	@Override
	public List<IPhoneBook> GetAllForUser(Integer userId) {
		
		Iterable<PhoneBook> entities = repository.GetAllContactsForUser(userId);
		return CreateBusinessObjectList(entities);
		
	}

	@Override
	public List<IPhoneBook> GetAllContactsInGroup(Integer groupId) {

		Iterable<PhoneBook> entities = repository.GetAllContactsInGroup(groupId);
		return CreateBusinessObjectList(entities);

	}
	
	private List<IPhoneBook> CreateBusinessObjectList(Iterable<PhoneBook> entities)
	{
		List<IPhoneBook> list = (List<IPhoneBook>)businessObjectFactory.GetList();
	
		for(PhoneBook entity : entities)
		{	
			list.add(ToBusinessObject(entity));
		}
		
		return (List<IPhoneBook>)list;
		
	}
	
	private IPhoneBook ToBusinessObject(PhoneBook entity)
	{
		IPhoneBook businessObject = businessObjectFactory.GetNewObject();
		IPhoneBookXContact phoneBooxContacts = null;
		ModelMapper modelMapper = new ModelMapper();
		
		businessObject.setId(entity.getId());
		businessObject.setAddress(entity.getAddress());
		businessObject.setCompany(entity.getCompany());
		businessObject.setContactName(entity.getContactName());
		businessObject.setIsFavorite(entity.getIsFavorite());
		businessObject.setTitle(entity.getTitle());
		businessObject.setUserId(entity.getUserId());
		
		if(entity.getContacts() != null)
		{
			for(PhoneBookXContact pbxc : entity.getContacts())
			{
				phoneBooxContacts = phoneBookXContactObjectFactory.GetNewObject();
				phoneBooxContacts.setId(pbxc.getId());
				phoneBooxContacts.setContactInfo(pbxc.getContactInfo());
				phoneBooxContacts.setContactTypeId(pbxc.getContactTypeId());
				phoneBooxContacts.setPhoneBookId(pbxc.getPhoneBookId());
				
				businessObject.getContacts().add(phoneBooxContacts);
			}
		}
		
		return businessObject;
	}

	@Override
	public List<IPhoneBook> GetAllContactsNotInGroup(Integer groupId, Integer userId) {
		Iterable<PhoneBook> entities = repository.GetAllContactsNotInGroup(userId, groupId);
		return CreateBusinessObjectList(entities);
	}

}