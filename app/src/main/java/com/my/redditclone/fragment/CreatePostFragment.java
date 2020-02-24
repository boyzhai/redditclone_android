package com.my.redditclone.fragment;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;

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

        return validate;
    }

    private View.OnClickListener onClickListener = v -> {
        switch (v.getId()){
            case R.id.btnSubmit:
                validateField();
                break;
        }
    };

}
