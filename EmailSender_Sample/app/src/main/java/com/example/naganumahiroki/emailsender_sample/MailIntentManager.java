package com.example.naganumahiroki.emailsender_sample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by naganumahiroki on 2015/09/17.
 */
public class MailIntentManager {

    String from_email;
    String to_email;
    static String body = "この端末はただいま 田中 が使用しております";
    static String subject = "[端末管理]  ";

    public static Intent generateMailerIntent(Context context) {
        String info = Build.DISPLAY + ":" + Build.HARDWARE + ":" + Build.DEVICE;
        return sendMail(context, getRegisterdAddress(context), subject + info, body);
    }

    public static Intent sendMail(Context ctx, String address, String subject, String content) {
        Uri uri = Uri.parse("mailto:" + address);//このアドレスに送信される

        // ACTION_SENDTOを使用してIntentを生成
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);//sendじゃダメだった
        // タイトルを設定
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        // 本文をを設定
        intent.putExtra(Intent.EXTRA_TEXT, content);
        // メーラー呼び出し
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    //登録してあるアカウントを取得
    private static String getRegisterdAddress(Context context) {
        ArrayList<String> mailList = new ArrayList<String>();
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
        for (Account account : accounts) {
            mailList.add(account.name);
        }
        Log.d("", mailList.get(0));
        return mailList.get(0);
    }

    public static void setSubject(String subject) {
        MailIntentManager.subject = subject;
    }

    public static void setBody(String body) {

        MailIntentManager.body = body;
    }
}
