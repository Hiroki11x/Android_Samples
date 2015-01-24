package com.example.transferanimationsample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;


public class MainActivity extends ActionBarActivity implements OnTouchListener, OnClickListener,OnLongClickListener {

	    private View move_view[] = new View[3];
	    private boolean longClickFlg = false;   //長押しチェック用フラグ
	    int currentX[] = new int[3];   //Viewの左辺座標：X軸
	    int currentY[]= new int[3];   //Viewの上辺座標：Y軸
	    int offsetX;    //画面タッチ位置の座標：X軸
	    int offsetY;    //画面タッチ位置の座標：Y軸
	     
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        move_view[0] = findViewById(R.id.btnRequest);
	        move_view[1] = findViewById(R.id.tweet);
	        move_view[2] = findViewById(R.id.btnClearAuth);
	        for(int i =0;i<3;i++){
	        	//リスナーの設定
		        move_view[i].setOnClickListener(this);
		        move_view[i].setOnTouchListener(this);
	        }
	        
	         
	         
	    }
	    @Override
	    public boolean onLongClick(View view) {
	        longClickFlg = true;
	        return false;
	    }
	    @Override
	    public void onClick(View view) {
	        //アニメーションの設定
	        AnimationSet animationSet = new AnimationSet(false);
	        ScaleAnimation scale = new ScaleAnimation(1,1.05f,1,1.05f, 0, 0);
	        scale.setDuration(10);
	        animationSet.addAnimation(scale);
	        view.startAnimation(animationSet);
	         
	        return ;
	    }
	 
	    @Override
	    public boolean onTouch(View view, MotionEvent event) {
	        int x = (int) event.getRawX();
	        int y = (int) event.getRawY();
	        int i=3;
	        if(view==move_view[0]){
	        	i=0;
	        }else if(view==move_view[1]){
	        	i=1;
	        }if(view==move_view[2]){
	        	i=2;
	        }
	         
	        switch(event.getAction()) {
	        case MotionEvent.ACTION_MOVE:
	        	if(longClickFlg) {
	                int diffX = offsetX - x;
	                int diffY = offsetY - y;
	                 
	                currentX[i] -= diffX;
	                currentY[i] -= diffY;
	                view.layout(currentX[i], currentY[i], currentX[i] + view.getWidth(), currentY[i] + view.getHeight());
	                offsetX = x;
	                offsetY = y;
	        	}
	            break;
	        case MotionEvent.ACTION_DOWN:
	        	currentX[i] = view.getLeft();
	        	currentY[i] = view.getTop();
	            offsetX = x;
	            offsetY = y;
	            break;
	        case MotionEvent.ACTION_UP:
	        	longClickFlg = false;
	            break;
	        }
	         
	        return false;
	    }
	


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
