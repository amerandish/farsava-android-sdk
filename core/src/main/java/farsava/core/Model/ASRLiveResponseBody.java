
package farsava.core.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ASRLiveResponseBody {

    @SerializedName("chunks")
    @Expose
    private Integer chunks;
    @SerializedName("confidences")
    @Expose
    private List<Double> confidences = null;
    @SerializedName("duration")
    @Expose
    private Double duration;
    @SerializedName("endpoints")
    @Expose
    private List<Integer> endpoints = null;
    @SerializedName("final")
    @Expose
    private Boolean _final;
    @SerializedName("lengths")
    @Expose
    private List<Double> lengths = null;
    @SerializedName("partial")
    @Expose
    private Integer partial;
    @SerializedName("processed")
    @Expose
    private Integer processed;
    @SerializedName("recieved")
    @Expose
    private Integer recieved;
    @SerializedName("starts")
    @Expose
    private List<Double> starts = null;
    @SerializedName("uwords")
    @Expose
    private List<String> uwords = null;
    @SerializedName("words")
    @Expose
    private List<String> words = null;

    public Integer getChunks() {
        return chunks;
    }

    public void setChunks(Integer chunks) {
        this.chunks = chunks;
    }

    public List<Double> getConfidences() {
        return confidences;
    }

    public void setConfidences(List<Double> confidences) {
        this.confidences = confidences;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public List<Integer> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Integer> endpoints) {
        this.endpoints = endpoints;
    }

    public Boolean getFinal() {
        return _final;
    }

    public void setFinal(Boolean _final) {
        this._final = _final;
    }

    public List<Double> getLengths() {
        return lengths;
    }

    public void setLengths(List<Double> lengths) {
        this.lengths = lengths;
    }

    public Integer getPartial() {
        return partial;
    }

    public void setPartial(Integer partial) {
        this.partial = partial;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public Integer getRecieved() {
        return recieved;
    }

    public void setRecieved(Integer recieved) {
        this.recieved = recieved;
    }

    public List<Double> getStarts() {
        return starts;
    }

    public void setStarts(List<Double> starts) {
        this.starts = starts;
    }

    public List<String> getUwords() {
        return uwords;
    }

    public void setUwords(List<String> uwords) {
        this.uwords = uwords;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

}
