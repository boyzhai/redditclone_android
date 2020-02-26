package com.my.redditclone.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.my.redditclone.BaseActivity;
import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;
import com.my.redditclone.activities.MainActivity;
import com.my.redditclone.utilities.Util;


public class TopicDetailFragment extends BaseFragment {

    private TextInputEditText edtTitle, edtDescription , edtCreatedDate, edtUpdatedDate , edtTotalVotePoints;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        /**
         * setTitle is to set the title of the toolbar for the fragment.
         */
        ((MainActivity)getActivity()).setTitle("Topic Details");
        edtTitle = view.findViewById(R.id.edtTitle);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtCreatedDate = view.findViewById(R.id.edtCreatedDate);
        edtUpdatedDate = view.findViewById(R.id.edtUpdatedDate);
        edtTotalVotePoints = view.findViewById(R.id.edt_ttl_votepoints);

        /**
         * It is required to check if the bundle data is null before we extract the data out from list .
         */
        if(Util.bundle.get("post_title") != null){
            edtTitle.setText(Util.bundle.get("post_title"));
            edtDescription.setText(Util.bundle.get("post_description"));
            edtCreatedDate.setText(Util.bundle.get("post_created_date"));
            edtUpdatedDate.setText(Util.bundle.get("post_updated_date"));
            edtTotalVotePoints.setText(Util.bundle.get("post_vote_points"));
        }


    }

}
