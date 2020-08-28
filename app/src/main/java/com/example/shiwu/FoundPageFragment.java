package com.example.shiwu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shiwu.adapter.MyAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class FoundPageFragment extends BaseFragment {
    private View rootView;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_found_page, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);
        Log.e(getTAG(), "onCreateView: ");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //int position = FragmentPagerItem.getPosition(getArguments());

        setOnRefreshListener();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new MyAdapter(this));
        Log.e(getTAG(), "onViewCreated: ");
    }

    private void setOnRefreshListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        //关闭上拉加载更多功能
        //refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "加载成功", Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
//                refreshlayout.finishLoadMoreWithNoMoreData();
            }
        });
    }

}