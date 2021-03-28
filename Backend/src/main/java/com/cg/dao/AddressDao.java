package com.cg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cg.entity.ContactInformation;
/*
 * @Repository annotation is used to indicate that the class provides the mechanism for 
 * storage, retrieval, search, update and delete operation on object.
 */
@Repository
public interface AddressDao extends JpaRepository<ContactInformation, Integer> {

	@Query("select c from ContactInformation c where (c.firstName=:search or c.mobileNumber=:search) and c.user.userId=:userId ")
	public List<ContactInformation> searchContactofUser(@Param("search") String search, @Param("userId") Integer userId);
	@Transactional
	@Modifying
	/*
	 * Spring Data JPA @Modifying Annotation, to execute not only SELECT queries
	 *  but also INSERT, UPDATE, DELETE, and even DDL queries
	 */
	/*
	 * This method is used to change the boolean value assigned
	 * to favorite variable, when this is executed, the value will be changed from 0 to 1
	 * and vice versa to add the contacts to favorites and remove from favorites
	 * @param id is the contactId of Integer type of specific contact, which will be sent by UI when user will click 
	 * on any specific contact number to add or remove from favorites, correspondingly changes will be made 
	 * in existing database
	 */
	@Query("update ContactInformation c set c.isFavorite=(case when c.isFavorite=0 then 1 else 0 end) where c.id=:id ")
	public void addContactAsFavorite(@Param("id") Integer contactId);
	/*
	 * This method is used to fetch the contacts corresponding to which having value 1
	 *  from the database as they are the contacts added to favorites and the 
	 *  list of those will be displayed
	 *  @param userId of type Integer will search for specific userId which will be passed from UI 
	 *  according to particular userId table data will be fetched
	 * 
	 */
	@Query("select c from ContactInformation c where c.isFavorite=1 and c.user.userId=:userId")
	public List<ContactInformation> getFavoriteContactsofUser(@Param("userId") Integer userId);
	
	@Query("select c from ContactInformation c where c.user.userId=:userId")
	public List<ContactInformation> getContactsofUser(@Param("userId") Integer userId);

	
}
