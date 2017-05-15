package sample.task.async;

/**
 * Created by hirokinaganuma on 2015/03/02.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {
    /*AsyncTask<>内の
     *1つ目:非同期処理の実行時にメインスレッド側から与える情報
     *2つ目:進捗状況を管理するための情報
     *3つ目:非同期処理の結果の情報
     */
    private TextView textView;//操作対象のTextView
    private Activity activity;//操作対象のActivity
    private ProgressDialog progress;

    public MyAsyncTask(Activity activity, TextView textView) {
        super();
        this.activity = activity;//現在のActivityを引数として代入
        this.textView = textView;//結果を表示するtextを引数として代入
    }

    @Override
    protected void onPreExecute() {//タスクが実行された直後に、UIスレッドで呼び出される。非同期処理の直前にダイアログを起動
        progress = new ProgressDialog(activity);//progressbarのインスタンス化
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//Dialogに表示するProgressBarの向きを決める
        progress.setMessage("transmitting...");//Progressbarに設定する文字列を設定する。
        progress.setMax(100);//progressbarの最大値を100にセットする
        progress.setProgress(0);//progressbarの初期値を0にセットする
        progress.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",//Dialogにキャンセルボタンをセット
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyAsyncTask.this.cancel(true);
                    }
                });
        progress.show();//progressbarを表示
    }


    @Override
    protected String doInBackground(String... params) {//引数として受け取ったURLをキーにページの取得を試みます。
        String result = null;//文字列を初期化
        for(int j =0 ; j <= 10 ;j++){
            publishProgress(1);//進捗状況をUIスレッドに通知
            SystemClock.sleep(20);//20ミリ秒(0.02秒待つ)/(擬似的に時間のかかる処理を作っている)
        }

        try {
            HttpGet reqest = new HttpGet(params[0]);//(引数のURLからHttpGetクラスでページを要求する)リクエスト情報を表す
            DefaultHttpClient client = new DefaultHttpClient();//サーバーに送信する役割を持つDefaultHttpClientクラスのインスタンスを生成
            HttpResponse response = client.execute(reqest);//executeメソッドにHttpGetクラスのオブジェクトを渡すことでサーバーにリクエストが送信されます。戻り値はHttpResponseクラスのオブジェクトに格納される。
            int status = response.getStatusLine().getStatusCode();//Httpステータスを取得(200 OKとか404 エラーとか)
            if(status == HttpStatus.SC_OK){//成功のとき
                result = EntityUtils.toString(response.getEntity(), "UTF-8");//getEntityメソッドで、レスポンス本体を取得、それをEntityUtilsクラスのtoStringメソッドで文字列に変換(UTF-8で)する。
            } else {//成功しなかったとき
                result = "Error : "+ status;
            }
        } catch (ClientProtocolException e) {//HTTPプロトコルでのエラーシグナル
            e.printStackTrace();
        } catch (ParseException e) {//解析中に予想外のエラーが発生したことを表すシグナル
            e.printStackTrace();
        } catch (IOException e) {//入出力例外のシグナル
            e.printStackTrace();
        }
        for(int j =0 ; j <= 90 ;j++){
            publishProgress(1);//進捗状況をUIスレッドに通知
            SystemClock.sleep(20);//20ミリ秒(0.02秒待つ)/(擬似的に時間のかかる処理を作っている)
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {//publishProgressによる進捗状況の通知
        progress.incrementProgressBy(values[0]);//この通知に応じてダイアログの値を加算(引数に与えられた値を加算/ex 1ずつ加算)
    }

    @Override
    protected void onPostExecute(String result) { //非同期処理が終了した際に実行
        textView.setText(result);
        progress.dismiss();//プログレスバーを閉じる
    }

    @Override
    protected void onCancelled() {
        textView.setText("Transmission canceled");
        progress.dismiss();//プログレスバーを閉じる
    }


}
