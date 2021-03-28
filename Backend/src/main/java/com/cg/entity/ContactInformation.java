package com.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "address_book")
@DynamicInsert
@DynamicUpdate
public class ContactInformation {
	@Id
	@Column(name = "contact_id")
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contactIdGenerator")
	//@SequenceGenerator(name = "contactIdGenerator", initialValue = 1)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contactId;

	@Column(name = "first_name")
	@Size(min = 3, max = 12)
	private String firstName;

	@Column(name = "last_name")
	@Size(min = 3, max = 12)
	private String lastName;

	@Column(name = "mobile_number", length = 10)
	private String mobileNumber;

	@Column(name = "isFavorite")
	private boolean isFavorite;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user = new User();
	
	
	

	public ContactInformation(int contactId, @Size(min = 3, max = 12) String firstName,
			@Size(min = 3, max = 12) String lastName, String mobileNumber, boolean isFavorite, User user) {
		super();
		this.contactId = contactId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobileNumber = mobileNumber;
		this.isFavorite = isFavorite;
		this.user = user;
	}

	public ContactInformation() {
		super();
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public boolean isFavorite() {
		return isFavorite;
	}

	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    

}
