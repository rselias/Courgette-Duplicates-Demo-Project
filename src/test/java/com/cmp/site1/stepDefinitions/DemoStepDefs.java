package com.cmp.site1.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.cmp.helpers.Log;
import com.cmp.site1.pages.DemoPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DemoStepDefs {
	public WebDriver driver;

	public DemoStepDefs() {
		driver = Hooks.driver;
	}
	
	@Given("^an environment exists$")
	public void an_environment_exists() {
		Log.info("An environment exists");
	}
	
	@Given("^take preliminary step$")
	public void take_preliminary_step() {
		Log.info("Taking preliminary step");
	}
	
	@When("^a condition is met$")
	public void condition() {
		Log.info("A condition is met");
	}
	
	@Then("^assert true$")
	public void assert_true() {
		DemoPage demo = new DemoPage(driver);
		demo.assertIsTrue();
	}
}