package pageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utilities.Constants;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAccountPage {

    WebDriver driver = Driver.getDriver();
    Random rd = new Random();
    JavascriptExecutor js = (JavascriptExecutor) driver;

    public MyAccountPage() {
        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//*[@id='menu-item-50']")
    public WebElement myAccountMenu;

    @FindBy(xpath = "//*[@id='reg_email']")
    public WebElement emailRegisterInput;

    @FindBy(xpath = "//*[@id='reg_password']")
    public WebElement passwordRegisterInput;

    @FindBy(xpath = "//*[@value='Register']")
    public WebElement registerBtn;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/p[1]")
    public WebElement greetingContent;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/p[2]")
    public WebElement dashboardContent;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/p[1]/a")
    public WebElement signOutLink;

    @FindBy(xpath = "//*[@id='username']")
    public WebElement usernameLoginInput;

    @FindBy(xpath = "//*[@id='password']")
    public WebElement passwordLoginInput;

    @FindBy(xpath = "//*[@class='woocommerce-Button button']")
    public WebElement loginBtn;

    @FindBy(xpath = "//*[@class='woocommerce']")
    public WebElement errorLogin;

    @FindBy(xpath = "//*[@class='woocommerce-error']/li")
    public WebElement errorRegistration;

    //TC01
    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-navigation']/ul/li/a")
    public List<WebElement> dashboardLinks;

    //TC02
    @FindBy(xpath = "//a[text()='Orders']")
    public WebElement OrdersLink;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/table/*")
    public List<WebElement> orderTable;

    //TC03
    @FindBy(xpath = "//*[@class='order-actions']/a")
    public WebElement viewBtn;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/h2")
    public WebElement orderDetailsHeader;

    @FindBy(xpath = "//*[@class='shop_table order_details']/*")
    public List<WebElement> orderDetails;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/header")
    public WebElement customerDetailsHeader;

    @FindBy(xpath = "//*[@class='shop_table customer_details']//tr")
    public List<WebElement> customerDetails;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/header[2]")
    public WebElement billingDetailsHeader;

    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/address")
    public List<WebElement> billingDetails;

    //TC04
    @FindBy(xpath = "//*[@class='woocommerce-MyAccount-content']/p")
    public WebElement orderSummary;

    //TC05
    @FindBy(xpath = "//a[text()='Addresses']")
    public WebElement addressesLink;

    @FindBy(xpath = "//*[@class='u-column1 col-1 woocommerce-Address']//h3")
    public WebElement billingAddressHeader;

    @FindBy(xpath = "//*[@class='u-column2 col-2 woocommerce-Address']//h3")
    public WebElement shippingAddressHeader;

    @FindBy(xpath = "//*[@class='u-column1 col-1 woocommerce-Address']/*")
    public List<WebElement> billingAddress;

    @FindBy(xpath = "//*[@class='u-column2 col-2 woocommerce-Address']/*")
    public List<WebElement> shippingAddress;

    //TC06
    @FindBy(xpath = "//*[@class='woocommerce-Address-title title']/a")
    public WebElement EditShippingAddressLink;

    @FindBy(xpath = "//form[@method='post']/p/input")
    public List<WebElement> shippingAddressForm;







    public String generateEmailAddress() {
        int randomNum = rd.nextInt(1000);
        return randomNum + "lala@lulu.com";
    }

    public void register(String username, String password) {
        emailRegisterInput.sendKeys(username);
        passwordRegisterInput.sendKeys(password);
        registerBtn.click();
    }

    public void checkRegisterSuccess() {
        Assert.assertTrue(greetingContent.isDisplayed());
        Assert.assertTrue(dashboardContent.isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), Constants.myAccountPageUrl);
        System.out.println("Registration is successfully and navigated to the Home page");
    }

    public void checkLoginSuccess() {
        Assert.assertTrue(greetingContent.isDisplayed());
        Assert.assertTrue(dashboardContent.isDisplayed());
        Assert.assertEquals(driver.getCurrentUrl(), Constants.myAccountPageUrl);
        System.out.println("Login is successfully and navigated to the Home page");
    }

    public void getRegistrationValidationMessage(WebElement obj) {
//        Boolean isValidInput = (Boolean) js.executeScript("return arguments[0].checkValidity();", obj);
//        Assert.assertFalse(isValidInput);
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", obj);
        System.out.println(validationMessage);
    }

    public void getErrorRegistrationWarning() {
        System.out.println(errorRegistration.getText());
    }

    public void login(String username, String password) {
        usernameLoginInput.clear();
        usernameLoginInput.sendKeys(username);
        passwordLoginInput.sendKeys(password);
        loginBtn.click();
    }

    public void checkIsMasked(WebElement objectTest) {
        Assert.assertEquals(objectTest.getAttribute("type"), "password");
    }

    public void checkIsSignIn() {
        String usernameBox = usernameLoginInput.getText();
        String passwordBox = passwordLoginInput.getText();
        Assert.assertTrue(usernameBox.isBlank());
        Assert.assertTrue(passwordBox.isBlank());
        Assert.assertTrue(loginBtn.isDisplayed());
    }

    public void verifyDashboardSiteIsPresent() {
        ArrayList<String> dashboardLinklList = new ArrayList<>();
        String dashboardLinkText;
        for (WebElement link : dashboardLinks) {
            dashboardLinkText = link.getText();
            dashboardLinklList.add(dashboardLinkText);
        }
        Assert.assertEquals(dashboardLinklList, Constants.expectedDashboardLinklList);
        System.out.println("Dashboard site are presented");
    }

    public void detailInOrdersLink(){
        List<String> ordersLinkDetailList = new ArrayList<>();
        String detailText;
        for (WebElement detail : orderTable) {
            detailText = detail.getText();
            ordersLinkDetailList.add(detailText);
        }
        System.out.println(ordersLinkDetailList);
    }

    public void verifyOrderDetailInViewBtn(){
        Assert.assertTrue(orderDetailsHeader.isDisplayed());
        System.out.println(orderDetailsHeader.getText()+"-----------------");

        List<String> ordersDetailList = new ArrayList<>();
        String orderDetailText;
        for (WebElement detail : orderDetails) {
            orderDetailText = detail.getText();
            ordersDetailList.add(orderDetailText);
        }
//        System.out.println(ordersDetailList);
        System.out.println("User able to view order Detail");
    }

    public void verifyCustomerDetailInViewBtn(){
        Assert.assertTrue(customerDetailsHeader.isDisplayed());
        System.out.println(customerDetailsHeader.getText()+"-----------------");
        List<String> customerDetailList = new ArrayList<>();
        String customerDetailText;
        for (WebElement detail : customerDetails) {
            customerDetailText = detail.getText();
            customerDetailList.add(customerDetailText);
        }
//        System.out.println(customerDetailList);
        System.out.println("User able to view customer Detail");
    }

    public void verifyBillingDetailInViewBtn(){
        Assert.assertTrue(billingDetailsHeader.isDisplayed());
        System.out.println(billingDetailsHeader.getText()+"-----------------");
        List<String> billingDetailList = new ArrayList<>();
        String billingDetailText;
        for (WebElement detail : billingDetails) {
            billingDetailText = detail.getText();
            billingDetailList.add(billingDetailText);
        }
//        System.out.println(billingDetailList);
        System.out.println("User able to view billing Detail");
    }

    public void verifyOrderSummaryInViewBtn() {
        Assert.assertTrue(orderSummary.isDisplayed());
        System.out.println(orderSummary.getText());
    }

    public void verifyBillingAddressInAddressLink(){
        Assert.assertTrue(billingAddressHeader.isDisplayed());
        System.out.println(billingAddressHeader.getText()+"-----------------");
        List<String> billingAddressList = new ArrayList<>();
        String billingAddressText;
        for (WebElement detail : billingAddress) {
            billingAddressText = detail.getText();
            billingAddressList.add(billingAddressText);
        }
//        System.out.println(billingAddressList);
        System.out.println("User able to view billing address");
    }

    public void verifyShippingAddressInAddressLink(){
        Assert.assertTrue(shippingAddressHeader.isDisplayed());
        System.out.println(shippingAddressHeader.getText()+"-----------------");
        List<String> shippingAddressList = new ArrayList<>();
        String shippingAddressText;
        for (WebElement detail : shippingAddress) {
            shippingAddressText = detail.getText();
            shippingAddressList.add(shippingAddressText);
        }
//        System.out.println(shippingAddressList);
        System.out.println("User able to view ship address");
    }

    //check if the textbox is visible and editable
//    boolean visible = eid.isDisplayed();
//    boolean enabled = eid.isEnabled();
//
//    if(visible==true && enabled==true) {
//        System.out.println("Email field is visible and editable");
//    }else
//            System.out.println("Email field not visible and editable");
}
