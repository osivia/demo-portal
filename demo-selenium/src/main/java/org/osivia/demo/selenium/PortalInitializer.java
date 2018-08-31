package org.osivia.demo.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Initalizer application
 *
 */
public class PortalInitializer {

    public static void main(String[] args) {
        if (args.length == 1) {
            System.out.println("Initializer " + args[0]);
            WebDriver driver = new FirefoxDriver();
            driver.get(args[0]);
            System.out.println(driver.getTitle());
        } else {
            System.out.println("java org.osivia.demo.selenium.PortalInitializer url");
        }
    }
}
