/**
 * Farsava API
 * Farsava API. Speech Recognition and Text to Speech by applying powerful deep neural network models.
 * <p>
 * OpenAPI spec version: 1.0.6
 * Contact: amir@amerandish.com
 * <p>
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

package com.amerandish.farsava.core.Model;

import com.amerandish.farsava.core.Util.Enums;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.UUID;

public class ASRResponseBody {
    @SerializedName("transcriptionId")
    private UUID transcriptionId = null;
    @SerializedName("duration")
    private Double duration = null;
    @SerializedName("inferenceTime")
    private Double inferenceTime = null;
    @SerializedName("status")
    private Enums.ASRStatus status = null;
    @SerializedName("results")
    private List<SpeechRecognitionResult> results = null;

    /**
     * A UUID string specifying a unique pair of audio and recognitionResult. It can be used to retrieve this recognitionResult using transcription endpoint. asrLongRunning recognitionResult will only be available using transcription endpoint and this transcriptionId.
     **/
    public UUID getTranscriptionId() {
        return transcriptionId;
    }

    public void setTranscriptionId(UUID transcriptionId) {
        this.transcriptionId = transcriptionId;
    }

    /**
     * File duration in seconds.
     **/
    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    /**
     * Total inference time in seconds.
     **/
    public Double getInferenceTime() {
        return inferenceTime;
    }

    public void setInferenceTime(Double inferenceTime) {
        this.inferenceTime = inferenceTime;
    }

    /**
     *
     **/
    public String getStatus() {
        return status.name();
    }

    public void setStatus(Enums.ASRStatus status) {
        this.status = status;
    }

    /**
     * Sequential list of transcription results corresponding to sequential portions of audio. May contain one or more recognition hypotheses (up to the maximum specified in maxAlternatives). These alternatives are ordered in terms of accuracy, with the top (first) alternative being the most probable, as ranked by the recognizer.
     **/
    public List<SpeechRecognitionResult> getResults() {
        return results;
    }

    public void setResults(List<SpeechRecognitionResult> results) {
        this.results = results;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ASRResponseBody aSRResponseBody = (ASRResponseBody) o;
        return (this.transcriptionId == null ? aSRResponseBody.transcriptionId == null : this.transcriptionId.equals(aSRResponseBody.transcriptionId)) &&
                (this.duration == null ? aSRResponseBody.duration == null : this.duration.equals(aSRResponseBody.duration)) &&
                (this.inferenceTime == null ? aSRResponseBody.inferenceTime == null : this.inferenceTime.equals(aSRResponseBody.inferenceTime)) &&
                (this.status == null ? aSRResponseBody.status == null : this.status.equals(aSRResponseBody.status)) &&
                (this.results == null ? aSRResponseBody.results == null : this.results.equals(aSRResponseBody.results));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.transcriptionId == null ? 0 : this.transcriptionId.hashCode());
        result = 31 * result + (this.duration == null ? 0 : this.duration.hashCode());
        result = 31 * result + (this.inferenceTime == null ? 0 : this.inferenceTime.hashCode());
        result = 31 * result + (this.status == null ? 0 : this.status.hashCode());
        result = 31 * result + (this.results == null ? 0 : this.results.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ASRResponseBody {\n");

        sb.append("  transcriptionId: ").append(transcriptionId).append("\n");
        sb.append("  duration: ").append(duration).append("\n");
        sb.append("  inferenceTime: ").append(inferenceTime).append("\n");
        sb.append("  status: ").append(status).append("\n");
        sb.append("  results: ").append(results).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
