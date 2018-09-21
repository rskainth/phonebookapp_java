package phoneBook.Contracts.BusinessObjects;

import java.util.List;

public interface IPhoneBook {

	Integer getId();

	void setId(Integer id);

	String getContactName();

	void setContactName(String contactName);

	String getCompany();

	void setCompany(String company);

	String getTitle();

	void setTitle(String title);

	String getAddress();

	void setAddress(String address);

	boolean getIsFavorite();

	void setIsFavorite(boolean isFavorite);
	
	Integer getUserId();
	
	void setUserId(Integer userId);
	
	public List<IPhoneBookXContact> getContacts();

	public void setContacts(List<IPhoneBookXContact> contacts);

}