package de.clearit.test.framework.webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.clearit.test.framework.elemente.WebBaseElement;

/**
 * IEWebDriverCreator.<br>
 * 
 * Create new IE WebDriver Instance
 */
public final class IEWebDriverCreator
{

   /**
    * Constructor.
    * 
    * Private constructor to hide the implicit public one
    */
   private IEWebDriverCreator()
   {
   }

   /**
    * IE Driver Instance erstellen<br>
    * Alle Einstellungen setzen<br>
    * 
    * @param webDriverSettings - WebDriver Einstellungen
    *           
    * @return WebDriver Instance
    */
   protected static RemoteWebDriver createIEWebDriver(WebDriverSettings webDriverSettings)
   {
      RemoteWebDriver driver;
      final DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();

      // Native Events ist True sonst werden Javascript Events nicht funktionieren.
      capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
      capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
      capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "www.google.de");
      capabilities.setCapability(InternetExplorerDriver.SILENT, true);
      if (webDriverSettings.isUseNoProxy())
      {
         capabilities.setCapability(CapabilityType.PROXY, new Proxy().getNoProxy());
      }

      if (webDriverSettings.isLocal())
      {
         driver = new InternetExplorerDriver(capabilities);
      }
      else
      {
         driver = new RemoteWebDriver(UrlCreator.createURL(webDriverSettings.getSeleniumGridUrl()), capabilities);
      }

      driver.get(webDriverSettings.getUrl());

      WebBaseElement overridelink = new WebBaseElement(By.id("overridelink"), driver);
      if (overridelink.isPresent())
      {
         driver.get("javascript:document.getElementById('overridelink').click();");
      }

      driver.manage().deleteAllCookies();
      if (!(webDriverSettings.getCookies().isEmpty()))
      {
    	  for(Cookie cookie : webDriverSettings.getCookies()) {
    		  driver.manage().addCookie(cookie);
    	  }
      }
      
      driver.manage().window().maximize();
      driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
      driver.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);

      return driver;
   }
}
