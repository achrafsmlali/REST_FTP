package lille1.car.asseman_durieux.paserelleFTP;

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
     * Try to access the FTP server without identification, using the getDir command
     */
    @Test
    public void testGetDirAuthentification() {
        RestAssured.expect().
                statusCode(401).
                when().
                get("/PasserelleFTP/rest/dir///tmp/json");
    }
    
    /**
     * Try to access the FTP server without identification, using the getFile command
     */
    @Test
    public void testGetFileAuthentification() {
        RestAssured.expect().
                statusCode(401).
                when().
                get("/PasserelleFTP/rest////dev/null/json");
    }
    
    /**
     * Try to access the FTP server without identification, using the removeFile command
     */
    @Test
    public void testRemoveFileAuthentification() {
        RestAssured.expect().
                statusCode(401).
                when().
                delete("/PasserelleFTP/rest/dir///tmp/json"); // TODO: which file to delete?
    }
    
    /**
     * Try to access the FTP server without identification, using the storeFile command
     */
    @Test
    public void testStoreFileAuthentification() {
        RestAssured.expect().
                statusCode(401).
                when().
                put("/PasserelleFTP/rest/dir///tmp/json"); // TODO
    }
    
    /**
     * Test a correct get command
     */
    @Test
    public void testGetFile() {
        RestAssured.expect().
                statusCode(200).
                when().with().authentication().basic("user", "pass").
                get("/PasserelleFTP/rest/dir///tmp/json"); // TODO
    }
    
    /**
     * Test FileNotFound return code using removeFile on a missing file.
     */
    @Test
    public void testFileNotFound() {
        RestAssured.expect().
                statusCode(404).
                when().with().authentication().basic("user", "pass").
                delete("/PasserelleFTP/rest/tmp/azdlkugzadlugzeme5z7zelh");
    }
    
    // TODO add test methods for correct requests with the other commands.
}