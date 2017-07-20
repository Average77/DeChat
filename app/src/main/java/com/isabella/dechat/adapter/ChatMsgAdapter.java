package com.isabella.dechat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.isabella.dechat.R;
import com.isabella.dechat.util.EaseSmileUtils;
import com.isabella.dechat.util.GlideUtils;
import com.isabella.dechat.util.PreferencesUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;
    Context context;
    private List<EMMessage> list;

    public ChatMsgAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getType() == EMMessage.Type.TXT) {
            if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
                return 1;
            } else {
                return 0;
            }
        }


        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_text_right, parent, false);
            //将创建的View注册点击事件

            return new ViewHolderRight(view);
        } else  {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_text_left, parent, false);
            //将创建的View注册点击事件

            return new ViewHolderLeft(view);
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        String s = list.get(position).getBody().toString();
        String substring = s.substring(5, s.length() - 1);
        if (holder instanceof ViewHolderRight) {
            final ViewHolderRight viewHolderRight = (ViewHolderRight) holder;
            Boolean isSetPhoto = PreferencesUtils.getValueByKey(context, "isSetPhoto", false);
            if (isSetPhoto) {
                GlideUtils.getInstance().havaHeadRound(PreferencesUtils.getValueByKey(context, "imagepath", ""), viewHolderRight.textRightImage, context);
            }
            viewHolderRight.textRightImageText.setText(EaseSmileUtils.getSmiledText(context, substring));
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolderRight.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(viewHolderRight.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = viewHolderRight.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(viewHolderRight.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }
        }else if (holder instanceof ViewHolderLeft){
            final ViewHolderLeft viewHolderLeft = (ViewHolderLeft) holder;
            GlideUtils.getInstance().havaHeadRound(PreferencesUtils.getValueByKey(context, "chatPath", ""), viewHolderLeft.textLeftImage, context);

            viewHolderLeft.textLeftImageText.setText(EaseSmileUtils.getSmiledText(context, substring));
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolderLeft.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(viewHolderLeft.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = viewHolderLeft.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(viewHolderLeft.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }
        }

    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }




    static class ViewHolderRight extends RecyclerView.ViewHolder{
        @BindView(R.id.text_right_image_text)
        TextView textRightImageText;
        @BindView(R.id.text_right_image)
        ImageView textRightImage;

        public ViewHolderRight(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderLeft extends RecyclerView.ViewHolder{
        @BindView(R.id.text_left_image)
        ImageView textLeftImage;
        @BindView(R.id.text_left_image_text)
        TextView textLeftImageText;

        public ViewHolderLeft(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
