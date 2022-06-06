
package com.pict.tia.models.api.jiracommits;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Instance {

    @SerializedName("primary")
    @Expose
    private Boolean primary;
    @SerializedName("baseUrl")
    @Expose
    private String baseUrl;
    @SerializedName("applicationLinkId")
    @Expose
    private String applicationLinkId;
    @SerializedName("singleInstance")
    @Expose
    private Boolean singleInstance;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("typeName")
    @Expose
    private String typeName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApplicationLinkId() {
        return applicationLinkId;
    }

    public void setApplicationLinkId(String applicationLinkId) {
        this.applicationLinkId = applicationLinkId;
    }

    public Boolean getSingleInstance() {
        return singleInstance;
    }

    public void setSingleInstance(Boolean singleInstance) {
        this.singleInstance = singleInstance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
