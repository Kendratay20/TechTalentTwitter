package com.tts.TechTalentTwitter.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.JoinColumn;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	private String email;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int active;

	@CreationTimestamp 
	private Date createdAt;	
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
	    inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	public User findByUsername(String username) {
	    return userRepository.findByUsername(username);
	}
	    
	public List<User> findAll(){
	    return (List<User>) userRepository.findAll();
	}
	    
	public void save(User user) {
	    userRepository.save(user);
	}	
	public User saveNewUser(User user) {
	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setActive(1);
	    Role userRole = roleRepository.findByRole("USER");
	    user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	    return userRepository.save(user);
	}
	public User getLoggedInUser() {
	    String loggedInUsername = SecurityContextHolder.
	      getContext().getAuthentication().getName();
	    
	    return findByUsername(loggedInUsername);
	}
}
