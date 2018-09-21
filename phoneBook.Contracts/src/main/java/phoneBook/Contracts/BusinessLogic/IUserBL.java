package phoneBook.Contracts.BusinessLogic;

import phoneBook.Contracts.BusinessObjects.IUser;

public interface IUserBL {
	
	IUser Add(IUser obj);
	
	Boolean Update(IUser obj);
	
	Boolean Delete(Integer id);
	
	IUser GetDetails(Integer id);

}