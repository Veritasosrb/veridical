
package com.pict.tia.models.analyzer;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Component {

    @SerializedName("matchInFileName")
    @Expose
    private Boolean matchInFileName = false;
    @SerializedName("matchInMethodName")
    @Expose
    private Boolean matchInMethodName = false;
    @SerializedName("matchInNewChanges")
    @Expose
    private Boolean matchInNewChanges = false;
    @SerializedName("matchInOldChanges")
    @Expose
    private Boolean matchInOldChanges = false;
    @SerializedName("matchInPathName")
    @Expose
    private Boolean matchInPathName = false;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("referencesMatched")
    @Expose
    private List<ReferencesMatched> referencesMatched = null;
    @SerializedName("tokens")
    @Expose
    private List<Token> tokens = null;

    public Boolean getMatchInFileName() {
        return matchInFileName;
    }

    public void setMatchInFileName(Boolean matchInFileName) {
        this.matchInFileName = matchInFileName;
    }

    public Boolean getMatchInMethodName() {
        return matchInMethodName;
    }

    public void setMatchInMethodName(Boolean matchInMethodName) {
        this.matchInMethodName = matchInMethodName;
    }

    public Boolean getMatchInNewChanges() {
        return matchInNewChanges;
    }

    public void setMatchInNewChanges(Boolean matchInNewChanges) {
        this.matchInNewChanges = matchInNewChanges;
    }

    public Boolean getMatchInOldChanges() {
        return matchInOldChanges;
    }

    public void setMatchInOldChanges(Boolean matchInOldChanges) {
        this.matchInOldChanges = matchInOldChanges;
    }

    public Boolean getMatchInPathName() {
        return matchInPathName;
    }

    public void setMatchInPathName(Boolean matchInPathName) {
        this.matchInPathName = matchInPathName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReferencesMatched> getReferencesMatched() {
        return referencesMatched;
    }

    public void setReferencesMatched(List<ReferencesMatched> referencesMatched) {
        this.referencesMatched = referencesMatched;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
}
