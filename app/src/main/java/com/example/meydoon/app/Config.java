package com.example.meydoon.app;

/**
 * Created by hooma on 2/21/2017.
 */
public class Config {
    // server URL configuration
    public static final String URL_REQUEST_SMS = "http://meydooncore.herokuapp.com/CreateUser";
    public static final String URL_VERIFY_OTP = "http://meydooncore.herokuapp.com/VerifyUser";
    public static final String URL_ADD_PRODUCT = "";
    public static final String URL_GET_SHOP_ID = "http://meydooncore.herokuapp.com/GetShopId";
    public static final String URL_REGISTER_SHOP = "";

    public static final String URL_HOME_FEED = "http://api.androidhive.info/feed/feed.json";
    public static final String URL_BROADCAST_MESSAGE_OUTBOX = "https://api.myjson.com/bins/1ezj9d";
    public static final String URL_NOTIFICATION_INBOX = "https://api.myjson.com/bins/1ezj9d";
    public static final String URL_SEARCH = "https://api.myjson.com/bins/q9p5l";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    //public static final String SMS_ORIGIN = "MEYDOON";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
}
