package lille1.car.asseman_durieux.paserelleFTP;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Asseman & Durieux
 */
public class PasserelleFTPTest {

  public PasserelleFTPTest() {
  }

  @BeforeClass
  public static void setUpClass() {
    RestAssured.port = 8888;
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
    RestAssured.expect()
            .statusCode(401)
            .given()
            .contentType(ContentType.JSON)
            .when()
            .get("/PasserelleFTP/rest/dir/tmp/");
  }

  /**
   * Test success get dir
   */
  @Test
  public void testGetDir() {
    RestAssured.expect()
            .statusCode(200)
            .given().auth().preemptive()
            .basic("user", "pass")
            .contentType(ContentType.JSON)
            .when()
            .get("/PasserelleFTP/rest/dir/tmp/");
  }

  /**
   * Test success get dir
   */
  @Test
  public void testGetDirNotExist() {
    RestAssured.expect().statusCode(404)
            .given().auth().preemptive()
            .basic("user", "pass")
            .when()
            .contentType(ContentType.HTML)
            .get("/PasserelleFTP/rest/dir/this_folder_doesnt_exist/json");
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

  /**
   * Try to store a file
   */
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
            .get("/PasserelleFTP/rest/tmp/testFile");

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

  /**
   * Test a correct mkdir
   */
  @Test
  public void testRmDir() {

    RestAssured.expect().statusCode(200)
            .given().auth().preemptive()
            .basic("user", "pass")
            .post("/PasserelleFTP/rest/mkdir/tmp/testFolder/");

    RestAssured.expect()
            .statusCode(200)
            .given().auth().preemptive()
            .basic("user", "pass")
            .when()
            .delete("/PasserelleFTP/rest/rmdir/tmp/testFolder/");
  }

  /**
   * Test a correct mkdir
   */
  @Test
  public void testMkDir() throws IOException {

    RestAssured.expect().statusCode(200)
            .given().auth().preemptive()
            .basic("user", "pass")
            .post("/PasserelleFTP/rest/mkdir/tmp/testFolder/");

    RestAssured.expect()
            .statusCode(200)
            .given().auth().preemptive()
            .basic("user", "pass")
            .contentType(ContentType.JSON)
            .when()
            .get("/PasserelleFTP/rest/dir/tmp/testFolder/");

    RestAssured.given().auth().preemptive()
            .basic("user", "pass")
            .delete("/PasserelleFTP/rest/rmdir/tmp/testFolder/");
  }

  /**
   * Test the creation of a dir on an not allowed folder
   */
  @Test
  public void testMkDirNotAllowed() {
    RestAssured.expect().statusCode(500)
            .given().auth().preemptive()
            .basic("user", "pass")
            .post("/PasserelleFTP/rest/mkdir/testFolder/");
  }

  /**
   * Test remove a not empty folder
   */
  @Test
  public void testrkDirNotEmpty() throws IOException {
    RestAssured.expect().statusCode(200)
            .given().auth().preemptive()
            .basic("user", "pass")
            .post("/PasserelleFTP/rest/mkdir/tmp/testFolder/");

    File temp = File.createTempFile("temp-file-name", ".tmp");
    FileWriter fw = new FileWriter(temp.getAbsoluteFile());
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write("tmp file");
    bw.close();
    temp.deleteOnExit();

    RestAssured.given().auth().preemptive()
            .basic("user", "pass")
            .multiPart(temp).when()
            .put("/PasserelleFTP/rest/tmp/testFolder/testFile");

    RestAssured.expect()
            .statusCode(500)
            .given().auth().preemptive()
            .basic("user", "pass")
            .when()
            .delete("/PasserelleFTP/rest/rmdir/tmp/testFolder/");

    RestAssured.given().auth().preemptive()
            .basic("user", "pass")
            .delete("/PasserelleFTP/rest/tmp/testFolder/testFile");

    RestAssured.expect()
            .statusCode(200)
            .given().auth().preemptive()
            .basic("user", "pass")
            .when()
            .delete("/PasserelleFTP/rest/rmdir/tmp/testFolder/");
  }
}
