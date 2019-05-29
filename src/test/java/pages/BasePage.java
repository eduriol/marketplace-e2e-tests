package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

    public static WebDriver driver;

    public void load(String baseURL) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(baseURL);
        driver.manage().window().maximize();
    }

    public void moveTo(String url) {
        driver.get(url);
    }

    public void clickOnLink(String linkText) {
        driver.findElement(By.partialLinkText(linkText)).click();
    }
}
