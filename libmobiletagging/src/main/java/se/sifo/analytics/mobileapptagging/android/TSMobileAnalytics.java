/*************************************************
 * TNS SIFO Mobile Application Tagging Framework *
 * (c) Copyright 2012 TNS SIFO, Sweden,          *
 * All rights reserved.                          *
 *************************************************/

package se.sifo.analytics.mobileapptagging.android;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebView;

import java.util.List;

/**
 * TNS Sifo Mobile Analytics SDK for Android:
 * MobileApplicationTaggingFramework.java :
 * <p/>
 * This framework will help you to measure usage of your application using TNS SIFO:s services.
 * In order to measure traffic, your application needs to send HTTP-requests to a server provided by Mobiletech,
 * using URLs following a specified pattern with information about your application.
 * The framework can help you with the whole process, both creating these URLs, as well as sending them to the server.
 *
 * @author Jakob Schyberg (jakob.schyberg@wecode.se)
 */
public class MobileTaggingFramework {

    /**
     * Default constructor
     */
    protected MobileTaggingFramework() {
    }


    /**
     * Call this method upon application start, for example in the onCreate-method of
     * the main Activity, to initialize the framework.
     * <p/>
     * The CPID must not be more than 6 characters, it must not be empty and may only contain digits.
     * The Application name must not be more than 243 characters.
     * The parameters must not be null.
     * If any of the parameters are invalid, null will be returned and getInstance() will also return null.
     *
     * @param context         The context of this application, used to get device ID for unique Tagging.
     *                        It will also necessary to initialize volley library
     *                        You can obtain this value using the method getApplicationContext() in the Android Activity-class.
     * @param cpID            The ID to identify you as a client/customer on the server. This value will be sent in tags
     *                        using the "cpid"-attribute. Only digits allowed. Max 6 characters.
     * @param applicationName The name of the application. Only specify the name, the interface will add platform.
     *                        This value will be sent in tags using the "type"-attribute. Max 243 characters.
     * @return The framework instance created with your values. Returns null if creation failed due to invalid parameters.
     */
    public static MobileTaggingFramework createInstance(Context context, String cpID, String applicationName) {
        return MobileTaggingFrameworkBackend.createInstance(context, cpID, applicationName, false);
    }

    /**
     * Call this method upon application start if you only want to measure TNS-Sifo Panelist users
     */
    public static MobileTaggingFramework createInstance(Context context, String cpID, String applicationName, boolean panelistTrackingOnly) {
        return MobileTaggingFrameworkBackend.createInstance(context, cpID, applicationName, panelistTrackingOnly);
    }

    /**
     * Call this method to initialize framework with Builder class
     * @param builder
     * @return
     */
    public static MobileTaggingFramework createInstance(MobileTaggingFramework builder) {
        return createInstance(builder.context, builder.cpId, builder.appName, builder.panelistTrackingOnly);
    }

    /**
     * Call to get an instance of the framework at any time after initialization.
     * Returns null if createInstance has not been called before.
     *
     * @return The framework instance with your values specified in createInstance, null if not created.
     */
    public static MobileTaggingFramework getInstance() {
        return frameworkInstance;
    }

    /**
     * Call to immediately send a tag to the server using the framework�s http-functionality.
     * <p/>
     * Use when a page is displayed, for example in the onResume-method of an Activity.
     * Strings will be encoded using Latin1 (ISO8859-1) and characters not supported in this character
     * set will not be stored properly on the server.
     * <p/>
     * The category parameter must not be more than 255 characters.
     * The input strings must not me null.
     * If any of the parameters are invalid the request will fail and return a value >0.
     * If the request is sent successfully 0 will be returned.
     *
     * @param category The name of category or page to be tagged. This value will be sent using
     *                 the "cat"-attribute. Max 255 characters.
     * @return 0 if request is sent successfully, otherwise a value bigger than 0.
     */
    public int sendTag(String category) {
        return dataRequestHandler.performMetricsRequest(category);
    }

    /**
     * Call to immediately send a tag to the server using the framework�s http-functionality.
     * <p/>
     * Use when a page is displayed, for example in the onResume-method of an Activity.
     * Strings will be encoded using Latin1 (ISO8859-1) and characters not supported in this character set will not be stored properly on the server.
     * <p/>
     * The category parameter must not be more than 255 characters.
     * The contentID must not be more than 255 characters.
     * The input strings must not be null.
     * If any of the parameters are invalid the request will fail and return a value >0.
     * If the request is sent successfully 0 will be returned.
     *
     * @param category  The name of category or page to be tagged. This value will be sent using
     *                  the "cat"-attribute. Max 255 characters.
     * @param contentID Value to identify specific content within the category, such as a specific article. Max 255 characters.
     * @return 0 if request is sent successfully, otherwise a value bigger than 0.
     */
    public int sendTag(String category, String contentID) {
        return dataRequestHandler.performMetricsRequest(category, contentID);
    }

