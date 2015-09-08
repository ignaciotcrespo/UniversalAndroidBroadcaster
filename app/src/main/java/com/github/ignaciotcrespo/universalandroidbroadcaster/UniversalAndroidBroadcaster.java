package com.github.ignaciotcrespo.universalandroidbroadcaster;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

/**
 * Broadcasts objects of any kind, usually for logging purposes, but can be used for anything.
 */
public class UniversalAndroidBroadcaster {

    public static final String CONTENT_XML = "text/xml";
    public static final String CONTENT_JSON = "application/json";
    public static final String CONTENT_SQL = "text/sql";

    private static WeakReference<Context> sGlobalContext = new WeakReference<>(null);
    private static String sGlobalSession = "" + System.currentTimeMillis();

    private UniversalAndroidBroadcaster() {
        // hidden constructor
    }

    private static void addExtras(Object object, String type, String contentType, Context context, Intent service) {
        service.putExtra("object", object.toString());
        if (!TextUtils.isEmpty(type)) {
            service.putExtra("type", type);
        }
        service.putExtra("session", sGlobalSession);
        service.putExtra("timestamp", System.currentTimeMillis());
        if (!TextUtils.isEmpty(contentType)) {
            service.putExtra("content-type", contentType);
        }
        service.putExtra("app", context.getPackageName());
    }

    /**
     * Broadcasts an object. It needs the context, so be sure to initialize the broadcaster first using {@link #initialize(Context)}
     *
     * @param object      The object to broadcast. <b>It will broadcast the toString() method of the object</b>
     * @param type        A custom text to identify the object. Can be anything.
     * @param contentType The content type of the object to manage it later in a proper way. Check constants {@link #CONTENT_XML}, etc
     */
    public static void broadcastObject(Object object, String type, String contentType) {
        Context context = sGlobalContext.get();
        if (context != null) {
            broadcastObject(context, object, type, contentType);
        }
    }

    /**
     * Broadcasts an object.
     *
     * @param context     The context to send the broadcast
     * @param object      The object to broadcast. <b>It will broadcast the toString() method of the object</b>
     * @param type        A custom text to identify the object. Can be anything.
     * @param contentType The content type of the object to manage it later in a proper way. Check constants {@link #CONTENT_XML}, etc
     */
    public static void broadcastObject(Context context, Object object, String type, String contentType) {
        final Intent intent = new Intent("com.github.ignaciotcrespo.universalbroadcaster.ADD_OBJECT");
        intent.addCategory("com.github.ignaciotcrespo.universalbroadcaster");
        addExtras(object, type, contentType, context, intent);
        context.sendBroadcast(intent);
    }

    public static void initialize(Context context) {
        sGlobalContext = new WeakReference<>(context);
    }


}
