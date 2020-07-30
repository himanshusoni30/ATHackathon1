package com.applitools.hackathon.VisualAIRockStar.test;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.applitools.hackathon.VisualAIRockStar.pages.Chart;
import com.applitools.hackathon.VisualAIRockStar.pages.FlashSales;
import com.applitools.hackathon.VisualAIRockStar.pages.Home;
import com.applitools.hackathon.VisualAIRockStar.pages.LoginPage;

public class TraditionalTestsV1 extends BaseTests {

	private static SoftAssert asrt;
	
	@BeforeMethod
	public void runVersion1Test() throws IOException {
		BaseTests.setUp("v1");
	}

	@Test(description = "Test: Login Page Labels and Elements.")
	public void testLoginPageLabelsAndElements() {
		asrt = new SoftAssert();
		LoginPage.checkLoginPageLabels(asrt);
		LoginPage.checkLoginPageElements(asrt);
		asrt.assertAll();
	}

	@Test(dataProvider = "CredentialsForLoginFail", description = "Test: Login Fail.")
	public void testLoginFailed(String userName, String password, String message) {
		asrt = new SoftAssert();
		LoginPage.loginIntoApp(userName, password);
		LoginPage.checkErrorMessages(message, asrt);
		asrt.assertAll();
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Login Pass")
	public void testLoginSuccess(String userName, String password) {
		asrt = new SoftAssert();
		LoginPage.loginIntoApp(userName, password);
		asrt.assertAll();
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Table Sort")
	public void testTableSort(String userName, String password) {
		asrt = new SoftAssert();
		LoginPage.loginIntoApp(userName, password);
		Home.clickAmountsAndSorting(asrt);
		asrt.assertAll();
	}

	/**
	 * UNABLE TO VALIDATE DATA FOR YEAR 2019 AND FURTHER.
	 * 
	 * @throws IOException
	 */
	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Canvas Chart.")
	public void testCanvasChart(String userName, String password) throws IOException {
		asrt = new SoftAssert();
		LoginPage.loginIntoApp(userName, password);
		Home.clickCompareExpensesLink();
		Chart.verifyYearAndBarData(asrt);
		asrt.assertAll();
	}

	@Test(dataProvider = "CredentialsForLoginPass", description = "Test: Dynamic Content.")
	public void testDynamicContent(String userName, String password) throws IOException {
		asrt = new SoftAssert();
		driver.get("https://demo.applitools.com/hackathonV2.html?showAd=true");
		LoginPage.loginIntoApp(userName, password);
		FlashSales.checkGif1Dimensions(asrt);
		FlashSales.checkGif2Dimensions(asrt);
		asrt.assertAll();
	}
}
