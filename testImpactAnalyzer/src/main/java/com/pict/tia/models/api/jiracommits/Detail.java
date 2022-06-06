
package com.pict.tia.models.api.jiracommits;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Detail {

    @SerializedName("repositories")
    @Expose
    private List<Repository> repositories = null;
    @SerializedName("_instance")
    @Expose
    private Instance instance;

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

}
