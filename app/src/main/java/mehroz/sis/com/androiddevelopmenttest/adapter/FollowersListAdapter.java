package mehroz.sis.com.androiddevelopmenttest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehroz.sis.com.androiddevelopmenttest.R;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserFollower;
import mehroz.sis.com.androiddevelopmenttest.utils.ImageHelper;


/**
 * Created by Mezzy on 12/4/2017.
 */

public class FollowersListAdapter extends RecyclerView.Adapter<FollowersListAdapter.MyViewHolder> {


    private Context context;
    private List<UserFollower> userFollowerList;


    public FollowersListAdapter(Context context, List<UserFollower> userFollowerList) {
        this.context = context;
        this.userFollowerList = userFollowerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.follower_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(userFollowerList.size()>0)
             holder.bindViews(userFollowerList.get(position));
    }

    @Override
    public int getItemCount() {
        return userFollowerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.followerListItem_ownerImage)
        de.hdodenhof.circleimageview.CircleImageView followerListItemOwnerImage;
        @BindView(R.id.followerListItem_followerName)
        TextView followerListItemFollowerName;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindViews(UserFollower userFollower) {
            ImageHelper.getInstance().setImageInGlide(context, userFollower.getAvatarUrl(), followerListItemOwnerImage);
            followerListItemFollowerName.setText(userFollower.getName());
        }
    }
}
