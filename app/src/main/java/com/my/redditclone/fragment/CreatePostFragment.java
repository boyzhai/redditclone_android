package com.my.redditclone.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;
import com.my.redditclone.activities.MainActivity;
import com.my.redditclone.utilities.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

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
        /**
         * setTitle is to set the title of the toolbar for the fragment.
         */
        ((MainActivity)getActivity()).setTitle("Create Post");
        tvTitleLayout = view.findViewById(R.id.tvTitleLayout);
        tvDescriptionLayout = view.findViewById(R.id.tvDescriptionLayout);
        edtTitle = view.findViewById(R.id.edtTitle);
        edtDescription = view.findViewById(R.id.edtDescription);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(onClickListener);

    }

    /**
     * validate every field when create post to check length or isEmpty .
     * @return if field is empty will return validate true as error message will show.
     *         if field is not empty will return vallidate false as no error message will show.
     */
    private boolean validateField(){
        boolean validate = false;

        if(edtTitle.getText().toString().trim().length() > tvTitleLayout.getCounterMaxLength()){
            tvTitleLayout.setError("Character count of Title cannot more than 255. ");
            validate = true;
        }else{
            tvTitleLayout.setError(null);
        }

        if(edtTitle.getText().toString().trim().isEmpty()){
            tvTitleLayout.setError("Title cannot be empty. ");
            validate = true;
        }else{
            tvTitleLayout.setError(null);
        }

        if(edtDescription.getText().toString().trim().length() > tvDescriptionLayout.getCounterMaxLength()){
            tvDescriptionLayout.setError("Character count of Description cannot more than 255. ");
            validate = true;
        }else{
            tvDescriptionLayout.setError(null);
        }

        if(edtDescription.getText().toString().trim().isEmpty()){
            tvDescriptionLayout.setError("Description cannot be empty. ");
            validate = true;

        }else{
            tvDescriptionLayout.setError(null);
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

    /**
     * createPost is to store data which key from the user into
     * Global HashMap storage which called as bundle in the Util packages.
     */

    private void createPost() {
        try {
            JSONObject postDetail = null;
            JSONArray postDetailsList = null;

            /**
             * index is post id increment as reference if needed
             */
            int index = 0;

            /**
             * This is to check if bundle storage is empty or null .
             * if bundle storage is null which store the list of the post then it will initialize the jsonobject and jsonarray.
             * if bundle storage is not null then it will grab the list of the post from the bundle and return it to the jsonobject and jsonarray as exisitng object or list.
             */
            if (Util.bundle.get("post_list") != null) {
                if (Util.bundle.get("post_list").length() > 0) {
                    postDetailsList = new JSONArray(Util.bundle.get("post_list"));
                    index = postDetailsList.length();
                    postDetail = new JSONObject();
                }
            } else {
                postDetail = new JSONObject();
                postDetailsList = new JSONArray();
            }

            /**
             * id = post id as reference id if needed.
             * title = post title
             * description = post description
             * created date = created date of the post when it is submitted.
             * updated date = updated date of the post when it will have any action from the list .
             * eg. increase upvote and downvote will reflected the updated date .
             *
             *
             */

            postDetail.put("id", index + 1);
            postDetail.put("title", edtTitle.getText().toString().trim());
            postDetail.put("description", edtDescription.getText().toString().trim());
            postDetail.put("created_date", new Date());
            postDetail.put("updated_date", new Date());
            postDetail.put("up_voted_count", 0);
            postDetail.put("down_voted_count", 0);
            postDetailsList.put(postDetail);


            /**
             * it will store the all data as jsonarray with jsonobject into bundle named as "post_list"
             * which use to grab list to use when it is needed.
             */
            Util.bundle.put("post_list", postDetailsList.toString());

            /**
             * It will show alert dialog when the submitted button is clicked and
             * It will navigate to the home page which the post that was just created into the recycleview list.
             */

            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("Success !")
                    .setMessage("New Post " + (index + 1) +" has been created.")
                    .setCancelable(false)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    navigateActivity(new Intent(getActivity(), MainActivity.class), true, false);
                                    break;
                            }
                        }
                    })
                    .show();


        }catch (JSONException e){
            e.printStackTrace();
        }
    }



}
