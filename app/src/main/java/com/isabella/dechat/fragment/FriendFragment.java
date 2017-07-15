package com.isabella.dechat.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.isabella.dechat.R;
import com.isabella.dechat.activity.MainActivity;
import com.isabella.dechat.activity.SplashActivity;
import com.isabella.dechat.base.AppManager;
import com.isabella.dechat.base.BaseFragment;
import com.isabella.dechat.base.IApplication;
import com.isabella.dechat.bean.FriendListDataBean;
import com.isabella.dechat.contact.FriendContact;
import com.isabella.dechat.presenter.FriendPresenter;
import com.isabella.dechat.util.PreferencesUtils;
import com.isabella.dechat.widget.MyToast;
import com.isabella.dechat.widget.sort.ClearEditText;
import com.isabella.dechat.widget.sort.PinyinComparator;
import com.isabella.dechat.widget.sort.SortAdapter;
import com.isabella.dechat.widget.sort.sortlist.CharacterParser;
import com.isabella.dechat.widget.sort.sortlist.SideBar;
import com.isabella.dechat.widget.sort.sortlist.SortModel;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FriendFragment extends BaseFragment<FriendContact.FriendView, FriendPresenter> implements FriendContact.FriendView {


    @BindView(R.id.friend_tips_login_tv)
    TextView friendTipsLoginTv;
    @BindView(R.id.filter_edit)
    ClearEditText mClearEditText;
    @BindView(R.id.sortlist)
    ListView sortListView;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sidrbar)
    SideBar sideBar;
    Unbinder unbinder;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.friend_tips_login_iv)
    ImageView friendTipsLoginIv;

    private SortAdapter adapter;

    List<FriendListDataBean> list = new ArrayList<>();
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList = new ArrayList<>();
    private PinyinComparator pinyinComparator;
    boolean temp = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!PreferencesUtils.getValueByKey(getActivity(),"isLogin",false)){
            friendTipsLoginIv.setVisibility(View.VISIBLE);
            friendTipsLoginTv.setVisibility(View.VISIBLE);
            springView.setVisibility(View.INVISIBLE);
            sideBar.setVisibility(View.INVISIBLE);
            sortListView.setVisibility(View.INVISIBLE);
            mClearEditText.setVisibility(View.INVISIBLE);
        }else{
            friendTipsLoginIv.setVisibility(View.GONE);
            friendTipsLoginTv.setVisibility(View.GONE);
            springView.setVisibility(View.VISIBLE);
            sideBar.setVisibility(View.VISIBLE);
            sortListView.setVisibility(View.VISIBLE);
            mClearEditText.setVisibility(View.VISIBLE);
        }
        initData();
        springView.setType(SpringView.Type.FOLLOW);
        return view;
    }


    private void initData() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @SuppressLint("NewApi")
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        presenter.getData(System.currentTimeMillis(),true);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                // Toast.makeText(getApplication(),
                SortModel item = (SortModel) adapter.getItem(position);
                MyToast.getInstance().makeText(item.getName());
            }
        });

        adapter = new SortAdapter(getActivity(), SourceDateList);

        springView.setHeader(new MeituanHeader(getActivity()));
        springView.setFooter(new MeituanFooter(getActivity()));

        springView.setListener(new SpringView.OnFreshListener() {


            @Override
            public void onRefresh() {
                temp = true;
                presenter.getData(System.currentTimeMillis(),temp);
            }

            @Override
            public void onLoadmore() {


                if (list != null) {
                    temp = false;
                    long lastTime = list.get(list.size() - 1).getRelationtime();

//                    KLog.i("-1==========",lastTime);
//                    KLog.i("-2==========",list.get(list.size() - 2).getLasttime());
//                    KLog.i("-3==========",list.get(list.size() - 3).getLasttime());
//                    KLog.i("-4==========",list.get(list.size() - 4).getLasttime());
//                    KLog.i("-5==========",list.get(list.size() - 5).getLasttime());
//                    KLog.i("-6==========",list.get(list.size() - 6).getLasttime());
//                    KLog.i("-7==========",list.get(list.size() - 7).getLasttime());
//                    KLog.i("-8==========",list.get(list.size() - 8).getLasttime());
//                    KLog.i("-9==========",list.get(list.size() - 9).getLasttime());
//                    KLog.i("-10==========",list.get(list.size() - 10).getLasttime());
//                   // KLog.i("-11==========",list.get(list.size() - 11).getLasttime());
                    presenter.getData(lastTime,temp);
                }


            }
        });
        sortListView.setAdapter(adapter);
    }

    FriendPresenter presenter = new FriendPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public FriendPresenter initPresenter() {
        return presenter;
    }

    @Override
    public void success(List<FriendListDataBean> date,boolean isData) {
        springView.onFinishFreshAndLoad();
        if (date == null || date.size() == 0) {
            return;
        }
        KLog.a(date);
        if (temp) {
            list.clear();
            SourceDateList.clear();
        }
        if (isData &&! temp) {
        }else{
            if (date!=null&&date.size()!=0) {
                list.addAll(date);
            }
        }

        List<String> constact = new ArrayList<String>();

        for (int i = 0; i < date.size(); i++) {
            constact.add(date.get(i).getNickname() + "|" + date.get(i).getImagePath() + "|" + date.get(i).getUserId());
        }
        String[] names = new String[]{};
        names = constact.toArray(names);
        List<SortModel> sortModels = filledData(names);
        if (isData && !temp) {
        }else{
            if (sortModels!=null&&sortModels.size()!=0) {
                SourceDateList.addAll(sortModels);
            }
        }

        adapter.notifyDataSetChanged();
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);

        mClearEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean arg1) {
                mClearEditText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

            }
        });
        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void failed(Throwable e) {

    }


    /**
     * 为ListView填充数据
     *
     * @param date
     * @return
     */
    private List<SortModel> filledData(String[] date) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < date.length; i++) {
            SortModel sortModel = new SortModel();
            String[] split = date[i].split("\\|");
            sortModel.setName(split[0]);
            sortModel.setUrl(split[1]);
            sortModel.setUserId(Integer.valueOf(split[2]));
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    @OnClick(R.id.friend_tips_login_iv)
    public void onViewClicked() {
        IApplication.setIsStart(true);
        toActivity(SplashActivity.class,null,0);
        AppManager.getAppManager().finishActivity(MainActivity.class);
    }
}
