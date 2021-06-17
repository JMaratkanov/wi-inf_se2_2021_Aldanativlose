import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RegistrationStudentWebPageTest {

    private static WebDriver driver = null;

    public RegistrationStudentWebPageTest(){

    }

    @BeforeClass
    public static void setUpClass(){
        // Muss jeder für sich selber setzten!!!
        //System.setProperty("webdriver.gecko.driver","C:\\Users\\Deadman\\IdeaProjects\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver","D:\\Users\\okorian\\Datein\\Uni\\SE2\\geckodriver-v0.29.1-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test
    public void testRegistrationStudentAllCorrect(){
        // Go to Coll@HBRS page
        driver.get("http://sepp-test.inf.h-brs.de:8080/CollHBRS-Aldanativlose_Haarspaldarei/");

        // Press on Registrieren
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[1]/vaadin-tabs/vaadin-tab[1]/a")).click();

        // Press on Als Student registrieren
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-button[1]")).click();
        /*
        // Enter Email
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/div/vaadin-form-layout/vaadin-email-field[1]//div/div[1]/slot[2]/input")).sendKeys("MaxMuster@hbrs.de");

        // Enter Email confirm
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/div/vaadin-form-layout/vaadin-email-field[2]//div/div[1]/slot[2]/input")).sendKeys("MaxMuster@hbrs.de");

        // Enter Password
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/div/vaadin-form-layout/vaadin-password-field[1]//div/div[1]/slot[2]/input")).sendKeys("123456");

        // Enter Password confirm
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/div/vaadin-form-layout/vaadin-password-field[2]//div/div[1]/slot[2]/input")).sendKeys("123456");

        // Press Registrieren
        //driver.findElement(By.xpath("/html/body/vaadin-app-layout/div/vaadin-horizontal-layout/vaadin-button[1]//button")).click();
        */
        //TODO User Loeschen
    }

    @AfterClass
    public static void tearDownClass(){
        driver.quit();
    }

}
