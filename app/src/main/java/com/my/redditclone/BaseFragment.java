package com.my.redditclone;


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
}
