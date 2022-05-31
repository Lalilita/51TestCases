package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.Driver;

import java.util.List;

public class OrderInfoPage {
    WebDriver driver = Driver.getDriver();
    Homepage homepage = new Homepage();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public OrderInfoPage() {
        PageFactory.initElements(driver, this);
    }

    //TC18
    @FindBy(xpath = "//*[@class='woocommerce']")
    public WebElement confirmHeader;

    @FindBy(xpath = "//*[@class='order']")
    public WebElement orderNumber;

    @FindBy(xpath = "//*[@class='woocommerce-thankyou-order-details order_details']/li")
    public List<WebElement> orderDetails;

    @FindBy(xpath = "//*[@class='date']")
    public WebElement orderDate;

    @FindBy(xpath = "//*[@class='total']")
    public WebElement orderTotal;

    @FindBy(xpath = "//*[@class='method']")
    public WebElement paymentMethod;

    @FindBy(xpath = "//*[@class='woocommerce']/p[2]")
    public WebElement paymentStatus;

    @FindBy(xpath = "//*[@class='order_item']")
    public WebElement orderItem;

    @FindBy(xpath = "//*[@class='shop_table order_details']/thead/tr")
    public List<WebElement> headDetail;

    @FindBy(xpath = "//*[@class='shop_table order_details']/tbody/tr")
    public List<WebElement> bodyDetail;

    @FindBy(xpath = "//*[@class='shop_table order_details']/tfoot/tr")
    public List<WebElement> footDetail;

    public void verifyOrderInformation() {
        boolean orderInformationIsDisplayed = (confirmHeader.isDisplayed() && orderNumber.isDisplayed() &&
                orderDate.isDisplayed() && orderTotal.isDisplayed() &&
                paymentMethod.isDisplayed() && orderItem.isDisplayed() &&
                paymentStatus.isDisplayed());
        Assert.assertTrue(orderInformationIsDisplayed);
        System.out.println("--------------- Order Details ---------------");

        String orderDetailText, headText, bodyText, footText;
        for (WebElement webElement : orderDetails) {
            orderDetailText = webElement.getText();
            System.out.println(orderDetailText + " ");
        }
        System.out.println("-----------------------------------------");

        for (WebElement webElement : headDetail) {
            headText = webElement.getText();
            System.out.println(headText + ":");
        }

        for (WebElement webElement : bodyDetail) {
            bodyText = webElement.getText();
            System.out.println(bodyText);
        }
        System.out.println("-----------------------------------------");

        for (WebElement webElement : footDetail) {
            footText = webElement.getText();
            System.out.println(footText);
        }
    }


}
