package se.kantarsifo.mobileanalytics.sampleapp;

import android.app.Application;
import android.util.Log;

import se.kantarsifo.mobileanalytics.framework.TSMobileAnalytics;
import se.kantarsifo.mobileanalytics.framework.TagDataRequest;
import se.kantarsifo.mobileanalytics.framework.TagDataRequestCallbackListener;


public class ApplicationImpl extends Application implements TagDataRequestCallbackListener {

    private static final TagInfo sTagInfo = new TagInfo();

    @Override
    public void onCreate() {
        super.onCreate();

//        ComponentName webComponent = new ComponentName(this, WebActivity.class);
//        getPackageManager().setComponentEnabledSetting(webComponent,
//                BuildConfig.USE_WEB_ACTIVITY ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
//                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                0);
    }


    @Override
    public void onDataRequestComplete(TagDataRequest request) {
        Log.d(Contants.LOG_TAG, request.getURL());
        Log.d(Contants.LOG_TAG, "Data request completed with success:" +
                "\nCode: " + request.getHttpStatusCode() +
                "\nRequest ID: " + request.getRequestID() +
                "\nCat: " + request.getCat() +
                "\nName: " + request.getName() +
                "\nId: " + request.getID());

        Log.d(Contants.LOG_TAG, "Number of successful requests: "
                + TSMobileAnalytics.getInstance().getNbrOfSuccessfulRequests());
        Log.d(Contants.LOG_TAG, "***********************************");
        Log.d(Contants.LOG_TAG, "Request queue size: " + TSMobileAnalytics.getInstance().getRequestQueue().size());
    }

    @Override
    public void onDataRequestFailed(TagDataRequest request) {
        Log.w(Contants.LOG_TAG, request.getURL());
        Log.w(Contants.LOG_TAG, "Data request completed with failure:" +
                "\nCode: " + request.getHttpStatusCode() +
                "\nRequest ID: " + request.getRequestID() +
                "\nCat: " + request.getCat() +
                "\nName: " + request.getName() +
                "\nId: " + request.getID());

        Log.w(Contants.LOG_TAG, "Number of successful requests: "
                + TSMobileAnalytics.getInstance().getNbrOfSuccessfulRequests());
        Log.w(Contants.LOG_TAG, "Number of failed requests: "
                + TSMobileAnalytics.getInstance().getNbrOfFailedRequests());
        Log.w(Contants.LOG_TAG, "***********************************");
        Log.w(Contants.LOG_TAG, "Request queue size: " + TSMobileAnalytics.getInstance().getRequestQueue().size());
    }


    public static TagInfo tagInfo() {
        return sTagInfo;
    }

    public static class TagInfo {
        private String[] categories = new String[2];
        private String name;
        private String contentId;

        public void setCategory(int index, String category) {
            categories[index] = category;
        }

        public String getCategory(int index) {
            return categories[index];
        }

        public String[] getCategories() {
            return categories;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

    }
}
