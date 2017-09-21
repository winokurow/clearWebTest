package de.clearit.test.helper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.Cookie;
import org.testng.annotations.Listeners;

import com.sun.jna.platform.win32.Netapi32Util.User;

import de.clearit.test.common.ScreenshotCreator;
import de.clearit.test.common.TestUtils;
import de.clearit.test.exceptions.AllgemeineTechnischeException;
import de.clearit.test.framework.AllTestListenerAdapters;
import de.clearit.test.framework.ExecutionTimer;
import de.clearit.test.framework.ExecutionTimerManager;
import de.clearit.test.framework.WebDriverCleanUp;
import de.clearit.test.framework.WebDriverHolder;
import de.clearit.test.framework.WebPropertyManager;
import de.clearit.test.framework.data.Browser;
import de.clearit.test.framework.webdriver.WebDriverManager;
import de.clearit.test.framework.webdriver.WebDriverSettings;
import de.clearit.test.framework.webdriver.WebDriverWrapper;


/**
 * <P>
 * Das abstrakte Elternklass für alle Tests.
 * 
 *
 * @implements WebDriverHolder - WebDriver Holder
 * 
 * @author Ilja Winokurow
 */
@Listeners(AllTestListenerAdapters.class)
public class WebTestHelper extends BaseTestHelper implements WebDriverHolder, WebDriverCleanUp
{

	private static String DEFAULT_VALUE = "false";
	   
   /** Webdriver Instance. */
   protected WebDriverWrapper driver;

   /** Alle im test erzeugten Webdriver */
   protected List<WebDriverWrapper> driversBeingUsedInTest = null;

   /** Die Eigenschaften des Tests. */
   protected WebPropertyManager properties;

   /** Angemeldete User. */
   protected User user = null;

   /** Einstellungen fuer die Performancevermessung der durchgefuehrten Tests mit Dynatrace **/
   private DynatraceMessung dynatraceMessung;

   /**
    * Webdriver anhalten
    */
   protected void stopWebDriver()
   {
      stopWebDriver(driver);
   }

   protected void stopWebDriver(WebDriverWrapper driver)
   {
      WebDriverManager.stopDriver(driver);
      driversBeingUsedInTest.remove(driver);
      this.driver = null;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public WebDriverWrapper getWebDriver()
   {
      return driver;
   }


   /**
    * Zentrale Erzeugung eines neuen Drivers, die Driver instanz darf nur hier neu zugewiesen werden!
    *
    * @param isIE
    *           - ob IE
    * @param url
    *           - URL der Anwendung
 * @return 
    */
   public WebDriverWrapper erzeugeNeuenDriver(String url, Browser browser)
   {

      // initialize Execution Timer
      if (ExecutionTimerManager.getExecutionTimer() == null)
      {
         executionTimer = new ExecutionTimer();
         executionTimer.init(TestUtils.getMethodName());
         ExecutionTimerManager.setExecutionTimer(executionTimer);
      }

      String grid = getProperties().getProperty("seleniumgrid.url");
      String gridHint = "Service Grid";
      WebDriverSettings webDriverSettings = new WebDriverSettings.WebDriverSettingsBuilder(url, isLocal(), browser).setGridHint(gridHint).setSeleniumGridUrl(grid).setCookies(new HashSet<Cookie>()).build();
      driver = WebDriverManager.startDriver(webDriverSettings);
      driversBeingUsedInTest.add(driver);
      aktiviereDynatraceMessung(driver);
      return driver;
   }

   @Override
public String getProperty(String key)
   {
      return getProperties().getProperty(key);
   }

   private WebPropertyManager getProperties()
   {
      if (properties == null)
      {
         throw new AllgemeineTechnischeException("Properties sind null. Start Test nicht aufgerufen?");
      }
      return properties;
   }

   /**
    * @param dateiname
    *           ohne Endung
    */
   protected void screenshotErzeugen(String dateiname)
   {
      new ScreenshotCreator().takeScreenshot(getWebDriver(), dateiname);
   }

  

   /**
    * Initialisieren Dynatrace Messung
    *
    * @param clazz
    *           - die Testklasse
    * @param method
    *           - die Testmethode
    */
   protected void initDynatraceMessung(Class clazz, Method method)
   {
      dynatraceMessung = new DynatraceMessung(clazz.getName(), method.getName());
   }

   protected void aktiviereDynatraceMessung(WebDriverWrapper driver)
   {
      if (dynatraceMessung != null)
      {
         dynatraceMessung.aktivieren(driver);
      }
   }

   protected void beendeDynatraceMessung()
   {
      if (dynatraceMessung != null)
      {
         dynatraceMessung.beenden();
      }
   }

@Override
public void stopWebDrivers() {
    if (driversBeingUsedInTest == null)
    {
       return;
    }
    List<WebDriverWrapper> driversToClose = new ArrayList<>(driversBeingUsedInTest);
    for (WebDriverWrapper webDriverWrapper : driversToClose)
    {
       stopWebDriver(webDriverWrapper);
    }
    driversBeingUsedInTest.clear();
	
}


private static boolean isLocal()
{
   final String remote = System.getProperty("remote", DEFAULT_VALUE);
   final boolean isLocal = remote.equals(DEFAULT_VALUE);
   return isLocal;
}
}
