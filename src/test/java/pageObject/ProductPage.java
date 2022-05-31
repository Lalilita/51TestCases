package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.Constants;
import utilities.Driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductPage {

    WebDriver driver = Driver.getDriver();
    Homepage homepage = new Homepage();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public ProductPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@type='submit']")
    public WebElement addToBasketBtn;

    @FindBy(xpath = "//*[@class='input-text qty text']")
    public WebElement qtyBox;


    public void inputInvalidQuantity() {
        String maxStock = qtyBox.getAttribute("max");
        int maxStockPlusOne = Integer.parseInt(maxStock) + 1;
        qtyBox.clear();
        qtyBox.sendKeys(String.valueOf(maxStockPlusOne));
        homepage.addToBasketBtn.click();
        Boolean isValidInput = (Boolean) js.executeScript("return arguments[0].checkValidity();", qtyBox);
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", qtyBox);
        String expectedThrowMessage = "Value must be less than or equal to " + maxStock + ".";
        Assert.assertFalse(isValidInput);
        Assert.assertEquals(expectedThrowMessage, validationMessage);
        System.out.println("Validation message have been thrown");
    }
}