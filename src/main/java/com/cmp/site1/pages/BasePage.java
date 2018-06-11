package com.cmp.site1.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
	public static WebDriver driver;
	JavascriptExecutor jexc = (JavascriptExecutor)driver;
	public static String home = System.getProperty("user.home");
	

	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
	}
}
