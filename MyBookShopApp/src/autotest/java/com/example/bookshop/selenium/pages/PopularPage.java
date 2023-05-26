package com.example.bookshop.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PopularPage extends AbstractPage<PopularPage> {

    public static final String URL = "http://localhost:8085/books/popular";

    public PopularPage(RemoteWebDriver aDriver) {
        super(aDriver);
    }

    @Override
    public String getUrl() {
        return URL;
    }

    public PopularPage submitBookSlug() {
        driver.findElement(By.className("Card"))
                .findElement(By.className("Card-title"))
                .findElement(By.tagName("a"))
                .click();
        return this;
    }

    public String getUrlBookSlug() {
        WebElement element = driver.findElement(By.className("Card"))
                .findElement(By.className("Card-title"))
                .findElement(By.tagName("a"));
        return element.getAttribute("href");
    }


}
