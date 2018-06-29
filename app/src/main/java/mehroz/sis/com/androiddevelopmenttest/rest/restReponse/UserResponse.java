
package mehroz.sis.com.androiddevelopmenttest.rest.restReponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse implements Parcelable {


    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("gravatar_id")
    @Expose
    private String gravatarId;

    @SerializedName("followers_url")
    @Expose
    private String followersUrl;

    @SerializedName("login")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("followers")
    @Expose
    private Integer followers;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }


    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }


    public String getName() {
        if (name == null)
            return "User Name";
        else
            return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        if (email == null)
            return "User Email";
        else
            return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }


    public UserResponse(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.avatarUrl = in.readString();
        this.gravatarId = in.readString();
        this.followers = in.readInt();
        this.followersUrl = in.readString();
        this.id = in.readInt();
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserResponse createFromParcel(Parcel in) {
            return new UserResponse(in);
        }

        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.avatarUrl);
        dest.writeString(this.gravatarId);
        dest.writeInt(this.followers);
        dest.writeString(this.followersUrl);
        dest.writeInt(this.id);
    }
}
