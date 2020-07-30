package com.applitools.hackathon.VisualAIRockStar.pages;

import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.applitools.hackathon.VisualAIRockStar.common.CommonActions;
import com.applitools.hackathon.VisualAIRockStar.test.BaseTests;

public class LoginPage extends BaseTests {
	// Locators for Login Page
	protected static By LOC_LOGIN_PAGE_LOGO = By.xpath("//a[@href='index.html']//img[contains(@src,'logo-big')]");
	protected static By LOC_LOGIN_PAGE_TITLE = By.xpath("//h4[@class='auth-header']");
	protected static By LOC_USERNAME_LABEL = By.xpath("//div[@class='form-group']//label[contains(text(),'U')]");
	protected static By LOC_USERNAME_TEXTBOX = By.xpath("//div[@class='form-group']//input[@id='username']");
	protected static By LOC_PASSWORD_LABEL = By.xpath("//div[@class='form-group']//label[contains(text(),'P')]");
	protected static By LOC_PASSWORD_TEXTBOX = By.xpath("//div[@class='form-group']//input[@id='password']");
	protected static By LOC_LOGIN_BUTTON = By.xpath("//div[@class='buttons-w']//button[@id='log-in']");
	protected static By LOC_REMEMBER_ME_LABEL = By
			.xpath("//label[@class='form-check-label' and contains(text(),'Remember')]");
	protected static By LOC_REMEMBER_ME_CHECKBOX = By
			.xpath("//label[@class='form-check-label' and contains(text(),'Remember')]//input[@type='checkbox']");
	protected static By LOC_WARNING = By.xpath("//div[@class='alert alert-warning' and @role='alert']");
	protected static By LOC_TWITTER_IMG = By.xpath("//img[contains(@src,'twitter')]");
	protected static By LOC_FACEBOOK_IMG = By.xpath("//img[contains(@src,'facebook')]");
	protected static By LOC_LINKEDIN_IMG = By.xpath("//img[contains(@src,'linkedin')]");

	/**
	 * Method to verify the labels and text fields on Login Page
	 */
	public static void checkLoginPageLabels(SoftAssert asrt) {
		String actualLoginPageTitle = driver.findElement(LOC_LOGIN_PAGE_TITLE).getText();
		String actualUserNameLabel = driver.findElement(LOC_USERNAME_LABEL).getText();
		String actualPasswordLabel = driver.findElement(LOC_PASSWORD_LABEL).getText();
		String actualLoginButton = driver.findElement(LOC_LOGIN_BUTTON).getText();
		String actualRememberMeLabel = driver.findElement(LOC_REMEMBER_ME_LABEL).getText();
		String actualUserNameTextBoxText = driver.findElement(LOC_USERNAME_TEXTBOX).getAttribute("placeholder");
		String actualPasswordTextBoxText = driver.findElement(LOC_PASSWORD_TEXTBOX).getAttribute("placeholder");

		CommonActions.verifyText("Login Form", actualLoginPageTitle, asrt);
		CommonActions.verifyText("Username", actualUserNameLabel, asrt);
		CommonActions.verifyText("Password", actualPasswordLabel, asrt);
		CommonActions.verifyText("Log In", actualLoginButton, asrt);
		CommonActions.verifyText("Remember Me", actualRememberMeLabel, asrt);
		CommonActions.verifyText("Enter your username", actualUserNameTextBoxText, asrt);
		CommonActions.verifyText("Enter your password", actualPasswordTextBoxText, asrt);
	}

	/**
	 * Method to verify that images, links, text-box, check-box and buttons exists
	 * on Login Page
	 */
	public static void checkLoginPageElements(SoftAssert asrt) {
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_LOGIN_PAGE_LOGO));
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_USERNAME_TEXTBOX));
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_PASSWORD_TEXTBOX));
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_REMEMBER_ME_CHECKBOX));
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_TWITTER_IMG));
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_FACEBOOK_IMG));
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_LINKEDIN_IMG));
		asrt.assertTrue(CommonActions.checkElementExist(driver, LOC_LOGIN_BUTTON));
	}

	public static void loginIntoApp(String userName, String password) {
		CommonActions.fillText(driver, LOC_USERNAME_TEXTBOX, userName);
		CommonActions.fillText(driver, LOC_PASSWORD_TEXTBOX, password);
		CommonActions.click(driver, LOC_LOGIN_BUTTON);
	}

	public static void checkErrorMessages(String message, SoftAssert asrt) {
		if (CommonActions.checkElementExist(driver, LOC_WARNING)) {
			String actualResult = driver.findElement(LOC_WARNING).getText();
			CommonActions.verifyText(message, actualResult, asrt);
		}
	}
}
