package com.example.ideal48.application160519.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ideal48.application160519.R;
import com.example.ideal48.application160519.UserDao;
import com.example.ideal48.application160519.UserRoomDatabase;
import com.example.ideal48.application160519.activity.UserActivity;
import com.example.ideal48.application160519.model.User;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by ideal48 on 18/5/19.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private List<User> mUserList;
    private LayoutInflater mInflater;
    private Context context;
    UserDao userDao;

    UserRoomDatabase userRoomDatabase;

    public UserListAdapter(Context context, List<User> userList) {
        mInflater = LayoutInflater.from(context);
        mUserList = userList;
        this.context = context;
        userRoomDatabase = UserRoomDatabase.getDatabase(context);
        userDao = userRoomDatabase.userDao();
    }

    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(final UserListAdapter.UserViewHolder holder, final int position) {
        final User mCurrentUser = mUserList.get(position);
        holder.fNameTextView.setText(mCurrentUser.getmFName());
        holder.lNameTextView.setText(mCurrentUser.getmLName());
        holder.userIdTextView.setText(mCurrentUser.getmUserId());
        holder.passwordTextView.setText(mCurrentUser.getmPassword());
        holder.dobTextView.setText(mCurrentUser.getmDOB());

        byte[] outImage = mCurrentUser.getmImageInByte();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        holder.userImageView.setImageBitmap(image);

        if (mCurrentUser.ismFav()) {
            holder.favImageView.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.favImageView.setImageResource(R.drawable.ic_favorite_border);
        }

        holder.favImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentUser.ismFav()) {
                    mCurrentUser.setmFav(false);
                    new AsyncTask<Void, Void, Void>(){
                        @Override
                        protected Void doInBackground(Void... voids) {
                            userDao.setFavUser(mCurrentUser.getmUserId(), false);
                            return null;
                        }
                    }.execute();
                    mUserList.remove(position);
                    notifyDataSetChanged();
                    holder.favImageView.setImageResource(R.drawable.ic_favorite_border);
                } else {
                    mCurrentUser.setmFav(true);
                    new AsyncTask<Void, Void, Void>(){
                        @Override
                        protected Void doInBackground(Void... voids) {
                            userDao.setFavUser(mCurrentUser.getmUserId(), true);
                            return null;
                        }
                    }.execute();
                    holder.favImageView.setImageResource(R.drawable.ic_favorite);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = mUserList.get(position);
                Intent i = new Intent(context , UserActivity.class);
                i.putExtra(context.getResources().getString(R.string.id), user.getId());
                i.putExtra(context.getResources().getString(R.string.user_id), user.getmUserId());
                i.putExtra(context.getResources().getString(R.string.password), user.getmPassword());
                i.putExtra(context.getResources().getString(R.string.first_name), user.getmFName());
                i.putExtra(context.getResources().getString(R.string.last_name), user.getmLName());
                i.putExtra(context.getString(R.string.dob), user.getmDOB());
                i.putExtra(context.getString(R.string.image_bytes), user.getmImageInByte());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public final TextView fNameTextView;
        public final TextView lNameTextView;
        public final TextView userIdTextView;
        public final TextView passwordTextView;
        public final TextView dobTextView;
        public final ImageView favImageView;
        public final ImageView userImageView;
        final UserListAdapter mAdapter;

        public UserViewHolder(View itemView, UserListAdapter adapter) {
            super(itemView);
            fNameTextView = itemView.findViewById(R.id.f_name_tv);
            lNameTextView = itemView.findViewById(R.id.l_name_tv);
            userIdTextView = itemView.findViewById(R.id.user_id_tv);
            passwordTextView = itemView.findViewById(R.id.password_tv);
            dobTextView = itemView.findViewById(R.id.item_dob_tv);
            userImageView = itemView.findViewById(R.id.user_iv);
            favImageView = itemView.findViewById(R.id.fav_iv);
            mAdapter = adapter;
        }
    }
}
