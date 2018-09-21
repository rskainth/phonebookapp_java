package phoneBook.Entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "`PhoneBookXContacts`")
public class PhoneBookXContact {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String contactInfo;
	private Integer contactTypeId;
	private Integer phoneBookId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name="PhoneBookId",referencedColumnName="id",nullable=false,unique=true)
	private PhoneBook phoneBook;
	
	public PhoneBook getPhoneBook() {
		return phoneBook;
	}

	public void setPhoneBook(PhoneBook phoneBook) {
		this.phoneBook = phoneBook;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getContactInfo() {
		return contactInfo;
	}
	
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	
	public Integer getContactTypeId() {
		return contactTypeId;
	}
	
	public void setContactTypeId(Integer contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	
	public Integer getPhoneBookId() {
		return phoneBookId;
	}
	
	public void setPhoneBookId(Integer phoneBookId) {
		this.phoneBookId = phoneBookId;
	}

}