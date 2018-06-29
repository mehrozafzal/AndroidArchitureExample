package mehroz.sis.com.androiddevelopmenttest.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mehroz.sis.com.androiddevelopmenttest.R;
import mehroz.sis.com.androiddevelopmenttest.fragments.SearchUserFragment;
import mehroz.sis.com.androiddevelopmenttest.fragments.UserDetailFragment;
import mehroz.sis.com.androiddevelopmenttest.shared.Constants;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainActivity_fragmentContainer)
    FrameLayout mainActivityFragmentContainer;
    @BindView(R.id.activity_toolbar)
    View activityToolbar;
    @SuppressLint("StaticFieldLeak")
    private static TextView toolBarTitle;
    Unbinder unbinder;
    private static Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        bindViews();
    }


    private void bindViews() {
        try {
            askPermission();
            toolBarTitle = activityToolbar.findViewById(R.id.toolbar_title);
            selectFragment(Constants.FragmentConstants.SEARCH_FRAGMENT_TAG, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void setToolBarTitle(String title) {
        toolBarTitle.setText(title);
    }


    public void selectFragment(String type, Bundle bundle) {
        Fragment fragment;
        String fragmentTag = "";
        switch (type) {
            case Constants.FragmentConstants.SEARCH_FRAGMENT_TAG:
                fragment = new SearchUserFragment();
                fragmentTag = Constants.FragmentConstants.SEARCH_FRAGMENT_TAG;
                break;
            case Constants.FragmentConstants.DETAIL_FRAGENT_TAG:
                fragment = new UserDetailFragment();
                fragmentTag = Constants.FragmentConstants.DETAIL_FRAGENT_TAG;
                break;
            default:
                return;
        }

        if (bundle != null)
            fragment.setArguments(bundle);


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        }

        Fragment fragmentStack = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if (fragmentStack == null) {
            fragmentTransaction.replace(R.id.mainActivity_fragmentContainer, fragment, fragmentTag);
            fragmentTransaction.addToBackStack(fragmentTag);
        }

        fragmentTransaction.commit();
        currentFragment = fragment;
    }


    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE},
                1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    askPermission();
                }
                return;
            }
        }
    }

}
