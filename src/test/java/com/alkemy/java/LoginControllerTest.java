package com.alkemy.java;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alkemy.java.model.User;
import com.alkemy.java.repo.IUserRepo;
import com.alkemy.java.service.ILoginService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoginControllerTest {
	
	@Autowired
	private ILoginService loginService;
	@Autowired
    private IUserRepo repo;
	
	private User user;
	
	@BeforeEach
	public void createUser() {
		
		User user = new User(1, "us@test.com", "1234test", true, "123456test");
		repo.save(user);
	}
			
	@Test
	public void checkUserNameMethod() throws Exception {
		user = repo.findById(1).get();
		String mail = "us@test.com";
		User userToCompare = loginService.checkUserName(mail);
		
		assertThat(userToCompare).isEqualToComparingFieldByField(user);		
	}
	
	@Test
	public void changePasswordMethod() throws Exception {
		
		user = repo.findById(1).get();
	    String newPassword = "490999AxT"; 
	    Boolean response = loginService.changePassword(newPassword, user.getUsername());
	    
	    assertTrue(response);//response = true, if old password is replace by the new password in DB. 
	}
		
}