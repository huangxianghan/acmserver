/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxh.websocket.User;
import com.hxh.websocket.JsonMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Administrator
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void ObjectMapperTest() throws IOException{
        User user = new User();
            user.setName("用户"+ Math.floor(Math.random()*1000));
            user.setAddr("127.0.0.1");
            user.setPort("8086");
            user.setPass("*");
            user.setLastLoginTime(new Date());
            JsonMessage cmm = new JsonMessage();
            cmm.setC(JsonMessage.USER_LOGIN);
            cmm.setD(user);
            ObjectMapper mapper = new ObjectMapper();
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, cmm);
            
            String jsonmsg = sw.toString();
            System.out.println(jsonmsg);
        
    }
}
