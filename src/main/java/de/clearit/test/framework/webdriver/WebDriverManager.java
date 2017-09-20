package de.clearit.test.framework.webdriver;

import java.net.URL;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.clearit.test.data.Browser;
import de.clearit.test.framework.WebPropertyManager;

/**
 * Webdriver Instance steuern.
 */
public final class WebDriverManager
{

   private static String DEFAULT_VALUE = "false";

   /** Logger */
   private static final Logger logger = Logger.getLogger("WebDriverManager");

   /**
    * Constructor.
    * 
    * Private constructor to hide the implicit public one
    */
   private WebDriverManager()
   {
   }

   /**
    * Start WebDriver.
    * 
    * @param grid
    *           - Selenium Grid URL
    * 
    * @param gridHint
    *           - Selenium Grid Bezeichnung
    * 
    * @param isIE
    *           - ob Internet Explorer
    * @param url
    *           - URL zu laden.
    * @return den gestarteten WebDriver
    * 
    */
   public static WebDriverWrapper createDriver(String grid, String gridHint, final Browser browser, String url)
   {
	   WebDriverWrapper driver = erzeugeWebDriverMitRetry(browser, url, grid);
      logger.info("Browser (" + (useNoProxy() ? "no Proxy" : "via Proxy") + ") "
            + (isLocal() ? "Lokaler" : "Remote/" + gridHint) + " Rechner: '" + driver.getIpOfNode() + "'");
      return driver;
   }

   /**
    * Stoppt den Webdriver und schliesst den Browser
    * 
    * @param webDriver
    */
   public static void stopDriver(WebDriverWrapper webDriver)
   {
      try
      {
         stopDriverOrFail(webDriver);
      }
      catch (Exception e)
      {
         logger.error("Fehler beim stoppen des Webdrivers: " + e.getMessage(), e);
      }
   }

   /**
    * Rausfinden ob Browser geschlossen war
    * 
    * @param webDriver
    *           - driver
    * 
    * @return ob Browser geschlossen war
    */
   public static boolean isClosedOrQuit(WebDriverWrapper webDriver)
   {
      boolean result = false;
      if (webDriver instanceof WebDriverWrapper)
      {
         result = webDriver.isClosedOrQuit();
      }
      else
      {
         if (webDriver == null || webDriver.toString().contains("(null)"))
         {
            result = true;
         }
      }
      return result;
   }

   /**
    * Rausfinden welche Browser benutzt wird
    * 
    * @param driver
    *           - driver
    * 
    * @return benutze Browser
    */
   public static Browser getBrowser(WebDriverWrapper driver)
   {
      if (driver instanceof WebDriverWrapper)
      {
         return driver.getBrowser();
      }
      return Browser.FIREFOX;
   }

   private static WebDriverWrapper erzeugeWebDriverMitRetry(final Browser browser, String url, String grid)
   {
	   WebDriverWrapper driver = null;
      driver = WebDriverManager.startDriver(url, isLocal(), grid, browser);
      return driver;
   }

   private static WebDriverWrapper startDriver(String url, final boolean local, final String seleniumGridUrl,
		   final Browser browser)
   {
      Validate.notEmpty(url, "Url ist nicht gesetzt.");

      RemoteWebDriver driver = null;
      String remoteOderLokal = local ? "Lokaler" : "Remote";
      boolean useNoProxy = useNoProxy();
      String proxyOderDirekt = useNoProxy ? "(kein Proxy)" : "(via Proxy)";
      WebPropertyManager properties = WebPropertyManager.getInstance();
      switch (browser)
      {
      case IE:
         String iePath = properties.getProperty("webdriver.iedriver.path");
         System.setProperty("webdriver.ie.driver", iePath);
         driver = IEWebDriverCreator.createIEWebDriver(url, local, useNoProxy, seleniumGridUrl);
	     break;
      case CHROME:
          String chromePath = properties.getProperty("webdriver.chromedriver.path");
          System.setProperty("webdriver.chrome.driver", chromePath);
          driver = ChromeWebDriverCreator.createChromeWebDriver(url, local, useNoProxy, seleniumGridUrl);
 	     break;
      default:
         String firefox = properties.getProperty("webdriver.geckodriver.path");
         System.setProperty("webdriver.gecko.driver", firefox);
         driver = FireFoxWebDriverCreator.createFirefoxWebDriver(url, useNoProxy, local, seleniumGridUrl);
      }
      URL urlObject = UrlCreator.createURL(url);
      String browserInfo = String.format("%s %s%s auf %s:%s und %s", remoteOderLokal, browser, proxyOderDirekt,
            urlObject.getHost(), urlObject.getPort(), urlObject.getQuery());
      return new WebDriverWrapper(driver, browserInfo, local, browser);
   }

   private static boolean useNoProxy()
   {
      String key = "noproxy";
      String useNoProxy = WebPropertyManager.getInstance().getProperty(key, System.getProperty(key, DEFAULT_VALUE));
      return "true".equals(useNoProxy);
   }

   private static void stopDriverOrFail(WebDriverWrapper webDriver)
   {
      if (!isClosedOrQuit(webDriver))
      {
         // webDriver.close();
         webDriver.quit();
         loggeBrowserSchliessenWennMoeglich(webDriver);
      }
   }

   private static void loggeBrowserSchliessenWennMoeglich(WebDriverWrapper webDriver)
   {
      if (webDriver instanceof WebDriverWrapper)
      {
         logger.info(webDriver.getBrowserInfo() + " geschlossen.");
      }
   }

   private static boolean isLocal()
   {
      final String remote = System.getProperty("remote", DEFAULT_VALUE);
      final boolean isLocal = remote.equals(DEFAULT_VALUE);
      return isLocal;
   }

}
