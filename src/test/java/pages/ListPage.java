package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ListPage extends BasePage {

    private final By listOfElementsLocator = By.className("elements");
    private final By elementLocator = By.tagName("li");

    public boolean hasElements() {
        WebElement listOfElements = driver.findElement(listOfElementsLocator);
        return listOfElements.findElements(elementLocator).size() > 0;
    }

}
