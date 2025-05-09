package org.example.demo2;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MainPageTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.bing.com/");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void search() {
        String input = "Selenium";
        WebElement searchField = driver.findElement(By.cssSelector("#sb_form_q"));
        searchField.sendKeys(input);
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.attributeContains(By.xpath("//a[contains(@class ,'tilk')]"), "href", "selenium"),
                ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class ,'tilk')]"))
        ));

        List<WebElement> results = driver.findElements(By.xpath("//a[contains(@class ,'tilk')]"));

        clickElement(results, 0);

        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        if (tabs.size() > 1) driver.switchTo().window(tabs.get(1));

        String expectedUrl = "https://www.selenium.dev/";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl, "URL страницы не соответствует ожидаемому");
    }

    public void clickElement(List<WebElement> results, int num) {
        results.get(num).click();
        System.out.println("Произведен клик по первой ссылке результатов поиска");
    }
}
