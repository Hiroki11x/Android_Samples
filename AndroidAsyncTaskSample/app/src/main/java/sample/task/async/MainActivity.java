package sample.task.async;

/**
 * Created by hirokinaganuma on 2015/03/02.
 */


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    MyAsyncTask task;
    TextView textView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)this.findViewById(R.id.txtResult);
        editText=(EditText)findViewById(R.id.editText);
    }

    public void startAsyncTask(View v){//Startボタンを押したとき
        task = new MyAsyncTask(this, textView);//このアクティビティとTextViewを引数にセットしてインスタンス化
        String args = editText.getText().toString();
        task.execute(args);//executeメソッドにはdoInBackground命令が利用する引数をセット
    }

    public void cancelAsyncTask(View v){//Cancelボタンを押したとき
        task.cancel(true);//AsyncTaskをキャンセル
        textView.setText("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
