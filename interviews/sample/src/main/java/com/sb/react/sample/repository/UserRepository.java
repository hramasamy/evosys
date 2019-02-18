package com.sb.react.sample.repository;

import com.sb.react.sample.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

   Optional<User> findByEmail (String email) ;

   Optional <User> findByUsernameOrEmail(String username, String email) ;

   List <User> findByIdIn (List<Long> userIds) ;

   Optional<User> findByUsername(String username) ;

   Boolean existsByUsername(String username) ;

   Boolean existsByEmail(String email);


}
