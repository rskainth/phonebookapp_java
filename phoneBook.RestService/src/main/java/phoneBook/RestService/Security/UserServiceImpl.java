package phoneBook.RestService.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
	
   @Autowired
   UserDAO oauthDao;

   @Override
   public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
	   
      ApplicationUser userEntity = null;
      
      try {
    	  
         userEntity = oauthDao.getUserDetails(username);
         CustomUser customUser = new CustomUser(userEntity);
         customUser.setId(userEntity.getId());
         return customUser;
      
      } 
      catch (Exception e) {
    	  
         e.printStackTrace();
         throw new UsernameNotFoundException("User " + username + " was not found in the database");
      
      }
   }
}
