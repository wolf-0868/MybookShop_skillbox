package com.example.bookshop.selenium;

import com.example.bookshop.selenium.pages.AuthorsPage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorPageSeleniumTestCase extends AbstractChromeSeleniumTestCase {

    @Test
    void testAccess() throws InterruptedException {
        AuthorsPage page = new AuthorsPage(driver);
        page.callPage()
                .pause();

        assertTrue(driver.getPageSource()
                .contains("BOOKSHOP"));
    }

    @Test
    void testAuthorSlugPage() throws InterruptedException {
        AuthorsPage page = new AuthorsPage(driver);
        String urlGenres = page.callPage()
                .pause()
                .getUrlAuthorSlug();
        page.submitAuthorSlug()
                .pause();

        assertEquals(driver.getCurrentUrl(), urlGenres);
        assertFalse(driver.getPageSource()
                .contains("404"));
    }

}
