package com.applitools.hackathon.VisualAIRockStar.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;

public class BaseTests {
	protected static WebDriver driver;
	public static Eyes eyes;
	protected static SoftAssert asrt;
	protected static Configuration suiteConfig;
	protected static BatchInfo batch;

	@DataProvider(name = "CredentialsForLoginFail")

	public static Object[][] credentials() {

		return new Object[][] { { "", "", "Both Username and Password must be present" },
				{ "XXXXXXXXX", "", "Password must be present" },
				{ "", "xxxxxxxxxxx", "Username must be present" } };
	}

	@DataProvider(name = "CredentialsForLoginPass")

	public static Object[][] validcred() {
		return new Object[][] { { "XXXXXXXXX", "xxxxxxxxxxx" } };
	}
	
	@BeforeSuite
	public static void intiate() throws IOException {
		Properties props = System.getProperties();
		try {
			props.load(new FileInputStream(new File("src/test/resources/test.properties")));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		initiateEyes();
	}

	public static void setUp(String version) throws IOException {
		System.getProperty("webdriver.chrome.driver");
		driver = new ChromeDriver();
		setDriverForVersions(version);
		Reporter.log("********** Test Execution Begin ************", true);
	}

	@AfterMethod
	public static void tearDown() {
		Reporter.log("********** Test Execution End ************", true);
		driver.quit();
	}

	@AfterSuite()
	public static void afterSuite()
	{
		eyes.close();
		eyes.abortIfNotClosed();
	}

	private static void initiateEyes() {
		eyes = new Eyes();
		eyes.setApiKey(System.getProperty("applitools.api.key"));
	}

	protected static void setTheBatch(String batchName) {
		batch = new BatchInfo(batchName);
		eyes.setBatch(batch);
	}
	
	private static void setDriverForVersions(String version) {
		if(version.equalsIgnoreCase("v1")) {
			driver.get(System.getProperty("site.url.v1"));
		}
		else if(version.equalsIgnoreCase("v2")) {
			driver.get(System.getProperty("site.url.v2"));
		}
		driver.manage().window().maximize();
	}
}
