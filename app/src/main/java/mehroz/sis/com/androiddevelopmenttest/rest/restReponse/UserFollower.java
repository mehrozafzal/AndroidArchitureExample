
package mehroz.sis.com.androiddevelopmenttest.rest.restReponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserFollower {


    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("login")
    @Expose
    private String login;


    public String getName() {
        return login;
    }

    public void setName(String login) {
        this.login = login;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }



}
