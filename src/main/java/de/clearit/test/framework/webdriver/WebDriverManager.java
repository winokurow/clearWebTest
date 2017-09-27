package de.clearit.test.framework.webdriver;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.clearit.test.framework.WebPropertyManager;
import de.clearit.test.framework.data.Browser;

/**
 * Webdriver Instance steuern.
 */
public final class WebDriverManager {

	private static String DEFAULT_VALUE = "false";

	/** Logger */
	private static final Logger logger = Logger.getLogger("WebDriverManager");

	/**
	 * Constructor.
	 * 
	 * Private constructor to hide the implicit public one
	 */
	private WebDriverManager() {
	}

	/**
	 * Stoppt den Webdriver und schliesst den Browser
	 * 
	 * @param webDriver
	 */
	public static void stopDriver(WebDriverWrapper webDriver) {
		try {
			stopDriverOrFail(webDriver);
		} catch (Exception e) {
			logger.error("Fehler beim stoppen des Webdrivers: " + e.getMessage(), e);
		}
	}

	/**
	 * Rausfinden ob Browser geschlossen war
	 * 
	 * @param webDriver
	 *            - driver
	 * 
	 * @return ob Browser geschlossen war
	 */
	public static boolean isClosedOrQuit(WebDriverWrapper webDriver) {
		boolean result = false;
		if (webDriver instanceof WebDriverWrapper) {
			result = webDriver.isClosedOrQuit();
		} else {
			if (webDriver == null || webDriver.toString().contains("(null)")) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Rausfinden welche Browser benutzt wird
	 * 
	 * @param driver
	 *            - driver
	 * 
	 * @return benutze Browser
	 */
	public static Browser getBrowser(WebDriverWrapper driver) {
		if (driver instanceof WebDriverWrapper) {
			return driver.getBrowser();
		}
		return Browser.FIREFOX;
	}

	public static WebDriverWrapper startDriver(WebDriverSettings webDriverSettings) {
		Validate.notEmpty(webDriverSettings.getUrl(), "Url ist nicht gesetzt.");

		RemoteWebDriver driver = null;

		WebPropertyManager properties = WebPropertyManager.getInstance();
		switch (webDriverSettings.getBrowser()) {
		case IE:
			String iePath = properties.getProperty("webdriver.iedriver.path");
			System.setProperty("webdriver.ie.driver", iePath);
			driver = IEWebDriverCreator.createIEWebDriver(webDriverSettings);
			break;
		case CHROME:
			String chromePath = properties.getProperty("webdriver.chromedriver.path");
			System.setProperty("webdriver.chrome.driver", chromePath);
			driver = ChromeWebDriverCreator.createChromeWebDriver(webDriverSettings);
			break;
		default:
			String firefox = properties.getProperty("webdriver.geckodriver.path");
			System.setProperty("webdriver.gecko.driver", firefox);
			driver = FireFoxWebDriverCreator.createFirefoxWebDriver(webDriverSettings);
		}

		webDriverSettings.setUseNoProxy(useNoProxy());

		WebDriverWrapper wrapper = new WebDriverWrapper(driver, webDriverSettings);

		logger.info(wrapper.getBrowserInfo() + " gestartet.");
		return wrapper;
	}

	private static boolean useNoProxy() {
		String key = "noproxy";
		String useNoProxy = WebPropertyManager.getInstance().getProperty(key, System.getProperty(key, DEFAULT_VALUE));
		return "true".equals(useNoProxy);
	}

	private static void stopDriverOrFail(WebDriverWrapper webDriver) {
		if (!isClosedOrQuit(webDriver)) {
			// webDriver.close();
			webDriver.quit();
			loggeBrowserSchliessenWennMoeglich(webDriver);
		}
	}

	private static void loggeBrowserSchliessenWennMoeglich(WebDriverWrapper driver) {
		if (driver instanceof WebDriverWrapper) {

			logger.info(driver.getBrowserInfo() + " geschlossen.");
		}
	}

}
