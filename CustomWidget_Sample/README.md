# CustomWidget_Sample
Android Widgetのサンプル

##導入手順

###1. まずwidgetを表示するために、
app/src/main/resにxmlフォルダを追加。その中に、今回のsampleではdemo_widget_provider.xmlを追加。
このxmlでは、以下のような項目を定めます。(http://qiita.com/torub/items/6c1fc66ef95ec77980c6 より)
initialLayoutはレイアウトファイルを設定。
previewImageに画像を設定すると、ウィジェットの追加画面でサムネイルが表示可能。
updatePeriodMills属性は、ウィジェットの更新タイマを定義します．このタイマに従ってAppWidgetProviderはonUpdateコールバックメソッドで呼ばれる(と書いてありますが、このサンプルでは時間を短くしても動作しなかったです)。

###2. 次に、widgetのレイアウトを記述します。
今回の、場合はwidget_layout.xmlもしくはcircular_widget_layout.xmlに定義しています。
このレイアウトは使えないとか制約はありますが、それ以外だいたいふだんと同じです。
レイアウトは以下4つのみが利用可能です。
FrameLayout
LinearLayout
RelativeLayout
GridLayout

###3. Javaファイルの設定①
MainActivity.javaは今回特に記述しません。スキップします。
Widgetをクリックするとアクションを起こしたい場合、BroadcastReceiverを継承したクラスにおいて、
その処理を記述します。sampleにおいてはコメントアウトしていますが、MyWidgetIntentReceiverがそれにあたります。

###4. Javaファイルの設定②
Widgetの生成、更新を行うJavaクラスを記述します。
sampleではMyWidgetProviderがそれにあたり、AppWidgetProviderクラスを継承する必要が有ります。

###5. AndroidManifest.xmlの編集
アプリケーションタグ内に、今回の場合以下のようにreceiverを記述しています。
```xml:AndroidManifest.xml
<receiver android:name=".MyWidgetProvider" >
            <intent-filter>
                        <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
            </intent-filter>
            <meta-data
                android:name="android.appwidget.provider"
                android:resource="@xml/demo_widget_provider" />
</receiver>

<receiver android:name=".MyWidgetIntentReceiver" 
          android:label="@string/app_name" >
            <intent-filter>
                        <action android:name="UPDATE_WIDGET" />
            </intent-filter>
            <meta-data
                android:name="android.appwidget.provider"
                android:resource="@xml/demo_widget_provider" />
</receiver>
```

