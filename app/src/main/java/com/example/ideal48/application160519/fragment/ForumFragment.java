package com.example.ideal48.application160519.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.activity.AnimeDetailsActivity;
import com.example.ideal48.application160519.model.ForumResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    List<ForumResponse.Topic> topicList;
    View loadingIndicator;
    TextView emptyFroumView;

    public ForumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.list_layout, container, false);

        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        emptyFroumView = view.findViewById(R.id.list_empty_view);
        recyclerView = view.findViewById(R.id.list_recycler_view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final Context context = getActivity();

        Call<ForumResponse> call = AnimeDetailsActivity.service.getForumResponse(AnimeDetailsActivity.malId);
        call.enqueue(new Callback<ForumResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForumResponse> call, @NonNull Response<ForumResponse> response) {
                if (response.body() != null && response.body().getmTopicList().size() != 0) {

                    topicList = response.body().getmTopicList();

                    TopicListAdapter adapter = new TopicListAdapter(context);

                    if (context != null) {
                        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    loadingIndicator.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);

                } else {
                    loadingIndicator.setVisibility(View.GONE);
                    emptyFroumView.setText(R.string.empty_forum_msg);
                    Toast.makeText(getActivity(), "Data Not Found !", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<ForumResponse> call, @NonNull Throwable t) {
                loadingIndicator.setVisibility(View.GONE);
                emptyFroumView.setText(R.string.empty_forum_msg);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class TopicListAdapter extends RecyclerView.Adapter<TopicListAdapter.TopicViewHolder> {

        private LayoutInflater mInflater;

        TopicListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View mItemView = mInflater.inflate(R.layout.forum_list_item, parent, false);
            return new TopicViewHolder(mItemView);
        }

        @Override
        public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {

            ForumResponse.Topic currentTopic = topicList.get(position);

            holder.topicTitle.setText(currentTopic.getmTitle());
            holder.topicAuthor.setText(currentTopic.getmAuthorName());
            holder.topicDate.setText(currentTopic.getmDatePosted());
            holder.repliesCount.setText(String.valueOf(currentTopic.getmRepliesCount()));
            holder.author.setText("by " + currentTopic.getmLastPost().getAuthorName());
            holder.date.setText(currentTopic.getmLastPost().getDatePosted());

        }

        @Override
        public int getItemCount() {
            return topicList.size();
        }

        class TopicViewHolder extends RecyclerView.ViewHolder {

            TextView topicTitle;
            TextView topicAuthor;
            TextView topicDate;
            TextView repliesCount;
            TextView author;
            TextView date;

            TopicViewHolder(View itemView) {
                super(itemView);
                topicTitle = itemView.findViewById(R.id.topic_title);
                topicAuthor = itemView.findViewById(R.id.topic_author);
                topicDate = itemView.findViewById(R.id.topic_date);
                repliesCount = itemView.findViewById(R.id.replies_count);
                author = itemView.findViewById(R.id.author_tv);
                date = itemView.findViewById(R.id.date_tv);
            }
        }
    }
}
