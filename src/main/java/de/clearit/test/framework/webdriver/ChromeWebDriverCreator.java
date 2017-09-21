package de.clearit.test.framework.webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    * @param webDriverSettings - WebDriver Einstellungen
    *           
    * @return WebDriver Instance
    */
   protected static RemoteWebDriver createChromeWebDriver(WebDriverSettings webDriverSettings)
   {
      RemoteWebDriver driver;
      final DesiredCapabilities capabilities = DesiredCapabilities.chrome();

      if (webDriverSettings.isUseNoProxy())
      {
         capabilities.setCapability(CapabilityType.PROXY, new Proxy().getNoProxy());
      }

      
      capabilities.setBrowserName("chrome");
      capabilities.setPlatform(Platform.ANY);

      
      if (webDriverSettings.isLocal())
      {
    	  ChromeOptions options = new ChromeOptions();
          //options.addExtensions(new File("src\test\resources\extensions\extension.crx"));
    	  capabilities.setCapability(ChromeOptions.CAPABILITY, options);
    	  driver = new ChromeDriver(capabilities);
      }
      else
      {
         driver = new RemoteWebDriver(UrlCreator.createURL(webDriverSettings.getSeleniumGridUrl()), capabilities);
      }

      driver.get(webDriverSettings.getUrl());

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
