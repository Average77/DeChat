package com.isabella.dechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.isabella.dechat.R;
import com.isabella.dechat.bean.PhotolistBean;
import com.isabella.dechat.util.GlideUtils;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/6/10.
 */

public class UserInfoRecyAdapter extends RecyclerView.Adapter<UserInfoRecyAdapter.CViewHolder> {
    private LayoutInflater inflater;
    private List<PhotolistBean> list;
    private Context context;
    private MyItemClickListener mItemClickListener = null;

    public UserInfoRecyAdapter(List<PhotolistBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public UserInfoRecyAdapter.CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycler_user_info, parent, false);

        CViewHolder viewHolder = new CViewHolder(view, mItemClickListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserInfoRecyAdapter.CViewHolder holder, int position) {
       GlideUtils.getInstance().havaRoundLetter(list.get(position).getImagePath(),holder.iv,context);

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    class CViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MyItemClickListener mListener;
        ImageView iv;

        public CViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            iv = (ImageView) itemView.findViewById(R.id.recy_user_info_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }
}
