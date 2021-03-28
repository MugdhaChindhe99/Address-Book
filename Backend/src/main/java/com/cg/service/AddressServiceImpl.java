package com.cg.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cg.dao.AddressDao;
import com.cg.dao.UserDao;
import com.cg.dto.Contact;
import com.cg.entity.ContactInformation;
import com.cg.entity.User;
import com.cg.exceptions.ContactException;
import com.cg.exceptions.UserException;
import com.cg.util.AddressBookConstants;

@Service
public class AddressServiceImpl implements AddressService {
	/*
	 * @Autowired is used to
	 * Inject the dependency of AddressDao class into AddressService class.
	 * It internally uses setter or constructor to inject dependency. 
	 */
	@Autowired
	private AddressDao addressDao;
	/*
	 * @Autowired is used to
	 * Inject the dependency of UserDao class into AddressService class.
	 * It internally uses setter or constructor to inject dependency. 
	 */
	

	@Autowired
	private UserDao userDao;

	@Override
	public List<ContactInformation> getAllContacts() {
		System.err.println("Inside retrieve() method");
		return addressDao.findAll();
	}
	/*
	 * Description    : The method  mentioned below deletes the contacts from the address_book DB.
	 * Method         : deleteContactById() - Here with the help of inbuilt JPARepository by use of deleteById method we delete the contact from DB.                              
	 * Exceptions     : NA
	 *  @return type  :  Is list
	 */
	@Override
	public Boolean deleteContactById(Integer contactId) {
		addressDao.deleteById(contactId);
		return true;
	}
	/*
	 * Description   :  The method  mentioned below searches the Contact entered by user.(FirstName or MobileNumber) and shows the contacts in which that firstName/ MobileNumber is available. 
	 * Method        :  searchContactofUser() - we'll get list of contacts in a list and display to user
	 * Exceptions    :  If passed firstName/MobileNumber  is not available then it pops contacts not available exception.  
	 * @return type  :  Is list
	 */
	@Override
	public List<ContactInformation> searchContactofUser(String search,Integer userId) throws ContactException {
		
		if (search != null) {
			List<ContactInformation> lst = addressDao.searchContactofUser(search,userId); // whole table
			if(lst.isEmpty()) {
				throw new ContactException(AddressBookConstants.SEARCH_CONTACT_NOT_AVAILABLE);	
			}
			return lst;
		}
		throw new ContactException(AddressBookConstants.SEARCH_CONTACT_NOT_AVAILABLE);
	}
	/*
	 * This method here will take the contact id as  argument passed from AddressController and 
	 * make a call to AddressDao to get the data from the database
	 */
	@Override
	public void addContactAsFavorite(Integer contactId) {
		addressDao.addContactAsFavorite(contactId);

	}
	/*
	 * This  method will take user id as an argument passed from AddressController
	 * and check whether any favorite contacts associated to particular 
	 * user id is present or not and correspondingly
	 * return the list 
	 */
	@Override
	public List<ContactInformation> getFavoriteContactsofUser(Integer userId) throws ContactException {
		List<ContactInformation> contacts = addressDao.getFavoriteContactsofUser(userId);
		if (contacts.isEmpty()) {
			throw new ContactException(AddressBookConstants.CONTACTS_NOT_AVAILABLE);
		}
		contacts.sort((c1, c2) -> c1.getFirstName().compareTo(c2.getFirstName()));
		return contacts;
	}

	@Override
	public List<ContactInformation> getContactsofUser(Integer userId) throws ContactException {
		List<ContactInformation> contacts = addressDao.getContactsofUser(userId);
		if (contacts.isEmpty()) {
			throw new ContactException(AddressBookConstants.CONTACTS_NOT_AVAILABLE);
		}
		contacts.sort((c1, c2) -> c1.getFirstName().compareTo(c2.getFirstName()));
		return contacts;
	}

	@Override
	public User registerUser(User user) throws UserException {
        List<User> list = userDao.findAll();
        String username = user.getUsername();
        System.err.println("Hi i'm username" +username);
        for(User u : list ) {
        	if(username.equals(u.getUsername())) {
         	throw new UserException("User Name already exits");
        	};
        }
		return userDao.save(user);
	}

