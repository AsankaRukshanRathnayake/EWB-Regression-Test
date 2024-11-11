package com.pwc.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.pwc.qa.util.TestUtil;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;

	public TestBase() {
		try {
			prop = new Properties();
			// Using ClassLoader to load the config file
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("com/pwc/qa/config/config.properties");

			// Check if the file is found, otherwise throw an exception
			if (inputStream == null) {
				throw new FileNotFoundException("Property file 'config.properties' not found in the classpath");
			}

			// Load the properties from the file
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

}
public static void initialization() {
	String browserName = prop.getProperty("browser");
	if(browserName.equals("chrome")) {
		System.setProperty("webdriver.chrome.driver", "D:\\Rajneesh\\Software\\Selenium_Drivers\\Chrome_\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
	
	driver.get(prop.getProperty("url"));
}
	
	/*
	public static WebDriver driver;
	public static Properties prop;
	
	public TestBase() {
		try {
		prop = new Properties();
			FileInputStream ip = new FileInputStream("D:\\PWC_Project\\EWB-Regression-Test\\src\\main\\java\\com\\pwc\\qa\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	}

}
public static void initialization() {
	String browserName = prop.getProperty("browser");
	if(browserName.equals("chrome")) {
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
	
	driver.get(prop.getProperty("url"));
}
*/
}