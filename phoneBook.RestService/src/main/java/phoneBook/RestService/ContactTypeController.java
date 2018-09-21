package phoneBook.RestService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import phoneBook.BusinessLogic.ContactTypeBL;
import phoneBook.Contracts.BusinessLogic.IContactTypeBL;
import phoneBook.Contracts.BusinessObjects.IContactType;
import phoneBook.Contracts.DataAccess.IContactTypeDA;
import phoneBook.DataAccess.ContactTypeDA;
import phoneBook.RestService.Models.RestServiceModel;
import phoneBook.businessObjects.ContactType;

@RestController
@RequestMapping("/contacttype")
public class ContactTypeController {
	
	@Autowired
    IContactTypeDA dataAccess;
	
	@Autowired
	IContactTypeBL businessLogic;
	
	@RequestMapping("/getall")
    public HttpEntity<RestServiceModel<List<IContactType>>> getAllContactTypes() {

        List<IContactType> businessObjectList = businessLogic.GetAll();
        
        RestServiceModel<List<IContactType>> restModel = new RestServiceModel<>();
        restModel.Model = businessObjectList;
        restModel.add(linkTo(methodOn(ContactTypeController.class).getAllContactTypes()).withSelfRel());

        return new ResponseEntity<>(restModel, HttpStatus.OK);
    }

}
