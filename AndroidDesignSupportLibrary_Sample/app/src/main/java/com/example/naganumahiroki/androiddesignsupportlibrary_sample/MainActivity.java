package com.example.naganumahiroki.androiddesignsupportlibrary_sample;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    TextInputLayout id,name;
    EditText idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (TextInputLayout) findViewById(R.id.user_id);
        //setErrorEnabledをセットしておくと、エラー表示の部分のスペースが予め確保される
        // false指定の場合はsetErrorしたときにエラーが追加される
        id.setErrorEnabled(true);
        id.setError("*この項目は必須です");
        idText =(EditText)findViewById(R.id.user_id_text);
        idText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(count==0){
                    id.setError("入力してください beforeTextChanged");
                }else{
                    id.setErrorEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==0){
                    id.setError("入力してください onTextChanged");
                }else{
                    id.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<1){
                    id.setError("入力してください afterTextChanged");
                }else {
                    id.setErrorEnabled(false);
                }
            }
        });

        name = (TextInputLayout) findViewById(R.id.user_name);
        //setErrorEnabledをセットしておくと、エラー表示の部分のスペースが予め確保される
        // false指定の場合はsetErrorしたときにエラーが追加される
        name.setErrorEnabled(false);

        //エラー文言のセット
        name.setError("Error !!");
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
