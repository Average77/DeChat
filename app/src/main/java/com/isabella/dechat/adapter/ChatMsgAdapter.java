package com.isabella.dechat.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.isabella.dechat.R;
import com.isabella.dechat.speex.SpeexPlayer;
import com.isabella.dechat.util.EaseSmileUtils;
import com.isabella.dechat.util.GlideUtils;
import com.isabella.dechat.util.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    LayoutInflater inflater;
    Context context;
    private List<EMMessage> list;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AnimationDrawable drawable;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                drawable1.stop();
            } else if (msg.what == 1) {
                drawable.stop();
                ;
            }
        }
    };
    private AnimationDrawable drawable1;
    private String chatPath;
    private String imagepath;
    private SpeexPlayer player;
    private SpeexPlayer player1;
    private final int widthPixels;

    public ChatMsgAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
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
            return list.get(position).direct() == EMMessage.Direct.RECEIVE ? 1 : 0;
        }
        if (list.get(position).getType() == EMMessage.Type.VOICE) {
            return list.get(position).direct() == EMMessage.Direct.RECEIVE ? 3 : 2;
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
        } else if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_text_left, parent, false);
            //将创建的View注册点击事件

            return new ViewHolderLeft(view);
        } else if (viewType == 2) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_voice_right, parent, false);
            return new ViewHolderVoiceRight(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chat_voice_left, parent, false);
            //将创建的View注册点击事件
            return new ViewHolderVoiceLeft(view);
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        String s = list.get(position).getBody().toString();
        String substring = s.substring(5, s.length() - 1);
        chatPath = PreferencesUtils.getValueByKey(context, "chatPath", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1439559070,3076958386&fm=26&gp=0.jpg");
        imagepath = PreferencesUtils.getValueByKey(context, "imagepath", "http://www.baidu.com/link?url=Yq6CQfjf2oSythhjkP5AmxUc3RrsbPwML7oLtkIzP8iVXdBf0QR3I_pmTftlOVueZrO8yn4KpkWoKtCXS7hUzTayldCtZskPjza9xZfMotbdsDBW6FoDYkCtYb09OLzVuAsgNBas6p-6HUyfL0OK1kmDYWEb6Qr9hu0vRnA_x0Hv6Iity0S7FMcH2IEzgd69crhc1NRoRuvWVGSNXzxIcgarf0sLVVDuxKT8L0b-2fLTiLESDDsDszdx7nO6M2L2wvXbdlcwg0HAW4Ptd_G9FhuygFUdCSng40cQ4hPB3KLkZ0bvhl6WFdKO_b6QaDo8f85ZlkuLEhwe8QQH-QfF38VYNuybcrKTODQJVu3CsPaj2W9Z-tVg7AUIiqHSByF4MhYn64nPxJel3GhVRQ3PTSfTScLghss0KZjWEfs0T-65hoLqZB7NffKzoj62jgnLNTML9QaQGP8cOVwz5yXq3wZG7pvv-YeeYXh8AQUUEXaihQwuytMGSGn5lOkV1NAq87RzU6pBb-wfOOiQ-MI5JS2Fp9fjWcHU9Jvs0CL_2TiXj8lVwl2WrJNmLBvw_25YOs-yff3wPgrCAEGxZDTAWsFTi8hTqgl4k8cnkfWaTnq&wd=&eqid=c9e3d59100006023000000025972b950");

        if (holder instanceof ViewHolderRight) {
            final ViewHolderRight viewHolderRight = (ViewHolderRight) holder;
            GlideUtils.getInstance().havaHeadRound(imagepath, viewHolderRight.textRightImage, context);
//            Boolean chatError = PreferencesUtils.getValueByKey(context, "chatError", false);
//            if (chatError){
//                if (viewHolderRight.chatError.getVisibility()!=View.VISIBLE){
//                    viewHolderRight.chatError.setVisibility(View.VISIBLE);
//                }
//            }else{
//                if (viewHolderRight.chatError.getVisibility()!=View.GONE){
//                    viewHolderRight.chatError.setVisibility(View.GONE);
//                }
//            }
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
            getData(position, viewHolderRight.chatRightTime);
        } else if (holder instanceof ViewHolderLeft) {
            final ViewHolderLeft viewHolderLeft = (ViewHolderLeft) holder;
            GlideUtils.getInstance().havaHeadRound(chatPath, viewHolderLeft.textLeftImage, context);

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
                getData(position, viewHolderLeft.chatLeftTime);
            }
        } else if (holder instanceof ViewHolderVoiceRight) {
            final ViewHolderVoiceRight right = (ViewHolderVoiceRight) holder;
            GlideUtils.getInstance().havaHeadRound(imagepath, right.head, context);

            getData(position, right.chatRightTime);
            drawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.anim_voice_send);
            //imageView.setBackground(drawable);
            final EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) list.get(position).getBody();
            player = new SpeexPlayer(voiceBody.getLocalUrl(), handler);
            int m = voiceBody.getLength() / 1000;
            right.second.setText(m + "″");
            if (m > 0) {
                if (m < 60) {
                    if (m!=1){
                        int width = widthPixels - 350;
                        Log.d("ChatMsgAdapter", "m*width/60+40:" + (m * width / 60 + 130));
                        right.voice.setPadding(10,0,m*width/60+130,0);
                    }
                } else {
                    right.voice.setPadding(10,0,widthPixels-350,0);
                }
            }
            right.voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ImageView) v).setImageDrawable(drawable);
                    if (!drawable.isRunning()) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getType() == EMMessage.Type.VOICE) {
                                if (i != position) {
                                    if (drawable.isRunning()) {
                                        player.stopPlay(false);
                                        drawable.stop();
                                        ((ImageView) v).setImageResource(R.drawable.send_voice_icon_3);
                                    } else if (drawable1.isRunning()) {
                                        player1.stopPlay(false);
                                        drawable1.stop();
                                    }
                                }
                            }
                        }
                        drawable.start();
                        player.startPlay();
                        handler.sendEmptyMessageDelayed(1, voiceBody.getLength());
                    } else {
                        drawable.stop();
                        player.stopPlay(false);
                        ((ImageView) v).setImageResource(R.drawable.send_voice_icon_3);
                    }
                }
            });
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = right.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(right.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = right.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(right.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }
        } else if (holder instanceof ViewHolderVoiceLeft) {
            final ViewHolderVoiceLeft left = (ViewHolderVoiceLeft) holder;
            GlideUtils.getInstance().havaHeadRound(chatPath, left.head, context);

            getData(position, left.chatLeftTime);
            drawable1 = (AnimationDrawable) context.getResources().getDrawable(R.drawable.anim_voice_receive);
            //imageView.setBackground(drawable);
            final EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) list.get(position).getBody();
            player1 = new SpeexPlayer(voiceBody.getLocalUrl(), handler);
            int m = voiceBody.getLength() / 1000;
            left.second.setText(m + "″");
            if (m > 0) {
                if (m < 60) {
                    if (m!=1){
                        int width = widthPixels - 350;
                        left.voice.setPadding(m*width/60+120,0,10,0);
                    }
                } else {
                    left.voice.setPadding(widthPixels-350,0,10,0);
                }
            }
            left.voice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ImageView) v).setImageDrawable(drawable1);

                    if (!drawable1.isRunning()) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getType() == EMMessage.Type.VOICE) {
                                if (i != position) {
                                    if (drawable.isRunning()) {
                                        player.stopPlay(false);
                                        drawable.stop();
                                    } else if (drawable1.isRunning()) {
                                        player1.stopPlay(false);
                                        drawable1.stop();
                                        ((ImageView) v).setImageResource(R.drawable.receive_voice_icon_3);
                                    }
                                }
                            }
                        }
                        drawable1.start();
                        player1.startPlay();
                        handler.sendEmptyMessageDelayed(0, voiceBody.getLength());
                    } else {
                        drawable1.stop();
                        player1.stopPlay(false);
                        ((ImageView) v).setImageResource(R.drawable.receive_voice_icon_3);
                    }
                }
            });
            if (mOnItemClickListener != null) {
                //为ItemView设置监听器
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = left.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(left.itemView, position); // 2
                    }
                });
            }
            if (mOnItemLongClickListener != null) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = left.getLayoutPosition();
                        mOnItemLongClickListener.onItemLongClick(left.itemView, position);
                        //返回true 表示消耗了事件 事件不会继续传递
                        return true;
                    }
                });
            }
        }

    }

    private void getData(int position, TextView tv) {
        if (position - 1 >= 0) {
            if (list.get(position).getMsgTime() - list.get(position - 1).getMsgTime() > 300000) {
                if (tv.getVisibility() != View.VISIBLE) {
                    tv.setVisibility(View.VISIBLE);
                }
                String format = simpleDateFormat.format(list.get(position).getMsgTime());
                tv.setText(format);

            } else {
                if (tv.getVisibility() != View.GONE) {
                    tv.setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class ViewHolderRight extends RecyclerView.ViewHolder {
        @BindView(R.id.text_right_image_text)
        TextView textRightImageText;
        @BindView(R.id.text_right_image)
        ImageView textRightImage;
        @BindView(R.id.chat_error)
        ImageView chatError;
        @BindView(R.id.chat_right_time)
        TextView chatRightTime;

        public ViewHolderRight(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderLeft extends RecyclerView.ViewHolder {
        @BindView(R.id.text_left_image)
        ImageView textLeftImage;
        @BindView(R.id.text_left_image_text)
        TextView textLeftImageText;
        @BindView(R.id.chat_left_time)
        TextView chatLeftTime;

        public ViewHolderLeft(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderVoiceLeft extends RecyclerView.ViewHolder {
        @BindView(R.id.voice_left_image)
        ImageView head;
        @BindView(R.id.chat_left_voice_iv)
        ImageView voice;
        @BindView(R.id.chat_voice_left_time)
        TextView chatLeftTime;
        @BindView(R.id.voice_left_second)
        TextView second;

        public ViewHolderVoiceLeft(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderVoiceRight extends RecyclerView.ViewHolder {
        @BindView(R.id.voice_right_image)
        ImageView head;
        @BindView(R.id.voice_right_image_msg)
        ImageView voice;
        @BindView(R.id.voice_right_time)
        TextView chatRightTime;
        @BindView(R.id.voice_error)
        ImageView voiceError;
        @BindView(R.id.voice_right_second)
        TextView second;

        public ViewHolderVoiceRight(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public void destory(){
        if (player!=null){
            player.stopPlay(true);
            drawable.stop();
            player=null;
        }
        if (player1!=null){
            player1.stopPlay(true);
            drawable1.stop();
            player1=null;
        }
        handler.removeCallbacksAndMessages(null);
    }
}
