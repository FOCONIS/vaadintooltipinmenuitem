package com.sample.test.slowgrid;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.ComboBoxElement;
import com.vaadin.testbench.elements.MenuBarElement;
import com.vaadin.testbench.elements.WindowElement;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Base class for all our tests, allowing us to change the applicable driver,
 * test URL or other configurations in one place.
 */
public class IT_TestBase extends TestBenchTestCase {
    public static final String baseUrl = "http://localhost:8080/";

    @BeforeClass
    public static void setupClass() {
    	WebDriverManager.chromedriver().setup();
    }
    
    @Before
    public void setUp() throws Exception {
  	
        // Create a new Selenium driver - it is automatically extended to work
        // with TestBench
        driver = new ChromeDriver();
        setDriver(driver);

        // Open the test application URL with the ?restartApplication URL
        // parameter to ensure Vaadin provides us with a fresh UI instance.
        getDriver().get(baseUrl + "?restartApplication");
        // Set a fixed view port of 1024x768 pixels for comparison

        // If you deploy using WTP in Eclipse, this will fail. You should
        // update baseUrl to point to where the app is deployed.
        String pageSource = getDriver().getPageSource();
        String errorMsg = "Application is not available at " + baseUrl + ". Server not started?";
        assertFalse(errorMsg, pageSource.contains("HTTP Status 404")
        		|| pageSource.contains("can't establish a connection to the server"));
    }
    
    @Test
    public void testMenuBarDescription() {
    	MenuBarElement menuBar = getMenuBar();
    	menuBar.clickItem("First item", "Dropdown item");
    	WebElement menuItem = getDriver().findElements(By.className("v-menubar-menuitem-selected")).get(1);
    	System.out.println("Tooltip: " + menuItem.getText());
        new Actions(getDriver()).moveToElement(menuItem).perform();
        // Wait for a small moment for the tooltip to appear (copied from showTooltip())
        try {
            Thread.sleep(1000); // VTooltip.OPEN_DELAY = 750;
        } catch (InterruptedException ignored) {
        }
    	WebElement tooltipElement = driver.findElement(By.className("v-tooltip"));
        String tooltipText = tooltipElement.getText();
    	assertThat(tooltipText, is("Dropdown Item Tooltip"));
    	assertTrue("Dropdown Item Tooltip not visible!", tooltipElement.isDisplayed());
    	menuBar.click(); // close the menu
    	
    	// so far so good ... now let's test the tooltip in the dialog
    	getDialogButton().click();
    	
    	MenuBarElement dialogMenuBar = getMenuBarInDialog();
    	dialogMenuBar.clickItem("First item in dialog", "Dropdown dialog item");
    	WebElement dialogMenuItem = getDriver().findElements(By.className("v-menubar-menuitem-selected")).get(1);
    	System.out.println("Tooltip in Dialog: " + dialogMenuItem.getText());
        new Actions(getDriver()).moveToElement(dialogMenuItem).perform();
        // Wait for a small moment for the tooltip to appear (copied from showTooltip())
        try {
            Thread.sleep(1000); // VTooltip.OPEN_DELAY = 750;
        } catch (InterruptedException ignored) {
        }
        WebElement dialogTooltipElement = driver.findElement(By.className("v-tooltip"));
    	String dialogTooltipText = dialogTooltipElement.getText();
    	assertThat(dialogTooltipText, is("Dropdown dialog item tooltip"));
    	assertTrue("Dialog Dropdown Item Tooltip not visible!", tooltipElement.isDisplayed());
    	dialogMenuBar.click(); // close the menu in the dialog
    }

    @After
    public void tearDown() throws Exception {

        // Calling quit() on the driver closes the test browser.
        // When called like this, the browser is immediately closed on _any_
        // error. If you wish to take a screenshot of the browser at the time
        // the error occurred, you'll need to add the ScreenshotOnFailureRule
        // to your test and remove this call to quit().
    	getDriver().quit();
    }
    
    private MenuBarElement getMenuBar() {
    	return $(MenuBarElement.class).first();
    }
    
    private MenuBarElement getMenuBarInDialog() {
    	return $$(WindowElement.class).caption("DialogWithMenu").$$(MenuBarElement.class).first();
    }
    
    private ButtonElement getDialogButton() {
    	return $(ButtonElement.class).caption("Open Dialog").first();
    }
    
}