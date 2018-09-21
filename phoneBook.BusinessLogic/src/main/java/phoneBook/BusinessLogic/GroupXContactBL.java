package phoneBook.BusinessLogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import phoneBook.Contracts.BusinessLogic.IGroupXContactBL;
import phoneBook.Contracts.BusinessObjects.IGroupXContact;
import phoneBook.Contracts.DataAccess.IGroupXContactDA;

@Component
public class GroupXContactBL implements IGroupXContactBL {

	@Autowired
	IGroupXContactDA _iGroupXContactDA;
	
	public IGroupXContact Add(IGroupXContact obj) {
		return _iGroupXContactDA.Add(obj);
	}

	public Boolean Update(IGroupXContact obj) {
		return _iGroupXContactDA.Update(obj);
	}

	public Boolean Delete(Integer id) {
		return _iGroupXContactDA.Delete(id);
	}

	public IGroupXContact GetDetails(Integer id) {
		return _iGroupXContactDA.GetDetails(id);
	}

	public List<IGroupXContact> GetAllContactsForGroup(Integer groupId) {
		return _iGroupXContactDA.GetAllContactsForGroup(groupId);
	}

	public List<IGroupXContact> GetAllGroupsForContact(Integer phoneBookId) {
		return _iGroupXContactDA.GetAllGroupsForContact(phoneBookId);
	}
	
}
