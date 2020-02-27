package com.my.redditclone;


import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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

    public String formatDate(Date date , String datePattern){

            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat(datePattern);

              return simpleDateFormat.format(date);

    }


    public String calculateTimeDifference(String dateComment , Date dateToday ){
        try {
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

            Date startDate = simpleDateFormat.parse(dateComment);
            Date endDate = simpleDateFormat.parse(simpleDateFormat.format(dateToday));

            //milliseconds
            long different = endDate.getTime() - startDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            StringBuilder timeDifference = new StringBuilder("");

            if(elapsedDays > 0){
                timeDifference.append(elapsedDays +" days ago");
            }else{
                if(elapsedHours > 0){
                    timeDifference.append(elapsedHours +" hours ago");
                }else{
                    if(elapsedMinutes > 0){
                        timeDifference.append(elapsedMinutes +" minutes ago");
                    }else{
                        timeDifference.append(elapsedSeconds +" seconds ago");
                    }
                }
            }

            return timeDifference.toString();

        }catch(ParseException e){
            e.printStackTrace();
            return null;
        }

    }

}
