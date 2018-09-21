package phoneBook.RestService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import phoneBook.Contracts.BusinessLogic.IPhoneBookBL;
import phoneBook.Contracts.BusinessLogic.IPhoneBookXContactBL;
import phoneBook.Contracts.BusinessObjects.IGroup;
import phoneBook.Contracts.BusinessObjects.IPhoneBook;
import phoneBook.Contracts.BusinessObjects.IPhoneBookXContact;
import phoneBook.Contracts.DataAccess.IPhoneBookDA;
import phoneBook.Contracts.DataAccess.IPhoneBookXContactDA;
import phoneBook.RestService.Models.RestServiceModel;
import phoneBook.RestService.Security.CustomUser;
import phoneBook.businessObjects.GroupXContact;
import phoneBook.businessObjects.PhoneBook;
import phoneBook.businessObjects.PhoneBookXContact;

@RestController
@RequestMapping("/phonebook")
public class PhoneBookController {
	
	@Autowired
    IPhoneBookDA dataAccess;
	
	@Autowired
	IPhoneBookBL businessLogic;
	
	@Autowired
    IPhoneBookXContactDA phoneBookXContactdataAccess;
	
	@Autowired
	IPhoneBookXContactBL phoneBookXContactBusinessLogic;
	
	// Create a new contact
	@RequestMapping(method = RequestMethod.POST, value="/addcontact")
	public HttpEntity<RestServiceModel<IPhoneBook>> createContact(@RequestBody PhoneBook contact)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		CustomUser user = (CustomUser)auth.getPrincipal();
		
		contact.setUserId(user.getId());
		IPhoneBook newContact = businessLogic.Add(contact);
		
		RestServiceModel<IPhoneBook> restModel = new RestServiceModel<>();
        restModel.Model = newContact;
        restModel.add(linkTo(methodOn(PhoneBookController.class).getContactDetails(newContact.getId())).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
	}
	
	// Create a new contact
	@RequestMapping(method = RequestMethod.PUT, value="/editcontact")
	public HttpEntity<RestServiceModel<IPhoneBook>> editContact(@RequestBody PhoneBook contact)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		CustomUser user = (CustomUser)auth.getPrincipal();
		
		contact.setUserId(user.getId());
		boolean result = businessLogic.Update(contact);
		
		RestServiceModel<IPhoneBook> restModel = new RestServiceModel<>();
        restModel.Model = contact;
        restModel.add(linkTo(methodOn(PhoneBookController.class).getContactDetails(contact.getId())).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
	}
	
	// Get contact details
	@RequestMapping("/getdetails")
    public HttpEntity<RestServiceModel<IPhoneBook>> getContactDetails(@RequestParam(value = "id", required = true) Integer id) {

		IPhoneBook contact = businessLogic.GetDetails(id);
        
        RestServiceModel<IPhoneBook> restModel = new RestServiceModel<>();
        restModel.Model = contact;
        restModel.add(linkTo(methodOn(GroupController.class).getGroupDetails(id)).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
    }
	
	// Get all contacts list for user
	@RequestMapping("/getallcontactsforuser")
    public HttpEntity<RestServiceModel<List<IPhoneBook>>> getAllContactForUser(@RequestParam(value = "userid", required = true) Integer userid, Pageable pageable) {

        List<IPhoneBook> businessObjectList = businessLogic.GetAllForUser(userid);
        
        RestServiceModel<List<IPhoneBook>> restModel = new RestServiceModel<>();
        restModel.Model = businessObjectList;
        restModel.add(linkTo(methodOn(ContactTypeController.class).getAllContactTypes()).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
    }
	
	// Get all contacts list for user
	@RequestMapping("/getallcontacts")
    public HttpEntity<RestServiceModel<List<IPhoneBook>>> getAllContactForCurrentUser() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser user = (CustomUser)auth.getPrincipal();
		
		Integer userid = user.getId();
		
        List<IPhoneBook> businessObjectList = businessLogic.GetAllForUser(userid);
        
        RestServiceModel<List<IPhoneBook>> restModel = new RestServiceModel<>();
        restModel.Model = businessObjectList;
        restModel.add(linkTo(methodOn(ContactTypeController.class).getAllContactTypes()).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
    }
	
	// Get all contacts list for user
	@RequestMapping("/getcontactsnotingroup")
    public HttpEntity<RestServiceModel<List<IPhoneBook>>> getContactsNotInGroup(@RequestParam(value = "groupid", required = true) Integer groupId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUser user = (CustomUser)auth.getPrincipal();
		
		Integer userid = user.getId();
		
        List<IPhoneBook> businessObjectList = businessLogic.GetAllContactsNotInGroup(groupId, userid);
        
        RestServiceModel<List<IPhoneBook>> restModel = new RestServiceModel<>();
        restModel.Model = businessObjectList;
        restModel.add(linkTo(methodOn(ContactTypeController.class).getAllContactTypes()).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
    }
	
	// Get all contacts in the group
	@RequestMapping("/getallgroupcontacts")
    public HttpEntity<RestServiceModel<List<IPhoneBook>>> getAllContactInGroup(@RequestParam(value = "groupid", required = true) Integer groupId) {

        List<IPhoneBook> businessObjectList = businessLogic.GetAllContactsInGroup(groupId);
        
        RestServiceModel<List<IPhoneBook>> restModel = new RestServiceModel<>();
        restModel.Model = businessObjectList;
        restModel.add(linkTo(methodOn(ContactTypeController.class).getAllContactTypes()).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
    }
	
	// Create a new group
	@RequestMapping(method = RequestMethod.POST, value="/addphoneoremailtocontact")
	public HttpEntity<RestServiceModel<IPhoneBookXContact>> addPhoneOrEmailToContact(@RequestBody PhoneBookXContact phoneBookXContact )
	{
		IPhoneBookXContact pbxc = phoneBookXContactBusinessLogic.Add(phoneBookXContact);
		
		RestServiceModel<IPhoneBookXContact> restModel = new RestServiceModel<>();
        restModel.Model = pbxc;
        restModel.add(linkTo(methodOn(ContactTypeController.class).getAllContactTypes()).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
	}
	
	// Delete a contact list
	// Get all contacts in the group
	@RequestMapping(method = RequestMethod.DELETE, value="/removephoneoremailfromcontact")
    public HttpEntity<RestServiceModel<List<IPhoneBook>>> removePhoneOrEmailFromContact(@RequestParam(value = "contactinfoid", required = true) Integer contactinfoid) {

        boolean result = phoneBookXContactBusinessLogic.Delete(contactinfoid);
        
    	return new ResponseEntity<>(HttpStatus.OK);
    }
	
	// Update a contact list

}