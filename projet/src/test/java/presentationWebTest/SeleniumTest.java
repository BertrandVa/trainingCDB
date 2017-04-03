package presentationWebTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ClientActions;

public class SeleniumTest {

    private WebDriverBackedSelenium selenium;
    WebDriver driver = new ChromeDriver();

    @Before
    public void setUp() throws Exception {
        String baseUrl = "http://localhost:8080/computer-database";
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

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
    
    private void checkPageAddComputer() {
        checkElement("computerName", null);
        checkElement("introduce", null);
        checkElement("discontinued", null);
        checkElement("company", "0");        
    }
    
    private void checkPageEditComputer(long id) {
        Computer computer = ClientActions.showComputerDetails(id);
        checkElement("computerName", computer.getName());
        checkElement("introduce", computer.getIntroduceDate().toString());
        checkElement("discontinued", computer.getDiscontinuedDate().toString());
        checkElement("company", computer.getManufacturer().getName());        
    }
    
    private void checkElement(String elementId, String expected) {
        assertEquals(driver.findElement(By.id(elementId)).getText(), expected );
    }


    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }

}
