package com.applitools.hackathon.VisualAIRockStar.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import com.applitools.hackathon.VisualAIRockStar.common.CommonActions;
import com.applitools.hackathon.VisualAIRockStar.test.BaseTests;

public class FlashSales extends BaseTests{
	private static final Logger LOGGER = Logger.getLogger(FlashSales.class);
	public static By LOC_LOGO1 = By.cssSelector("div[id='flashSale']");
	public static By LOC_LOGO2 = By.cssSelector("div[id='flashSale2']");
	
	static Dimension dimension = null;
	static int wlogo1=170;
	static int hlogo1=124;
	
	static int wlogo2=181;
	static int hlogo2=124;
	
	public static void checkGif1Dimensions(SoftAssert asrt) {
		Reporter.log("Check if gif exists or not. Also check the dimensions.", true);
		if(CommonActions.checkElementExist(driver, LOC_LOGO1)) {
			Reporter.log("Check the dimensions of gif.", true);
			Rectangle logo = driver.findElement(LOC_LOGO1).getRect();
			dimension = logo.getDimension();
			CommonActions.verifyNumbers(wlogo1, dimension.getWidth(),asrt);
			CommonActions.verifyNumbers(hlogo1, dimension.getHeight(),asrt);
		}else {
			LOGGER.fatal("FlashSale.gif does not exist.");
			Reporter.log("FlashSale.gif does not exist.", true);
		}
	}
	
	public static void checkGif2Dimensions(SoftAssert asrt) {
		Reporter.log("Check if gif exists or not. Also check the dimensions.", true);
		if(CommonActions.checkElementExist(driver, LOC_LOGO2)) {
			Reporter.log("Check the dimensions of gif.", true);
			Rectangle logo = driver.findElement(LOC_LOGO2).getRect();
			dimension = logo.getDimension();
			CommonActions.verifyNumbers(wlogo2, dimension.getWidth(),asrt);
			CommonActions.verifyNumbers(hlogo2, dimension.getHeight(),asrt);
		}else {
			LOGGER.fatal("FlashSale2.gif does not exist.");
			Reporter.log("FlashSale2.gif does not exist.", true);
		}
	}
}
