package com.my.redditclone.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.my.redditclone.BaseActivity;
import com.my.redditclone.R;
import com.my.redditclone.fragment.CreatePostFragment;
import com.my.redditclone.fragment.TopicFragment;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        navigateFragment(new TopicFragment(), false , new TopicFragment().getClass().getName());

    }

    private void initToolbar(){

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvToolbarTitle = findViewById(R.id.toolbar_title);
    }

    public void setTitle(String title){
        tvToolbarTitle.setText(title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Fragment currentFragment  = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(currentFragment instanceof  TopicFragment){
            if(currentFragment.getFragmentManager().getBackStackEntryCount() == 0){
                  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
