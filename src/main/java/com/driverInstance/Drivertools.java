package com.driverInstance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Stream;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.apache.commons.exec.CommandLine;
import com.applitools.eyes.images.Eyes;
import com.extent.ExtentReporter;
import com.propertyfilereader.PropertyFileReader;
import com.utility.LoggingUtils;
import com.utility.Utilities;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class Drivertools {

	private String host;
	private int port;
	private String deviceName;
	private static String platform;
	private int appTimeOut;
	private String remoteUrl;
	private String appPackage;
	private String appActivity;
	private String appVersion;
	private String APkFileName;
	private PropertyFileReader handler;
	private static String testName;
	private String browserType;
	private String url = "";
	public static String runModule;
	private URLConnection connection;
	private URL connectURL;
	private String installBuild;
	public static boolean startTest = false;
	private static String runMode = "null";
	private static String driverVersion = "";
	public static boolean click = true;
	public static String methodName = "";
	private static AppiumDriverLocalService service;
	private static AppiumServiceBuilder builder;
	private static DesiredCapabilities cap;

	public static String getTestName() {
		return testName;
	}
	

	@SuppressWarnings("static-access")
	public void setTestName(String testName) {
		this.testName = testName;
	}

	public static ThreadLocal<AppiumDriver<WebElement>> tlDriver = new ThreadLocal<>();
	
	
//	public static ThreadLocal<AppiumDriver<MobileElement>> tlDriver = new ThreadLocal<>();

	public static ThreadLocal<WebDriver> tlWebDriver = new ThreadLocal<>();

	public static Eyes eyes = new Eyes();

	public static ExtentReporter extent = new ExtentReporter();

	protected DesiredCapabilities capabilities = new DesiredCapabilities();

	protected Utilities util = new Utilities();

	private static String ENV = "";

	/** The Constant logger. */
//	final static Logger logger = Logger.getLogger("rootLogger");
	static LoggingUtils logger = new LoggingUtils();

	//public static AppiumDriver<MobileElement> getDriver() {
	public static AppiumDriver<WebElement> getDriver() {
		return tlDriver.get();
	}
	

	public static WebDriver getWebDriver() {
		return tlWebDriver.get();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public static String getPlatform() {
		return platform;
	}

	public static void setPlatfrom(String Platform) {
		platform = Platform;
	}

	protected int getappTimeOut() {
		return appTimeOut;
	}

	protected void setappTimeOut(int timeOut) {
		this.appTimeOut = timeOut;
	}

	public String getremoteUrl() {
		return this.remoteUrl;
	}

	protected void setremoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	protected void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
	}

	protected String getAppPackage() {
		return this.appPackage;
	}

	protected void setAppActivity(String appActivity) {
		this.appActivity = appActivity;
	}

	protected String getappActivity() {
		return this.appActivity;
	}

	protected void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	protected String getAppVersion() {
		return this.appVersion;
	}

	protected String getAPKName() {
		return this.APkFileName;
	}

	protected void setAPKName(String apkName) {
		this.APkFileName = apkName;
	}

	protected PropertyFileReader getHandler() {
		return handler;
	}

	protected void setHandler(PropertyFileReader handler) {
		this.handler = handler;
	}

	protected void setBrowserType(String BrowserType) {
		this.browserType = BrowserType;
	}

	protected String getBrowserType() {
		return this.browserType;
	}

	protected void setURL(String url) {
		this.url = url;
	}

	protected String getURL() {
		return this.url;
	}

	protected String runMode() {
		return this.runMode();
	}

	@SuppressWarnings("static-access")
	protected void setRunModule(String runModule) {
		this.runModule = runModule;
	}

	public static String getRunModule() {
		return runModule;
	}

	@SuppressWarnings("static-access")
	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	@SuppressWarnings("static-access")
	public String getRunMode() {
		return this.runMode;
	}

	@SuppressWarnings("static-access")
	public void setENV(String env) {
		this.ENV = env;
	}
	
	@SuppressWarnings("static-access")
	protected void setInstallBuild(String installBuild) {
		this.installBuild = installBuild;
	}

	protected String getInstallBuild() {
		return this.installBuild;//testflight
	}

	
	
	
	public static String getENV() {
		return ENV;
	}

	public static String getDriverVersion() {
		return driverVersion;
	}

	@SuppressWarnings("static-access")
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}
	
//==========================newlines================================================================
		
	public static String buildversion() {
		return BuildVersion;
	}
//==============================================================================================

	
	public static String getENvironment() {
		return "<h5> ENV : <a href=\""+getENV()+"\" onclick='return "+click+";'\">"+getENV()+"</a></h5>";
	}
