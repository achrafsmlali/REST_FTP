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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
     * Try to access the FTP server without identification, using the getDir
     * command
     */
    @Test
    public void testGetDirAuthentification() {
        RestAssured.expect().
                statusCode(401).
                when().
                get("/PasserelleFTP/rest/dir//tmp/json");
    }

    /**
     * Try to access the FTP server without identification, using the getFile
     * command
     */
    @Test
    public void testGetFileAuthentification() {
        RestAssured.expect().
                statusCode(401).
                when().
                get("/PasserelleFTP/rest/dev/null/json");
    }

    /**
     * Try to access the FTP server without identification, using the removeFile
     * command
     */
    @Test
    public void testRemoveFileAuthentification() throws IOException {
        File temp = File.createTempFile("temp-file-name", ".tmp");
        FileWriter fw = new FileWriter(temp.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("tmp file");
        bw.close();
        temp.deleteOnExit();

        RestAssured
                .given().auth().preemptive()
                .basic("user", "pass")
                .multiPart(temp).when()
                .put("/PasserelleFTP/rest/tmp/testFile");

        RestAssured.expect().statusCode(401)
                .given().delete("/PasserelleFTP/rest/tmp/testFile");

        RestAssured.given().auth().preemptive()
                .basic("user", "pass")
                .delete("/PasserelleFTP/rest/tmp/testFile");
    }

    /**
     * Try to access the FTP server without identification, using the removeFile
     * command
     */
    @Test
    public void testRemoveFile() throws IOException {
        File temp = File.createTempFile("temp-file-name", ".tmp");
        FileWriter fw = new FileWriter(temp.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("tmp file");
        bw.close();
        temp.deleteOnExit();

        RestAssured
                .given().auth().preemptive()
                .basic("user", "pass")
                .multiPart(temp).when()
                .put("/PasserelleFTP/rest/tmp/testFile");

        RestAssured.expect().statusCode(200)
                .given().auth().preemptive()
                .basic("user", "pass")
                .delete("/PasserelleFTP/rest/tmp/testFile");
    }

    /**
     * Try to access the FTP server without identification, using the storeFile
     * command
     */
    @Test
    public void testStoreFileAuthentification() throws IOException {
        File temp = File.createTempFile("temp-file-name", ".tmp");
        FileWriter fw = new FileWriter(temp.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("tmp file");
        bw.close();
        temp.deleteOnExit();

        RestAssured.expect().statusCode(401)
                .given().multiPart(temp).when()
                .put("/PasserelleFTP/rest/tmp/testFile");
    }

    @Test
    public void testStoreFile() throws IOException {
        File temp = File.createTempFile("temp-file-name", ".tmp");
        FileWriter fw = new FileWriter(temp.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("tmp file");
        bw.close();
        temp.deleteOnExit();

        RestAssured.expect().statusCode(200)
                .given().auth().preemptive()
                .basic("user", "pass")
                .multiPart(temp).when()
                .put("/PasserelleFTP/rest/tmp/testFile");

        RestAssured.given().auth().preemptive()
                .basic("user", "pass")
                .delete("/PasserelleFTP/rest/tmp/testFile");
    }

    /**
     * Test a correct get command
     */
    @Test
    public void testGetFile() throws IOException {
        File temp = File.createTempFile("temp-file-name", ".tmp");
        FileWriter fw = new FileWriter(temp.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("tmp file");
        bw.close();
        temp.deleteOnExit();

        RestAssured.given().auth().preemptive()
                .basic("user", "pass")
                .multiPart(temp).when()
                .put("/PasserelleFTP/rest/tmp/testFile");

        RestAssured.expect().statusCode(200)
                .given().auth().preemptive()
                .basic("user", "pass")
                .get("/PasserelleFTP/rest/tmp/testFile"); // TODO

        RestAssured.given().auth().preemptive()
                .basic("user", "pass")
                .delete("/PasserelleFTP/rest/tmp/testFile");
    }

    /**
     * Test FileNotFound return code using removeFile on a missing file.
     */
    @Test
    public void testFileNotFound() {

        RestAssured.expect().given()
                .auth().preemptive()
                .basic("user", "pass")
                .when().get("/PasserelleFTP/rest/tmp/azdlkugzadlugzeme5z7zelh").then()
                .statusCode(404);

    }
    // TODO add test methods for correct requests with the other commands.
}
