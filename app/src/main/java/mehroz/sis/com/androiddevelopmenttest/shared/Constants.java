package mehroz.sis.com.androiddevelopmenttest.shared;

import java.util.List;

import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserFollower;
import mehroz.sis.com.androiddevelopmenttest.rest.restReponse.UserResponse;

public final class Constants {

    public static final class FragmentConstants {
        public static final String SEARCH_FRAGMENT_TAG = "SEARCH_FRAGMENT";
        public static final String SEARCH_FRAGMENT_TITLE = "SEARCH USER";
        public static final String DETAIL_FRAGENT_TAG = "DETAIL_FRAGMENT";
        public static final String DETAIL_FRAGENT_TITLE = "USER DETAIL";
    }

    public static final class ResponseObjects{
        public static UserResponse userResponse;
        public static List<UserFollower> userFollowers;
    }

    public static final class KeyConstants{

        public static final String USER_KEY = "USER_OBJ";
        public static final String USER_FOLLOWERS_KEY = "USER_FOLLOWERS_OBJ";
        public static final String USER_NAME_KEY = "USER_NAME";
    }

}
