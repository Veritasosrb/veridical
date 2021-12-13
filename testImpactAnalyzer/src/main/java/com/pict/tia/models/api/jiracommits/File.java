
package com.pict.tia.models.api.jiracommits;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class File {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("changeType")
    @Expose
    private String changeType;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

}
