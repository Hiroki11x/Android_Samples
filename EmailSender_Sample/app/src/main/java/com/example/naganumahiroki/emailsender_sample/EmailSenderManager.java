package com.example.naganumahiroki.emailsender_sample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by naganumahiroki on 2015/09/17.
 */
public class EmailSenderManager {

    //RFC822 電子メールを交換する際に使用されるテキストのフォーマットである、
    // “インターネット・ メッセージ・フォーマット”をパーズ/生成する手続きを定義しています。

    static public void sendMail(Context context) {
        final String email = getRegisterdAddress(context);
        final String password = "password";
        String body = "";
        String subject = "端末管理";

        try {
            //email と password更新
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sp.edit().putString("email", email).commit();
            sp.edit().putString("password", password).commit();

            //以下メール送信
            final Properties property = new Properties();
            property.put("mail.smtp.host", "smtp.gmail.com");
            property.put("mail.host", "smtp.gmail.com");
            property.put("mail.smtp.port", "465");
            property.put("mail.smtp.socketFactory.port", "465");
            property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            // セッション
            final Session session = Session.getInstance(property, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(email, password);
                }
            });

            MimeMessage mimeMsg = new MimeMessage(session);

            mimeMsg.setSubject(subject, "utf-8");
            mimeMsg.setFrom(new InternetAddress(email));
            mimeMsg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(email));


            // 添付ファイル本文
            final MimeBodyPart txtPart = new MimeBodyPart();
            txtPart.setText(body, "utf-8");

            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(txtPart);
            //mp.addBodyPart(filePart); //添付ファイルをする場合はこれ
            mimeMsg.setContent(mp);

            // メール送信する。
            final Transport transport = session.getTransport("smtp");
            transport.connect(email, password);
            transport.sendMessage(mimeMsg, mimeMsg.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            System.out.println("exception = " + e);

        } finally {
            System.out.println("finish sending email");
        }
    }

    //登録してあるアカウントを取得
    static private String getRegisterdAddress(Context context) {
        ArrayList<String> mailList = new ArrayList<String>();
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
        for (Account account : accounts) {
            mailList.add(account.name);
        }
        Log.d("", mailList.get(0));
        return mailList.get(0);
    }
}
