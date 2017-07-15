package com.isabella.dechat.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.isabella.dechat.R;
import com.isabella.dechat.bean.FriendListDataBean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/7/14.
 */

public class FriendAdapter extends BaseAdapter {
    private List<FriendListDataBean> list = null;
    private Context mContext;

    public FriendAdapter(Context mContext, List<FriendListDataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<FriendListDataBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list!=null?list.size():0;
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final FriendListDataBean mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.phone_constacts_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.icon = (SimpleDraweeView) view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }



        viewHolder.tvTitle.setText(list.get(position).getNickname());
        Uri uri = Uri.parse(list.get(position).getImagePath());
        viewHolder.icon.setImageURI(uri);
        return view;

    }



    final static class ViewHolder {

        TextView tvTitle;
        SimpleDraweeView icon;

    }

}
