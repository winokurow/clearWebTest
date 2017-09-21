package de.clearit.test.framework.webdriver;

import java.net.URL;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.internal.FindsByLinkText;
import org.openqa.selenium.internal.FindsByName;
import org.openqa.selenium.internal.FindsByTagName;
import org.openqa.selenium.internal.FindsByXPath;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.clearit.test.framework.data.Browser;
import de.clearit.test.helper.web.WebdriverUtils;

public class WebDriverWrapper
		implements WebDriver, JavascriptExecutor, FindsById, FindsByClassName, FindsByLinkText, FindsByName,
		FindsByCssSelector, FindsByTagName, FindsByXPath, HasInputDevices, HasCapabilities, TakesScreenshot {

	/* webDriver instance */
	private final RemoteWebDriver driver;

	/**
	 * @return the webDriverSettings
	 */
	public WebDriverSettings getWebDriverSettings() {
		return webDriverSettings;
	}

	/* ob close was already called */
	private boolean closeCalled;

	/* ob quit was already called */
	private boolean quitCalled;

	/* WebDriverSettings */
	private WebDriverSettings webDriverSettings;

	/**
	 * Constructor.
	 * 
	 * @param webDriver
	 *            - driver
	 * @param WebDriverSettings
	 *            - die Einstellungen
	 */
	public WebDriverWrapper(final RemoteWebDriver webDriver, WebDriverSettings webDriverSettings) {
		this.driver = webDriver;
		this.webDriverSettings = webDriverSettings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#get(java.lang.
	 * String)
	 */
	@Override
	public void get(String url) {
		driver.get(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getCurrentUrl()
	 */
	@Override
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getTitle()
	 */
	@Override
	public String getTitle() {
		return driver.getTitle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElements(org.
	 * openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElement(org.
	 * openqa.selenium.By)
	 */
	@Override
	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getPageSource()
	 */
	@Override
	public String getPageSource() {
		return driver.getPageSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#close()
	 */
	@Override
	public void close() {
		driver.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#quit()
	 */
	@Override
	public void quit() {
		if (!quitCalled) // spart ein synchronize
		{
			quitIfNeeded();
		}
	}

	private synchronized void quitIfNeeded() {
		if (!quitCalled) {
			driver.quit();
			quitCalled = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#getWindowHandles()
	 */
	@Override
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#getWindowHandle()
	 */
	@Override
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#switchTo()
	 */
	@Override
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#navigate()
	 */
	@Override
	public Navigation navigate() {
		return driver.navigate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#manage()
	 */
	@Override
	public Options manage() {
		return driver.manage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#getScreenshotAs(org.
	 * openqa.selenium.OutputType)
	 */
	@Override
	public <T> T getScreenshotAs(OutputType<T> outputType) {
		return ((TakesScreenshot) driver).getScreenshotAs(outputType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getIpOfNode()
	 */
	public String getIpOfNode() {
		if (webDriverSettings.isLocal()) {
			return "localhost";
		} else {
			return WebdriverUtils.getIPOfNode(driver);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#getCapabilities()
	 */
	@Override
	public Capabilities getCapabilities() {
		return driver.getCapabilities();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getKeyboard()
	 */
	@Override
	public Keyboard getKeyboard() {
		return driver.getKeyboard();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getMouse()
	 */
	@Override
	public Mouse getMouse() {
		return driver.getMouse();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByXPath(
	 * java.lang.String)
	 */
	@Override
	public WebElement findElementByXPath(String using) {
		return driver.findElementByXPath(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByXPath(
	 * java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsByXPath(String using) {
		return driver.findElementsByXPath(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByTagName
	 * (java.lang.String)
	 */
	@Override
	public WebElement findElementByTagName(String using) {
		return driver.findElementByTagName(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementsByTagName(java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsByTagName(String using) {
		return driver.findElementsByTagName(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementByCssSelector(java.lang.String)
	 */
	@Override
	public WebElement findElementByCssSelector(String using) {
		return driver.findElementByCssSelector(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementsByCssSelector(java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsByCssSelector(String using) {
		return driver.findElementsByCssSelector(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByName(
	 * java.lang.String)
	 */
	@Override
	public WebElement findElementByName(String using) {
		return driver.findElementByName(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByName(
	 * java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsByName(String using) {
		return driver.findElementsByName(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementByLinkText(java.lang.String)
	 */
	@Override
	public WebElement findElementByLinkText(String using) {
		return driver.findElementByLinkText(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementsByLinkText(java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsByLinkText(String using) {
		return driver.findElementsByLinkText(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementByPartialLinkText(java.lang.String)
	 */
	@Override
	public WebElement findElementByPartialLinkText(String using) {
		return driver.findElementByPartialLinkText(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementsByPartialLinkText(java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsByPartialLinkText(String using) {
		return driver.findElementsByPartialLinkText(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementByClassName(java.lang.String)
	 */
	@Override
	public WebElement findElementByClassName(String using) {
		return driver.findElementByClassName(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#
	 * findElementsByClassName(java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsByClassName(String using) {
		return driver.findElementsByClassName(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElementById(java
	 * .lang.String)
	 */
	@Override
	public WebElement findElementById(String using) {
		return driver.findElementById(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsById(
	 * java.lang.String)
	 */
	@Override
	public List<WebElement> findElementsById(String using) {
		return driver.findElementsById(using);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#executeScript(java.
	 * lang.String, java.lang.Object)
	 */
	@Override
	public Object executeScript(String script, Object... args) {
		return driver.executeScript(script, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#executeAsyncScript(
	 * java.lang.String, java.lang.Object)
	 */
	@Override
	public Object executeAsyncScript(String script, Object... args) {
		return driver.executeAsyncScript(script, args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#initClosedAndQuit()
	 */
	public synchronized void initClosedAndQuit() {
		closeCalled = false;
		quitCalled = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.clearit.test.framework.webdriver.WebDriverWrapper#isClosedOrQuit()
	 */
	public synchronized boolean isClosedOrQuit() {
		return closeCalled || quitCalled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getBrowser()
	 */
	public Browser getBrowser() {
		return webDriverSettings.getBrowser();
	}

	/**
	 * Browser Info ausgeben
	 * 
	 * @return Browser Info
	 */
	public String getBrowserInfo() {
		String remoteOderLokal = webDriverSettings.isLocal() ? "Lokaler" : "Remote";
		String proxyOderDirekt = webDriverSettings.isUseNoProxy() ? "(kein Proxy)" : "(via Proxy)";

		URL urlObject = UrlCreator.createURL(webDriverSettings.getUrl());

		String browserInfo = String.format("%s %s%s auf %s:%s und %s", remoteOderLokal, webDriverSettings.getBrowser(),
				proxyOderDirekt, urlObject.getHost(), urlObject.getPort(), urlObject.getQuery());
		return browserInfo;
	}
}
