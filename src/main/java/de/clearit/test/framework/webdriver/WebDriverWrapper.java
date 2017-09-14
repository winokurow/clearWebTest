package de.clearit.test.framework.webdriver;

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

import de.clearit.test.data.Browser;
import de.clearit.test.helper.web.WebdriverUtils;

public class WebDriverWrapper implements WebDriver, JavascriptExecutor, FindsById, FindsByClassName, FindsByLinkText,
FindsByName, FindsByCssSelector, FindsByTagName, FindsByXPath, HasInputDevices, HasCapabilities, TakesScreenshot
{
	   
   /* webDriver instance */
   private final RemoteWebDriver webDriver;

   /* ob close was already called */
   private boolean closeCalled;

   /* ob quit was already called */
   private boolean quitCalled;

   /* ob local */
   private boolean local;

   /* browserInfo */
   private final String browserInfo;

   /* Browser */
   private Browser browser = Browser.FIREFOX;

   /**
    * Constructor.
    * 
    * @param webDriver
    *           - driver
    * @param local
    *           - der Text, der wird in Log angezeigt
    * @param local
    *           - ob auf lokale Rechner ausgeführt wird
    * @param isIE
    *           - ob IE
    */
   public WebDriverWrapper(final RemoteWebDriver webDriver, String browserInfo, boolean local, Browser browser)
   {
      this.local = local;
      this.webDriver = webDriver;
      this.browserInfo = browserInfo;
      this.browser = browser;
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#get(java.lang.String)
 */
@Override
   public void get(String url)
   {
      webDriver.get(url);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getCurrentUrl()
 */
@Override
   public String getCurrentUrl()
   {
      return webDriver.getCurrentUrl();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getTitle()
 */
@Override
   public String getTitle()
   {
      return webDriver.getTitle();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElements(org.openqa.selenium.By)
 */
@Override
   public List<WebElement> findElements(By by)
   {
      return webDriver.findElements(by);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElement(org.openqa.selenium.By)
 */
@Override
   public WebElement findElement(By by)
   {
      return webDriver.findElement(by);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getPageSource()
 */
@Override
   public String getPageSource()
   {
      return webDriver.getPageSource();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#close()
 */
@Override
   public void close()
   {
      webDriver.close();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#quit()
 */
@Override
   public void quit()
   {
      if (!quitCalled) // spart ein synchronize
      {
         quitIfNeeded();
      }
   }

   private synchronized void quitIfNeeded()
   {
      if (!quitCalled)
      {
         webDriver.quit();
         quitCalled = true;
      }
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getWindowHandles()
 */
@Override
   public Set<String> getWindowHandles()
   {
      return webDriver.getWindowHandles();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getWindowHandle()
 */
@Override
   public String getWindowHandle()
   {
      return webDriver.getWindowHandle();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#switchTo()
 */
@Override
   public TargetLocator switchTo()
   {
      return webDriver.switchTo();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#navigate()
 */
@Override
   public Navigation navigate()
   {
      return webDriver.navigate();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#manage()
 */
@Override
   public Options manage()
   {
      return webDriver.manage();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#toString()
 */
   @Override
   public String toString()
   {
      return super.toString();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getScreenshotAs(org.openqa.selenium.OutputType)
 */
@Override
   public <T> T getScreenshotAs(OutputType<T> outputType)
   {
      return ((TakesScreenshot) webDriver).getScreenshotAs(outputType);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getIpOfNode()
 */
public String getIpOfNode()
   {
      if (local)
      {
         return "localhost";
      }
      else
      {
         return WebdriverUtils.getIPOfNode(webDriver);
      }
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getCapabilities()
 */
@Override
   public Capabilities getCapabilities()
   {
      return webDriver.getCapabilities();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getKeyboard()
 */
@Override
   public Keyboard getKeyboard()
   {
      return webDriver.getKeyboard();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getMouse()
 */
@Override
   public Mouse getMouse()
   {
      return webDriver.getMouse();
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByXPath(java.lang.String)
 */
@Override
   public WebElement findElementByXPath(String using)
   {
      return webDriver.findElementByXPath(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByXPath(java.lang.String)
 */
@Override
   public List<WebElement> findElementsByXPath(String using)
   {
      return webDriver.findElementsByXPath(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByTagName(java.lang.String)
 */
@Override
   public WebElement findElementByTagName(String using)
   {
      return webDriver.findElementByTagName(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByTagName(java.lang.String)
 */
@Override
   public List<WebElement> findElementsByTagName(String using)
   {
      return webDriver.findElementsByTagName(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByCssSelector(java.lang.String)
 */
@Override
   public WebElement findElementByCssSelector(String using)
   {
      return webDriver.findElementByCssSelector(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByCssSelector(java.lang.String)
 */
@Override
   public List<WebElement> findElementsByCssSelector(String using)
   {
      return webDriver.findElementsByCssSelector(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByName(java.lang.String)
 */
@Override
   public WebElement findElementByName(String using)
   {
      return webDriver.findElementByName(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByName(java.lang.String)
 */
@Override
   public List<WebElement> findElementsByName(String using)
   {
      return webDriver.findElementsByName(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByLinkText(java.lang.String)
 */
@Override
   public WebElement findElementByLinkText(String using)
   {
      return webDriver.findElementByLinkText(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByLinkText(java.lang.String)
 */
@Override
   public List<WebElement> findElementsByLinkText(String using)
   {
      return webDriver.findElementsByLinkText(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByPartialLinkText(java.lang.String)
 */
@Override
   public WebElement findElementByPartialLinkText(String using)
   {
      return webDriver.findElementByPartialLinkText(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByPartialLinkText(java.lang.String)
 */
@Override
   public List<WebElement> findElementsByPartialLinkText(String using)
   {
      return webDriver.findElementsByPartialLinkText(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementByClassName(java.lang.String)
 */
@Override
   public WebElement findElementByClassName(String using)
   {
      return webDriver.findElementByClassName(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsByClassName(java.lang.String)
 */
@Override
   public List<WebElement> findElementsByClassName(String using)
   {
      return webDriver.findElementsByClassName(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementById(java.lang.String)
 */
@Override
   public WebElement findElementById(String using)
   {
      return webDriver.findElementById(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#findElementsById(java.lang.String)
 */
@Override
   public List<WebElement> findElementsById(String using)
   {
      return webDriver.findElementsById(using);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#executeScript(java.lang.String, java.lang.Object)
 */
@Override
   public Object executeScript(String script, Object... args)
   {
      return webDriver.executeScript(script, args);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#executeAsyncScript(java.lang.String, java.lang.Object)
 */
@Override
   public Object executeAsyncScript(String script, Object... args)
   {
      return webDriver.executeAsyncScript(script, args);
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#initClosedAndQuit()
 */
public synchronized void initClosedAndQuit()
   {
      closeCalled = false;
      quitCalled = false;
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#isClosedOrQuit()
 */
public synchronized boolean isClosedOrQuit()
   {
      return closeCalled || quitCalled;
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getBrowserInfo()
 */
public String getBrowserInfo()
   {
      return browserInfo;
   }

   /* (non-Javadoc)
 * @see de.clearit.test.framework.webdriver.WebDriverWrapper#getBrowser()
 */
public Browser getBrowser()
   {
      return browser;
   }


}
