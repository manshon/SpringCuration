package com.example.demo.service;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }

        User user = repository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return (UserDetails) user;
    }

    @Transactional
    public void registerAdmin(String username, String password) {
        User user = new User(username, passwordEncoder.encode(password));
//        user.setAdmin(true);
        repository.save(user);
    }

    @Transactional
    public void registerUser(String username, String password) {
        User user = new User(username, passwordEncoder.encode(password));
        repository.save(user);
    }

//    @Transactional
//    public void editUser(String username, String password) {
//    		User user = new User(username, passwordEncoder.encode(password));
//    		repository
//
//    }
    @Transactional
	public void updateUser(String password, Long userId) {
    		repository.updateUser(password, userId);
	}

}
