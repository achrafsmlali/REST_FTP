package test.lille1.car.asseman_durieux.paserelleFTP;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.jayway.restassured.*;

/**
 *
 * @author asseman
 */
public class PasserelleFTPTest {
    
    public PasserelleFTPTest() {
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
    
    /**
     * Test authentification using the get command.
     */
    @Test
    public void testAuthentification() {
        // Trying to access without identification
        RestAssured.expect().
                statusCode(401).
                when().
                get("/PasserelleFTP/rest/dir///tmp/json");
        
        // Should work better with identification
        RestAssured.expect().
                statusCode(200).
                when().with().authentication().basic("user", "pass").
                get("/PasserelleFTP/rest/dir///tmp/json");
    }
    
    /**
     * Test FileNotFound return code using removeFile on a missing file.
     */
    @Test
    public void testFileNotFound() {
        RestAssured.expect().
                statusCode(404).
                when().with().authentication().basic("user", "pass").
                get("/PasserelleFTP/rest/tmp/azdlkugzadlugzeme5z7zelh");
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}