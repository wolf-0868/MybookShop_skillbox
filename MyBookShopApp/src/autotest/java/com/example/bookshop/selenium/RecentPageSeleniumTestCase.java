package com.example.bookshop.selenium;

import com.example.bookshop.selenium.pages.RecentPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecentPageSeleniumTestCase extends AbstractChromeSeleniumTestCase {

    @Test
    void testAccess() throws InterruptedException {
        RecentPage page = new RecentPage(driver);
        page.callPage()
                .pause();

        assertTrue(driver.getPageSource()
                .contains("BOOKSHOP"));
    }

    @Test
    @Disabled
    void testBookSlugPage() throws InterruptedException {
        RecentPage page = new RecentPage(driver);
        String urlBook = page
                .callPage()
                .pause()
                .getUrlBookSlug();
        page.submitBookSlug()
                .pause();

        assertEquals(driver.getCurrentUrl(), urlBook);
        assertFalse(driver.getPageSource()
                .contains("404"));
    }

}
