package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String config_filePath = System.getProperty("user.dir") + "/src/test/java/propertiesFolder/config.properties";
    public static final int sliderCount = 3;
    public static final int arrivalCount = 3;
    public static final int testAddBookCount = 10;
    public static final int testSubtractBookCount = 5;

    //page url: homepage
    public static final String homePageUrl = "http://practice.automationtesting.in/";
    public static final String shopPageUrl = "http://practice.automationtesting.in/shop/";
    public static final String basketPageUrl = "http://practice.automationtesting.in/basket/";
    public static final String checkoutPageUrl = "http://practice.automationtesting.in/checkout/";
    public static final String orderInformationPageUrl = "http://practice.automationtesting.in/checkout/order-received/10684/?key=wc_order_6293d37b79ef5";

    //page url: my account
    public static final String myAccountPageUrl = "http://practice.automationtesting.in/my-account/";


    //arrival title
    public static final String arrival1Title = "Selenium Ruby – Automation Practice Site";
    public static final String arrival2Title = "Thinking in HTML – Automation Practice Site";
    public static final String arrival3Title = "Mastering JavaScript – Automation Practice Site";
    public static final String orderInformationPageTitle = "Checkout – Automation Practice Site";

    //order detail list
    public static final ArrayList<String> expectedBillingDetailList = new ArrayList<>(Arrays.asList
            ("First Name *", "Last Name *", "Company Name", "Email Address *", "Phone *",
                    "Country *", "Country *", "", "Address *", "Town / City *", "State *", "State",
                    "", "ZIP *", "Create an account?", ""));
    public static final ArrayList<String> expectedOrderDetailList = new ArrayList<>(Arrays.asList
            ("Product", "Total", "Subtotal", "Roaming Tax", "Total"));
    public static final ArrayList<String> expectedAdditionalDetailList = new ArrayList<>(List.of("Order Notes"));
    public static final ArrayList<String> expectedPaymentGatewayDetailList = new ArrayList<>(Arrays.asList
            ("Direct Bank Transfer", "Check Payments", "Cash on Delivery", "PayPal Express Checkout"));

    //dashboard link list
    public static final ArrayList<String> expectedDashboardLinklList = new ArrayList<>(Arrays.asList
            ("Dashboard", "Orders", "Downloads", "Addresses", "Account Details", "Logout"));
}
