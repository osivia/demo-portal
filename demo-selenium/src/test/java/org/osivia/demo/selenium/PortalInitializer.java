package org.osivia.demo.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class PortalInitializer {
    public static void main(String [] args)
    {
        System.setProperty("webdriver.firefox.marionette","/opt/geckodriver");
        WebDriver browser = new FirefoxDriver();
        browser.get("https://demo-intra.osivia.org");
        System.out.println(browser.getTitle());
    }
}