    /**
     * Call to immediately send a tag to the server using the framework�s http-functionality.
     * <p/>
     * Use when a page is displayed, for example in the onResume-method of an Activity.
     * Strings will be encoded using Latin1 (ISO8859-1) and characters not supported in this character set will not be stored properly on the server.
     * <p/>
     * The category parameter must not be more than 255 characters.
     * The contentID must not be more than 255 characters.
     * The contentName must not be more than 255 characters.
     * The input strings must not be null.
     * If any of the parameters are invalid the request will fail and return a value >0.
     * If the request is sent successfully 0 will be returned.
     *
     * @param category    The name of category or page to be tagged. This value will be sent using
     *                    the "cat"-attribute. Max 255 characters.
     * @param contentID   Value to identify specific content within the category, such as a specific article. Max 255 characters.
     * @param contentName Name to identify specific content within the category, such as a specific article. Max 255 characters.
     * @return 0 if request is sent successfully, otherwise a value bigger than 0.
     */
    public int sendTag(String category, String contentName, String contentID) {
        return dataRequestHandler.performMetricsRequest(category, contentID, contentName);
    }

    /**
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated Please use {@link #sendTag(String, String, String)}.
     */
    @Deprecated
    public int sendTag(String categories, String deprecated, String contentID, String contentName) {
        return dataRequestHandler.performMetricsRequest(categories, contentID, contentName);
    }

    /**
     * Call to immediately send a tag to the server using the framework�s http-functionality.
     * <p/>
     * Use when a page is displayed, for example in the onResume-method of an Activity.
     * Strings will be encoded using Latin1 (ISO8859-1) and characters not supported in this character set will not be stored properly on the server.
     * <p/>
     * The category list must not contain more than 4 objects and they must not be more than 62 characters each.
     * The contentID must not be more than 255 characters.
     * The contentName must not be more than 255 characters.
     * The input strings must not be null.
     * If any of the parameters are invalid the request will fail and return a value >0.
     * If the request is sent successfully 0 will be returned.
     *
     * @param categories  Array of names in category structure. This value will be sent using
     *                    the "cat"-attribute. Max 4 objects with 62 characters each.
     * @param contentID   Value to identify specific content within the category, such as a specific article. Max 255 characters.
     * @param contentName Name to identify specific content within the category, such as a specific article. Max 255 characters.
     * @return 0 if request is sent successfully, otherwise a value bigger than 0.
     */
    public int sendTag(String[] categories, String contentName, String contentID) {
        return dataRequestHandler.performMetricsRequest(categories, contentID, contentName);
    }

    /**
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated Please use {@link #sendTag(String[], String, String)}.
     */
    @Deprecated
    public int sendTag(String[] categories, String deprecated, String contentID, String contentName) {
        return dataRequestHandler.performMetricsRequest(categories, contentID, contentName);
    }

    /**
     * Create a URL to use to send a tag to the server, if you want to make the request manually.
     * <p/>
     * You only need to use this method if you are making the HTTP-request yourself. If you want the framework
     * to make the HTTP-request for you, you only need to use the "sendTag"-method.
     * <p/>
     * The category parameter must not be more than 255 characters.
     * The input strings must not me null.
     * If any of the parameters are invalid null will be returned.
     *
     * @param category The name of category or page to be tagged. This value will be sent using
     *                 the "cat"-attribute. Max 255 characters.
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated This doesn't take into account some important cookies
     * and support is planned to be dropped in future versions.
     * Please use {@link #sendTag(String)} instead.
     */
    @Deprecated
    public String getURL(String category) {
        return dataRequestHandler.getURL(category);
    }

    /**
     * Create a URL to use to send a tag to the server, if you want to make the request manually.
     * <p/>
     * You only need to use this method if you are making the HTTP-request yourself. If you want the framework
     * to make the HTTP-request for you, you only need to use the "sendTag"-method.
     * <p/>
     * The category parameter must not be more than 255 characters.
     * The contentID must not be more than 255 characters.
     * The input strings must not be null.
     * If any of the parameters are invalid null will be returned.
     *
     * @param category  The name of category or page to be tagged. This value will be sent using
     *                  the "cat"-attribute. Max 255 characters.
     * @param contentID Value to identify specific content within the category, such as a specific article. Max 255 characters.
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated This doesn't take into account some important cookies
     * and support is planned to be dropped in future versions.
     * Please use {@link #sendTag(String, String)} instead.
     */
    @Deprecated
    public String getURL(String category, String contentID) {
        return dataRequestHandler.getURL(category, contentID);
    }

