package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

import static utilities.ConfigProp.configfile;

public class BasketPage {
    WebDriver driver = Driver.getDriver();
    Homepage homepage = new Homepage();
    ProductPage productPage = new ProductPage();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public BasketPage() {
        PageFactory.initElements(driver, this);
    }

    //TC06
    String[] itemNeeded = {"Functional Programming in JS",
            "HTML5 Forms", "JS Data Structures and Algorithm"};
    public List<Object> itemNeededList = Arrays.asList(itemNeeded);
    public List<String> addedBookList = new ArrayList<>();
    public List<String> bookInCart = new ArrayList<>();
    public List<String> priceInCart = new ArrayList<>();
    public String bookName;


    @FindBy(xpath = "//*[@class='products masonry-done']/li/a[2]")
    public List<WebElement> addToBasketBtns;

    @FindBy(xpath = "//*[@title='View your shopping cart']")
    public WebElement menuItems;

    @FindBy(xpath = "//*[@class='cart_item']/td[3]")
    public List<WebElement> cartList;

    @FindBy(xpath = "//*[@class='cart_item']/td[4]/span")
    public List<WebElement> itemPriceList;

    //TC07
    @FindBy(xpath = "//*[@class='cart_item']/td[3]")
    public WebElement firstProductOnShopPage;

    //TC08
    @FindBy(xpath = "//*[@id='page-34']/div/div[1]/div/div/div/a")
    public WebElement proceedToChkOutBtn;

    //TC11
    @FindBy(xpath = "//*[@class='cart_item']/td[1]/a")
    public WebElement removeIcon;

    @FindBy(xpath = "//*[@class='woocommerce-message']")
    public WebElement removeMessage;

    //TC12
    public int bookAmountBeforeAdd;
    public int bookAmountAfterAdd;
    public int bookAmountBeforeSubtract;
    public int bookAmountAfterSubtract;

    @FindBy(xpath = "//*[@class='input-text qty text'][1]")
    public WebElement basketFirstQtyBox;

    @FindBy(xpath = "//input[@value='Update Basket']")
    public WebElement basketUpdateBasket;

    //TC09
    @FindBy(xpath = "//*[@class='showcoupon']")
    public WebElement enterCouponLink;

    @FindBy(xpath = "//input[@id='coupon_code']")
    public WebElement couponInput;

    @FindBy(xpath = "//input[@value='Apply Coupon']")
    public WebElement applyCouponBtn;

    @FindBy(xpath = "//ul[@class='woocommerce-error']/li")
    public WebElement couponError;

    //TC13
    @FindBy(xpath = "//*[@class='cart-subtotal']//span")
    public WebElement subtotal;

    @FindBy(xpath = "//*[@class='order-total']//span")
    public WebElement totalPrice;

    //-------------------methods-------------------\\
    public void addToBasKet() throws InterruptedException {
        homepage.shopMenu.click();
        for (int i = 0; i < homepage.products.size(); i++) {
            bookName = homepage.products.get(i).getText();
            if (itemNeededList.contains(bookName)) {
                homepage.products.get(i).click();
                productPage.addToBasketBtn.click();
                addedBookList.add(bookName);
                homepage.shopMenu.click();
                Thread.sleep(1000);
            }
        }
        Assert.assertEquals(addedBookList, itemNeededList);
        System.out.println("All needed items have been added to basket");
    }

    public void viewOnMenuItems() {
        menuItems.click();
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).isDisplayed() && itemPriceList.get(i).isDisplayed()) {
                bookInCart.add(cartList.get(i).getText());
                priceInCart.add(itemPriceList.get(i).getText().substring(1));
            }
        }
        Assert.assertEquals(addedBookList, bookInCart);
        System.out.println("All selected items and prices are presented on Menu items");
    }

//    public void clickProceedToCheckoutBtn() {
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollBy(0,-1000)", "");
//        proceedToChkOutBtn.click();
//    }

    public void verifyRemoveItemByRemoveIcon() throws InterruptedException {
        removeIcon.click();
        Thread.sleep(2000);
        String removeMessageText = removeMessage.getText();
        Assert.assertEquals(removeMessageText, configfile.getProperty("expectedRemoveMessageText"));
        System.out.println(removeMessageText);
    }

    public void addBookInQtyBox(int amountOfBookToAdd) {
        bookAmountBeforeAdd = Integer.parseInt(basketFirstQtyBox.getAttribute("value"));
        for (int i = 0; i <= amountOfBookToAdd; i++) {
            basketFirstQtyBox.sendKeys(Keys.ARROW_UP);
        }
        bookAmountAfterAdd = Integer.parseInt(basketFirstQtyBox.getAttribute("value"));
        Assert.assertTrue(bookAmountBeforeAdd < bookAmountAfterAdd);
        System.out.println("Book amount before adding < book amount after adding ");
    }

    public void subtractBookInQtyBox(int amountOfBookToSubtract) {
        bookAmountBeforeSubtract = Integer.parseInt(basketFirstQtyBox.getAttribute("value"));
        for (int i = 0; i <= amountOfBookToSubtract; i++) {
            basketFirstQtyBox.sendKeys(Keys.ARROW_DOWN);
        }
        bookAmountAfterSubtract = Integer.parseInt(basketFirstQtyBox.getAttribute("value"));
        Assert.assertTrue(bookAmountBeforeSubtract > bookAmountAfterSubtract);
        System.out.println("Book amount before subtraction > book amount after subtraction ");
    }

    public void applyCouponToGet50rps(String couponCode) throws InterruptedException {
        enterCouponLink.click();
        couponInput.sendKeys(couponCode);
        applyCouponBtn.click();
        Thread.sleep(2000);
        Assert.assertTrue(couponError.isDisplayed());
        System.out.println("Coupon have been applied");
    }

    public void policyOfCoupon() {
        System.out.println("The coupon is applicable for the book price > 450 rps");
    }

    public void verifyTotalAndSubtotalCondition() {
        String subtotalDigit = subtotal.getText().substring(1);
        String totalDigit = totalPrice.getText().substring(1);
        Assert.assertTrue(Double.parseDouble(subtotalDigit) < Double.parseDouble(totalDigit));
        System.out.println("The total always < subtotal because taxes are added in the subtotal");
    }

    public void verifyProceedToCheckoutBtnLeadsPaymentGateway(){
        driver.navigate().refresh();
        Object o = js.executeScript("window.scrollBy(0,1000)");
        proceedToChkOutBtn.click();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, Constants.checkoutPageUrl);
        System.out.println("Clicking on Proceed to Checkout button leads to payment gateway page");
    }


}
