package cn.ming;

import cn.ming.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiroSpringbootApplicationTests {

	@Autowired
	UserServiceImpl userService;

	@Test
	public void contextLoads() {
		System.out.println(userService.queryUserByName("dandan"));
	}

}
