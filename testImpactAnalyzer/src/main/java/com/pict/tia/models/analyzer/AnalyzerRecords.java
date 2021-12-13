
package com.pict.tia.models.analyzer;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AnalyzerRecords {

    @SerializedName("Records")
    @Expose
    private List<Records> records = null;

    public List<Records> getRecords() {
        return records;
    }

    public void setRecords(List<Records> records) {
        this.records = records;
    }

}
