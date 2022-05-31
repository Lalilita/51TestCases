package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObject.MyAccountPage;
import utilities.ConfigProp;
import utilities.Driver;

public class RegistrationTest {
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

    @AfterMethod
    public void clearBox() {
        myAccPage.emailRegisterInput.clear();
        myAccPage.passwordRegisterInput.clear();
    }

    @AfterTest
    public void teardown() {
        myAccPage.emailRegisterInput.clear();
        myAccPage.passwordRegisterInput.clear();
        driver.quit();
    }

    @AfterSuite
    public void afterClass() {
        System.out.println("----------- MY ACCOUNT - REGISTRATION TEST DONE -----------");
    }

    @DataProvider
    public Object[][] newGeneratedEmailValidPassword() {
        return new Object[][]{
                new Object[]{myAccPage.generateEmailAddress(), ConfigProp.getProperty("validPassword")},
        };
    }

    @DataProvider
    public Object[][] validEmailValidPassword() {
        return new Object[][]{
                new Object[]{ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword")},
        };
    }

    @DataProvider
    public Object[][] invalidEmailValidPassword() {
        return new Object[][]{
                new Object[]{ConfigProp.getProperty("invalidEmailAddress"), ConfigProp.getProperty("validPassword")},
        };
    }

    @DataProvider
    public Object[][] emptyEmailValidPassword() {
        return new Object[][]{
                new Object[]{" ", ConfigProp.getProperty("validPassword")},
        };
    }

    @DataProvider
    public Object[][] validEmailEmptyPassword() {
        return new Object[][]{
                new Object[]{ConfigProp.getProperty("validUsername"), " "},
        };
    }

    @DataProvider
    public Object[][] emptyUserEmptyPassword() {
        return new Object[][]{
                new Object[]{" ", " "},
        };
    }

    @DataProvider
    public Object[][] containsEmptyEmailAndPassword() {
        return new Object[][]{
                new Object[]{" ", ConfigProp.getProperty("validPassword")},
                new Object[]{ConfigProp.getProperty("validUsername"), " "},
                new Object[]{" ", " "},
        };
    }

    @Test(dataProvider = "newGeneratedEmailValidPassword")
    public void TC01_RegistrationSignIn(String username, String password) {
        myAccPage.register(username, password);
        myAccPage.checkRegisterSuccess();
        myAccPage.signOutLink.click();
    }

    @Test(dataProvider = "invalidEmailValidPassword")
    public void TC02_RegistrationWithInvalidEmailId(String username, String password) {
        myAccPage.register(username, password);
        myAccPage.getRegistrationValidationMessage(myAccPage.emailRegisterInput);
        myAccPage.getRegistrationValidationMessage(myAccPage.passwordRegisterInput);
    }

    @Test(dataProvider = "emptyEmailValidPassword")
    public void TC03_RegistrationWithEmptyEmailId(String username, String password) {
        myAccPage.register(username, password);
        myAccPage.getErrorRegistrationWarning();
    }

    @Test(dataProvider = "validEmailEmptyPassword")
    public void TC04_RegistrationWithEmptyPassword(String username, String password) {
        myAccPage.register(username, password);
        myAccPage.getErrorRegistrationWarning();
    }

    @Test(dataProvider = "emptyUserEmptyPassword")
    public void TC05_RegistrationWithEmptyEmailIdAndPassword(String username, String password) {
        myAccPage.register(username, password);
        myAccPage.getErrorRegistrationWarning();
    }


    //    @Test(dataProvider = "containsEmptyEmailAndPassword")
//    public void TC03toTC05(String username, String password) {
//        myAccPage.register(username, password);
//        myAccPage.emailRegisterInput.clear();
//        myAccPage.passwordRegisterInput.clear();
//        myAccPage.getErrorRegistrationWarning();
//        System.out.println("-------------------------------");
//    }

}
