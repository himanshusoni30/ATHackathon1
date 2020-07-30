package com.applitools.hackathon.VisualAIRockStar.test;

import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.applitools.eyes.exceptions.DiffsFoundException;
import com.applitools.hackathon.VisualAIRockStar.common.CommonActions;
import com.applitools.hackathon.VisualAIRockStar.pages.Chart;
import com.applitools.hackathon.VisualAIRockStar.pages.Home;
import com.applitools.hackathon.VisualAIRockStar.pages.LoginPage;

public class VisualAITestsV2 extends BaseTests {
	
	@BeforeMethod
	public void runVersion2Test() throws IOException {
		BaseTests.setUp("v2");
	}

	@Test(description = "Test: Login Page Labels and Elements.")
	public static void testLoginPageLabelsAndElements() {
		BaseTests.setTheBatch("Version 2: Login UI Elements Test");
		CommonActions.verifyWindowVisually("Test the labels and elements of Login Page.");
	}

	@Test(dataProvider = "CredentialsForLoginFail", priority = 4, description = "Test: Login Fail.")
	public static void testLoginFail(String userName, String password, String test) {
		LoginPage.loginIntoApp(userName, password);
		CommonActions.verifyWindowVisually("Verify login fail. Username: " + userName + "and Password: " + password);
	}

	@Test(dataProvider = "CredentialsForLoginPass", priority = 3, description = "Test: Login Pass.")
	public static void testLoginPass(String userName, String password) {
		BaseTests.setTheBatch("Version 2: Login");
		LoginPage.loginIntoApp(userName, password);
		CommonActions.verifyWindowVisually("Verify login pass with valid credentials.");
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Table Sort.")
	public static void testTableSort(String userName, String password) {
		BaseTests.setTheBatch("Version 2: Table Sort Test");
		LoginPage.loginIntoApp(userName, password);
		eyes.setForceFullPageScreenshot(true);
		CommonActions.verifyWindowVisually("Recent Transaction table before sorting.");
		Home.clickAmountsColumnHeader();
		CommonActions.verifyWindowVisually("Recent Transaction table after sorting.");
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Canvas Chart.")
	public static void testCanvasChart(String userName, String password) {
		BaseTests.setTheBatch("Version 2: Canvas Chart Test");
		LoginPage.loginIntoApp(userName, password);
		eyes.setForceFullPageScreenshot(true);
		Home.clickCompareExpensesLink();
		try {
			CommonActions.verifyWindowVisually("Expenses for the year 2017 and 2018");
		}catch(DiffsFoundException d) {
			System.out.println("Difference found exception: "+d);
			Reporter.log("Exception Found"+d);
		}finally {
			Chart.clickShowDataForNextYear();
			CommonActions.verifyWindowVisually("Expenses for the year 2019");
		}
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Dynamic Content.")
	public void testDynamicContent(String userName, String password) throws IOException {
		driver.get("https://demo.applitools.com/hackathonV2.html?showAd=true");
		BaseTests.setTheBatch("Version 2: Dynamic Content Test");
		LoginPage.loginIntoApp(userName, password);
		CommonActions.verifyWindowVisually("Test Dynamic Contents");
	}
}
