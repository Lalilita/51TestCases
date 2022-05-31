package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.ConfigProp;
import utilities.Constants;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;


public class CheckoutPage {

    WebDriver driver = Driver.getDriver();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public CheckoutPage() {
        PageFactory.initElements(driver, this);
    }

    //TC17
    public List<String> billingDetailList = new ArrayList<>();
    public List<String> orderDetailList = new ArrayList<>();
    public List<String> additionalDetailList = new ArrayList<>();
    public List<String> paymentGatewayDetailList = new ArrayList<>();

    @FindBy(xpath = "//*[@class='woocommerce-billing-fields']//label")
    public List<WebElement> billingDetails;

    @FindBy(xpath = "//*[@class='shop_table woocommerce-checkout-review-order-table']//th")
    public List<WebElement> orderDetails;

    @FindBy(xpath = "//*[@class='woocommerce-shipping-fields']/p/label")
    public List<WebElement> additionalDetails;

    @FindBy(xpath = "//*[@class='wc_payment_methods payment_methods methods']/li/label")
    public List<WebElement> paymentGatewayDetails;

    @FindBy(xpath = "//*[@id='billing_first_name']")
    public WebElement fNameInput;

    @FindBy(xpath = "//*[@id='billing_last_name']")
    public WebElement lNameInput;

    @FindBy(xpath = "//*[@id='billing_email']")
    public WebElement emailInput;

    @FindBy(xpath = "//*[@id='billing_phone']")
    public WebElement phoneInput;

    @FindBy(xpath = "//*[@id='s2id_billing_country']//span[2]/b")
    public WebElement countryDropDown;

    @FindBy(xpath = "//*[@id='s2id_autogen1_search']")
    public WebElement countryBox;

    @FindBy(xpath = "//*[@id='select2-results-1']/li[236]") //United Stated (US)
    public WebElement countrySelect;

    @FindBy(xpath = "//*[@id='billing_address_1']")
    public WebElement addressInput;

    @FindBy(xpath = "//*[@id='billing_city']")
    public WebElement cityInput;

    @FindBy(xpath = "//*[@id='s2id_billing_state']/a/span[2]/b")
    public WebElement stateDropDown;

    @FindBy(xpath = "//*[@id='s2id_autogen2_search']")
    public WebElement stateBox;

    @FindBy(xpath = "//*[@id='select2-results-2']/li[10]") //Florida
    public WebElement stateSelect;

    @FindBy(xpath = "//*[@id='billing_postcode']")
    public WebElement zipInput;

    @FindBy(xpath = "//*[@id='order_comments']")
    public WebElement orderNotesBox;

    @FindBy(xpath = "//*[@id='payment_method_bacs']")
    public WebElement directBankRadioBtn;

    @FindBy(xpath = "//*[@id='payment_method_cheque']")
    public WebElement chkPaymentRadioBtn;

    @FindBy(xpath = "//*[@id='payment_method_cod']")
    public WebElement cashOnDeliveryRadioBtn;

    @FindBy(xpath = "//*[@id='payment_method_ppec_paypal']")
    public WebElement payPalRadioBtn;

    @FindBy(xpath = "//*[@id='place_order']")
    public WebElement placeOrderBtn;

    //coupon part
    @FindBy(xpath = "//*[text()='Click here to enter your code']")
    public WebElement enterCouponLink;

    @FindBy(xpath = "//input[@placeholder='Coupon code']")
    public WebElement couponInput;

    @FindBy(xpath = "//input[@name='apply_coupon']")
    public WebElement applyCouponBtn;

    @FindBy(xpath = "//*[@class='woocommerce-error']")
    public WebElement couponError;


    //-------------------methods-------------------\\


    public void verifyBillingDetails() {
        String billingDetailText;
        for (WebElement billingDetail : billingDetails) {
            billingDetailText = billingDetail.getText();
            billingDetailList.add(billingDetailText);
        }
        Assert.assertEquals(billingDetailList, Constants.expectedBillingDetailList);
        System.out.println("Billing details are displayed");
    }

    public void verifyOrderDetails() {
        String orderDetailText;
        for (WebElement orderDetail : orderDetails) {
            orderDetailText = orderDetail.getText();
            orderDetailList.add(orderDetailText);
        }
        Assert.assertEquals(orderDetailList, Constants.expectedOrderDetailList);
        System.out.println("Order details are displayed");
    }

    public void verifyAdditionalDetails() {
        String additionalDetailText;
        for (WebElement additionalDetail : additionalDetails) {
            additionalDetailText = additionalDetail.getText();
            additionalDetailList.add(additionalDetailText);
        }
        Assert.assertEquals(additionalDetailList, Constants.expectedAdditionalDetailList);
        System.out.println("Additional details are displayed");
    }

    public void verifyPaymentGatewayDetails() {
        String paymentGatewayDetailText;
        for (WebElement paymentGatewayDetail : paymentGatewayDetails) {
            paymentGatewayDetailText = paymentGatewayDetail.getText();
            paymentGatewayDetailList.add(paymentGatewayDetailText);
        }
        Assert.assertEquals(paymentGatewayDetailList, Constants.expectedPaymentGatewayDetailList);
        Assert.assertTrue(directBankRadioBtn.isSelected());
        System.out.println("Direct bank transfer btn is selected by default");
    }

    public void verifyCouponApplySessionPresentInPaymentGateway() throws InterruptedException {
        Assert.assertTrue(enterCouponLink.isDisplayed());
        enterCouponLink.click();
        Thread.sleep(2000);
        Assert.assertTrue(couponInput.isDisplayed());
        Assert.assertTrue(applyCouponBtn.isDisplayed());
        System.out.println("Feasibility to add coupon in the payment gateway page");
    }

    public void fillOutBillingDetails() throws InterruptedException {
        fNameInput.sendKeys(ConfigProp.getProperty("fName"));
        lNameInput.sendKeys(ConfigProp.getProperty("lName"));
        emailInput.sendKeys(ConfigProp.getProperty("email"));
        phoneInput.sendKeys(ConfigProp.getProperty("phone"));
        countryDropDown.click();
        countryBox.click();
        Thread.sleep(3000);
        countrySelect.click();
        addressInput.sendKeys(ConfigProp.getProperty("address"));
        cityInput.sendKeys(ConfigProp.getProperty("city"));
        stateDropDown.click();
        stateBox.click();
        Thread.sleep(2000);
        stateSelect.click();
        zipInput.sendKeys(ConfigProp.getProperty("zip"));
        System.out.println("Billing details are able to filled out");
    }

    public void fillOutAdditionalDetail(String note) {
        orderNotesBox.sendKeys(note);
        System.out.println("Additional detail is able to filled out");
    }

    public void optPayment(String selectPayment) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-1000)", "");
        switch (selectPayment) {
            case "bankTransfer":
                directBankRadioBtn.click();
                Assert.assertTrue(directBankRadioBtn.isSelected());
                System.out.println("Direct Bank Transfer is selected");
                break;
            case "check":
                chkPaymentRadioBtn.click();
                Assert.assertTrue(chkPaymentRadioBtn.isSelected());
                System.out.println("Check Payments is selected");
                break;
            case "cash":
                cashOnDeliveryRadioBtn.click();
                Assert.assertTrue(cashOnDeliveryRadioBtn.isSelected());
                System.out.println("Cash on Delivery is selected");
                break;
            case "paypal":
                payPalRadioBtn.click();
                Assert.assertTrue(payPalRadioBtn.isSelected());
                System.out.println("PayPal Express Checkout is selected");
                break;
        }

    }

}
