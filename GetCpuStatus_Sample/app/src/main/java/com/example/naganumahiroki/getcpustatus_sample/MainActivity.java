package com.example.naganumahiroki.getcpustatus_sample;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    int source = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = (TextView)findViewById(R.id.text);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source = (source+1) % 3;
                if(source==0){
                    textView1.setText(getInfoFromFile());
                }else if(source == 1){
                    textView1.setText(getInfoFromOSBuild());
                }else{
                    textView1.setText(getInfoFromOriginalMethod());
                }
            }
        });
    }

    public String getInfoFromFile(){
        String str = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String line;
            while((line = reader.readLine()) != null) {
                str += line + "\n";
            }
            reader.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "読み込みエラー", Toast.LENGTH_SHORT).show();
        }
        return str;
    }

    public String getInfoFromOSBuild(){
        String str = "";
        str += Build.BOARD +"\n";
        str += Build.BOOTLOADER +"\n";
        str += Build.BRAND +"\n";
        str += Build.CPU_ABI +"\n";
        str += Build.CPU_ABI2 +"\n";
        str += Build.DEVICE +"\n";
        str += Build.DISPLAY +"\n";
        str += Build.FINGERPRINT +"\n";
        str += Build.HARDWARE +"\n";
        str += Build.HOST +"\n";
        str += Build.ID +"\n";
        str += Build.MANUFACTURER +"\n";
        str += Build.MODEL +"\n";
        str += Build.PRODUCT +"\n";

        str += Build.RADIO +"\n";
        str += Build.TAGS +"\n";
        str += Build.TIME +"\n";
        str += Build.TYPE +"\n";
        str += Build.UNKNOWN +"\n";
        str += Build.USER +"\n";

        str += Build.VERSION.CODENAME +"\n";
        str += Build.VERSION.INCREMENTAL +"\n";
        str += Build.VERSION.RELEASE +"\n";
        str += Build.VERSION.SDK +"\n";
        str += Build.VERSION.SDK_INT +"\n";

        return str;
    }

    public String getInfoFromOriginalMethod(){
        String str = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String line;
            while((line = reader.readLine()) != null) {
                if(line.startsWith("Hardware"))
                str += line + "\n";
            }
            reader.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "読み込みエラー", Toast.LENGTH_SHORT).show();
        }

        //コア数の取得(http://m-miya.blog.jp/archives/1025533687.html)
        int core = Runtime.getRuntime().availableProcessors();
        str += String.valueOf("core num: "+core);
        return str;
    }
}