    /**
     * Create a URL to use to send a tag to the server, if you want to make the request manually.
     * <p/>
     * You only need to use this method if you are making the HTTP-request yourself. If you want the framework
     * to make the HTTP-request for you, you only need to use the "sendTag"-method.
     * <p/>
     * The category parameter must not be more than 255 characters.
     * The contentID must not be more than 255 characters.
     * The contentName must not be more than 255 characters.
     * The input strings must not be null.
     * If any of the parameters are invalid null will be returned.
     *
     * @param category    The name of category or page to be tagged. This value will be sent using
     *                    the "cat"-attribute. Max 255 characters.
     * @param contentID   Value to identify specific content within the category, such as a specific article. Max 255 characters.
     * @param contentName Name to identify specific content within the category, such as a specific article. Max 255 characters.
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated This doesn't take into account some important cookies
     * and support is planned to be dropped in future versions.
     * Please use {@link #sendTag(String, String, String)} instead.
     */
    @Deprecated
    public String getURL(String category, String contentName, String contentID) {
        return dataRequestHandler.getURL(category, contentID, contentName);
    }

    /**
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated This doesn't take into account some important cookies
     * and support is planned to be dropped in future versions.
     * Please use {@link #sendTag(String, String, String)} instead.
     */
    @Deprecated
    public String getURL(String category, String deprecated, String contentID, String contentName) {
        return dataRequestHandler.getURL(category, contentID, contentName);
    }

    /**
     * Create a URL to use to send a tag to the server, if you want to make the request manually.
     * <p/>
     * You only need to use this method if you are making the HTTP-request yourself. If you want the framework
     * to make the HTTP-request for you, you only need to use the "sendTag"-method.
     * <p/>
     * The category list must not contain more than 4 objects and they must not be more than 62 characters each.
     * The contentID must not be more than 255 characters.
     * The contentName must not be more than 255 characters.
     * The input strings must not be null.
     * If any of the parameters are invalid null will be returned.
     *
     * @param category    Array of names in category structure. This value will be sent using
     *                    the "cat"-attribute. Max 4 objects with 62 characters each.
     * @param contentID   Value to identify specific content within the category, such as a specific article. Max 255 characters.
     * @param contentName Name to identify specific content within the category, such as a specific article. Max 255 characters.
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated This doesn't take into account some important cookies
     * and support is planned to be dropped in future versions.
     * Please use {@link #sendTag(String[], String, String)} instead.
     */
    @Deprecated
    public String getURL(String[] category, String contentName, String contentID) {
        return dataRequestHandler.getURL(category, contentID, contentName);
    }

    /**
     * @return The String holding the created URL. NULL if unsuccessful.
     * @deprecated This doesn't take into account some important cookies
     * and support is planned to be dropped in future versions.
     * Please use {@link #sendTag(String[], String, String)} instead.
     */
    @Deprecated
    public String getURL(String[] category, String deprecated, String contentID, String contentName) {
        return dataRequestHandler.getURL(category, contentID, contentName);
    }

    /**
     * Activate framework cookies for the given WebView.
     * This enables the third party cookie option on the WebView.
     * <p/>
     * This function needs to be called once for every WebView before it can be guaranteed
     * to pass on the proper cookies. This is especially important from API 21 onwards.
     *
     * @param webView The WebView that should use cookies from the framework.
     * @deprecated Framework uses java.net.Cookie library now
     * Please use {@link #activateCookies()} instead.
     */
    @Deprecated
    public void activateCookies(WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
    }

    // TODO: 29/09/2017 test this method
    /**
     * Activate framework cookies for WebViews.
     * If SifoCookieManager is null, create new one with cookiePolicy.(ACCEPT_ALL)
     * <p/>
     */
    public void activateCookies() {
        SifoCookieManager.getInstance();
    }


    /**
     * Activate LogCat prints for the framework.
     * @deprecated This library uses Builder to initialize
     * Please use {@link #MobileTaggingFramework(Builder)} instead
     * Default is false
     */
    @Deprecated
    public static void setLogPrintsActivated(boolean printToLog) {
        logPrintsActivated = printToLog;
    }

    /**
     * Activate https url for send data. Default is true.
     * @deprecated This library uses Builder to initialize
     * Please use {@link #MobileTaggingFramework(Builder)} instead
     * Default is false
     */
    public static void useHttps(boolean https) {
        useHttpsActivated = https;
    }

    /***** Advanced methods *****/
    /**
     * Advanced/debugging: Get all pending requests.
     * This list can be used to handle memory issues, or for debugging purpose.
     *
     * @return The list of pending requests.
     */
    public List<TagDataRequest> getRequestQueue() {
        return dataRequestHandler.getDataRequestQueue();
    }

