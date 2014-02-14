/**
 * 
 */
package rwar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.miemiedev.smt.entity.User;
import com.github.miemiedev.smt.service.AuthService;

/**
 * @author 131528
 */
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = { "/applicationContext.xml" })  
@ActiveProfiles("development-web") 
public class UserTest extends AbstractJUnit4SpringContextTests{
    @Autowired
    AuthService authService;

   

    @Test
    public void test() {
        User user = new User();
        user.setEmail("asdfdf@sina.com");
        user.setName("saff");
        user.setRealName("dddd");
        user.setState("0");
        user.setType("dgdg");
        user.setAliasesName("gggg");
        authService.add(user);
    }

    
}
