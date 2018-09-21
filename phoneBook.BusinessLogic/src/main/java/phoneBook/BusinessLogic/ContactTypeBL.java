package phoneBook.BusinessLogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import phoneBook.Contracts.BusinessLogic.IContactTypeBL;
import phoneBook.Contracts.BusinessObjects.IContactType;
import phoneBook.Contracts.DataAccess.IContactTypeDA;

@Component
public class ContactTypeBL implements IContactTypeBL {
	
	@Autowired
	IContactTypeDA _contactTypeDA;
	
	public IContactType Add(IContactType obj) {
		return this._contactTypeDA.Add(obj);
	}

	public Boolean Update(IContactType obj) {
		return this._contactTypeDA.Update(obj);
	}

	public Boolean Delete(Integer id) {
		return this._contactTypeDA.Delete(id);
	}

	public IContactType GetDetails(Integer id) {
		return this._contactTypeDA.GetDetails(id);
	}

	public List<IContactType> GetAll() {
		return this._contactTypeDA.GetAll();
	}
	
}
