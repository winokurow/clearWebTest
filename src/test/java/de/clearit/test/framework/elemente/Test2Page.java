package de.clearit.test.framework.elemente;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import de.clearit.test.framework.Check;
import de.clearit.test.framework.WebPageObject;

public class Test2Page extends WebPageObject {
	@Check
	WebBaseElement message = new WebBaseElement(By.id("message"));

	public Test2Page(WebDriver driver) {
		this.title = "Seite2";
		this.driver = driver;
		this.waitForMainElementsIsShown();
	}
}
