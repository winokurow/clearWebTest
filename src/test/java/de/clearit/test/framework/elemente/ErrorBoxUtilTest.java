package de.clearit.test.framework.elemente;

import java.io.File;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;

import de.clearit.test.framework.ErrorBoxUtil;

public class ErrorBoxUtilTest {
	@Test
	public void testErrorBoxUtil() {
		WebDriver driver = initialize("page2");
		Assert.assertEquals(ErrorBoxUtil.holeAktuellenErrorText(driver, 10), "Hallo");
	}

	private WebDriver initialize(String page) {
		WebDriver driver = new HtmlUnitDriver(true);
		File file = new File("src/test/resources/" + page + ".html");
		driver.get("file:///" + file.getAbsolutePath().replace("\\", "/"));
		return driver;
	}

}
