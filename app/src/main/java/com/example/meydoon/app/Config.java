package com.example.meydoon.app;

/**
 * Created by hooma on 2/21/2017.
 */
public class Config {
    // server URL configuration

    // request for verification sms
    public static final String URL_REQUEST_SMS = "http://api.emeydoon.com/CreateUser";

    // sends verification code
    public static final String URL_VERIFY_OTP = "http://api.emeydoon.com/VerifyUser";

    // adds product to the shop
    public static final String URL_ADD_PRODUCT = "http://api.emeydoon.com/SaveProduct";

    // gets shop_id by user_id
    public static final String URL_GET_SHOP_ID = "http://api.emeydoon.com/GetShopId";

    // registers shop
    public static final String URL_REGISTER_SHOP = "http://api.emeydoon.com/SaveShop";

    // gets user's home feed
    public static final String URL_HOME_FEED = "http://api.emeydoon.com/GetFeed";

    // gets notifications for users
    public static final String URL_NOTIFICATION_INBOX = "http://api.emeydoon.com/GetReceivedNotifications";

    // searches shops
    public static final String URL_SEARCH = "http://api.emeydoon.com/SearchonShopsbyName";

    // gets a product's details
    public static final String URL_PRODUCT_DETAILS = "http://api.emeydoon.com/GetProductDetails";

    // gets shop contact information
    public static final String URL_GET_SHOP_CONTACT_INFO = "http://api.emeydoon.com/GetShopContactInfo";

    // get shop profile information
    public static final String URL_SHOP_PROFILE_INFO = "http://api.emeydoon.com/GetShopProfile";

    // get shop products
    public static final String URL_SHOP_PROFILE_PRODUCTS = "http://api.emeydoon.com/GetShopProducts";

    // gets shops broadcast messages
    public static final String URL_BROADCAST_MESSAGE_OUTBOX = "http://api.emeydoon.com/GetShopBroadcastedMessages";

    // send broadcast message
    public static final String URL_SEND_BROADCAST_MESSAGE = "http://api.emeydoon.com/SendBroadcastMessage";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    //public static final String SMS_ORIGIN = "MEYDOON";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
}
