package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObject.*;
import utilities.ConfigProp;
import utilities.Constants;
import utilities.Driver;

import static utilities.ConfigProp.configfile;

public class HomepageTest {
    WebDriver driver;
    String currentUrl;
    Homepage homepage = new Homepage();
    ProductPage productPage = new ProductPage();
    BasketPage basketpage = new BasketPage();
    CheckoutPage checkoutpage = new CheckoutPage();
    OrderInfoPage orderinfopage = new OrderInfoPage();


    @BeforeSuite
    public void goToAutomationPracticeSite() {
        WebDriverManager.chromedriver().setup();
        driver = Driver.getDriver();
    }

    @BeforeClass
    public void goToHomepage() {

        homepage.goToHomePage();
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("--------- HOME PAGE TEST DONE -----------");
    }

    @Test(priority = 1)
    public void TC01_homePageWithThreeSliders() {
        Assert.assertEquals(homepage.sliders.size(), Constants.sliderCount);
    }

    @Test(priority = 2)
    public void TC02_homPageWithThreeArrivals() {
        Assert.assertEquals(homepage.arrivals.size(), Constants.arrivalCount);
    }

    @Test(priority = 3)
    public void TC03_homePageImagesArrivalShouldNegative() {
        homepage.verifyImageArrivalShouldNavigate();
    }

    @Test(priority = 4)
    public void TC04_homePageArrivalsImageDescription() {
        homepage.verifyArrivalHasDescription();
    }

    @Test(priority = 5)
    public void TC05_homePageArrivalsImagesReviews() {
        homepage.verifyArrivalHasReview();
    }

    @Test(priority = 6)
    public void TC06_homePageArrivalsImagesAddToBasket() throws InterruptedException {
        //since the product in arrivals are out of stock,
        //I select some products on shop page to test adding to basket.
        basketpage.addToBasKet();
        basketpage.viewOnMenuItems();
    }

    @Test(priority = 7, dependsOnMethods = "TC06_homePageArrivalsImagesAddToBasket")
    public void TC07_homePageArrivalsAddToBasketWithMoreBook() {
        basketpage.firstProductOnShopPage.click();
        productPage.inputInvalidQuantity();
        basketpage.menuItems.click();
    }

    @Test(priority = 8, dependsOnMethods = "TC06_homePageArrivalsImagesAddToBasket")
    public void TC08_homeArrivalsAddToBasketItems() {
        basketpage.proceedToChkOutBtn.click();
        currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, Constants.checkoutPageUrl);
    }

    @Test(priority = 9, dependsOnMethods = {"TC06_homePageArrivalsImagesAddToBasket", "TC08_homeArrivalsAddToBasketItems"})
    public void TC09_homeArrivalsAddToBasketItemsCoupon() throws InterruptedException {
        basketpage.applyCouponToGet50rps(configfile.getProperty("couponCode"));
        System.out.println(basketpage.couponError.getText());
    }

    @Test(priority = 10, dependsOnMethods = {"TC06_homePageArrivalsImagesAddToBasket", "TC08_homeArrivalsAddToBasketItems",
            "TC09_homeArrivalsAddToBasketItemsCoupon"})
    public void TC10_homeArrivalsAddToBasketItemsCouponValueLessThan450() {
        basketpage.policyOfCoupon();
    }

    @Test(priority = 11, dependsOnMethods = "TC06_homePageArrivalsImagesAddToBasket")
    public void TC11_homeArrivalsAddToBasketItemsRemoveBook() throws InterruptedException {
        basketpage.menuItems.click();
        basketpage.verifyRemoveItemByRemoveIcon();
    }

    @Test(priority = 12, dependsOnMethods = "TC06_homePageArrivalsImagesAddToBasket")
    public void TC12_homeArrivalsAddToBasketItemsAddBook() {
        basketpage.addBookInQtyBox(Constants.testAddBookCount);
        basketpage.subtractBookInQtyBox(Constants.testSubtractBookCount);
        basketpage.basketUpdateBasket.click();
    }

    @Test(priority = 13, dependsOnMethods = "TC06_homePageArrivalsImagesAddToBasket")
    public void TC13_homeArrivalsAddToBasketItemsCheckOutBookFinalPrice() {
        Assert.assertTrue(basketpage.totalPrice.isDisplayed());
    }

    @Test(priority = 14, dependsOnMethods = "TC12_homeArrivalsAddToBasketItemsAddBook")
    public void TC14_homeArrivalsAddToBasketItemsCheckOutUpdateBasket() {
        //same steps as TC12
    }

    @Test(priority = 15, dependsOnMethods = {"TC06_homePageArrivalsImagesAddToBasket",
            "TC13_homeArrivalsAddToBasketItemsCheckOutBookFinalPrice"})
    public void TC15_homeArrivalsAddToBasketItemsCheckOutToTalAndSubtotalCondition() {
        basketpage.verifyTotalAndSubtotalCondition();
    }

    @Test(priority = 16, dependsOnMethods = {"TC06_homePageArrivalsImagesAddToBasket",
            "TC13_homeArrivalsAddToBasketItemsCheckOutBookFinalPrice",
            "TC15_homeArrivalsAddToBasketItemsCheckOutToTalAndSubtotalCondition"})
    public void TC16_homeArrivalsAddToBasketItemsCheckOutFunctionality() {
        basketpage.verifyProceedToCheckoutBtnLeadsPaymentGateway();
    }

    @Test(priority = 17, dependsOnMethods = {"TC06_homePageArrivalsImagesAddToBasket",
            "TC13_homeArrivalsAddToBasketItemsCheckOutBookFinalPrice",
            "TC15_homeArrivalsAddToBasketItemsCheckOutToTalAndSubtotalCondition",
            "TC16_homeArrivalsAddToBasketItemsCheckOutFunctionality"})
    public void TC17_homeArrivalsAddToBasketItemsCheckOutPaymentGateway() throws InterruptedException {
        driver.navigate().refresh();
        checkoutpage.verifyCouponApplySessionPresentInPaymentGateway();
        checkoutpage.verifyBillingDetails();
        checkoutpage.verifyOrderDetails();
        checkoutpage.verifyAdditionalDetails();
        checkoutpage.verifyPaymentGatewayDetails();
        checkoutpage.fillOutBillingDetails();
        checkoutpage.fillOutAdditionalDetail(ConfigProp.getProperty("additionalDetail"));
        checkoutpage.optPayment(ConfigProp.getProperty("selectPayment3"));
    }

    @Test(priority = 18, dependsOnMethods = {"TC06_homePageArrivalsImagesAddToBasket",
            "TC13_homeArrivalsAddToBasketItemsCheckOutBookFinalPrice",
            "TC15_homeArrivalsAddToBasketItemsCheckOutToTalAndSubtotalCondition",
            "TC16_homeArrivalsAddToBasketItemsCheckOutFunctionality",
            "TC17_homeArrivalsAddToBasketItemsCheckOutPaymentGateway"})
    public void TC18_homeArrivalsAddToBasketItemsCheckOutPaymentGatewayPlaceOrder() {
        checkoutpage.placeOrderBtn.click();
        System.out.println(driver.getTitle());
        orderinfopage.verifyOrderInformation();
    }
}
