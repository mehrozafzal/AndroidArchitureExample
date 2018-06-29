package mehroz.sis.com.androiddevelopmenttest.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import mehroz.sis.com.androiddevelopmenttest.R;
import mehroz.sis.com.androiddevelopmenttest.activities.MainActivity;
import mehroz.sis.com.androiddevelopmenttest.interfaces.BasicFuntions;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserResponse;
import mehroz.sis.com.androiddevelopmenttest.shared.Constants;
import mehroz.sis.com.androiddevelopmenttest.storage.Preferences;
import mehroz.sis.com.androiddevelopmenttest.utils.CustomDialog;
import mehroz.sis.com.androiddevelopmenttest.utils.RestResponseHandler;

public class SearchUserFragment extends Fragment implements BasicFuntions, TextView.OnEditorActionListener, RestResponseHandler.ResponseCallBack, CustomDialog.DialogNotifier.AlertDialogNotifier {

    Unbinder unbinder;
    RestResponseHandler restResponseHandler;
    CustomDialog customDialog;

    @BindView(R.id.searchUserFragment_usernameInput)
    EditText searchUserFragmentUsernameInput;
    @BindView(R.id.searchUserFragment_cacheButton)
    Button searchUserFragmentCacheButton;
    private UserResponse userResponse;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        instantiateTheLayoutViews();
        setRetainInstance(true);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        parseUserObjectFromString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void instantiateTheLayoutViews() {
        restResponseHandler = RestResponseHandler.getInstance();
        customDialog = CustomDialog.getInstance();
        settingUpTheView();
    }

    @Override
    public void settingUpTheView() {
        ((MainActivity) getActivity()).setToolBarTitle(Constants.FragmentConstants.SEARCH_FRAGMENT_TITLE);
        settingUpTheViewListeners();
    }

    @Override
    public void settingUpTheViewListeners() {
        restResponseHandler.setAlertDialogNotifier(this);
        customDialog.setAlertDialogNotifier(this);
        searchUserFragmentUsernameInput.setOnEditorActionListener(this);
    }


    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (!searchUserFragmentUsernameInput.getText().toString().trim().equalsIgnoreCase("")) {
                restResponseHandler.getSingleUserFromApi(getContext(), searchUserFragmentUsernameInput.getText().toString().trim());
                return true;

            } else
                customDialog.createAlertDialog(getContext(), "Please enter a username");
        }
        return false;
    }

    @Override
    public void onResponseCall(Object object) {
        if (object instanceof UserResponse) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.KeyConstants.USER_KEY, (UserResponse) object);
            ((MainActivity) getActivity()).selectFragment(Constants.FragmentConstants.DETAIL_FRAGENT_TAG, bundle);
        }
    }

    @Override
    public void onErrorResponseCall() {
        customDialog.createAlertDialog(getContext(), "No such user found");
    }

    @Override
    public void dialogCancelNotifier() {

    }

    @Override
    public void dialogAcceptNotifier() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    private void parseUserObjectFromString() {
        String object = Preferences.getInstance(getContext()).getStringPref(Constants.KeyConstants.USER_KEY);
        Gson gson = new Gson();
        userResponse = gson.fromJson(object, UserResponse.class);
        if (userResponse != null) {
            if (searchUserFragmentUsernameInput != null)
                searchUserFragmentUsernameInput.setText(userResponse.getName());
        }

    }


    @OnClick(R.id.searchUserFragment_cacheButton)
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.searchUserFragment_cacheButton:
                ((MainActivity) getActivity()).selectFragment(Constants.FragmentConstants.DETAIL_FRAGENT_TAG, null);
        }
    }
}