//==========================newlines================================================================
	public static String getParameterFromXML(String param) {
		return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(param);
	}

	static String RequiredBuild=getParameterFromXML("BuildVersion");
	static String Release=getParameterFromXML("release");
	static String BuildVersion=RequiredBuild + "( "+Release+" )";
	public static String BuildType=getParameterFromXML("InstallBuild");
//==============================================================================================
	public Drivertools(String application) {
		setHandler(new PropertyFileReader("properties/Execution.properties"));
		setHost(getHandler().getproperty("HOST_IP"));
		setPort(Integer.parseInt(getHandler().getproperty("HOST_PORT")));
		setappTimeOut(Integer.parseInt(getHandler().getproperty("APP_TIMEOUT")));
		//setremoteUrl("http://" + getHost() + ":" + getPort() + "/wd/hub");
		setremoteUrl("http://localhost:4723/wd/hub");
		//"http://127.0.0.1:4723/wd/hub;
		
//faster 
		setHandler(new PropertyFileReader("properties/AppPackageActivity.properties"));
		setAppPackage(getHandler().getproperty(application + "Package"));
		setAppActivity(getHandler().getproperty(application + "Activity"));
		setAppVersion(getHandler().getproperty(application + "Version"));
		setAPKName(getHandler().getproperty(application + "apkfile"));//wd/hub---conne
		setDriverVersion(getHandler().getproperty("DriverVersion"));
	}
	
	

	{
		
		setInstallBuild(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("InstallBuild"));
		setPlatfrom(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getSuite().getName());
		setTestName(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getName());
		setBrowserType(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browserType"));
		setURL(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("url"));
		setRunModule(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("runModule"));
		setRunMode(Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("runMode"));
		
		if(getTestName().equals("Android_UserSessionManagement")) {
			setPlatfrom(Utilities.setPlatform);
		}else if(methodName.equals("Login")) {
			setPlatfrom("Web");
		}

		try {
			connectURL = new URL("https://www.google.com");
			connection = connectURL.openConnection();
			connection.connect();
			connection.getInputStream().close();
		} catch (IOException e1) {
			System.out.println("<<<<<<---- Network is Down  ---->>>>>>>");
			System.exit(0);
		}
		
		//Set ENV
		if (getPlatform().equals("Web")) {
			if (getURL().equals("https://newpwa.zee5.com/")) {
				setENV(getURL());
			} else if (getURL().equals("https://www.zee5.com/")) {
				setENV(getURL());
			}else if(getURL().equals("https://pwa-preprod2.zee5.com/")) {
				setENV(getURL());
			}
		} else if (getPlatform().equals("Android")) {
			setENV("Native App");
			click = false;
		} else if (getPlatform().equals("MPWA")) {
			setENV("Chrome Application");
			click = false;
		} 
		else if (getPlatform().equals("Plobal_iOS")) {
			setENV("Native App");
			click = false;
		}
		
		logger.info("PlatForm :: " + getPlatform());
		if (Stream.of("Android", "ios", "Web", "MPWA", "TV","iOSWeb","Plobal_iOS").anyMatch(getPlatform()::equals)) {
			setHandler(new PropertyFileReader("properties/ExecutionControl.properties"));
			if (getHandler().getproperty(getTestName()).equals("Y") && (getRunMode().contentEquals(getTestName()))
					|| (getRunMode().contentEquals("Suites"))) {
				logger.info("Running Test :: " + getTestName());
				logger.info("Run Mode :: YES");
				startTest = true;
			} else {
				logger.info(getTestName() + " : Test Skipped");
				logger.info("RunMode is :: No");
				startTest = false;
				throw new SkipException(getTestName() + " : Test Skipped ");
			}
		} else {
			throw new SkipException("PlatForm not matched...");
		}
	}
	
	public static void startServer() {
		//Set Capabilities
		cap = new DesiredCapabilities();
		cap.setCapability("noReset", "false");
//		
		//Build the Appium service
		builder = new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(4723);
		builder.withCapabilities(cap);
		builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
//		builder.withArgument(GeneralServerFlag.LOG_LEVEL,"info");
		
		//Start the server with the builder
		service = AppiumDriverLocalService.buildService(builder);
		service.start();
	}
	
	@AfterSuite
	public static void stopServer() {
		service.stop();
	}
	
	
	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}
	
	public static void startServer1() {
		CommandLine cmd = new CommandLine("C:\\Program Files (x86)\\Appium\\node.exe");
		cmd.addArgument("C:\\Program Files (x86)\\Appium\\node_modules\\appium\\bin\\Appium.js");
		cmd.addArgument("--address");
		cmd.addArgument("127.0.0.1");
		cmd.addArgument("--port");
		cmd.addArgument("4723");
		
		DefaultExecuteResultHandler handler = new DefaultExecuteResultHandler();
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		try {
			executor.execute(cmd, handler);
			Thread.sleep(10000);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
