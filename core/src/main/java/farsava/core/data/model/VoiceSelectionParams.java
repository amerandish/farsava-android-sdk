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

package farsava.core.data.model;

import com.google.gson.annotations.SerializedName;

import farsava.core.util.Enums;

/**
 * The desired voice of the synthesized audio.
 **/
public class VoiceSelectionParams {

    @SerializedName("voiceId")
    private String voiceId = "b6e9c993-729e-4e0f-955b-f229cf1f77ee";
    @SerializedName("languageCode")
    private Enums.LanguageCode languageCode = Enums.LanguageCode.fa;
    @SerializedName("name")
    private String name = "default";
    @SerializedName("gender")
    private Enums.VoiceGender gender = Enums.VoiceGender.female;

    public VoiceSelectionParams() {
    }

    /**
     * id of the desired voice to synthesize.
     **/
    public String getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId;
    }

    /**
     *
     **/
    public String getLanguageCode() {
        return languageCode.name();
    }

    public void setLanguageCode(Enums.LanguageCode languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * Name of the desired voice.
     **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     **/
    public String getGender() {
        return gender.name();
    }

    public void setGender(Enums.VoiceGender gender) {
        this.gender = gender;
    }
}