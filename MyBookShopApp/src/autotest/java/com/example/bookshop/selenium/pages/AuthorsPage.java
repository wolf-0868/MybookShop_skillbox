package com.example.bookshop.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorsPage extends AbstractPage<AuthorsPage> {

    public static final String URL = "http://localhost:8085/authors";

    public AuthorsPage(RemoteWebDriver aDriver) {
        super(aDriver);
    }

    @Override
    public String getUrl() {
        return URL;
    }

    public AuthorsPage submitAuthorSlug() {
        driver.findElement(By.className("Authors-item"))
                .findElement(By.tagName("a"))
                .click();
        return this;
    }

    public String getUrlAuthorSlug() {
        WebElement element = driver.findElement(By.className("Authors-item"))
                .findElement(By.tagName("a"));
        return element.getAttribute("href");
    }

}
