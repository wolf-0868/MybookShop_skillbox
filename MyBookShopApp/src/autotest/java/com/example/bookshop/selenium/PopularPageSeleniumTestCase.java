package com.example.bookshop.selenium;

import com.example.bookshop.selenium.pages.PopularPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PopularPageSeleniumTestCase extends AbstractChromeSeleniumTestCase {

    @Test
    void testAccess() throws InterruptedException {
        PopularPage page = new PopularPage(driver);
        page.callPage()
                .pause();

        assertTrue(driver.getPageSource()
                .contains("BOOKSHOP"));
    }

    @Test
    void testBookSlugPage() throws InterruptedException {
        PopularPage page = new PopularPage(driver);
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
