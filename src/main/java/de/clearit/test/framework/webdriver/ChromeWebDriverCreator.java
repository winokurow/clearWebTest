package de.clearit.test.framework.webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * ChromeWebDriverCreator.<br>
 * 
 * Create new Chrome WebDriver Instance
 */
public final class ChromeWebDriverCreator
{

   /**
    * Constructor.
    * 
    * Private constructor to hide the implicit public one
    */
   private ChromeWebDriverCreator()
   {
   }

   /**
    * Chrome Driver Instance erstellen<br>
    * Alle Einstellungen setzen<br>
    * 
    * @param url
    *           - test url.
    * @param local
    *           - ob auf lokalen Rechner ausgeführt werden.
    * @param useNoProxy
    *           - ob Proxy benutzt werden.
    * @param seleniumGridUrl
    *           - URL des Selenium Grid.
    * 
    * @return WebDriver Instance
    */
   protected static RemoteWebDriver createChromeWebDriver(final String url, final boolean local, boolean useNoProxy,
         final String seleniumGridUrl)
   {
      RemoteWebDriver driver;
      final DesiredCapabilities capabilities = DesiredCapabilities.chrome();

      if (useNoProxy)
      {
         capabilities.setCapability(CapabilityType.PROXY, new Proxy().getNoProxy());
      }

      
      capabilities.setBrowserName("chrome");
      capabilities.setPlatform(Platform.ANY);

      
      if (local)
      {
         driver = new ChromeDriver(capabilities);
      }
      else
      {
         driver = new RemoteWebDriver(UrlCreator.createURL(seleniumGridUrl), capabilities);
      }

      driver.get(url);

      driver.manage().deleteAllCookies();
      driver.manage().window().maximize();
      driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
      driver.manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);

      return driver;
   }
}
