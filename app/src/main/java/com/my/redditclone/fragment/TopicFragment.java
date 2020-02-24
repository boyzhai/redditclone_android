package com.my.redditclone.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;
import com.my.redditclone.activities.MainActivity;


public class TopicFragment extends BaseFragment {

    private Button btnTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ((MainActivity)getActivity()).setTitle("Home");
        btnTest = view.findViewById(R.id.button);
        btnTest.setOnClickListener( v -> {
            navigateFragment( new TopicDetailFragment() , true , new TopicDetailFragment().getClass().getName());
        });
    }

}
