package com.cmp.site1.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DemoPage extends BasePage {

	public DemoPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void assertIsTrue() {
		Assert.assertTrue("False", true);
	}
}
