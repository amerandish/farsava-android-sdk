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

import com.google.gson.annotations.SerializedName;

public class WordInfo {

    @SerializedName("startTime")
    private Double startTime = null;
    @SerializedName("endTime")
    private Double endTime = null;
    @SerializedName("word")
    private String word = null;
    @SerializedName("confidence")
    private Double confidence = null;

    /**
     * Time offset relative to the beginning of the audio, and corresponding to the start of the spoken word. This is an experimental feature and the accuracy of the time offset can vary. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating confidence was not set.
     **/
    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    /**
     * Time offset relative to the beginning of the audio, and corresponding to the end of the spoken word. This is an experimental feature and the accuracy of the time offset can vary. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating confidence was not set.
     **/
    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }

    /**
     * The word corresponding to this set of information.
     **/
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    /**
     * The confidence of ASR engine for generated output. The confidence estimate between 0.0 and 1.0. A higher number indicates an estimated greater likelihood that the recognized words are correct. It is the total confidence of recognition in transcript level and each word confidence in word info object. This field is not guaranteed to be accurate and users should not rely on it to be always provided. The default of 0.0 is a sentinel value indicating confidence was not set.
     * minimum: 0
     * maximum: 1
     **/
    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WordInfo wordInfo = (WordInfo) o;
        return (this.startTime == null ? wordInfo.startTime == null : this.startTime.equals(wordInfo.startTime)) &&
                (this.endTime == null ? wordInfo.endTime == null : this.endTime.equals(wordInfo.endTime)) &&
                (this.word == null ? wordInfo.word == null : this.word.equals(wordInfo.word)) &&
                (this.confidence == null ? wordInfo.confidence == null : this.confidence.equals(wordInfo.confidence));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.startTime == null ? 0 : this.startTime.hashCode());
        result = 31 * result + (this.endTime == null ? 0 : this.endTime.hashCode());
        result = 31 * result + (this.word == null ? 0 : this.word.hashCode());
        result = 31 * result + (this.confidence == null ? 0 : this.confidence.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WordInfo {\n");

        sb.append("  startTime: ").append(startTime).append("\n");
        sb.append("  endTime: ").append(endTime).append("\n");
        sb.append("  word: ").append(word).append("\n");
        sb.append("  confidence: ").append(confidence).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
