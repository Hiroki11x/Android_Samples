package com.example.naganumahiroki.recyclerview_sample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by naganumahiroki on 2015/09/24.
 *
 * このクラスはRecyclerView.ViewHolderクラスを継承させたものでビューホルダーと呼びます。
 * このクラスは、リスト1行分レイアウト（item.xml）のビューオブジェクトを保持するクラスです。
 * リストに表示するデータを設定するメソッド内で使用します。
 *
 * ビューはインスタンス変数に宣言し、コンストラクタ内でインスタンス生成させます。
 * 今回は、テキストのみであるためTextViewのみを記述します。
 *
 */
public  class SingleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView mTextView;
    private Context mContext;
    public SingleViewHolder(View v,Context context) {
        super(v);
        v.setOnClickListener(this);
        mContext = context;
        mTextView = (TextView) v.findViewById(R.id.item_name);
    }
    @Override
    public void onClick(View view) {
        //getPositionで何番目のItemか取れる
         if(getPosition()==1){
             Intent intent = new Intent(mContext,GridRecyclerViewActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             mContext.startActivity(intent);
         }else if(getPosition()==2){
             Intent intent = new Intent(mContext,StaggeredGridLayoutActivity.class);
             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
             mContext.startActivity(intent);
         }
        Toast.makeText(mContext, "revrevr4ve4ver" + getPosition(), Toast.LENGTH_SHORT).show();
    }
}