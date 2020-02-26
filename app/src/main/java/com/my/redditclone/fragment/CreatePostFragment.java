package com.my.redditclone.fragment;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;
import com.my.redditclone.activities.MainActivity;
import com.my.redditclone.model.Topic;
import com.my.redditclone.utilities.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreatePostFragment extends BaseFragment {


    private TextInputLayout tvTitleLayout , tvDescriptionLayout;
    private TextInputEditText edtTitle, edtDescription;
    private Button btnSubmit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_post, container, false);
        initView(view);
        return view;
    }


    private void initView(View view){
        tvTitleLayout = view.findViewById(R.id.tvTitleLayout);
        tvDescriptionLayout = view.findViewById(R.id.tvDescriptionLayout);
        edtTitle = view.findViewById(R.id.edtTitle);
        edtDescription = view.findViewById(R.id.edtDescription);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(onClickListener);

    }

    private boolean validateField(){
        boolean validate = false;

        if(edtTitle.getText().toString().trim().isEmpty()){
            tvTitleLayout.setError("Title cannot be empty");
            validate = true;
        }else{
            tvTitleLayout.setError(null);
        }

        if(edtDescription.getText().toString().trim().isEmpty()){
            tvDescriptionLayout.setError("Description cannot be empty");
            validate = true;

        }else{
            tvTitleLayout.setError(null);
        }

        return !validate;
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()){
            case R.id.btnSubmit:
                if(validateField()){
                    createPost();
                }
                break;
        }
    };

    private void createPost() {
        try {
            JSONObject postDetail = null;
            JSONArray postDetailsList = null;

            List<Topic> topicList = null;

            int index = 0;

            if (Util.bundle.get("post_list") != null) {
                if (Util.bundle.get("post_list").length() > 0) {
                    postDetailsList = new JSONArray(Util.bundle.get("post_list"));
                    index = postDetailsList.length();
                    postDetail = new JSONObject();
                }
            } else {
                topicList = new ArrayList<>();
                postDetail = new JSONObject();
                postDetailsList = new JSONArray();
            }

//            Topic topic = new Topic();
//            topic.setId(index + 1);
//            topic.setTitle(edtTitle.getText().toString().trim());
//            topic.setDescription(edtDescription.getText().toString().trim());
//            topic.setCreatedDate(new Date().toString());
//            topic.setUpdatedDate(new Date().toString());
//            topic.setUpVotedCount(0);
//            topic.setDownVotedCount(0);
//            topicList.add(topic);

            postDetail.put("id", index + 1);
            postDetail.put("title", edtTitle.getText().toString().trim());
            postDetail.put("description", edtDescription.getText().toString().trim());
            postDetail.put("created_date", new Date());
            postDetail.put("updated_date", new Date());
            postDetail.put("up_voted_count", 0);
            postDetail.put("down_voted_count", 0);
            postDetailsList.put(postDetail);

           // Log.i("test", topicList.toString());

           // Util.bundle.get("post_list");
            Util.bundle.put("post_list", postDetailsList.toString());
            navigateActivity(new Intent(getActivity(), MainActivity.class), true, false);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }



}
