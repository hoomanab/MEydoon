package com.example.meydoon.app;

/**
 * Created by hooma on 2/21/2017.
 */
public class Config {
    // server URL configuration

    // request for verification sms
    public static final String URL_REQUEST_SMS = "http://meydooncore.herokuapp.com/CreateUser";

    // sends verification code
    public static final String URL_VERIFY_OTP = "http://meydooncore.herokuapp.com/VerifyUser";

    // adds product to the shop
    public static final String URL_ADD_PRODUCT = "http://meydooncore.herokuapp.com/SaveProduct";

    // gets shop_id by user_id
    public static final String URL_GET_SHOP_ID = "http://meydooncore.herokuapp.com/GetShopId";

    // registers shop
    public static final String URL_REGISTER_SHOP = "http://meydooncore.herokuapp.com/SaveShop";

    // gets user's home feed
    public static final String URL_HOME_FEED = "http://api.androidhive.info/feed/feed.json";

    // gets shops broadcast messages
    public static final String URL_BROADCAST_MESSAGE_OUTBOX = "https://api.myjson.com/bins/1ezj9d";

    // gets notifications for users
    public static final String URL_NOTIFICATION_INBOX = "https://api.myjson.com/bins/1ezj9d";

    // searches
    public static final String URL_SEARCH = "https://api.myjson.com/bins/q9p5l";

    // gets a product's details
    public static final String URL_PRODUCT_DETAILS = "";

    // gets shop contact information
    public static final String URL_GET_SHOP_CONTACT_INFO = "";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    //public static final String SMS_ORIGIN = "MEYDOON";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
}
