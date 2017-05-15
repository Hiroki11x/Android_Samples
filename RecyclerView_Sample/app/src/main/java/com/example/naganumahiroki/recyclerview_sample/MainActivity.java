package com.example.naganumahiroki.recyclerview_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        // リストのレイアウトサイズが動的に変わらず固定されている場合、
        // setHasFixedSize()にtrueを設定すると、パフォーマンスが向上します。今回はレイアウトサイズが動的に変わることはありません。

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // レイアウトマネージャーをRecyclerViewに設定しています。
        // RecyclerViewを使う場合は、レイアウトマネージャーを必ず設定する必要があります。
        // １行リストを表示する場合は、LinearLayoutManagerを設定します。

        final List list = new ArrayList();

        list.add("Single line layout");
        list.add("Grid layout");
        list.add("Staggered gridLayout layout");
        for (int i = 0; i < 30 ; i++ ) {
            list.add("item" + Math.pow(i,5)%10000);
        }
        // データを生成し、アダプターに設定しています。
        // RecyclerAdapterクラスはRecyclerView.Adapterクラスを継承した自作クラスです。
        // ここではMainActivityクラス内に内部クラスとして宣言しています。
        recyclerView.setAdapter(new SingleRecyclerAdapter(getApplicationContext(), list));
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
