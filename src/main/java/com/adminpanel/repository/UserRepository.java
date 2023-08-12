package com.adminpanel.repository;

import com.adminpanel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);


//	@Modifying
//	@Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.email = :email WHERE u.id = :id")
//	void updateUserInfoById(Long id, String firstName, String lastName, String email);
}
