package mehroz.sis.com.androiddevelopmenttest.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mehroz.sis.com.androiddevelopmenttest.R;
import mehroz.sis.com.androiddevelopmenttest.activities.MainActivity;
import mehroz.sis.com.androiddevelopmenttest.adapter.FollowersListAdapter;
import mehroz.sis.com.androiddevelopmenttest.interfaces.BasicFuntions;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserFollower;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserResponse;
import mehroz.sis.com.androiddevelopmenttest.shared.Constants;
import mehroz.sis.com.androiddevelopmenttest.storage.Preferences;
import mehroz.sis.com.androiddevelopmenttest.utils.CustomDialog;
import mehroz.sis.com.androiddevelopmenttest.utils.ImageHelper;
import mehroz.sis.com.androiddevelopmenttest.utils.RestResponseHandler;

public class UserDetailFragment extends Fragment implements BasicFuntions, RestResponseHandler.ResponseCallBack, CustomDialog.DialogNotifier.AlertDialogNotifier {


    @BindView(R.id.userDetail_avatar)
    ImageView userDetailAvatar;
    @BindView(R.id.userDetail_name)
    TextView userDetailName;
    @BindView(R.id.userDetail_email)
    TextView userDetailEmail;
    @BindView(R.id.userDetail_recyclerView)
    RecyclerView userDetailRecyclerView;

    ImageHelper imageHelper;
    RestResponseHandler restResponseHandler;
    CustomDialog customDialog;
    Unbinder unbinder;
    Bundle bundle;
    UserResponse userResponse;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        setRetainInstance(true);
        instantiateTheLayoutViews();
        return view;
    }

    @Override
    public void instantiateTheLayoutViews() {
        try {
            ((MainActivity) getActivity()).setToolBarTitle(Constants.FragmentConstants.DETAIL_FRAGENT_TITLE);
            if (getArguments() != null) {
                bundle = getArguments();
                assert bundle != null;
                userResponse = bundle.getParcelable(Constants.KeyConstants.USER_KEY);
                imageHelper = ImageHelper.getInstance();
                restResponseHandler = RestResponseHandler.getInstance();
                customDialog = CustomDialog.getInstance();
                settingUpTheView();
            } else
                parseUserFollowerObjectFromString();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void settingUpTheView() {
        try {
            if (userResponse != null) {
                imageHelper.setImageInGlide(getContext(), userResponse.getAvatarUrl(), userDetailAvatar);
                if (!userResponse.getName().equals(""))
                    userDetailName.setText(userResponse.getName());
                if (!userResponse.getEmail().equals(""))
                    userDetailEmail.setText(userResponse.getEmail());
                if (!userResponse.getFollowersUrl().equals(""))
                    restResponseHandler.getUserFollowerListFromApi(getContext(), userResponse.getName());

            }
            settingUpTheViewListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void settingUpTheViewListeners() {
        customDialog.setAlertDialogNotifier(this);
        restResponseHandler.setAlertDialogNotifier(this);
    }


    @Override
    public void onResponseCall(Object object) {
        if (object != null) {
            FollowersListAdapter followersListAdapter = new FollowersListAdapter(getContext(), (List<UserFollower>) object);
            userDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            userDetailRecyclerView.setAdapter(followersListAdapter);
        }
    }

    @Override
    public void onErrorResponseCall() {

    }

    @Override
    public void dialogCancelNotifier() {

    }

    @Override
    public void dialogAcceptNotifier() {

    }


    private void parseUserFollowerObjectFromString() {
        String userObject = Preferences.getInstance(getContext()).getStringPref(Constants.KeyConstants.USER_KEY);
        String followersObject = Preferences.getInstance(getContext()).getStringPref(Constants.KeyConstants.USER_FOLLOWERS_KEY);
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<UserFollower>>() {
        }.getType();
        List<UserFollower> userFollowerList = new Gson().fromJson(followersObject, listType);
        UserResponse userResponse = gson.fromJson(userObject, UserResponse.class);

        if (userResponse != null) {
            ImageHelper.setImageInGlide(getContext(), userResponse.getAvatarUrl(), userDetailAvatar);
            userDetailName.setText(userResponse.getName());
            userDetailEmail.setText(userResponse.getEmail());
        }

        if (userFollowerList != null) {
            FollowersListAdapter followersListAdapter = new FollowersListAdapter(getContext(), userFollowerList);
            userDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            userDetailRecyclerView.setAdapter(followersListAdapter);
        }
    }
}
