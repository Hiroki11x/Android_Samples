package com.example.naganumahiroki.custumwidget_sample;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RemoteViews;

import java.lang.reflect.Method;

/**
 * Created by naganumahiroki on 2015/09/16.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    /**
     onUpdateメソッドが実行時に画面を変更するために使われます．
     このクラスはウィジェットとのインターフェイスとなります．サンプルではonUpdateしか使っていませんが，
     ウィジェットの更新や有効・無効化・削除などのイベントを受けとることができるそうです．
    */

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = createViews(context);

        Intent configIntent = new Intent(context, MainActivity.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        views.setOnClickPendingIntent(R.id.parent, configPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager) {

        // Construct the RemoteViews object
        RemoteViews views = createViews(context);
        ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);

        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        views.setOnClickPendingIntent(R.id.parent, configPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(thisWidget, views);
    }

    static RemoteViews createViews(Context context){
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.circular_widget_layout);
        views.setTextViewText(R.id.name, "Project Name");
        views.setTextViewText(R.id.device, Build.MODEL);
        views.setTextViewText(R.id.layout_size, getLayoutSize(context));
        views.setTextViewText(R.id.os, "Android" + Build.VERSION.RELEASE);
        views.setTextViewText(R.id.open_gl_version, getOpenGlVersion());
        views.setTextViewText(R.id.caution, "Project Code");

        return views;
    }

    static String getOpenGlVersion() {
        if (Build.VERSION.SDK_INT < 8) {
            return "GL ES 1.0 and 1.1";
        } else if (Build.VERSION.SDK_INT >= 8 && Build.VERSION.SDK_INT < 18) {
            return "GL ES 2.0";
        } else if (Build.VERSION.SDK_INT >= 18 && Build.VERSION.SDK_INT < 21) {
            return "GL ES 3.0";
        } else if (Build.VERSION.SDK_INT >= 21) {
            return "GL ES 3.1";
        }
        return "GL ES";
    }

    static String getLayoutSize(Context context) {
        Point size = getRealSize(context);
        return String.valueOf(size.x) + " × " + String.valueOf(size.y);
    }

    static Point getRealSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point real = new Point(0, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // Android 4.2以上
            display.getRealSize(real);
            return real;

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            // Android 3.2以上
            try {
                Method getRawWidth = Display.class.getMethod("getRawWidth");
                Method getRawHeight = Display.class.getMethod("getRawHeight");
                int width = (Integer) getRawWidth.invoke(display);
                int height = (Integer) getRawHeight.invoke(display);
                real.set(width, height);
                return real;

            } catch (Exception e) {
                Log.e("size", "Exception", e);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                Point size = new Point();
                display.getSize(size);
                return size;
            }
        }

        return real;
    }

    public static PendingIntent clickButton(Context context) {

        // クリック回数を増加
        MyWidgetIntentReceiver.clickCount ++;

        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction("UPDATE_WIDGET");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // アップデート
    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, MyWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }

}
