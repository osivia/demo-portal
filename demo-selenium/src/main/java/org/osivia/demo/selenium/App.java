package org.osivia.demo.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Initalizer application
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Initializer");
        System.setProperty("webdriver.firefox.marionette","/opt/geckodriver");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://demo-intra.osivia.org");
        System.out.println(driver.getTitle());
    }
}
