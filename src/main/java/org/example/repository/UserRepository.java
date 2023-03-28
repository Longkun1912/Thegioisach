package org.example.repository;

import org.example.entity.Role;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u from User u WHERE u.email =:email")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("SELECT u from User u WHERE u.username =:username")
    Optional<User> findUserByName(@Param("username") String username);

    @Query("SELECT u from User u WHERE u.phone_number =:phone_number")
    Optional<User> findUserByPhoneNumber(@Param("phone_number") String phone_number);

    @Query("SELECT u from User u WHERE (u.role =:role)")
    List<User> findUserByRole(@Param("role")Role role);
}
