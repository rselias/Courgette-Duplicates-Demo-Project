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
		rerunFailedScenarios = false,
		showTestOutput = true,
		reportTargetDir = "output",
		cucumberOptions = @CucumberOptions(
				features ="classpath:features",
				glue ={"com.cmp.site1.stepDefinitions"},
				plugin = { "rerun:target/rerunSmoke.txt", "pretty", "html:target/cucumber-html-report", 
				"json:output/cucumber-report/cucumber.json"},
				tags = {"@Isolated"},
				monochrome = true,
				dryRun = false,
				strict = true
		))
public class ParallelScenariosTest {
}
