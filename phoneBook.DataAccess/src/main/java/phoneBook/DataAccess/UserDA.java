package phoneBook.DataAccess;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import phoneBook.Contracts.BusinessObjects.IUser;
import phoneBook.Contracts.DataAccess.IUserDA;
import phoneBook.Contracts.ObjectFactory.IBusinessObjectFactory;
import phoneBook.Entities.User;
import phoneBook.Repository.UserRepository;

@Component
public class UserDA implements IUserDA {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	IBusinessObjectFactory<IUser> businessObjectFactory;
	
	@Override
	public IUser Add(IUser obj) {
		
		ModelMapper modelMapper = new ModelMapper();
		User entity = modelMapper.map(obj, User.class);
		
		// The mapper is making some mistake in maping the username.
		// Added this line to set the username in the entity.
		entity.setUserName(obj.getUsername());
		
		repository.save(entity);
		
		obj.setId(entity.getId());
		
		return obj;
		
	}

	@Override
	public Boolean Update(IUser obj) {
		
		try
		{
			User entity = repository.findById(obj.getId()).get();
		
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
	public IUser GetDetails(Integer id) {
		
		IUser businessObject = businessObjectFactory.GetNewObject();
		
		User entity = repository.findById(id).get();
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(entity, businessObject);
		
		return businessObject;
		
	}

	@Override
	public IUser GetDetails(String username) {
		
		IUser businessObject = businessObjectFactory.GetNewObject();
		
		User entity = repository.GetByUsername(username);
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.map(entity, businessObject);
		
		return businessObject;
	}

}
