
package com.pict.tia.models.api.jiracommits;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class JiraStashPOJO {

    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("detail")
    @Expose
    private List<Detail> detail = null;

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

}
