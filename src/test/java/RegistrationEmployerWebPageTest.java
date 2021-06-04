import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RegistrationEmployerWebPageTest {
    private static WebDriver driver = null;

    public RegistrationEmployerWebPageTest(){

    }

    @BeforeClass
    public static void setUpClass(){
        // Muss jeder für sich selber setzten!!!
        //System.setProperty("webdriver.gecko.driver","C:\\Users\\Deadman\\IdeaProjects\\geckodriver.exe");
        System.setProperty("webdriver.gecko.driver","D:\\Users\\okorian\\Datein\\Uni\\SE2\\geckodriver-v0.29.1-win64\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test
    public void testRegistrationEmployerAllCorrect(){
        // Go to Coll@HBRS page
        driver.get("http://sepp-test.inf.h-brs.de:8080/CollHBRS-Aldanativlose_Haarspaldarei/");

        // Press on Registrieren
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[1]/vaadin-tabs/vaadin-tab[1]/a")).click();

        // Press on Als Unternehmen registrieren
        driver.findElement(By.xpath("/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-button[2]")).click();

        // Enter Firmenname
        driver.findElement((By.xpath("/html/body/vaadin-app-layout/div/vaadin-form-layout/vaadin-text-field[1]//div/div[1]/slot[2]/input"))).sendKeys("asdgf");
    }

    @AfterClass
    public static void tearDownClass(){
        //driver.quit();
    }
}
