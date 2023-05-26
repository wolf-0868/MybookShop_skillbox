package com.example.bookshop.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class AbstractPage<T extends AbstractPage> {

    protected final RemoteWebDriver driver;

    public AbstractPage(RemoteWebDriver aDriver) {
        driver = aDriver;
    }

    public abstract String getUrl();

    public T callPage() {
        driver.get(getUrl());
        return (T) this;
    }

    public T pause() throws InterruptedException {
        Thread.sleep(2000);
        return (T) this;
    }

    public T setUpSearchToken(String aToken) {
        driver.findElement(By.id("query")).sendKeys(aToken);
        return (T) this;
    }

    public T submitSearch() {
        driver.findElement(By.id("search")).submit();
        return (T) this;
    }

    public T clickToNavigationLink(NavigationLinkType aType) {
        driver.findElement(By.xpath(aType.xPath)).click();
        return (T) this;
    }

    public RemoteWebDriver getDriver() {
        return driver;
    }

}
