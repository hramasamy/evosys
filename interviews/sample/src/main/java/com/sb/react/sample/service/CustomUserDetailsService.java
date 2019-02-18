package com.sb.react.sample.service;

import com.sb.react.sample.model.User;
;
import com.sb.react.sample.payload.UserPrincipal;
import com.sb.react.sample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository ;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)  throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " +
        usernameOrEmail)) ;
        return UserPrincipal.create(user) ;
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException
                ("User not found exception : " + id) ) ;
        return  UserPrincipal.create(user) ;
    }
}
