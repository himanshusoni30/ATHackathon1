package com.applitools.hackathon.VisualAIRockStar.test;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.applitools.hackathon.VisualAIRockStar.common.CommonActions;
import com.applitools.hackathon.VisualAIRockStar.pages.Chart;
import com.applitools.hackathon.VisualAIRockStar.pages.Home;
import com.applitools.hackathon.VisualAIRockStar.pages.LoginPage;

public class VisualAITestsV1 extends BaseTests {
	
	@BeforeMethod
	public void runVersion1Test() throws IOException {
		BaseTests.setUp("v1");
	}

	@Test(description = "Test: Login Page Labels and Elements.")
	public static void testLoginPageLabelsAndElements() {
		BaseTests.setTheBatch("Version 1: Login UI Elements Test");
		CommonActions.verifyWindowVisually("Test the labels and elements of Login Page.");
	}

	@Test(dataProvider = "CredentialsForLoginFail", priority = 2, description = "Test: Login Fail.")
	public static void testLoginFail(String userName, String password, String test) {
		LoginPage.loginIntoApp(userName, password);
		CommonActions.verifyWindowVisually("Verify login fail. Username: " + userName + "and Password: " + password);
	}

	@Test(dataProvider = "CredentialsForLoginPass", priority = 1, description = "Test: Login Pass.")
	public static void testLoginPass(String userName, String password) {
		BaseTests.setTheBatch("Version 1: Login");
		LoginPage.loginIntoApp(userName, password);
		CommonActions.verifyWindowVisually("Verify login pass with valid credentials.");
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Table Sort.")
	public static void testTableSort(String userName, String password) {
		BaseTests.setTheBatch("Version 1: Table Sort Test");
		LoginPage.loginIntoApp(userName, password);
		eyes.setForceFullPageScreenshot(true);
		CommonActions.verifyWindowVisually("Recent Transaction table before sorting.");
		Home.clickAmountsColumnHeader();
		CommonActions.verifyWindowVisually("Recent Transaction table after sorting.");
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Canvas Chart.")
	public static void testCanvasChart(String userName, String password) {
		BaseTests.setTheBatch("Version 1: Canvas Chart Test");
		LoginPage.loginIntoApp(userName, password);
		eyes.setForceFullPageScreenshot(true);
		Home.clickCompareExpensesLink();
		CommonActions.verifyWindowVisually("Expenses for the year 2017 and 2018");
		Chart.clickShowDataForNextYear();
		CommonActions.verifyWindowVisually("Expenses for the year 2019");
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Dynamic Content.")
	public void testDynamicContent(String userName, String password) throws IOException {
		driver.get("https://demo.applitools.com/hackathon.html?showAd=true");
		BaseTests.setTheBatch("Version 1: Dynamic Content Test");
		LoginPage.loginIntoApp(userName, password);
		CommonActions.verifyWindowVisually("Test Dynamic Contents");
	}
}
