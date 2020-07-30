package com.applitools.hackathon.VisualAIRockStar.pages;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.applitools.hackathon.VisualAIRockStar.common.CommonActions;
import com.applitools.hackathon.VisualAIRockStar.test.BaseTests;

public class Home extends BaseTests {
	protected static By LOC_TABLE_HEADER = By.xpath("//table[@id='transactionsTable']//thead//tr//th");
	protected static By LOC_AMOUNTS_COL = By.xpath("//table[@id='transactionsTable']//thead//tr//th[5]");
	protected static By LOC_TABLE_BODY_ROWS = By.xpath("//table[@id='transactionsTable']//tbody//tr");
	protected static By LOC_COMPARE_EXPENSES = By.id("showExpensesChart");

	public static void verifyTableHeaders(SoftAssert asrt) {
		List<String> expectedHeader = new ArrayList<String>();
		expectedHeader.add("STATUS");
		expectedHeader.add("DATE");
		expectedHeader.add("DESCRIPTION");
		expectedHeader.add("CATEGORY");
		expectedHeader.add("AMOUNT");

		List<WebElement> actualHeader = driver.findElements(LOC_TABLE_HEADER);
		for (int i = 0; i < actualHeader.size(); i++) {
			CommonActions.verifyText(expectedHeader.get(i), actualHeader.get(i).getText(), asrt);
		}
	}

	public static void clickAmountsAndSorting(SoftAssert asrt) {

		Map<String, ArrayList<String>> tableDataBefore = Home.storeTableDataInMap();

		List<String> amountsBefore = Home.storeAmountsColumn();
		List<String> sortedList = Home.convertDoubleAndSort(amountsBefore);

		CommonActions.click(driver, LOC_AMOUNTS_COL);

		List<String> amountsAfter = Home.storeAmountsColumn();

		Map<String, ArrayList<String>> tableDataAfter = Home.storeTableDataInMap();
		Reporter.log("Verify row data is intact after sorting.", true);
		for (String key : tableDataAfter.keySet()) {
			List<String> rowDataBefore = tableDataBefore.get(key);
			List<String> rowDataAfter = tableDataAfter.get(key);
			asrt.assertEquals(rowDataAfter, rowDataBefore);
		}

		Reporter.log("Compare results of Amounts columns before and after sorting.", true);
		for (int i = 0; i < amountsAfter.size(); i++) {
			CommonActions.verifyText(amountsBefore.get(i), amountsAfter.get(i), asrt);
		}
		asrt.assertEquals(sortedList, amountsBefore);
	}

	public static Map<String, ArrayList<String>> storeTableDataInMap() {
		Map<String, ArrayList<String>> rowsData = new HashMap<String, ArrayList<String>>();

		List<WebElement> rows = driver.findElements(LOC_TABLE_BODY_ROWS);
		for (WebElement e : rows) {
			ArrayList<String> colData = new ArrayList<String>();
			List<WebElement> cols = e.findElements(By.tagName("td"));
			for (WebElement c : cols) {
				colData.add(c.getText());
			}
			rowsData.put(colData.get(2), colData);
		}
		return rowsData;
	}

	public static List<String> storeAmountsColumn() {
		List<String> amounts = new ArrayList<String>();
		List<WebElement> rows = driver.findElements(LOC_TABLE_BODY_ROWS);
		for (int i = 0; i < rows.size(); i++) {
			String value = driver
					.findElement(By.xpath("//table[@id='transactionsTable']//tbody//tr[" + (i + 1) + "]//td[5]"))
					.getText();
			String replaceSpace = value.replaceAll(" ", "");
			if (replaceSpace.contains(",")) {
				replaceSpace = replaceSpace.replaceFirst(",", "");
			}
			String splitNumbers = replaceSpace.substring(0, replaceSpace.length() - 3);
			amounts.add(splitNumbers);
		}
		return amounts;
	}

	public static List<String> convertDoubleAndSort(List<String> list) {
		DecimalFormat df2 = new DecimalFormat("#.00");
		List<Double> doubleList = new ArrayList<Double>();
		for (int i = 0; i < list.size(); i++) {
			double d = Double.parseDouble(list.get(i));
			doubleList.add(d);
		}
		Collections.sort(doubleList);

		for (int i = 0; i < doubleList.size(); i++) {
			String val = df2.format(doubleList.get(i));
			list.remove(i);
			if (val.contains("-")) {
				list.add(i, val);
			} else {
				list.add(i, "+" + val);
			}
		}
		return list;
	}

	public static void clickAmountsColumnHeader() {
		CommonActions.click(driver, LOC_AMOUNTS_COL);
	}

	public static void clickCompareExpensesLink() {
		CommonActions.click(driver, LOC_COMPARE_EXPENSES);
	}

	public static void verifyCompareExpensesText(SoftAssert asrt) {
		asrt.assertEquals("Compare Expenses", driver.findElement(LOC_COMPARE_EXPENSES).getText());
	}

}
