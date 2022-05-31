package pageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.Constants;
import utilities.Driver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Homepage {

    WebDriver driver = Driver.getDriver();

    public Homepage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id='menu-item-40']")
    public WebElement shopMenu;

    @FindBy(xpath = "//*[@class='woocommerce-breadcrumb']/a")
    public WebElement homeMenu;

    //TC01
    @FindBy(xpath = "//*[@class='n2-ss-slider-3']/div")
    public List<WebElement> sliders;

    //TC02
    String arrivalPath;

    @FindBy(xpath = "//*[@class='themify_builder_sub_row clearfix gutter-default   sub_row_1-0-2']/div")
    public List<WebElement> arrivals;

    //TC04
    @FindBy(xpath = "//*[@id='tab-description']//p")
    public WebElement description;

    //TC05
    @FindBy(xpath = "//*[@class='reviews_tab']")
    public WebElement reviewTab;

    @FindBy(xpath = "//*[@id='comments']/p")
    public WebElement review;

    //TC06
    @FindBy(xpath = "//*[@class='products masonry-done']/li/a/h3")
    public List<WebElement> products;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement addToBasketBtn;


    //-------------------methods-------------------\\
    public void goToHomePage() {
        shopMenu.click();
        homeMenu.click();
    }

    //TC03
    public void verifyImageArrivalShouldNavigate() {
        List<String> arrivalTitleList = new ArrayList<String>(Arrays.asList(
                Constants.arrival1Title, Constants.arrival2Title, Constants.arrival3Title));
        String currentTitle;

        for (int i = 1; i <= arrivals.size(); i++) {
            arrivalPath = String.format("//*[@class='themify_builder_sub_row clearfix gutter-default   sub_row_1-0-2']/div[%d]", i);
            driver.findElement(By.xpath(arrivalPath)).click();
            currentTitle = driver.getTitle();
            Assert.assertEquals(currentTitle, arrivalTitleList.get(i - 1));
            driver.navigate().back();
        }
    }

    public void verifyImagesInArrivalsShouldNavigate_URL() {
        String url1 = "http://practice.automationtesting.in/product/selenium-ruby/";
        String url2 = "http://practice.automationtesting.in/product/thinking-in-html/";
        String url3 = "http://practice.automationtesting.in/product/mastering-javascript/";
        List<String> urls = new ArrayList<String>(Arrays.asList(url1, url2, url3));
        for (int i = 0; i < arrivals.size(); i++) {
            WebElement eachItem = arrivals.get(i);
            eachItem.click();
            Assert.assertTrue(Driver.getDriver().getCurrentUrl().equalsIgnoreCase(urls.get(i)));
            Driver.getDriver().navigate().back();
        }
    }

    //TC04
    public void verifyArrivalHasDescription() {
        for (int i = 1; i <= arrivals.size(); i++) {
            arrivalPath = String.format("//*[@class='themify_builder_sub_row clearfix gutter-default   sub_row_1-0-2']/div[%d]", i);
            driver.findElement(By.xpath(arrivalPath)).click();
            Assert.assertTrue(description.isDisplayed());
            driver.navigate().back();
        }
    }

    //TC05
    public void verifyArrivalHasReview() {
        for (int i = 1; i <= arrivals.size(); i++) {
            arrivalPath = String.format("//*[@class='themify_builder_sub_row clearfix gutter-default   sub_row_1-0-2']/div[%d]", i);
            driver.findElement(By.xpath(arrivalPath)).click();
            reviewTab.click();
            Assert.assertTrue(review.isDisplayed());
            driver.navigate().back();
        }
    }
}
