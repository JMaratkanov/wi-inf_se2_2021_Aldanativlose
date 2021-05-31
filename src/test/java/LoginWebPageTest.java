import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoginWebPageTest {

    private static WebDriver driver = null;

    public LoginWebPageTest(){

    }

    @BeforeClass
    public static void setUpClass(){
        System.setProperty("webdriver.gecko.driver","D:\\Users\\okorian\\Downloads\\geckodriver-v0.29.1-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test
    public void testLoginWithWrongEmailAndCorrectPassword(){
        // Go to Login page
        driver.get("http://localhost:8080/login");

        // Set Email
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-text-field/input")).sendKeys("MaxMuster");

        // Set Password
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-password-field/input")).sendKeys(("123456"));

        // Click Login
        driver.findElement( (By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"))).click();

        // Check Errormassage
        WebElement element = driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper"));
        String pageText = element.getText();

        assertEquals(true, pageText.contains("Incorrect username or password"));
        assertEquals(true, pageText.contains("Check that you have entered the correct username and password and try again."));
    }

    @Test
    public void testLoginWithWrongEmailAndWrongPassword(){
        // Go to Login page
        driver.get("http://localhost:8080/login");

        // Set Email
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-text-field/input")).sendKeys("MaxMuster");

        // Set Password
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-password-field/input")).sendKeys(("123"));

        // Click Login
        driver.findElement( (By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"))).click();

        // Check Errormassage
        WebElement element = driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper"));
        String pageText = element.getText();

        assertEquals(true, pageText.contains("Incorrect username or password"));
        assertEquals(true, pageText.contains("Check that you have entered the correct username and password and try again."));
    }

    @Test
    public void testLoginWithCorrectEmailAndWrongPassword(){
        // Go to Login page
        driver.get("http://localhost:8080/login");

        // Set Email
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-text-field/input")).sendKeys("MaxMuster@hbrs.de");

        // Set Password
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-password-field/input")).sendKeys(("123"));

        // Click Login
        driver.findElement( (By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"))).click();

        // Check Errormassage
        WebElement element = driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper"));
        String pageText = element.getText();

        assertEquals(true, pageText.contains("Incorrect username or password"));
        assertEquals(true, pageText.contains("Check that you have entered the correct username and password and try again."));
    }
    /*
    @Test
    public void testLoginWithCorrectEmailAndCorrectPassword(){
        // Go to Login page
        driver.get("http://localhost:8080/login");

        // Set Email
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-text-field/input")).sendKeys("MaxMuster@hbrs.de");

        // Set Password
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-password-field/input")).sendKeys(("123456"));

        // Click Login
        driver.findElement( (By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button"))).click();

    }
    */
    @AfterClass
    public static void tearDownClass(){
        driver.quit();
    }
}