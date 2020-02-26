package com.my.redditclone;


import android.content.Intent;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    public void navigateFragment(Fragment fragment , boolean addToBackStack , String tag){
        ((BaseActivity)getActivity()).navigateFragment(fragment , addToBackStack ,tag );
        if(addToBackStack){
            ((BaseActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else{
            ((BaseActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }


    public void navigateActivity(Intent intent , boolean closePreviousActivity , boolean finishCurrentActivity ){
        ((BaseActivity)getActivity()).navigateActivity(intent , closePreviousActivity ,finishCurrentActivity);
    }
}
