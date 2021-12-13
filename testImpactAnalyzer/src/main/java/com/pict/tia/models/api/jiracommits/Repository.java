
package com.pict.tia.models.api.jiracommits;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Repository {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("avatarDescription")
    @Expose
    private String avatarDescription;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("commits")
    @Expose
    private List<Commit> commits = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarDescription() {
        return avatarDescription;
    }

    public void setAvatarDescription(String avatarDescription) {
        this.avatarDescription = avatarDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

}
