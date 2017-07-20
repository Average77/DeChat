package com.isabella.dechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

/**
 * Created by muhanxi on 17/7/16.
 */

public class ChatAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    private List<EMMessage> list;

    public ChatAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);

        ViewHolderLeftText viewHolderLeftText = null ;
        ViewHolderRightText viewHolderRightText = null ;


        if (convertView == null) {

            switch (type) {
                case 1:
                    //自己发送的文本
                    convertView = inflater.inflate(R.layout.item_chat_text_right, parent, false);
                    viewHolderRightText = new ViewHolderRightText(convertView);
                    viewHolderRightText.textRightImage= (ImageView) convertView.findViewById(R.id.text_right_image);
                    viewHolderRightText.textRightImageText= (TextView) convertView.findViewById(R.id.text_right_image_text);
                    convertView.setTag(viewHolderRightText);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.item_chat_text_left, parent, false);
                    viewHolderLeftText = new ViewHolderLeftText(convertView);
                    viewHolderLeftText.textLeftImage= (ImageView) convertView.findViewById(R.id.text_left_image);
                    viewHolderLeftText.textLeftImageText= (TextView) convertView.findViewById(R.id.text_left_image_text);
                    convertView.setTag(viewHolderLeftText);
                    break;
            }
        }else {
            switch (type) {
                case 1:
                    //自己发送的文本
                    viewHolderRightText = (ViewHolderRightText)convertView.getTag() ;
                    break;
                case 2:
                    viewHolderLeftText = (ViewHolderLeftText)convertView.getTag() ;
                    break;
            }
        }
        String s = list.get(position).getBody().toString();
        String substring = s.substring(5, s.length() - 1);
        switch (type) {
            case 1:
                //自己发送的文本

                Boolean isSetPhoto = PreferencesUtils.getValueByKey(context, "isSetPhoto", false);
                if (isSetPhoto) {
                    GlideUtils.getInstance().havaHeadRound(PreferencesUtils.getValueByKey(context, "imagepath", ""), viewHolderRightText.textRightImage, context);
                }
                viewHolderRightText.textRightImageText.setText(EaseSmileUtils.getSmiledText(context, substring));
                break;
            case 2:
                GlideUtils.getInstance().havaHeadRound(PreferencesUtils.getValueByKey(context, "chatPath", ""), viewHolderLeftText.textLeftImage, context);

                viewHolderLeftText.textLeftImageText.setText(EaseSmileUtils.getSmiledText(context, substring));
                break;
        }





        return convertView;
    }


    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getType() == EMMessage.Type.TXT) {
            if (list.get(position).direct() == EMMessage.Direct.RECEIVE) {
                return 2;
            } else {
                return 1;
            }
        }


        return  -1 ;
    }



    static class ViewHolderRightText {
        @BindView(R.id.text_right_image)
        ImageView textRightImage;
        @BindView(R.id.text_right_image_text)
        TextView textRightImageText;

        ViewHolderRightText(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderLeftText {
        @BindView(R.id.text_left_image_text)
        TextView textLeftImageText;
        @BindView(R.id.text_left_image)
        ImageView textLeftImage;

        ViewHolderLeftText(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
