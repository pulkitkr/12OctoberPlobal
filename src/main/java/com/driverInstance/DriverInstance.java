package com.driverInstance;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.model.ScreenCapture;
import com.deviceDetails.DeviceDetails;
import com.propertyfilereader.PropertyFileReader;
import com.utility.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverInstance extends Drivertools {

	public DriverInstance(String Application) {
		super(Application);

		try {
			switch (getPlatform()) {

			case "Web":
				LaunchBrowser(getBrowserType());
				break;

			case "iOSWeb":
				LaunchiOSBrowser(getBrowserType());
				break;

			case "MPWA":
				tlDriver.set(((AppiumDriver<WebElement>) new IOSDriver<WebElement>(new URL(getremoteUrl()),
						this.generateiOSCapabilities(Application))));
				tlDriver.get().get(getURL());
				break;

			case "Android":
				tlDriver.set((AppiumDriver<WebElement>) new AndroidDriver<WebElement>(new URL(getremoteUrl()),
						this.generateAndroidCapability()));
				break;

			case "Plobal_iOS":
				tlDriver.set((AppiumDriver<WebElement>) new IOSDriver<WebElement>(new URL(getremoteUrl()),
						this.generateiOSCapabilities(Application)));
				break;

			default:
				throw new SkipException("Incorrect Platform...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SkipException("Device not connected OR Appium Studio service is down...");
		}

		Utilities util = new Utilities();
		util.initDriver();
	}

	/**
	 * @param application
	 * @return Android capabilities
	 * @throws Exception
	 */
	protected DesiredCapabilities generateAndroidCapabilities(String application) {
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability("fullReset", false);
		capabilities.setCapability("autoAcceptAlerts", true);
		if (getPlatform().equals("MPWA")) {
			capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			return capabilities;
		}
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getAppPackage());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getappActivity());
		if (Utilities.relaunch) {
			removePermisson(getAppPackage());
		}
		return capabilities;
	}

	protected DesiredCapabilities generateAndroidCapability() {
		// Set the Desired Capabilities
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
//		caps.setCapability("compressXml", "true");
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
//		4f9e8c63
		// caps.setCapability("fullReset", false);
		caps.setCapability("noReset", false);
		caps.setCapability("autoAcceptAlerts", true);

		logger.info("APK INSTALLED..");
		caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getAppPackage());
		caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getappActivity());
		if (Utilities.relaunch) {
			removePermisson(getAppPackage());
		}
		return caps;

	}

	/**
	 * @param application
	 * @return iOS capabilities
	 * @throws Exception
	 */
	protected DesiredCapabilities generateiOSCapabilities(String application) {
		DesiredCapabilities iOScapabilities = new DesiredCapabilities();

		if (getPlatform().equals("MPWA")) {
			iOScapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iOS");
			iOScapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
			iOScapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
			// capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
			iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.mobilesafari");
			iOScapabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
		} else {
			iOScapabilities.setCapability("deviceName", "iOS");
			iOScapabilities.setCapability("udid", "02bb95913cd357408c445e73802534f53aa8d30d");
			iOScapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");

			if (application.equalsIgnoreCase("Message")) {
				System.out.println("Message App");
				extent.extentLogger("", "Message App");
				iOScapabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
				iOScapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
				iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.MobileSMS");
				// above line will open appstore app
			} else if (application.equalsIgnoreCase("plobal")) {
				iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "ios.staging.plobaltestshutterstock");
				logger.info("Tapping on Plobal app");
			} else if (getInstallBuild().equalsIgnoreCase("testflight")) {
				System.out.println("testflight");
				iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.TestFlight");
				// above line will open testflight app
			} else if (getInstallBuild().equalsIgnoreCase("appstore")) {
				System.out.println("appstore");
				iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.apple.AppStore");
				// above line will open appstore app
			} else if (getInstallBuild().equalsIgnoreCase("no")) {
				iOScapabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.zee5.hipi");
				// above line will open hipi app
				logger.info("Tapping on HiPi app");

			}

		}

		iOScapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
		return iOScapabilities;
	}

	/**
	 * Function to Launch Web Browsers
	 * 
	 * @param browserName
	 */
	public void LaunchBrowser(String browserName) {
		setHandler(new PropertyFileReader("properties/AppPackageActivity.properties"));
		if (browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().version("0.26.0").setup();
			tlWebDriver.set(new FirefoxDriver());
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().version(getDriverVersion()).setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-gpu");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			tlWebDriver.set(new ChromeDriver(options));
		} else if (browserName.equalsIgnoreCase("IE")) {
			tlWebDriver.set(new InternetExplorerDriver());
		} else if (browserName.equalsIgnoreCase("MSEdge")) {
			tlWebDriver.set(new EdgeDriver());
		}
		tlWebDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		tlWebDriver.get().get(getURL());
		tlWebDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Function to Launch Web Browsers
	 * 
	 * @param browserName
	 */
	public void LaunchiOSBrowser(String browserName) {
		setHandler(new PropertyFileReader("properties/AppPackageActivity.properties"));

		if (browserName.equalsIgnoreCase("Safari")) {
			System.out.println("SAFARI BROWSER");
			// Instantiate a SafariDriver.
			WebDriver driver = new SafariDriver();
			driver.manage().window().maximize();
			tlWebDriver.set(driver);
		} else if (browserName.equalsIgnoreCase("Firefox")) {
			WebDriverManager.firefoxdriver().version("0.26.0").setup();
			tlWebDriver.set(new FirefoxDriver());
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().version("86.0.4240.22").setup();

			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");

			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-gpu");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			tlWebDriver.set(new ChromeDriver(options));
		} else if (browserName.equalsIgnoreCase("IE")) {
			tlWebDriver.set(new InternetExplorerDriver());
		} else if (browserName.equalsIgnoreCase("MSEdge")) {
			tlWebDriver.set(new EdgeDriver());
		}

		tlWebDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		tlWebDriver.get().get(getURL());
		tlWebDriver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	/**
	 * To Remove the permission of an application
	 * 
	 * @param packagename
	 */
	public static void removePermisson(String packagename) {
		logger.info("****Clearing the App Data****");
		String cmd2 = "adb shell pm clear " + packagename;
		try {
			Runtime.getRuntime().exec(cmd2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@BeforeSuite
	public static void main() {

		int port = 4723;
		if (!checkIfServerIsRunnning(port)) {
			startServer();
//			startServer1();
//			stopServer();
		} else {
			System.out.println("Appium Server already running on Port - " + port);
		}
	}
}