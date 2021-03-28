package com.cg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.entity.User;
/*
 * @Repository annotation is used to indicate that the class provides the mechanism for 
 * storage, retrieval, search, update and delete operation on object.
 */
@Repository
public interface UserDao  extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.username=:username and u.password=:password")
	public User login(@Param("username") String username, @Param("password") String password);
	
	@Query("select u from User u where u.username=:username")
	public User findbyusername(@Param("username") String username);
	

}
