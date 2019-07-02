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

public class ASRRequestBodyURI {

    @SerializedName("config")
    private RecognitionConfig config = null;
    @SerializedName("audio")
    private RecognitionAudioURI audio = null;

    /**
     *
     **/
    public RecognitionConfig getConfig() {
        return config;
    }

    public void setConfig(RecognitionConfig config) {
        this.config = config;
    }

    /**
     *
     **/
    public RecognitionAudioURI getAudio() {
        return audio;
    }

    public void setAudio(RecognitionAudioURI audio) {
        this.audio = audio;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ASRRequestBodyURI aSRRequestBodyURI = (ASRRequestBodyURI) o;
        return (this.config == null ? aSRRequestBodyURI.config == null : this.config.equals(aSRRequestBodyURI.config)) &&
                (this.audio == null ? aSRRequestBodyURI.audio == null : this.audio.equals(aSRRequestBodyURI.audio));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.config == null ? 0 : this.config.hashCode());
        result = 31 * result + (this.audio == null ? 0 : this.audio.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ASRRequestBodyURI {\n");

        sb.append("  config: ").append(config).append("\n");
        sb.append("  audio: ").append(audio).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
