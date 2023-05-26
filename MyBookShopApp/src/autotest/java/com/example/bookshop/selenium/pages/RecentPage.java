package com.example.bookshop.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class RecentPage extends AbstractPage<RecentPage> {

    public static final String URL = "http://localhost:8085/books/recent";

    public RecentPage(RemoteWebDriver aDriver) {
        super(aDriver);
    }

    @Override
    public String getUrl() {
        return URL;
    }

    public RecentPage submitBookSlug() {
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
