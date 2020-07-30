package com.applitools.hackathon.VisualAIRockStar.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.applitools.hackathon.VisualAIRockStar.common.CommonActions;
import com.applitools.hackathon.VisualAIRockStar.test.BaseTests;

public class Chart extends BaseTests {
	/**
	 * Since the data was fixed for years 2017 and 2018 retrieving data from
	 * <script> tag was possible. It took almost 2-3 hrs to come up with approach
	 * for TraditionalTests.
	 */

	protected static By LOC_SHOW_DATA_BUTTON = By.id("addDataset");

	static String[] month = { "'January'", "'February'", "'March'", "'April'", "'May'", "'June'", "'July'" };
	static String[] years = { "'2017'", "'2018'" };
	static int[] barforYear1 = { 10, 20, 30, 40, 50, 60, 70 };
	static int[] barforYear2 = { 8, 9, -10, 10, 40, 60, 40 };
	static Document doc;

	public static String connectURLRetrieveContents() throws IOException {
		doc = Jsoup.connect("https://demo.applitools.com/hackathonChart.html").get();
		Element ele = doc.select("script").get(2);
		String text = ele.html();
		String singleLine = text.replaceAll("\n|\t", "");
		String[] arr = singleLine.split(";");
		return arr[2];
	}

	public static List<String> returnBarChartData(String text, String regex) {
		final Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);// label: (.+?), //"data: \\[(.+?)\\]}"
		final Matcher matcher = pattern.matcher(text);
		List<String> list = new ArrayList<String>();
		while (matcher.find()) {
			list.add(matcher.group(1));
		}
		return list;
	}

	public static void verifyYearAndBarData(SoftAssert asrt) throws IOException {
		List<String> monthList = returnBarChartData(connectURLRetrieveContents(), "labels: \\[(.+?)\\]");
		List<String> yearsList = returnBarChartData(connectURLRetrieveContents(), "label: (.+?),");
		List<String> barData = returnBarChartData(connectURLRetrieveContents(), "data: \\[(.+?)\\]}");
		Reporter.log("Compare expenses based on years.", true);
		for (int i = 0; i < yearsList.size(); i++) {
			CommonActions.verifyText(years[i], yearsList.get(i).trim(), asrt);
			String[] d1 = barData.get(0).split(",");
			String[] d2 = barData.get(1).split(",");
			String[] m = monthList.get(0).split(",");
			for (int j = 0; j < d1.length; j++) {
				asrt.assertEquals(barforYear1[j], Integer.parseInt(d1[j].trim()));
				asrt.assertEquals(barforYear2[j], Integer.parseInt(d2[j].trim()));
				asrt.assertEquals(month[j], m[j].trim());
			}
		}
		CommonActions.click(driver, LOC_SHOW_DATA_BUTTON);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public static void clickShowDataForNextYear() {
		CommonActions.click(driver, LOC_SHOW_DATA_BUTTON);
	}
}
