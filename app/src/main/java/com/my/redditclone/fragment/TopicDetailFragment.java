package com.my.redditclone.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.my.redditclone.BaseActivity;
import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;
import com.my.redditclone.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopicDetailFragment extends BaseFragment {


    public TopicDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ((MainActivity)getActivity()).setTitle("Topic Details");
    }

}
