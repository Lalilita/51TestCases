package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObject.*;
import utilities.ConfigProp;
import utilities.Driver;

public class LoginTest {
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

    @AfterTest
    public void teardown() {
        driver.quit();
    }

    @AfterSuite
    public void afterClass() {
        System.out.println("----------- MY ACCOUNT - LOGIN TEST DONE -----------");
    }

    @DataProvider
    public Object[][] newGeneratedEmailValidPassword() {
        return new Object[][]{
                new Object[]{myAccPage.generateEmailAddress(), ConfigProp.getProperty("validPassword")},
        };
    }

    @DataProvider
    public Object[][] validUserValidPassword() {
        return new Object[][]{
                new Object[]{ConfigProp.getProperty("validUsername"), ConfigProp.getProperty("validPassword")},
        };
    }

    @DataProvider
    public Object[][] invalidUserInvalidPassword() {
        return new Object[][]{
                new Object[]{ConfigProp.getProperty("invalidUsername"), ConfigProp.getProperty("invalidPassword")},
        };
    }

    @DataProvider
    public Object[][] validUserEmptyPassword() {
        return new Object[][]{
                new Object[]{ConfigProp.getProperty("validUsername"), " "},
        };
    }

    @DataProvider
    public Object[][] emptyUserValidPassword() {
        return new Object[][]{
                new Object[]{" ", ConfigProp.getProperty("validPassword")},
        };
    }

    @DataProvider
    public Object[][] emptyUserEmptyPassword() {
        return new Object[][]{
                new Object[]{" ", " "},
        };
    }

    @DataProvider
    public Object[][] caseChangedValidUserValidPassword() {
        return new Object[][]{
                new Object[]{ConfigProp.getProperty("caseChangedValidUsername"), ConfigProp.getProperty("caseChangedValidPassword")},
        };
    }

    @Test(dataProvider = "newGeneratedEmailValidPassword")
    public void TC01_loginValidUsernameAndPassword(String username, String password) {
        myAccPage.register(username, password);
        Assert.assertTrue(myAccPage.greetingContent.isDisplayed());
        myAccPage.signOutLink.click();
        myAccPage.login(username, password);
        myAccPage.signOutLink.click();
    }

    @Test(dataProvider = "invalidUserInvalidPassword")
    public void TC02_loginIncorrectUsernameIncorrectPassword(String username, String password) {
        myAccPage.login(username, password);
        Assert.assertTrue(myAccPage.errorLogin.isDisplayed());
        Assert.assertTrue(myAccPage.usernameLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.passwordLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.loginBtn.isDisplayed());
    }

    @Test(dataProvider = "validUserEmptyPassword")
    public void TC03_loginCorrectUsernameEmptyPassword(String username, String password) {
        myAccPage.login(username, password);
        Assert.assertTrue(myAccPage.errorLogin.isDisplayed());
        Assert.assertTrue(myAccPage.usernameLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.passwordLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.loginBtn.isDisplayed());
    }

    @Test(dataProvider = "emptyUserValidPassword")
    public void TC04_loginEmptyUsernameValidPassword(String username, String password) {
        myAccPage.login(username, password);
        Assert.assertTrue(myAccPage.errorLogin.isDisplayed());
        Assert.assertTrue(myAccPage.usernameLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.passwordLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.loginBtn.isDisplayed());
    }

    @Test(dataProvider = "emptyUserEmptyPassword")
    public void TC05_loginEmptyUsernameEmptyPassword(String username, String password) {
        myAccPage.login(username, password);
        Assert.assertTrue(myAccPage.errorLogin.isDisplayed());
        Assert.assertTrue(myAccPage.usernameLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.passwordLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.loginBtn.isDisplayed());
    }

    @Test
    public void TC06_loginPasswordShouldBeMasked() {
        myAccPage.passwordLoginInput.sendKeys(ConfigProp.getProperty("validPassword"));
        myAccPage.checkIsMasked(myAccPage.passwordLoginInput);
    }

    @Test(dataProvider = "caseChangedValidUserValidPassword")
    public void TC07_loginHandlesCaseSensitive(String username, String password) {
        myAccPage.login(username, password);
        Assert.assertTrue(myAccPage.errorLogin.isDisplayed());
        Assert.assertTrue(myAccPage.usernameLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.passwordLoginInput.isDisplayed());
        Assert.assertTrue(myAccPage.loginBtn.isDisplayed());
    }

    @Test(dataProvider = "validUserValidPassword")
    public void TC08_loginAuthentication(String username, String password) {
        myAccPage.login(username, password);
        myAccPage.signOutLink.click();
        driver.navigate().back();
        myAccPage.checkIsSignIn();
    }
}
