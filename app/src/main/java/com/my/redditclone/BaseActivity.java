package com.my.redditclone;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     *
     * @param intent
     * @param closePreviousActivity - remove all activity on the background
     * @param finishCurrentActivity - remove current activity on the background
     */
    public void navigateActivity(Intent intent , boolean closePreviousActivity , boolean finishCurrentActivity ){
        if(closePreviousActivity){

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }

        if(finishCurrentActivity){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        startActivity(intent);
    }

    public void navigateFragment(Fragment fragment , boolean addToBackStack , String tag){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(addToBackStack){
           fragmentTransaction.replace(R.id.content_frame , fragment , tag);
           fragmentTransaction.addToBackStack(tag);
        }else{
            fragmentTransaction.add(R.id.content_frame , fragment , tag);
        }
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }


}
