package com.example.naganumahiroki.recyclerview_sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naganumahiroki on 2015/09/24.
 */
public class SingleRecyclerAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private List<String> mItemList = new ArrayList<>();

    public SingleRecyclerAdapter(final Context context, final List itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new SingleViewHolder(view,mContext);
    }
    // ビューホルダーを生成するメソッドです。LayoutInflaterを使ってレイアウトXMLのビューオブジェクトを生成し、
    // それをコンストラクタの引数としてビューホルダーを生成し返却します。
    // このメソッドは、リストを生成する時に自動で実行されます。

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TextView textItem = (TextView) holder.itemView.findViewById(R.id.item_name);
        textItem.setText(mItemList.get(position));
    }
    // リストに表示するデータを設定するメソッドです。テキストに表示する文字を設定しています。
    // 設定する文字列は、RecyclerAdapterのコンストラクタに渡されたString型リストの値を使用しています

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
    //表示するリストの行数を返却するメソッドです。

}