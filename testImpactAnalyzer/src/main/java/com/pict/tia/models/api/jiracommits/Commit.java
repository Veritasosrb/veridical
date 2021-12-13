
package com.pict.tia.models.api.jiracommits;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Commit {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("displayId")
    @Expose
    private String displayId;
    @SerializedName("authorTimestamp")
    @Expose
    private String authorTimestamp;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("fileCount")
    @Expose
    private Integer fileCount;
    @SerializedName("merge")
    @Expose
    private Boolean merge;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("files")
    @Expose
    private List<File> files = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getAuthorTimestamp() {
        return authorTimestamp;
    }

    public void setAuthorTimestamp(String authorTimestamp) {
        this.authorTimestamp = authorTimestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public Boolean getMerge() {
        return merge;
    }

    public void setMerge(Boolean merge) {
        this.merge = merge;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

}
