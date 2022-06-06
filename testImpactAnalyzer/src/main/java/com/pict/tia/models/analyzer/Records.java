
package com.pict.tia.models.analyzer;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Records {

    @SerializedName("commitDetails")
    @Expose
    private CommitDetails commitDetails;
    @SerializedName("components")
    @Expose
    private List<Component> components = null;
    @SerializedName("labels")
    @Expose
    private List<Label> labels = null;

    public CommitDetails getCommitDetails() {
        return commitDetails;
    }

    public void setCommitDetails(CommitDetails commitDetails) {
        this.commitDetails = commitDetails;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

}
