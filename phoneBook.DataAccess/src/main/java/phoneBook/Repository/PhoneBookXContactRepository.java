package phoneBook.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import phoneBook.Entities.PhoneBookXContact;

public interface PhoneBookXContactRepository extends CrudRepository<PhoneBookXContact, Integer> {

	@Transactional
	@Modifying
	@Query(value = "insert into PhoneBookXContacts (contactTypeId, contactInfo, phoneBookId) values (:contactTypeId, :contactInfo, :phoneBookId)", nativeQuery = true)
	void AddPhoneBookXContact(@Param("contactTypeId")Integer contactTypeId, @Param("contactInfo")String contactInfo, @Param("phoneBookId")Integer phoneBookId);
}