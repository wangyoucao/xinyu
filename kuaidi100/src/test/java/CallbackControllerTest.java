/**
 * 
 */


import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

/**
 * @author 131528
 *
 */
public class CallbackControllerTest {

    /**
     * Test method for {@link com.if2c.kuaidi.web.CallbackController#receiveCallBack(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testReceiveCallBack() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.if2c.kuaidi.web.CallbackController#getMailNoInfo(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testGetMailNoInfo() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.if2c.kuaidi.web.CallbackController#postMailNoInfo(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testPostMailNoInfo() {
        
        try {
            new CallbackController().postMailNoInfo(null, null);
        } catch (ParseException e) {
           
            e.printStackTrace();
        }
       
    }

}
