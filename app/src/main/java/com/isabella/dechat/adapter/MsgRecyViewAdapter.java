package com.isabella.dechat.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.isabella.dechat.R;
import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.NearbyDataBean;
import com.isabella.dechat.util.DeviceUtils;
import com.isabella.dechat.util.Distance;
import com.isabella.dechat.util.GlideUtils;
import com.isabella.dechat.util.PreferencesUtils;

import java.util.List;


public class MsgRecyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NearbyDataBean> mValues;
    private boolean mIsStagger;
    private double distance;
    private Context context;
    private int tag = 1; // 1 先行布局 2 瀑布流
    private int itemWidth;
    java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");
    private String format;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    public MsgRecyViewAdapter(Context context,List<NearbyDataBean> mValues) {
        this.context = context;
        this.mValues=mValues;
        //当前屏幕 的宽度 除以2
        itemWidth = DeviceUtils.getDisplayInfomation(context).x / 2;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
//    public void setData(List<NearbyDataBean> data, int page) {
//        if (mValues == null) {
//            mValues = new ArrayList<>();
//        }
//        if (page == 1) {
//            mValues.clear();
//        }
//        if (data != null || data.size() != 0) {
//            mValues.addAll(data);
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycler_message_stag, parent, false);
            //将创建的View注册点击事件

            return new StaggerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycler_message_list, parent, false);
            //将创建的View注册点击事件

            return new ViewHolder(view);
        }
    }

    public void dataChange(int type) {
        this.tag = type;

    }

    @Override
    public int getItemViewType(int position) {
        if (tag == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        String lat = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lat", "34");
        String lng = PreferencesUtils.getValueByKey(IApplication.getApplication(), "lng", "34");
        Log.d("MsgRecyViewAdapter", lat);
        Log.d("MsgRecyViewAdapter", lng);
        if ((!"34".equals(lat) || !"34".equals(lng)) && mValues.get(position).getLng() != 0.0 || mValues.get(position).getLng() != 0.0) {
            distance = Distance.getInstance().LantitudeLongitudeDist(Double.valueOf(lng), Double.valueOf(lat), mValues.get(position).getLng(), mValues.get(position).getLat());
            format = df.format(distance);
        } else {
            format = "0";
        }
        Log.d("MsgRecyViewAdapter", "distance:" + distance);

        if (holder instanceof ViewHolder) {

            //列表的形式展示
            final ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.nickname.setText(mValues.get(position).getNickname());


            viewHolder.intro.setText(mValues.get(position).getIntroduce());
            GlideUtils.getInstance().haveCacheLarger(mValues.get(position).getImagePath(), viewHolder.iv, context);
            //判断是否设置了监听器
            if(mOnItemClickListener != null){
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = viewHolder.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(viewHolder.itemView,position); // 2
                    }
                });
            }
            if(mOnItemLongClickListener != null){
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = viewHolder.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(viewHolder.itemView,position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }
            viewHolder.sex.setText(mValues.get(position).getAge() + "岁 , " + mValues.get(position).getGender());
            if ("0".equals(format)) {
                viewHolder.distance.setVisibility(View.INVISIBLE);
                viewHolder.dis.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.distance.setVisibility(View.VISIBLE);
                viewHolder.iv.setVisibility(View.VISIBLE);
                viewHolder.distance.setText("相距" + format + "km");
                viewHolder.dis.setVisibility(View.VISIBLE);
            }


        } else {
            final StaggerViewHolder staggeredViewHolder = (StaggerViewHolder) holder;
            if ("0".equals(format)) {
                staggeredViewHolder.mContentView.setText("距离未知");
            } else {
                staggeredViewHolder.mContentView.setText("相距" + format + "km");
            }

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) staggeredViewHolder.iv.getLayoutParams();
            StaggeredGridLayoutManager.LayoutParams card = (StaggeredGridLayoutManager.LayoutParams) staggeredViewHolder.cardView.getLayoutParams();

            float scale = (float) itemWidth / (float) mValues.get(position).getPicWidth();
            params.width = itemWidth;
            params.height = (int) ((float) scale * (float) mValues.get(position).getPicHeight());
            card.width = itemWidth;
            card.height = params.height + 125;

            System.out.println("params.scale = " + scale);
            System.out.println("params.width = " + params.width + " " + mValues.get(position).getPicWidth());
            System.out.println("params.height = " + params.height + "  " + mValues.get(position).getPicHeight());

            staggeredViewHolder.iv.setLayoutParams(params);
            staggeredViewHolder.cardView.setLayoutParams(card);

//            params.width = itemWidth

            GlideUtils.getInstance().haveCache(mValues.get(position).getImagePath(), staggeredViewHolder.iv, context);
            // Glide.with(context).load(mValues.get(position).getImagePath()).error(R.drawable.ic_album_default).into(staggeredViewHolder.iv);
            if(mOnItemClickListener != null){
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = staggeredViewHolder.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(staggeredViewHolder.itemView,position); // 2
                    }
                });
            }
            if(mOnItemLongClickListener != null){
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = staggeredViewHolder.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(staggeredViewHolder.itemView,position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    class StaggerViewHolder extends RecyclerView.ViewHolder {
        public TextView mContentView;
        public CardView cardView;
        public ImageView iv;

        public StaggerViewHolder(View itemView) {
            super(itemView);
            mContentView = (TextView) itemView.findViewById(R.id.recy_msg_stag_dis);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            iv = (ImageView) itemView.findViewById(R.id.recy_msg_stag_iv);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nickname;
        public final TextView sex;
        public final TextView intro;
        public final TextView distance;
        public final ImageView iv;
        public final ImageView dis;

        public ViewHolder(View view) {
            super(view);
            nickname = (TextView) view.findViewById(R.id.recy_msg_nickname);
            sex = (TextView) view.findViewById(R.id.recy_msg_sex);
            intro = (TextView) view.findViewById(R.id.recy_msg_intro);
            distance = (TextView) view.findViewById(R.id.recy_msg_distance);
            iv = (ImageView) view.findViewById(R.id.recy_msg_iv);
            dis = (ImageView) view.findViewById(R.id.recy_msg_distance_iv);
        }

    }

}
