package mehroz.sis.com.androiddevelopmenttest.ApplicationModule;

import android.app.Application;

import mehroz.sis.com.androiddevelopmenttest.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class GlobalModule extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTypeface();
    }

    private void initTypeface() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Poppins-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
