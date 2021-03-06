package de.clearit.test.framework.elemente;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import de.clearit.test.framework.Check;
import de.clearit.test.framework.ExecutionTimer;
import de.clearit.test.framework.ExecutionTimerManager;
import de.clearit.test.framework.WebPageObject;

public class TestCheckboxPage extends WebPageObject {
	@Check
	WebBaseElement lastname = new WebBaseElement(By.id("lastname"));

	WebBaseElement firstname = new WebBaseElement(By.id("firstname"));

	public TestCheckboxPage(WebDriver driver) {
		this.driver = driver;
		ExecutionTimer exec = new ExecutionTimer();
		exec.init("timmm");
		ExecutionTimerManager.setExecutionTimer(exec);

	}
}
