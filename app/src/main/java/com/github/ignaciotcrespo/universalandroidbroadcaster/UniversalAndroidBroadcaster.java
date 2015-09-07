package com.github.ignaciotcrespo.universalandroidbroadcaster;

import android.content.Context;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 * Created by crespo on 07/09/15.
 */
public class UniversalAndroidBroadcaster {

    private static WeakReference<Context> sGlobalContext = new WeakReference<>(null);
    private static String sGlobalSession = "" + System.currentTimeMillis();

    private UniversalAndroidBroadcaster() {
        // hidden constructor
    }

    private static void addExtras(Object object, String type, String contentType, Context context, Intent service) {
        service.putExtra("object", object.toString());
        service.putExtra("type", type);
        service.putExtra("session", sGlobalSession);
        service.putExtra("timestamp", System.currentTimeMillis());
        service.putExtra("content-type", contentType);
        service.putExtra("app", context.getPackageName());
    }

    public static void broadcastObject(Object object, String type, String contentType) {
        Context context = sGlobalContext.get();
        if (context != null) {
            broadcastObject(context, object, type, contentType);
        }
    }

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
