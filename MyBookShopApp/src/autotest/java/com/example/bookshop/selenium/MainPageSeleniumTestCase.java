package com.example.bookshop.selenium;

import com.example.bookshop.selenium.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainPageSeleniumTestCase extends AbstractChromeSeleniumTestCase {

    @Test
    void testAccess() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.callPage()
                .pause();

        assertTrue(driver.getPageSource()
                .contains("BOOKSHOP"));
    }

    @Test
    void testSearchByQuery() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.callPage()
                .pause()
                .setUpSearchToken("Trumbo")
                .pause()
                .submitSearch()
                .pause();

        assertTrue(driver.getPageSource()
                .contains("Trumbo"));
    }

    @Test
    void testBookSlugPage() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        String urlBook = mainPage.callPage()
                .pause()
                .getUrlBookSlug();
        mainPage.submitBookSlug()
                .pause();

        assertEquals(driver.getCurrentUrl(), urlBook);
        assertFalse(driver.getPageSource().contains("404"));
    }

}