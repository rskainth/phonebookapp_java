package phoneBook.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import phoneBook.Entities.PhoneBook;
import phoneBook.Entities.User;

public interface PhoneBookRepository extends JpaRepository<PhoneBook, Integer> {
	
	@Query("SELECT pb FROM PhoneBook pb JOIN GroupXContact gxc on gxc.contactId = pb.id WHERE gxc.groupId = :groupId")
	List<PhoneBook> GetAllContactsInGroup(@Param("groupId")Integer groupid);
	
	@Query("SELECT pb FROM PhoneBook pb where pb.userId = :userid and pb.id not in (select gxc.contactId from GroupXContact gxc where gxc.groupId= :groupId)")
	List<PhoneBook> GetAllContactsNotInGroup(@Param("userid")Integer userid, @Param("groupId")Integer groupid);
	
	@Query("SELECT pb FROM PhoneBook pb WHERE pb.userId = :userId")
	List<PhoneBook> GetAllContactsForUser(@Param("userId")Integer userId);

}
