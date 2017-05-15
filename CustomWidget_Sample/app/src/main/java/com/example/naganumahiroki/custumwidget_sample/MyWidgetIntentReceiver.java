package com.example.naganumahiroki.custumwidget_sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by naganumahiroki on 2015/09/16.
 *
 *
 * ボタンクリック時のアクションを実現するためにBroadcastReceiverを実装します．
 ウィジェットのをonReceiveメソッドで実現します．
 *
 *
 */
public class MyWidgetIntentReceiver extends BroadcastReceiver {
    public static int clickCount = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("UPDATE_WIDGET")) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.circular_widget_layout);

            // テキストをクリック回数を元に更新
//            remoteViews.setTextViewText(R.id.title, "クリック回数: " + MyWidgetIntentReceiver.clickCount);

            // もう一回クリックイベントを登録(毎回登録しないと上手く動かず)
//            remoteViews.setOnClickPendingIntent(R.id.button, MyWidgetProvider.clickButton(context));

            MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
        }
    }
}