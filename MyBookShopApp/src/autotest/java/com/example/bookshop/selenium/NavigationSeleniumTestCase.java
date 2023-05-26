package com.example.bookshop.selenium;

import com.example.bookshop.selenium.pages.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class NavigationSeleniumTestCase extends AbstractChromeSeleniumTestCase {

    @Test
    void testMainNavigation() throws InterruptedException {
        GenresPage page = new GenresPage(driver);
        page.callPage()
                .pause()
                .clickToNavigationLink(NavigationLinkType.MAIN)
                .pause();

        assertFalse(driver.getPageSource().contains("404"));
        assertEquals(MainPage.URL, driver.getCurrentUrl());
    }

    @Test
    void testGenresNavigation() throws InterruptedException {
        MainPage page = new MainPage(driver);
        page.callPage()
                .pause()
                .clickToNavigationLink(NavigationLinkType.GENRES)
                .pause();

        assertFalse(driver.getPageSource().contains("404"));
        assertEquals(GenresPage.URL, driver.getCurrentUrl());
    }

    @Test
    void testRecentNavigation() throws InterruptedException {
        MainPage page = new MainPage(driver);
        page.callPage()
                .pause()
                .clickToNavigationLink(NavigationLinkType.RECENT)
                .pause();

        assertFalse(driver.getPageSource().contains("404"));
        assertEquals(RecentPage.URL, driver.getCurrentUrl());
    }

    @Test
    void testPopularsNavigation() throws InterruptedException {
        MainPage page = new MainPage(driver);
        page.callPage()
                .pause()
                .clickToNavigationLink(NavigationLinkType.POPULARS)
                .pause();

        assertFalse(driver.getPageSource().contains("404"));
        assertEquals(PopularPage.URL, driver.getCurrentUrl());
    }

    @Test
    void testAuthorsNavigation() throws InterruptedException {
        MainPage page = new MainPage(driver);
        page.callPage()
                .pause()
                .clickToNavigationLink(NavigationLinkType.AUTHORS)
                .pause();

        assertFalse(driver.getPageSource().contains("404"));
        assertEquals(AuthorsPage.URL, driver.getCurrentUrl());
    }

}
