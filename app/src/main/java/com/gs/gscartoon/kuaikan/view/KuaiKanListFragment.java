package com.gs.gscartoon.kuaikan.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gs.gscartoon.R;
import com.gs.gscartoon.kuaikan.KuaiKanListContract;

import butterknife.ButterKnife;


public class KuaiKanListFragment extends Fragment implements KuaiKanListContract.View{
    private final static String TAG = "KuaiKanListFragment";

    private KuaiKanListContract.Presenter mPresenter;

    public KuaiKanListFragment() {

    }

    public static KuaiKanListFragment newInstance(/*int myVideo, String categories*/) {
        KuaiKanListFragment fragment = new KuaiKanListFragment();
        /*Bundle args = new Bundle();
        args.putInt(MyVideoActivity.MY_VIDEO, myVideo);
        args.putString(AppConstants.CATEGORIES, categories);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mMyVideo = getArguments().getInt(MyVideoActivity.MY_VIDEO,MyVideoActivity.ALL);
            //mCategories = getArguments().getString(AppConstants.CATEGORIES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kuai_kan_list, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initView(){

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView(){
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setPresenter(KuaiKanListContract.Presenter presenter) {
        mPresenter = presenter;
    }
}