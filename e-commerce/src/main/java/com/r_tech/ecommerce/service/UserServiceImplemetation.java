package com.r_tech.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.r_tech.ecommerce.jwt.JwtProvider;
import org.springframework.stereotype.Service;

import com.r_tech.ecommerce.repository.UserRepository;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.User;

@Service
public class UserServiceImplemetation implements UserService {

	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;

	public UserServiceImplemetation(UserRepository userRepository, JwtProvider jwtProvider) {
		super();
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
         Optional<User> user = userRepository.findById(userId);
         if(user.isPresent()) {
        	 return user.get();
         }
         throw new UserException(" user not found with id: "+ userId);
		
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if(user == null) {
			throw new UserException("user not found with: "+ email);
		}
		return user;
	}
	
	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAllByOrderByCreatedAtDesc();
	}

}
