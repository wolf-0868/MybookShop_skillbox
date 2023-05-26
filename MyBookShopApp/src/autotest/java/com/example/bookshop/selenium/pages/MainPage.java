package com.example.bookshop.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MainPage extends AbstractPage<MainPage> {

    public static final String URL = "http://localhost:8085/";

    public MainPage(RemoteWebDriver aDriver) {
        super(aDriver);
    }

    @Override
    public String getUrl() {
        return URL;
    }

    public MainPage submitBookSlug() {
        driver.findElement(By.className("Slider-content"))
                .findElement(By.className("Card-picture"))
                .click();
        return this;
    }

    public String getUrlBookSlug() {
        WebElement element = driver.findElement(By.className("Slider-content"))
                .findElement(By.className("Card-picture"));
        return element.getAttribute("href");
    }

}
