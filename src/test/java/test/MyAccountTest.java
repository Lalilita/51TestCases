package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObject.MyAccountPage;
import utilities.ConfigProp;
import utilities.Driver;

public class MyAccountTest {
    WebDriver driver;
    MyAccountPage myAccPage = new MyAccountPage();


    @BeforeSuite
    public void goToAutomationPracticeSite() {
        WebDriverManager.chromedriver().setup();
        driver = Driver.getDriver();
    }

    @BeforeTest
    public void goToMyAccountPage() {
        myAccPage.myAccountMenu.click();
    }

//    @AfterMethod
//    public void clearBox() {
//        myAccPage.emailRegisterInput.clear();
//        myAccPage.passwordRegisterInput.clear();
//    }
//
//    @AfterTest
//    public void teardown() {
//        myAccPage.emailRegisterInput.clear();
//        myAccPage.passwordRegisterInput.clear();
//        driver.quit();
//    }

//    @AfterSuite
//    public void afterClass() {
//        System.out.println("----------- MY ACCOUNT TEST DONE -----------");
//    }




//    @DataProvider
//    public Object[][] validEmailValidPassword() {
//        return new Object[][]{
//                new Object[]{ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword")},
//        };
//    }



    @Test
    public void TC01_myAccountsDashboard() {
        myAccPage.login(ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword"));
        myAccPage.checkLoginSuccess();
        myAccPage.myAccountMenu.click();
        myAccPage.verifyDashboardSiteIsPresent();
    }

    @Test
    public void TC02_myAccountsOrders1() {
        //user must place any order before do this test
        myAccPage.login(ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword"));
        myAccPage.checkLoginSuccess();
        myAccPage.myAccountMenu.click();
        myAccPage.OrdersLink.click();
        myAccPage.detailInOrdersLink();
    }

    @Test
    public void TC03_myAccountsOrders2() {
        myAccPage.login(ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword"));
        myAccPage.checkLoginSuccess();
        myAccPage.myAccountMenu.click();
        myAccPage.OrdersLink.click();
        myAccPage.viewBtn.click();
        myAccPage.verifyOrderDetailInViewBtn();
        myAccPage.verifyCustomerDetailInViewBtn();
        myAccPage.verifyBillingDetailInViewBtn();
    }

    @Test
    public void TC04_myAccountsOrders3() {
        myAccPage.login(ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword"));
        myAccPage.checkLoginSuccess();
        myAccPage.myAccountMenu.click();
        myAccPage.OrdersLink.click();
        myAccPage.viewBtn.click();
        myAccPage.verifyOrderSummaryInViewBtn();
    }

    @Test
    public void TC05_myAccountsAddressFunctionality1() {
        myAccPage.login(ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword"));
        myAccPage.checkLoginSuccess();
        myAccPage.myAccountMenu.click();
        myAccPage.addressesLink.click();
        myAccPage.verifyBillingAddressInAddressLink();
        myAccPage.verifyShippingAddressInAddressLink();
    }

    @Test
    public void TC06_myAccountsAddressFunctionality2() {
        myAccPage.login(ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword"));
        myAccPage.checkLoginSuccess();
        myAccPage.myAccountMenu.click();
        myAccPage.addressesLink.click();
        myAccPage.EditShippingAddressLink.click();
    }

//    @Test
//    public void TC07_myAccountsDetails() {
//    }

//    @Test
//    public void TC08_myAccountsLogOut() {
//    }

}
