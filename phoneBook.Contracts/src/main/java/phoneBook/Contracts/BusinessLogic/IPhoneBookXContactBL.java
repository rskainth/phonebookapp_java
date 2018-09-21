package phoneBook.Contracts.BusinessLogic;

import java.util.List;

import phoneBook.Contracts.BusinessObjects.IPhoneBookXContact;

public interface IPhoneBookXContactBL {
	
	IPhoneBookXContact Add(IPhoneBookXContact obj);
	
	Boolean Update(IPhoneBookXContact obj);
	
	Boolean Delete(Integer id);
	
	IPhoneBookXContact GetDetails(Integer id);
	
	List<IPhoneBookXContact> GetAllForPhoneBookContact(Integer phoneBookId);

}
