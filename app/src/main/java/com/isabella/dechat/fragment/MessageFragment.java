package com.isabella.dechat.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isabella.dechat.R;
import com.isabella.dechat.activity.UserInfoActivity;
import com.isabella.dechat.adapter.MsgRecyViewAdapter;
import com.isabella.dechat.base.BaseFragment;
import com.isabella.dechat.bean.NearbyDataBean;
import com.isabella.dechat.contact.RecyclerContact;
import com.isabella.dechat.presenter.RecyclerPresenter;
import com.isabella.dechat.widget.SpacesItemDecoration;
import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MessageFragment extends BaseFragment<RecyclerContact.RecyView, RecyclerPresenter> implements RecyclerContact.RecyView {


    @BindView(R.id.msg_recycler)
    RecyclerView msgRecycler;
    @BindView(R.id.refresh_layout)
    SpringView refreshLayout;
    @BindView(R.id.msg_floating)
    FloatingActionButton msgFloating;
    Unbinder unbinder;
    List<NearbyDataBean> list = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    int page = 1;
    private MsgRecyViewAdapter adapter;
    RecyclerPresenter presenter = new RecyclerPresenter();
    // private HorizontalDividerItemDecoration build;
    //  private DividerItemDecoration dividerItemDecoration;
    private SpacesItemDecoration decoration;
    //private List<NearbyDataBean> nearbyDataBeen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        refreshLayout.setType(SpringView.Type.FOLLOW);

        return view;
    }

    private void initView() {


        msgFloating.setTag(1);
        decoration = new SpacesItemDecoration(10);

        msgFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag();
                if (tag == 1) {
                    msgFloating.setTag(2);
                    toStaggeredGridLayoutManager();
                    ((FloatingActionButton) v).setImageResource(R.drawable.ic_list_mode);


                } else {
                    msgFloating.setTag(1);
                    toLinearLayoutManager();
                    ((FloatingActionButton) v).setImageResource(R.drawable.ic_grid_mode);
                }
            }
        });

        //  dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST,10,10, Color.parseColor("#ffffff"));
        horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).build();
        // build = new HorizontalDividerItemDecoration.Builder(getActivity()).margin(10,10).color(Color.parseColor("#ffffff")).build();
        adapter = new MsgRecyViewAdapter(getActivity(), list);
//        if (PreferencesUtils.getValueByKey(getActivity(),"isData",false)){
//            nearbyDataBeen = IApplication.getApplication().daoSession.getNearbyDataBeanDao().loadAll();
//            adapter.setData(nearbyDataBeen,page);
//        }

        toLinearLayoutManager();

        presenter.getData(System.currentTimeMillis(), page);


        refreshLayout.setHeader(new MeituanHeader(getActivity()));
        refreshLayout.setFooter(new MeituanFooter(getActivity()));

        refreshLayout.setListener(new SpringView.OnFreshListener() {

            private long lastTime;

            @Override
            public void onRefresh() {
                System.out.println("onRefresh = ");
                page = 1;
                presenter.getData(System.currentTimeMillis(), page);
            }

            @Override
            public void onLoadmore() {
                page = 2;
                System.out.println("onLoadmore = ");
                if (list != null) {
                    lastTime = list.get(list.size() - 1).getLasttime();
                    presenter.getData(lastTime, page);
                }


            }
        });
        adapter.setOnItemClickListener(new MsgRecyViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //  Toast.makeText(getActivity(), "click " + position+"", Toast.LENGTH_SHORT).show();
                Bundle bundle=new Bundle();
                NearbyDataBean nearbyDataBean = list.get(position);

                Intent intent=new Intent(getActivity(),UserInfoActivity.class);
                intent.putExtra("userId",nearbyDataBean.getUserId());
                intent.putExtra("nickname",nearbyDataBean.getNickname());
                intent.putExtra("imagePath",nearbyDataBean.getImagePath());
                intent.putExtra("picWidth",nearbyDataBean.getPicWidth());
                intent.putExtra("picHeight",nearbyDataBean.getPicHeight());
                intent.putExtra("intro",nearbyDataBean.getIntroduce());
                intent.putExtra("sex",nearbyDataBean.getGender());
                intent.putExtra("address",nearbyDataBean.getArea());
                intent.putExtra("age",nearbyDataBean.getAge());
                intent.putExtra("lasttime",nearbyDataBean.getLasttime());
                startActivity(intent);
               // MyToast.makeText(getActivity(), list.get(position).getUserId() + "   " + position, Toast.LENGTH_SHORT);

            }
        });
//        adapter.setOnItemLongClickListener(new MsgRecyViewAdapter.OnItemLongClickListener() {
//            @Override
//            public void onItemLongClick(View view, int position) {
//              //  Toast.makeText(getActivity(), "click " + position+"", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void toLinearLayoutManager() {
        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        adapter.dataChange(1);
        msgRecycler.setLayoutManager(linearLayoutManager);
        msgRecycler.setAdapter(adapter);
        msgRecycler.addItemDecoration(horizontalDividerItemDecoration);
        msgRecycler.removeItemDecoration(decoration);
    }


    public void toStaggeredGridLayoutManager() {
        if (staggeredGridLayoutManager == null) {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        adapter.dataChange(2);
        msgRecycler.setLayoutManager(staggeredGridLayoutManager);
        msgRecycler.setAdapter(adapter);
        msgRecycler.removeItemDecoration(horizontalDividerItemDecoration);
        msgRecycler.addItemDecoration(decoration);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public RecyclerPresenter initPresenter() {
        return presenter;
    }

    @Override
    public void success(List<NearbyDataBean> data, boolean isData) {
        refreshLayout.onFinishFreshAndLoad();

        if (page == 1) {
            list.clear();
        }

        if (isData && page == 2) {
        } else {
            Log.d("MessageFragment", "data:" + data);
            // adapter.setData(data, page);

            if (data != null || data.size() != 0) {
                list.addAll(data);

            }
            //notifyDataSetChanged();
            //list.addAll(data);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void failed(Throwable e) {

    }

}
