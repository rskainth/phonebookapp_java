package phoneBook.Contracts.BusinessObjects;

public interface IUser {
	
	Integer getId();

	void setId(Integer id);

	String getUsername();

	void setUsername(String username);
	
	String getPassword();

	void setPassword(String password);
	
	String getName();

	void setName(String name);

}