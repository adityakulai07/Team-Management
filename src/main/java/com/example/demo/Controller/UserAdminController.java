package com.example.demo.Controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.entity.UserAdmin;
import com.example.demo.repository.UserAdminRepository;

@RestController
public class UserAdminController {
	
	@Autowired
	private UserAdminRepository userAdminRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public UserAdminController(UserAdminRepository userAdminRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
		
			this.userAdminRepository = userAdminRepository;
			this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

//	@GetMapping("/admins")
//	public List<UserAdmin> getUsers() {
//		
//		return userAdminRepository.findAll();
//		
//	}
	
	@PostMapping("/users/sign-up")
	public void signUp(@Valid @RequestBody UserAdmin userAdmin) {
		
		userAdmin.setPassword(bCryptPasswordEncoder.encode(userAdmin.getPassword()));
		userAdminRepository.save(userAdmin);
		
		
	}

}