    /**
     * Advanced/debugging: Get the number of failed requests since the instance was created.
     *
     * @return The number of failed requests.
     */
    public int getNbrOfFailedRequests() {
        return dataRequestHandler.getNbrOfFailedRequests();
    }

    /**
     * Advanced/debugging: Get the number of successful requests since the instance was created.
     *
     * @return The number of successful requests.
     */
    public int getNbrOfSuccessfulRequests() {
        return dataRequestHandler.getNbrOfSuccessfulRequests();
    }

    /**
     * Advanced/debugging: Add a callback-listener to get notified when a tag request fails or is successful.
     *
     * @param callbackListener The callback-listener implementing the TagDataRequestCallbackListener interface.
     */
    public void setCallbackListener(TagDataRequestCallbackListener callbackListener) {
        dataRequestHandler.setCallbackListener(callbackListener);
    }

    /***** End of public methods *****/

    /**
     * Our framework instance.
     */
    protected static MobileTaggingFrameworkBackend frameworkInstance = null;

    /**
     * Our TagDataRequestHandler.
     */
    protected TagDataRequestHandler dataRequestHandler;

    /**
     * Are logs enabled?
     */
    protected static boolean logPrintsActivated = false;

    /**
     * Send data with https url
     */
    protected static boolean useHttpsActivated = true;

    /**
     * The ID to identify you as a client/customer on the server. This value will be sent in tags
     * using the "cpid"-attribute. It can be 6 digits or less for using MOBITECH or 32 digits for using CODIGO.
     */
    protected String cpId;

    /**
     * The name of the application. Only specify the name, the interface will add platform.
     * This value will be sent in tags using the "type"-attribute. Max 243 characters.
     */
    protected String appName;

    /**
     * You only want to meassure TNS-Sifo Panelist users, set true with {@link #MobileTaggingFramework(Builder)}
     */
    protected boolean panelistTrackingOnly = false;

    /**
     * The context of this application, used to get device ID for unique Tagging.
     *      *                        It will also necessary to initialize volley library

     * You can obtain this value using the method getApplicationContext() in the Android Activity-class.
     */
    protected Context context;


    /**
     * MobileTaggingFramework constructor with Builder class
     * @param builder
     * You can use also {@link #createInstance(Context, String, String, boolean)}
     */
    public MobileTaggingFramework(Builder builder) {
        this.context = builder.context;
        this.cpId = builder.cpId;
        this.appName = builder.appName;
        this.panelistTrackingOnly = builder.panelistTrackingOnly;
        useHttpsActivated = builder.useHttpsActivated;
        logPrintsActivated = builder.logPrintsActivated;
    }


    /**
     * Builder class for initialize framework
     * Use it as param with {link {@link #MobileTaggingFramework(Builder)}}
     */
    public static class Builder {
        private final Context context;
        private String cpId;
        private String appName;
        private boolean panelistTrackingOnly = false;
        private boolean logPrintsActivated = false;
        private boolean useHttpsActivated = true;

        /**
         * Constructor of Builder class
         * @param context The context of this application, used to get device ID for unique Tagging.
         *                It will also necessary to initialize volley library
         *                You can obtain this value using the method getApplicationContext() in the Android Activity-class.
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         *
         * @param cpId
         * @return
         */
        public Builder setCpId(String cpId) {
            this.cpId = cpId;
            return this;
        }

        /**
         *
         * @param appName
         * @return
         */
        public Builder setApplicationName(String appName) {
            this.appName = appName;
            return this;
        }

        /**
         *
         * @param panelistTrackingOnly
         * @return
         */
        public Builder panelistTrackingOnly(boolean panelistTrackingOnly) {
            this.panelistTrackingOnly = panelistTrackingOnly;
            return this;
        }

        /**
         *
         * @param https
         * @return
         */
        public Builder useHttps(boolean https) {
            this.useHttpsActivated = https;
            return this;
        }

        /**
         *
         * @param logPrintsActivated
         * @return
         */
        public Builder setLogPrintsActivated(boolean logPrintsActivated) {
            this.logPrintsActivated = logPrintsActivated;
            return this;
        }


        /**
         *
         * @return return constructor of MobileTaggingFramework {@link #MobileTaggingFramework(Builder)}
         */
        public MobileTaggingFramework build() {
            return new MobileTaggingFramework(this);
        }
    }

    /**
     * Destroy method of framework
     * Use for when you want to initialize framework with new params
     * @return frameworkInstance = null
     */
    public static void destroyFramework() {
        frameworkInstance = null;
    }
}