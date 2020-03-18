package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserAdmin;
import com.example.demo.repository.UserAdminRepository;

import static java.util.Collections.emptyList;

@Service
public class UserAdminServiceImpl implements UserAdminService {

	@Autowired
	private UserAdminRepository userAdminRepository;

    public UserAdminServiceImpl(UserAdminRepository userAdminRepository) {
        this.userAdminRepository = userAdminRepository;
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAdmin userAdmin = userAdminRepository.findByUsername(username);
        if (userAdmin == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(userAdmin.getUsername(), userAdmin.getPassword(), emptyList());
	}

}
