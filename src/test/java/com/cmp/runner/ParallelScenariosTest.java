package com.cmp.runner;

import org.junit.runner.RunWith;
import courgette.api.CourgetteOptions;
import courgette.api.CourgetteRunLevel;
import courgette.api.junit.Courgette;
import cucumber.api.CucumberOptions;

@RunWith(Courgette.class) 

@CourgetteOptions(
		threads = 2,
		runLevel = CourgetteRunLevel.SCENARIO,
		rerunFailedScenarios = true,
		showTestOutput = true,
		reportTargetDir = "output",
		cucumberOptions = @CucumberOptions(
				features ="src/test/resources/features/site1",
				glue ={"com.cmp.site1.stepDefinitions"},
				plugin = { "pretty",
						"html:target/cucumber-html-report",
						"json:output/cucumber-report/cucumber.json",
						"junit:output/test.xml"},
				tags = {"@Current"},
				monochrome = true,
				dryRun = false,
				strict = true
		))
public class ParallelScenariosTest {
}
