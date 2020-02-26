package com.my.redditclone.fragment;


import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.my.redditclone.BaseFragment;
import com.my.redditclone.R;
import com.my.redditclone.activities.MainActivity;
import com.my.redditclone.model.Topic;
import com.my.redditclone.utilities.Util;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        ((MainActivity)getActivity()).setTitle("Home");
        rv_topiclist = view.findViewById(R.id.rv_topic_list);
        btnCreatePost = view.findViewById(R.id.btn_create_post);
        btnCreatePost.setOnClickListener(v -> {
            navigateFragment(new CreatePostFragment() , true , new CreatePostFragment().getClass().getName());
        });
        initTopicList();
        topicListAdapter = new TopicListAdapter(topicList);
        rv_topiclist.setAdapter(topicListAdapter);

        topicListAdapter.sort(false);
        topicListAdapter.clear();
        topicListAdapter.addAll(topicList);

        topicListAdapter.notifyDataSetChanged();

    }

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
               // topicList.add(new Topic(true));
                for(Topic topic : topicList) {
                    System.out.println(topic.getUpVotedCount());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
          //  topicList.add(new Topic(true));
        }
    }

    public class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.ViewHolder>{

        private List<Topic> topicList;
        private SortedList<Topic> topicSortedList;

        public void sort(final Boolean ascending){
            topicSortedList = new SortedList<>(Topic.class, new SortedList.Callback<Topic>() {
                @Override
                public int compare(Topic o1, Topic o2) {
                   if(ascending){
                       return o1.getUpVotedCount().compareTo(o2.getUpVotedCount());
                   }else{
                       return o2.getUpVotedCount().compareTo(o1.getUpVotedCount());
                   }
                }

                @Override
                public void onChanged(int position, int count) {
                    notifyItemRangeChanged(position , count);
                }

                @Override
                public boolean areContentsTheSame(Topic oldItem, Topic newItem) {
                    return oldItem.getUpVotedCount().equals(newItem.getUpVotedCount());


                }

                @Override
                public boolean areItemsTheSame(Topic item1, Topic item2) {
                    return false;
                }

                @Override
                public void onInserted(int position, int count) {
                    notifyItemRangeInserted(position,count);
                }

                @Override
                public void onRemoved(int position, int count) {
                    notifyItemRangeRemoved(position, count);
                }

                @Override
                public void onMoved(int fromPosition, int toPosition) {
                        notifyItemMoved(fromPosition , toPosition);
                }
            });
        }

        public void addAll(List<Topic> topicList){
            topicSortedList.beginBatchedUpdates();
            for(int i = 0 ; i < topicList.size() ; i++){
                topicSortedList.add(topicList.get(i));
            }
            topicSortedList.endBatchedUpdates();
        }

        public Topic get(int position){
            return topicSortedList.get(position);
        }

        public void clear(){
            topicSortedList.beginBatchedUpdates();
            while(topicSortedList.size() > 0){
                topicSortedList.removeItemAt(topicSortedList.size() - 1);
            }
            topicSortedList.endBatchedUpdates();
        }

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
            Topic topic = topicSortedList.get(position);
           if(topic.isAddTopic()){
                holder.cvAddPost.setVisibility(View.VISIBLE);
                holder.cvPost.setVisibility(View.INVISIBLE);
                holder.clAddPost.setOnClickListener(v -> {
                    navigateFragment(new CreatePostFragment() , true , new CreatePostFragment().getClass().getName());
                });
            }

            if(topic.getId() != null ) {
                holder.cvAddPost.setVisibility(View.INVISIBLE);
                holder.cvPost.setVisibility(View.VISIBLE);
                holder.tvTitle.setText(topic.getTitle());
                holder.tvDescription.setText(topic.getDescription());
                holder.tvDate.setText(topic.getCreatedDate());
                holder.tvVote.setText(Integer.toString(topic.getUpVotedCount()));
            }

            holder.btnUpVote.setOnClickListener(v -> {
                int vote = topic.getUpVotedCount();
                vote++;
                holder.tvVote.setText(Integer.toString(vote));
                topic.setUpVotedCount(vote);
                topicList.set(position , topic);
                Gson gson = new Gson();
                JsonArray newTopicList = gson.toJsonTree(topicList).getAsJsonArray();
                Util.bundle.put("post_list",newTopicList.toString());


        });

            holder.btnDownVote.setOnClickListener(v ->{
                int vote = topic.getUpVotedCount();
                if(vote > 0){
                    vote--;
                }

                holder.tvVote.setText(Integer.toString(vote));
                topic.setUpVotedCount(vote);
                topicList.set(position , topic);
                Gson gson = new Gson();
                JsonArray newTopicList = gson.toJsonTree(topicList).getAsJsonArray();
                Util.bundle.put("post_list",newTopicList.toString());
            });


        }



        public int getLastPosition(){
            return topicList.size() - 1;
        }

        @Override
        public int getItemCount() {
            if(topicSortedList != null){
                return topicSortedList.size();
            }else{
              return topicList.size();
            }
        }
    }



}
