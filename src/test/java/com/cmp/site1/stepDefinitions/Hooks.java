package com.cmp.site1.stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;

import com.cmp.helpers.BrowserType;
import com.cmp.helpers.Log;
import com.cmp.helpers.ResourceHelper;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	public static WebDriver driver;
	public static String os = System.getProperty("os.name");
	public static LogEntries logEntries;

	@Before
	/**
	 * Delete all cookies at the start of each scenario to avoid shared state
	 * between tests
	 */
	public void openBrowser(Scenario scenario) throws IOException {
		long threadId = Thread.currentThread().getId();
	    String processName = ManagementFactory.getRuntimeMXBean().getName();
	    System.out.println("Started in thread: " + threadId + ", in JVM: " + processName);
	}

	public void initiateDriver(BrowserType browser) {
		ChromeOptions cOptions = new ChromeOptions();
		FirefoxOptions fOptions = new FirefoxOptions();
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		DesiredCapabilities capFirefox = DesiredCapabilities.firefox();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.SEVERE);
		switch (browser) {
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
			cOptions.addArguments("disable-infobars");
			cOptions.addArguments("disable-gpu");
			cOptions.addArguments("--incognito");
			cap.setCapability(ChromeOptions.CAPABILITY, cOptions);
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver = new ChromeDriver(cap);
			Log.info("Chrome Browser started");
			break;
		case FIREFOX:
			driver = new FirefoxDriver();
			Log.info("Firefox Browser started");
			break;
		case IEXPLORER:
			driver = new InternetExplorerDriver();
			Log.info("IExplorer Browser started");
			break;
		case EDGE:
			driver = new EdgeDriver();
			Log.info("Edge Browser started");
			break;
		case SAFARI:
			driver = new SafariDriver();
			Log.info("Safari Browser started");
			break;
		case FHEADLESS:
			FirefoxBinary firefoxBinary = new FirefoxBinary();
			firefoxBinary.addCommandLineOptions("--headless");
			fOptions.setBinary(firefoxBinary);
			capFirefox.setCapability("moz:firefoxOptions", fOptions);
			capFirefox.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver = new FirefoxDriver(capFirefox);
			Log.info("Firefox Headless Browser started");
			break;
		case HEADLESS:
			if (os.contains("Windows")) {
				// windows path
				System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
			} else {
				// linux path, default
				System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
			}
			cOptions.addArguments("--headless");
			cOptions.addArguments("--window-size=2560,1374");
			cOptions.addArguments("--disable-gpu");
			cOptions.addArguments("--disable-extensions");
			cOptions.addArguments("--incognito");
			cOptions.addArguments("--test-type");
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			ChromeDriverService cDriverService = ChromeDriverService.createDefaultService();
			driver = new ChromeDriver(cDriverService, cOptions);
			Log.info("Chrome Headless Browser started");
			break;
		default:
			System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
			cOptions.addArguments("disable-infobars");
			cap.setCapability(ChromeOptions.CAPABILITY, cOptions);
			cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver = new ChromeDriver(cap);
			Log.info("Chrome Browser started");
			break;
		}
		driver.manage().deleteAllCookies();
		driver.manage().window().setSize(new Dimension(2560, 1374));
	}

	@After
	public void tearDown(Scenario scenario) throws Exception {
		if (scenario.isFailed()) {
			try {
				getScenarioNameWithStatus(scenario);
				scenario.write("Current Page URL is " + driver.getCurrentUrl());
			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				Log.error(somePlatformsDontSupportScreenshots.getMessage());
			} catch (ClassCastException cce) {
				cce.printStackTrace();
			}
		}
	    try {
            driver = null;
            Log.info("Closing Browser");
        } catch (UnreachableBrowserException e) {
        	Log.error("Cannot close browser:Browser is Unreachable ", e.getMessage());
        }
	}

	public void getScenarioNameWithStatus(Scenario scenario) {
		Log.info("------------------------------");
		Log.info(scenario.getName() + " Status - " + scenario.getStatus());
		Log.info("------------------------------");
	}

	public String getscreenshot(Scenario scenario) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = (ts.getScreenshotAs(OutputType.FILE));
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String scenarioName = scenario.getName().toLowerCase().replaceAll("\\s+", "_");
		String dest = ResourceHelper
				.getResourcePath("output\\screenshots\\" + scenarioName + "_" + timeStamp + ".png");
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);
		return dest;
	}
}