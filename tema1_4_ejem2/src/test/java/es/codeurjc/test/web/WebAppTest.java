package es.codeurjc.test.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.ChromeDriverManager;

public class WebAppTest {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		ChromeDriverManager.getInstance().setup();
		WebApp.start();
	}
	
	@AfterClass
	public static void teardownClass() {
		WebApp.stop();
	}

	@Before
	public void setupTest() throws MalformedURLException {

		String eusURL = System.getenv("ET_EUS_API");
	    if (eusURL == null) {
	    	//Local Google Chrome
	        driver = new ChromeDriver();
	    } else {
	    	//Selenium Grid in ElasTest
//	        DesiredCapabilities caps = new DesiredCapabilities();
//	        caps.setBrowserName("chrome");
//	        caps.setCapability(ChromeOptions.CAPABILITY, new ChromeOptions());
	    	DesiredCapabilities caps = DesiredCapabilities.chrome();
	        driver = new RemoteWebDriver(new URL(eusURL), caps);
	    }
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test() {
		
		driver.get("http://localhost:8080/");

		String newTitle = "MessageTitle";
		String newBody = "MessageBody";

		driver.findElement(By.id("title-input")).sendKeys(newTitle);
		driver.findElement(By.id("body-input")).sendKeys(newBody);

		driver.findElement(By.id("submit")).click();

		String title = driver.findElement(By.id("title")).getText();
		String body = driver.findElement(By.id("body")).getText();

		assertThat(title).isEqualTo(newTitle);
		assertThat(body).isEqualTo(newBody);
	}

}
