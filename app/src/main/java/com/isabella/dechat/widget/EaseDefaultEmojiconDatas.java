package com.isabella.dechat.widget;


import com.isabella.dechat.R;
import com.isabella.dechat.bean.EaseEmojicon;
import com.isabella.dechat.util.EaseSmileUtils;

public class EaseDefaultEmojiconDatas {

    private static String[] emojis = new String[]{
            EaseSmileUtils.ee_1,
            EaseSmileUtils.ee_2,
            EaseSmileUtils.ee_3,
            EaseSmileUtils.ee_4,
            EaseSmileUtils.ee_5,
            EaseSmileUtils.ee_6,
            EaseSmileUtils.ee_7,
            EaseSmileUtils.ee_8,
            EaseSmileUtils.ee_9,
            EaseSmileUtils.ee_10,
            EaseSmileUtils.ee_11,
            EaseSmileUtils.ee_12,
            EaseSmileUtils.ee_13,
            EaseSmileUtils.ee_14,
            EaseSmileUtils.ee_15,
            EaseSmileUtils.ee_16,
            EaseSmileUtils.ee_17,
            EaseSmileUtils.ee_18,
            EaseSmileUtils.ee_19,
            EaseSmileUtils.ee_20,
            EaseSmileUtils.ee_21,
            EaseSmileUtils.ee_22,
            EaseSmileUtils.ee_23,
            EaseSmileUtils.ee_24,
            EaseSmileUtils.ee_25,
            EaseSmileUtils.ee_26,
            EaseSmileUtils.ee_27,
            EaseSmileUtils.ee_28,
            EaseSmileUtils.ee_29,
            EaseSmileUtils.ee_30,
            EaseSmileUtils.ee_31,
            EaseSmileUtils.ee_32,
            EaseSmileUtils.ee_33,
            EaseSmileUtils.ee_34,
            EaseSmileUtils.ee_35,
            EaseSmileUtils.ee_36,
            EaseSmileUtils.ee_37,
            EaseSmileUtils.ee_38,
            EaseSmileUtils.ee_39,
            EaseSmileUtils.ee_40,
            EaseSmileUtils.ee_41,
            EaseSmileUtils.ee_42,
            EaseSmileUtils.ee_43,
            EaseSmileUtils.ee_44,
            EaseSmileUtils.ee_45,
            EaseSmileUtils.ee_46,
            EaseSmileUtils.ee_47,
            EaseSmileUtils.ee_48,

    };

    private static int[] icons = new int[]{
            R.mipmap.emoji_1,
            R.mipmap.emoji_2,
            R.mipmap.emoji_3,
            R.mipmap.emoji_4,
            R.mipmap.emoji_5,
            R.mipmap.emoji_6,
            R.mipmap.emoji_7,
            R.mipmap.emoji_8,
            R.mipmap.emoji_9,
            R.mipmap.emoji_10,
            R.mipmap.emoji_11,
            R.mipmap.emoji_12,
            R.mipmap.emoji_13,
            R.mipmap.emoji_14,
            R.mipmap.emoji_15,
            R.mipmap.emoji_16,
            R.mipmap.emoji_17,
            R.mipmap.emoji_18,
            R.mipmap.emoji_19,
            R.mipmap.emoji_20,
            R.mipmap.emoji_21,
            R.mipmap.emoji_22,
            R.mipmap.emoji_23,
            R.mipmap.emoji_24,
            R.mipmap.emoji_25,
            R.mipmap.emoji_26,
            R.mipmap.emoji_27,
            R.mipmap.emoji_28,
            R.mipmap.emoji_29,
            R.mipmap.emoji_30,
            R.mipmap.emoji_31,
            R.mipmap.emoji_32,
            R.mipmap.emoji_33,
            R.mipmap.emoji_34,
            R.mipmap.emoji_35,
            R.mipmap.emoji_36,
            R.mipmap.emoji_37,
            R.mipmap.emoji_38,
            R.mipmap.emoji_39,
            R.mipmap.emoji_40,
            R.mipmap.emoji_41,
            R.mipmap.emoji_42,
            R.mipmap.ic_introd,
            R.mipmap.ic_address,
            R.mipmap.ic_age,
            R.mipmap.ic_nickname,
            R.mipmap.ic_sex_boy,
            R.mipmap.ic_sex_gril,



    };


    private static final EaseEmojicon[] DATA = createData();

    private static EaseEmojicon[] createData() {
        EaseEmojicon[] datas = new EaseEmojicon[icons.length];
        for (int i = 0; i < icons.length; i++) {
            datas[i] = new EaseEmojicon(icons[i], emojis[i], EaseEmojicon.Type.NORMAL);
        }
        return datas;
    }

    public static EaseEmojicon[] getData() {
        return DATA;
    }
}
