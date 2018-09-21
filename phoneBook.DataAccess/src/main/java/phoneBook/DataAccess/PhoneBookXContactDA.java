package phoneBook.DataAccess;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import phoneBook.Contracts.BusinessObjects.IPhoneBookXContact;
import phoneBook.Contracts.DataAccess.IPhoneBookXContactDA;
import phoneBook.Contracts.ObjectFactory.IBusinessObjectFactory;
import phoneBook.Entities.PhoneBookXContact;
import phoneBook.Repository.PhoneBookXContactRepository;

@Component
public class PhoneBookXContactDA implements IPhoneBookXContactDA {
	
	@Autowired
	private PhoneBookXContactRepository repository;
	
	@Autowired
	IBusinessObjectFactory<IPhoneBookXContact> businessObjectFactory;

	@Override
	public IPhoneBookXContact Add(IPhoneBookXContact obj) {
		
		//ModelMapper modelMapper = new ModelMapper();
		//PhoneBookXContact entity = modelMapper.map(obj, PhoneBookXContact.class);
		
		repository.AddPhoneBookXContact(obj.getContactTypeId(), obj.getContactInfo(), obj.getPhoneBookId());
		
		//obj.setId(entity.getId());
		
		return obj;
		
	}

	@Override
	public Boolean Update(IPhoneBookXContact obj) {
		
		try
		{
			PhoneBookXContact entity = repository.findById(obj.getId()).get();
		
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
	public IPhoneBookXContact GetDetails(Integer id) {
		
		IPhoneBookXContact businessObject = businessObjectFactory.GetNewObject();
		
		PhoneBookXContact entity = repository.findById(id).get();
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(entity, businessObject);
		
		return businessObject;
		
	}

	@Override
	public List<IPhoneBookXContact> GetAllForPhoneBookContact(Integer phoneBookId) {
		
		List<IPhoneBookXContact> list = (List<IPhoneBookXContact>)businessObjectFactory.GetList();
		
		Iterable<PhoneBookXContact> entities = repository.findAll();
		
		ModelMapper modelMapper = new ModelMapper();
		IPhoneBookXContact businessObject = null;
		
		for(PhoneBookXContact entity : entities)
		{
			businessObject = businessObjectFactory.GetNewObject();
			modelMapper.map(entity, businessObject);
			
			list.add(businessObject);
		}
		
		return (List<IPhoneBookXContact>)list;
		
	}

}