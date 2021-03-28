package com.cg.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.cg.dto.Contact;
import com.cg.entity.ContactInformation;
import com.cg.entity.User;
import com.cg.exceptions.ContactException;
import com.cg.exceptions.UserException;

public interface AddressService {
	public List<ContactInformation> getAllContacts();

	public List<ContactInformation> getContactsofUser(@Param("userId") Integer userId) throws ContactException;
	
	public ContactInformation addContactToUser(Contact contact) throws ContactException;
	
	public ContactInformation updateUserContact(Contact contact);

	public Boolean deleteContactById(Integer contactId);

	public List<ContactInformation> searchContactofUser(@Param("search") String search, @Param("userId") Integer userId) throws ContactException;

	public void addContactAsFavorite(@Param("id") Integer contactId);

	public List<ContactInformation> getFavoriteContactsofUser(@Param("userId") Integer userId) throws ContactException;

	public User registerUser(User user) throws UserException;
	
	public List<User> viewAllUsers();
	
	public Optional<ContactInformation> findContactById(Integer contactId);
	public Optional<User> finduserbyId(Integer userId);

	public User login(String username, String password) throws UserException;
	
	public ByteArrayInputStream loadByuserId(int userId); 
	
	public void save(MultipartFile file);
}
