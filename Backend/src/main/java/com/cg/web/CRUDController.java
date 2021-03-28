package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.Contact;
import com.cg.entity.ContactInformation;
import com.cg.entity.User;
import com.cg.exceptions.ContactException;
import com.cg.exceptions.UserException;
import com.cg.service.AddressService;

@RestController
public class CRUDController {

	@Autowired
	private AddressService addressService;

	@CrossOrigin
	@PostMapping("/addUser")
	public User registerUser(@RequestBody User user) throws UserException {
		System.err.println("inside add user");
		return addressService.registerUser(user);
	}

	@CrossOrigin
	@GetMapping("/viewUsers")
	public List<User> viewAllUsers() {
		return addressService.viewAllUsers();

	}

	@CrossOrigin
	@GetMapping(path = "/login/{username}/{password}")
	public User login(@PathVariable String username, @PathVariable String password) throws UserException{
		return addressService.login(username, password);
	}

	@CrossOrigin
	@GetMapping("/getAllContacts")
	public List<ContactInformation> getAllContacts() {
		return addressService.getAllContacts();
	}
	@CrossOrigin
	@PostMapping("/addContact")
	public ContactInformation addContactToUser(@RequestBody Contact contact) throws ContactException {
		System.err.println("inside add contact controller");
		return addressService.addContactToUser(contact);
	}

	@CrossOrigin
	@DeleteMapping("/delete/{contactId}")
	public void deleteById(@PathVariable int contactId) {
		addressService.deleteContactById(contactId);
	}
	
	@CrossOrigin
	@PutMapping("/update")
	public ContactInformation updateUserContact(@RequestBody Contact contact) {
		return addressService.updateUserContact(contact);
	}


}
