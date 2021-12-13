
package com.pict.tia.models.analyzer;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class ReferencesMatched {

    @SerializedName("matchInFileName")
    @Expose
    private Boolean matchInFileName;
    @SerializedName("matchInPathName")
    @Expose
    private Boolean matchInPathName;
    @SerializedName("referenceAbsoluteFilePath")
    @Expose
    private String referenceAbsoluteFilePath;

    public Boolean getMatchInFileName() {
        return matchInFileName;
    }

    public void setMatchInFileName(Boolean matchInFileName) {
        this.matchInFileName = matchInFileName;
    }

    public Boolean getMatchInPathName() {
        return matchInPathName;
    }

    public void setMatchInPathName(Boolean matchInPathName) {
        this.matchInPathName = matchInPathName;
    }

    public String getReferenceAbsoluteFilePath() {
        return referenceAbsoluteFilePath;
    }

    public void setReferenceAbsoluteFilePath(String referenceAbsoluteFilePath) {
        this.referenceAbsoluteFilePath = referenceAbsoluteFilePath;
    }

}
