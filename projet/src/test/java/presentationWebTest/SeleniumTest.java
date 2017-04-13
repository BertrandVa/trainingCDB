package presentationWebTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ClientActions;

public class SeleniumTest {

    private WebDriverBackedSelenium selenium;
    
    
    @Ignore
    @Before
    public void setUp() throws Exception {
        String baseUrl = "http://localhost:8080/computer-database";
        System.setProperty("webdriver.chrome.driver", "/home/excilys/workspace/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Ignore
    @Test
    public void testSelenium() throws Exception {
        
        /*
         * vérification de la page addComputer
         */
        selenium.open("/dashboard");
        selenium.click("id=addComputer");
        selenium.waitForPageToLoad("100");
        checkPageAddComputer();
        selenium.click("id=cancel");
        selenium.waitForPageToLoad("100");
        selenium.open("/editComputer?id=574");
        selenium.waitForPageToLoad("100");
        checkPageEditComputer(574);
        selenium.click("id=cancel");
    }
    
    @Ignore 
    @Test
    private void checkPageAddComputer() {
        checkElement("computerName", null);
        checkElement("introduce", null);
        checkElement("discontinued", null);
        checkElement("company", "0");        
    }
    
    @Ignore
    @Test
    private void checkPageEditComputer(long id) {
        Computer computer = ClientActions.showComputerDetails(id);
        checkElement("computerName", computer.getName());
        checkElement("introduce", computer.getIntroduceDate().toString());
        checkElement("discontinued", computer.getDiscontinuedDate().toString());
        checkElement("company", computer.getManufacturer().getName());        
    }
    
    @Ignore
    @Test
    public void checkElement(String elementId, String expected) {
        System.setProperty("webdriver.chrome.driver", "/home/excilys/workspace/drivers/chromedriver");
        WebDriver driver = new ChromeDriver();
        assertEquals(driver.findElement(By.id(elementId)).getText(), expected );
    }


    @Ignore
    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }

}
