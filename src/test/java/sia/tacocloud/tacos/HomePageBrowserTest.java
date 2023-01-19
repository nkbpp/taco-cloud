package sia.tacocloud.tacos;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

//https://habr.com/ru/post/502292/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomePageBrowserTest {

    @LocalServerPort
    private int port;
    private static HtmlUnitDriver browser;

    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();

        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterAll
    public static void teardown() {
        browser.quit();
    }

    @Test
    public void testHomePage() {
        String homePage = "http://localhost:" + port;
        browser.get(homePage);

        String titleText = browser.getTitle();
        assertThat(titleText).isEqualTo("Taco Cloud");

        String h1Text = browser.findElement(new By.ByTagName("h1")).getText();
        assertThat(h1Text).isEqualTo("Welcome to...");


        String imgSrc = browser.findElement(new By.ByTagName("img"))
                .getAttribute("src");
        assertThat(imgSrc).isEqualTo(homePage + "/images/TacoCloud.png");
    }

}
