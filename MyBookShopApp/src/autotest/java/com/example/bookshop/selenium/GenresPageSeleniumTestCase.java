package com.example.bookshop.selenium;

import com.example.bookshop.selenium.pages.GenresPage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GenresPageSeleniumTestCase extends AbstractChromeSeleniumTestCase {

    @Test
    @Order(1)
    void testAccess() throws InterruptedException {
        GenresPage page = new GenresPage(driver);
        page.callPage()
                .pause();

        assertTrue(driver.getPageSource()
                .contains("BOOKSHOP"));
    }

    @Test
    @Order(2)
    void testGenresSlugPage() throws InterruptedException {
        GenresPage page = new GenresPage(driver);
        String urlGenres = page.callPage()
                .pause()
                .getUrlGenreSlug();
        page.submitGenreSlug()
                .pause();

        assertEquals(driver.getCurrentUrl(), urlGenres);
        assertFalse(driver.getPageSource()
                .contains("404"));
    }

    @Test
    @Order(3)
    void testBookSlugPage() throws InterruptedException {
        GenresPage page = new GenresPage(driver);
        String urlBook = page
                .pause()
                .getUrlBookSlug();
        page.submitBookSlug()
                .pause();

        assertEquals(driver.getCurrentUrl(), urlBook);
        assertFalse(driver.getPageSource()
                .contains("404"));
    }

}
