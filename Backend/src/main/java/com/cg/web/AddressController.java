package com.cg.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.ContactInformation;
import com.cg.exceptions.ContactException;
import com.cg.service.AddressService;

@RestController
public class AddressController {
	/*
	 * @Autowired is used to
	 * Inject the dependency of AddressService class into AddressController class.
	 * It internally uses setter or constructor to inject dependency. 
	 */
	@Autowired
	private AddressService addressService;

	@CrossOrigin
	@GetMapping("/searchContact/{search}/{userId}")
	public List<ContactInformation> searchContactofUser(@PathVariable String search,@PathVariable Integer userId) throws ContactException {
		return addressService.searchContactofUser(search,userId);
	}
	/*
	 * addasFavorite method will take the id as argument and pass this 
	 * contact id to service to raise the call for specific operation
	 */
	@CrossOrigin
	@GetMapping("/addfavorite/{id}")
	public void addContactAsFavorite(@PathVariable int id) {
		addressService.addContactAsFavorite(id);

	}
	/*
	 * this method i.e getFavoritecontacts will take user id as argument 
	 * and send it to service which will call particular function associated 
	 * to it to fetch favorite list
	 */
	@CrossOrigin
	@GetMapping("/viewfavoriteContacts/{userId}")
	public List<ContactInformation> getFavoritecontactsofUser(@PathVariable Integer userId) throws ContactException {
		return addressService.getFavoriteContactsofUser(userId);
	}

	@CrossOrigin
	@GetMapping("/contacts/{userId}")
	public List<ContactInformation> getContactsofUser(@PathVariable Integer userId) throws ContactException {
		return addressService.getContactsofUser(userId);
	}
	@CrossOrigin
	@GetMapping("/contact/{contactId}")
	public Optional<ContactInformation> findContactById(@PathVariable Integer contactId){
		
		return addressService.findContactById(contactId);
		
	}

}
