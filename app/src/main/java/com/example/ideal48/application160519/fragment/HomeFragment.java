package com.example.ideal48.application160519.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ideal48.application160519.AppSession;
import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.adapter.UserListAdapter;
import com.example.ideal48.application160519.model.User;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    // Member Variables
    List<User> usersList;
    RecyclerView mRecyclerView;
    UserDao userDao;
    String userId;
    View loadingIndicator;
    TextView mEmptyListTV;
    View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate list_layout.xml onto screen
        view = inflater.inflate(R.layout.list_layout, container, false);

        // Find and initialize views from XML layout
        loadingIndicator = view.findViewById(R.id.list_loading_indicator);
        mEmptyListTV = view.findViewById(R.id.list_empty_view);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        // Initialize DAO to perform database queries.
        UserRoomDatabase userRoomDatabase = UserRoomDatabase.getDatabase(getActivity());
        userDao = userRoomDatabase.userDao();

        // Get userId of log_in user
        userId = new AppSession(getActivity()).getLoginId();

//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
////                User user = usersList.get(position);
////                Intent i = new Intent(getActivity(), UserActivity.class);
////                i.putExtra(getString(R.string.id), user.getId());
////                i.putExtra(getString(R.string.user_id), user.getmUserId());
////                i.putExtra(getString(R.string.password), user.getmPassword());
////                i.putExtra(getString(R.string.first_name), user.getmFName());
////                i.putExtra(getString(R.string.last_name), user.getmLName());
////                startActivity(i);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                // Get users list from database in usersList
                usersList = userDao.getAllUsers(userId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                // check whether usersList is empty or not
                if (usersList != null && usersList.size() != 0) {
                    UserListAdapter mAdapter = new UserListAdapter(getActivity(), usersList);
                    mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                    loadingIndicator.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    // Show message if usersList is Empty
                    loadingIndicator.setVisibility(View.GONE);
                    mEmptyListTV.setText("Users Not Found !");
                }
            }
        }.execute();
    }
}
