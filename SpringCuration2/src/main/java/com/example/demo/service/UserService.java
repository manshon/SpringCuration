package com.example.demo.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Community;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.LoginUserDetails;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CommunityRepository communityRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
//
//	private SessionFactory sessionFactory;
//
//	public void setSessionFactory(SessionFactory sf){
//		this.sessionFactory = sf;
//	}


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("Username is empty");
        }

        User user = repository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new LoginUserDetails(user);
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

    @Transactional
    public boolean isFollowAnyCommunity(Long userId) {
    		User user = repository.findById(userId);
    		Set<Community> community = user.getFollowCommunities();
    		if(!community.isEmpty()) {
    			return true;
    		}else {
    			return false;
    		}
    }


    @Transactional
    public void followCommunity(Long userId, Long communityId) {
    		User user = repository.findById(userId);
    		Community community = communityRepository.findById(communityId);
    		Set<Community> followedCommunity = user.getFollowCommunities();

    		user.addFollowCommunity(community);
    		community.addUser(user);
    		repository.save(user);
    		communityRepository.save(community);
    }

    @Transactional
    public void unFollowCommunity(Long userId, Long communityId) {
		User user = repository.findById(userId);
		Community community = communityRepository.findById(communityId);
		Set<Community> followedCommunity = user.getFollowCommunities();
		Set<User> followUsers = community.getFollowUsers();

		followedCommunity.remove(community);
		followUsers.remove(user);

		user.setFollowCommunities(followedCommunity);
		community.setFollowUsers(followUsers);
		repository.save(user);
		communityRepository.save(community);
    }



}















