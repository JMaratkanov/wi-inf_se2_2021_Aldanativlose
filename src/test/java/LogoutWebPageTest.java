import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutWebPageTest {

    private static WebDriver driver = null;

    public LogoutWebPageTest(){

    }

    @BeforeClass
    public static void setUpClass(){
        // Muss jeder für sich selber setzten!!!
        //System.setProperty("webdriver.gecko.driver","C:\\Users\\Deadman\\IdeaProjects\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver","D:\\Users\\okorian\\Datein\\Uni\\SE2\\geckodriver-v0.29.1-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @AfterClass
    public static void tearDownClass(){
        //driver.quit();
    }

    @Test
    public void testLogout(){
        // Go to Login page
        driver.get("http://localhost:8080/login");

        // Set Email
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-text-field/input")).sendKeys("demo");

        // Set Password
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-password-field/input")).sendKeys(("demo"));

        // Click Login
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper/form/vaadin-button")).click();

        //Click Logout
        driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button")).click();

        //Check for loginpage
        WebElement element = driver.findElement( By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-login-form/vaadin-login-form-wrapper"));
        String pageText = element.getText();

        assertEquals(true, pageText.contains("Anmeldung"));
    }
}
