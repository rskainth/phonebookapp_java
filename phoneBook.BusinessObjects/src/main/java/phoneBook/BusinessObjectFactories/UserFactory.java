package phoneBook.BusinessObjectFactories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import phoneBook.Contracts.BusinessObjects.IUser;
import phoneBook.Contracts.ObjectFactory.IBusinessObjectFactory;
import phoneBook.businessObjects.User;

@Component
public class UserFactory implements IBusinessObjectFactory<IUser>  {

	public IUser GetNewObject() {
		return new User();
	}

	public List<? extends IUser> GetList() {
		LinkedList<User> list = new LinkedList<User>();	
		return (LinkedList<IUser>)(List)list;
	}

}