	@Override
	public User login(String username, String password) throws UserException {
		if (username != null && password != null) {
			User tmpList = userDao.login(username, password);
			return tmpList;
		}
		throw new UserException(AddressBookConstants.NOT_AUTHENTICATED);

	}

	@Override
	public List<User> viewAllUsers() {
		return userDao.findAll();
	}

	@Override
	public ContactInformation addContactToUser(Contact contact) throws ContactException {
		System.err.println("i m in contact class " + contact.getUserId());
		List<ContactInformation> list = addressDao.getContactsofUser(contact.getUserId());
		String contactNumber = contact.getMobileNumber();
		for (ContactInformation c : list) {
			if(contactNumber.equals(c.getMobileNumber())) {
	         	throw new ContactException("Contact Number Already exits");
	        	};
		}
		System.err.println("inside add contact Service");
		ContactInformation ci = new ContactInformation();
		ci.setContactId(contact.getContactId());
		ci.setFirstName(contact.getFirstName());
		ci.setLastName(contact.getLastName());
		ci.setMobileNumber(contact.getMobileNumber());
		ci.getUser().setUserId(contact.getUserId());
		return addressDao.save(ci);

	}

	@Override
	public ContactInformation updateUserContact(Contact contact) {
		System.err.println("inside update contact Service");
		Integer contact1 = contact.getContactId();
		System.err.println("I'm ContactId " + contact1);
		Optional<ContactInformation> contact2 = addressDao.findById(contact1);
		System.err.println("I'm Object " + contact2);
		ContactInformation ci = new ContactInformation();
		ci.setContactId(contact.getContactId());
		ci.setFirstName(contact.getFirstName());
		ci.setLastName(contact.getLastName());
		ci.setFavorite(contact.isFavorite());
		ci.setMobileNumber(contact.getMobileNumber());
		ci.getUser().setUserId(contact.getUserId());
		return addressDao.save(ci);
	}

	@Override
	public Optional<ContactInformation> findContactById(Integer contactId) {	
		return addressDao.findById(contactId);
	}
	
	// Export By Id : //
	
	@Override
	public ByteArrayInputStream loadByuserId(int userId) {
		List<ContactInformation> users1 = addressDao.getContactsofUser(userId);

		final CSVFormat format = CSVFormat.DEFAULT.withHeader("ContactId", "FirstName", "Favorite", "LastName",
				"MobileNumber");

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (ContactInformation user : users1) {
				List<String> data = Arrays.asList(String.valueOf(user.getContactId()), user.getFirstName(),
						String.valueOf(user.isFavorite()), user.getLastName(), user.getMobileNumber());

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();

			ByteArrayInputStream ins = new ByteArrayInputStream(out.toByteArray());
			return ins;
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
	// Import By Id
	public void save(MultipartFile file) {
		List<ContactInformation> developerTutorialList = new ArrayList<>();
		try {
			System.err.println("file.getInputStream()" + file.getInputStream());

			try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

					CSVParser csvParser = new CSVParser(fileReader,
							CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

				Iterable<CSVRecord> csvRecords = csvParser.getRecords();

				for (CSVRecord csvRecord : csvRecords) {
					Contact contact = new Contact(Integer.parseInt(csvRecord.get("ContactId")),
							csvRecord.get("FirstName"), csvRecord.get("LastName"), csvRecord.get("MobileNumber"),
							Boolean.getBoolean(csvRecord.get("Favorite")),(csvRecord.get("UserName")));

					System.err.println("ye userid hai " + contact.getUserId());
					
					User urlu = userDao.findbyusername(contact.getUsername());

					ContactInformation user = new ContactInformation();
					user.setContactId(contact.getContactId());
					user.setFirstName(contact.getFirstName());
					user.setLastName(contact.getLastName());
					user.setMobileNumber(contact.getMobileNumber());
					user.setFavorite(contact.isFavorite());
					user.setUser(urlu);

					developerTutorialList.add(user);
				}
			} catch (IOException e) {
				throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
			}

			addressDao.saveAll(developerTutorialList);
			System.err.println("yeah");
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
   // Find User By ID Used in Import 
	@Override
	public Optional<User> finduserbyId(Integer userId) {
		// TODO Auto-generated method stub
		return userDao.findById(userId);
	}
	
//	public User finduserbyId(Integer userId) { CHANGE 1
//	
//		
//		return userDao.findById(userId).get();
//	}
}
