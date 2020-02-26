package com.my.redditclone.fragment;


import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;
import com.my.redditclone.activities.MainActivity;
import com.my.redditclone.helper.RecyclerTouchListener;
import com.my.redditclone.model.Topic;
import com.my.redditclone.utilities.Util;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class TopicFragment extends BaseFragment {

    private RecyclerView  rv_topiclist;
    private List<Topic> topicList;
    private TopicListAdapter topicListAdapter;
    private Button btnCreatePost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        topicList = new ArrayList<>();
        /**
         * setTitle is to set the title of the toolbar for the fragment.
         */
        ((MainActivity)getActivity()).setTitle("Home");
        rv_topiclist = view.findViewById(R.id.rv_topic_list);
        btnCreatePost = view.findViewById(R.id.btn_create_post);
        btnCreatePost.setOnClickListener(v -> {
            navigateFragment(new CreatePostFragment() , true , new CreatePostFragment().getClass().getName());
        });
        initTopicList();
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
    }
    /**
     * initTopicList - It will required to check the bundle is null or not .
     * It will extract the data from the bundle then initialize the data into jsonarray.
     * Gson is used to convert the json to arraylist which needed to set into recycleview.
     * Collections is used to sort the list to descending order .
     *  eg. a. o2 compare to o1  = descennding order
     *      b. o1 compare to o2 =  ascending order.
     */
    private void initTopicList(){
        topicList.clear();
        if(Util.bundle.get("post_list") != null) {
            Log.i("test", Util.bundle.get("post_list"));
            try {
                JSONArray topicJSON = new JSONArray(Util.bundle.get("post_list"));
                Gson gson = new Gson();
                Type TopicListType = new TypeToken<ArrayList<Topic>>() {
                }.getType();
                topicList = gson.fromJson(topicJSON.toString(), TopicListType);
                //descending order
                Collections.sort(topicList, (o1, o2) -> o2.getUpVotedCount().compareTo(o1.getUpVotedCount()));

                /**
                 *  initialize the adapter of the recycle and set the list from the above into recycleview adapter
                 */
                topicListAdapter = new TopicListAdapter(topicList);
                rv_topiclist.setAdapter(topicListAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Custom create the adapter of the recycleview and custom adapter layout as required.
     */
    public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder>{

        private List<Topic> topicList;

        public class ViewHolder extends  RecyclerView.ViewHolder{
            ConstraintLayout clAddPost , clPost;
            MaterialCardView cvAddPost , cvPost;
            TextView tvTitle , tvDescription , tvVote , tvDate;
            Button btnUpVote, btnDownVote;
            public ViewHolder(@NonNull View view) {
                super(view);
                tvTitle = view.findViewById(R.id.tv_title);
                tvDescription = view.findViewById(R.id.tv_description);
                tvVote = view.findViewById(R.id.tv_vote_point);
                tvDate = view.findViewById(R.id.tv_date);
                btnUpVote = view.findViewById(R.id.btn_upvote);
                btnDownVote = view.findViewById(R.id.btn_downvote);
                clAddPost = view.findViewById(R.id.cl_layout_add_post);
                clPost = view.findViewById(R.id.cl_layout_card);
                cvAddPost = view.findViewById(R.id.cv_add_post);
                cvPost = view.findViewById(R.id.cv_layout_card);
            }
        }

        public TopicListAdapter(List<Topic> topicList){
            this.topicList = topicList;

        }

        @Override
        public TopicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_topic_list, parent ,false);
            return new TopicListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TopicListAdapter.ViewHolder holder, int position) {
            Topic topic = topicList.get(position);
//           if(topic.isAddTopic()){
//                holder.cvAddPost.setVisibility(View.VISIBLE);
//                holder.cvPost.setVisibility(View.INVISIBLE);
//                holder.clAddPost.setOnClickListener(v -> {
//                    navigateFragment(new CreatePostFragment() , true , new CreatePostFragment().getClass().getName());
//                });
//            }

            /**
             * check if data is null or not before set the data into the view show as below.
             */

            if(topic.getId() != null ) {
                holder.cvAddPost.setVisibility(View.INVISIBLE);
                holder.cvPost.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(topic.getTitle());
                holder.tvDescription.setText(topic.getDescription());
                holder.tvDate.setText(calculateTimeDifference(new Date(topic.getCreatedDate()), new Date()));
                holder.tvVote.setText(Integer.toString(topic.getUpVotedCount()) + " points");
            }

            /**
             * This button is used increase the number of upvote point with infinite number and set the data into correct arraylist position
             * After that , It is need to convert the arraylist into jsonarray to matches with the bundle list to avoid any crash or conflict by using GSON.
             */

            holder.btnUpVote.setOnClickListener(v -> {
                int vote = topic.getUpVotedCount();
                vote++;
                holder.tvVote.setText(Integer.toString(vote)+ " points");
                topic.setUpVotedCount(vote);
                topic.setUpdatedDate(new Date().toString());


                topicList.set(position , topic);
                Gson gson = new Gson();
                JsonArray newTopicList = gson.toJsonTree(topicList).getAsJsonArray();
                Util.bundle.put("post_list",newTopicList.toString());
                rv_topiclist.invalidate();


        });

            /**
             * This button is used to decrease the number of upvote point with infinite number and set the data into correct arraylist position
             * After that , It is need to convert the arraylist into jsonarray to matches with the bundle list to avoid any crash or conflict by using GSON.
             */
            holder.btnDownVote.setOnClickListener(v ->{
                int vote = topic.getUpVotedCount();
                if(vote > 0){
                    vote--;
                }

                holder.tvVote.setText(Integer.toString(vote) + " points");
                topic.setUpVotedCount(vote);
                topic.setUpdatedDate(new Date().toString());
                topicList.set(position , topic);
                Gson gson = new Gson();
                JsonArray newTopicList = gson.toJsonTree(topicList).getAsJsonArray();
                Util.bundle.put("post_list",newTopicList.toString());
                topicListAdapter.notifyDataSetChanged();
            });

            /**
             * This layout is used as button with clicklistener when user click the layout on the screen and it will navigate to another page called Topic Details.
             *
             * It will store the data into global bundle for each field needed from each different position on the recycleview is clicked
             * The data will extract it from the global bundle and display on the TopicDetails Fragment.
             */

            holder.clPost.setOnClickListener(v ->{
                Util.bundle.put("post_title",topicList.get(position).getTitle());
                Util.bundle.put("post_description",topicList.get(position).getDescription());
                Util.bundle.put("post_created_date",topicList.get(position).getCreatedDate());
                Util.bundle.put("post_updated_date",topicList.get(position).getUpdatedDate());
                Util.bundle.put("post_vote_points",Integer.toString(topicList.get(position).getUpVotedCount()));
                navigateFragment(new TopicDetailFragment() , true , new TopicDetailFragment().getClass().getName());
            });


        }

        /**
         *  getItemCount - If list want to show only 20 item then it required to set it with 20 ever the list has more than 20 item.
         *
         */
        @Override
        public int getItemCount() {
            //only return 20 item to the list
            if(topicList.size() > 20){
                return 20;
            }else{
                return topicList.size();
            }

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh :

                // this is the menu bar button with refresh icon to invalidate which used to update ui data from  recycle view.
                initTopicList();
                   break;
        }
        return true;

    }

}
