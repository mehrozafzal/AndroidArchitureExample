package mehroz.sis.com.androiddevelopmenttest.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import mehroz.sis.com.androiddevelopmenttest.rest.ApiClient;
import mehroz.sis.com.androiddevelopmenttest.rest.ApiInterface;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserFollower;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserResponse;
import mehroz.sis.com.androiddevelopmenttest.shared.Constants;
import mehroz.sis.com.androiddevelopmenttest.storage.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestResponseHandler {

    private static RestResponseHandler restResponseHandler;
    public ResponseCallBack responseCallBack;
    private static ProgressDialog dialog;

    public static RestResponseHandler getInstance() {
        if (restResponseHandler == null) {
            synchronized (RestResponseHandler.class) {
                restResponseHandler = new RestResponseHandler();
            }
        }

        return restResponseHandler;
    }

    public void setAlertDialogNotifier(ResponseCallBack responseCallBack) {
        this.responseCallBack = responseCallBack;
    }

    private void createProgressDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Hold on...");
        dialog.show();
    }

    public void getSingleUserFromApi(final Context context, final String userName) {
        createProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.getSingleUser(userName);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (dialog != null) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
                UserResponse userResponse = response.body();
                if (userResponse != null) {
                    Gson gson = new Gson();
                    Preferences.getInstance(context).setStringPref(Constants.KeyConstants.USER_KEY,gson.toJson(userResponse));
                    Constants.ResponseObjects.userResponse = userResponse;
                    responseCallBack.onResponseCall(userResponse);

                } else
                    responseCallBack.onErrorResponseCall();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                if (dialog != null) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
                responseCallBack.onErrorResponseCall();
            }
        });

    }

    public void getUserFollowerListFromApi(final Context context, final String userName) {
        createProgressDialog(context);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<UserFollower>> call = apiService.getUserFollowerList(userName);
        call.enqueue(new Callback<List<UserFollower>>() {
            @Override
            public void onResponse(Call<List<UserFollower>> call, Response<List<UserFollower>> response) {

                if (dialog != null) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
                List<UserFollower> followersList = response.body();
                if (followersList != null) {
                    Gson gson = new Gson();
                    Preferences.getInstance(context).setStringPref(Constants.KeyConstants.USER_FOLLOWERS_KEY,gson.toJson(followersList));
                    Constants.ResponseObjects.userFollowers = followersList;
                    responseCallBack.onResponseCall(followersList);
                } else
                    responseCallBack.onErrorResponseCall();
            }

            @Override
            public void onFailure(Call<List<UserFollower>> call, Throwable t) {
                if (dialog != null) {
                    if (dialog.isShowing())
                        dialog.dismiss();
                }
                responseCallBack.onErrorResponseCall();
            }
        });

    }


    public interface ResponseCallBack {
        void onResponseCall(Object object);

        void onErrorResponseCall();

    }
}
