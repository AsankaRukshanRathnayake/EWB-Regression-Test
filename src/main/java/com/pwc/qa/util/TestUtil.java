package com.pwc.qa.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.pwc.qa.base.TestBase;

public class TestUtil extends TestBase {

	public static long PAGE_LOAD_TIMEOUT = 30;
	public static long IMPLICIT_WAIT = 20;

	// Use relative path inside resources folder
	public static String TESTDATA_SHEET_PATH = "/com/pwc/qa/testdata/PwCTestData.xlsx";
	public static String PROJECT_NAME = "PWCAutomationTest";
	public static boolean TAKE_SCREENSHOT = true;
	public static boolean SLOW_DOWN = true;

	static Workbook book;
	static Sheet sheet;

	// Method to load test data from the Excel file
	public static Object[][] getTestData(String sheetName) {
		InputStream file = null;
		try {
			// Load the file from the classpath
			file = TestUtil.class.getResourceAsStream(TESTDATA_SHEET_PATH);
			if (file == null) {
				throw new FileNotFoundException("Excel file not found in classpath: " + TESTDATA_SHEET_PATH);
			}
			book = WorkbookFactory.create(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get the sheet by name
		sheet = book.getSheet(sheetName);

		// Create the data array to hold the test data
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		// Populate the data array with values from the Excel sheet
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}

	// Method to take a screenshot if enabled
	public static void takeScreenshot(WebDriver driver, String StrProjectName) {
		if (TAKE_SCREENSHOT) {
			String dir = "screenshot";
			String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String time = new SimpleDateFormat("HHmmss").format(new Date());
			String screenShotPath = "";

			if (StrProjectName != null) {
				screenShotPath = "test-output-final" + File.separator + dir + File.separator + date + File.separator + time + ".png";
			} else {
				screenShotPath = StrProjectName + File.separator + "test-output-final" + File.separator + dir + File.separator + date + File.separator + time + ".png";
			}
			System.out.println("screenShotPath=[" + screenShotPath + "]");
			String srcForDisplay = "screenshot/" + date + "/" + time + ".png";

			try {
				if (driver != null) {
					File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(source, new File(screenShotPath));
					screenShotPath = screenShotPath.substring(screenShotPath.indexOf("\\"));
					String log = new File("screenshot").getAbsolutePath();
					// Reporter.log("<br/>" + "<img width='55%' src=" + srcForDisplay + " />" + "<br/>ScreenShot saved in: " + log);
					System.out.println("Screen Captured Successfully!");
				}
			} catch (IOException e) {
				screenShotPath = "Failed to capture screenshot: " + e.getMessage();
			}
		} else {
			System.out.println("Screenshot disabled");
		}
	}

	// Method to add a delay if slow down is enabled
	public static void suiteSlowdown() throws InterruptedException {
		if (SLOW_DOWN) {
			Thread.sleep(3000);
		}
	}
}