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

package farsava.core.Model;

import com.google.gson.annotations.SerializedName;

import farsava.core.Util.Enums;

/**
 * Provides information to the recognizer that specifies how to process the request.
 **/
public class RecognitionConfig {

    @SerializedName("audioEncoding")
    private Enums.AudioEncoding audioEncoding = null;
    @SerializedName("sampleRateHertz")
    private Integer sampleRateHertz = 16000;
    @SerializedName("languageCode")
    private Enums.LanguageCode languageCode = Enums.LanguageCode.fa;
    @SerializedName("maxAlternatives")
    private Integer maxAlternatives = 1;
    @SerializedName("profanityFilter")
    private Boolean profanityFilter = true;
    @SerializedName("asrModel")
    private String asrModel = "default";
    @SerializedName("languageModel")
    private String languageModel = "general";

    public RecognitionConfig(Enums.AudioEncoding audioEncoding, Integer sampleRateHertz) {
        this.audioEncoding = audioEncoding;
        this.sampleRateHertz = sampleRateHertz;
    }

    public RecognitionConfig(Enums.AudioEncoding audioEncoding, Integer sampleRateHertz, Enums.LanguageCode languageCode, Integer maxAlternatives, Boolean profanityFilter, String asrModel, String languageModel) {
        this.audioEncoding = audioEncoding;
        this.sampleRateHertz = sampleRateHertz;
        this.languageCode = languageCode;
        this.maxAlternatives = maxAlternatives;
        this.profanityFilter = profanityFilter;
        this.asrModel = asrModel;
        this.languageModel = languageModel;
    }

    /**
     *
     **/
    public String getAudioEncoding() {
        return audioEncoding.name();
    }

    public void setAudioEncoding(Enums.AudioEncoding audioEncoding) {
        this.audioEncoding = audioEncoding;
    }

    /**
     * Sample rate in Hertz of the audio data sent in all RecognitionAudio messages. Valid values are 8000-48000. 16000 is optimal. For best results, set the sampling rate of the audio source to 16000 Hz. If that is not possible, use the native sample rate of the audio source (instead of re-sampling). This field is required for all audio formats. In Text to Speech endpoint is the synthesis sample rate (in hertz) for audio and Optional. If this is different from the voice's natural sample rate, then the synthesizer will honor this request by converting to the desired sample rate (which might result in worse audio quality), unless the specified sample rate is not supported for the encoding chosen.
     **/
    public Integer getSampleRateHertz() {
        return sampleRateHertz;
    }

    public void setSampleRateHertz(Integer sampleRateHertz) {
        this.sampleRateHertz = sampleRateHertz;
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
     * Optional Maximum number of recognition hypotheses to be returned. Specifically, the maximum number of SpeechRecognitionAlternative messages within each SpeechRecognitionResult. The server may return fewer than maxAlternatives. Valid values are 1-5. A value of 0 or 1 will return a maximum of one. If omitted, will return a maximum of one.
     * minimum: 0
     * maximum: 5
     **/
    public Integer getMaxAlternatives() {
        return maxAlternatives;
    }

    public void setMaxAlternatives(Integer maxAlternatives) {
        this.maxAlternatives = maxAlternatives;
    }

    /**
     * Optional If set to true, the server will attempt to filter out profanities, replacing all but the initial character in each filtered word with asterisks, e.g. \"s***\". If set to false or omitted, profanities will not be filtered out.
     **/
    public Boolean getProfanityFilter() {
        return profanityFilter;
    }

    public void setProfanityFilter(Boolean profanityFilter) {
        this.profanityFilter = profanityFilter;
    }

    /**
     *
     **/
    public String getAsrModel() {
        return asrModel;
    }

    public void setAsrModel(String asrModel) {
        this.asrModel = asrModel;
    }

    /**
     * This is the language model id of a customized trained language model. You can train your own language models and then use them to recognize speech. Refer to [languagemodel/train](#languagemodel/train) for more info.    There are some pretrained language models which you can use.    Model | Description   ------------ | -------------   general | Best for audio content that is not one of the specific language models. This is the default language model and if you are not sure which one to use, simply use 'general'.   numbers | Best for audio content that contains only spoken numbers. For examble this language model can be used for speech enabled number input fileds.   yesno | Best for audio content that contains yes or no. For examble this language model can be used to receive confirmation from user.   country | Best for audio content that contains only spoken country. For examble this language model can be used for speech enabled input fileds.   city | Best for audio content that contains only spoken city. For examble this language model      can be used for speech enabled input fileds.   career | Best for audio content that contains only spoken career names. For examble this language model can be used for speech enabled input fileds.
     **/
    public String getLanguageModel() {
        return languageModel;
    }

    public void setLanguageModel(String languageModel) {
        this.languageModel = languageModel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecognitionConfig recognitionConfig = (RecognitionConfig) o;
        return (this.audioEncoding == null ? recognitionConfig.audioEncoding == null : this.audioEncoding.equals(recognitionConfig.audioEncoding)) &&
                (this.sampleRateHertz == null ? recognitionConfig.sampleRateHertz == null : this.sampleRateHertz.equals(recognitionConfig.sampleRateHertz)) &&
                (this.languageCode == null ? recognitionConfig.languageCode == null : this.languageCode.equals(recognitionConfig.languageCode)) &&
                (this.maxAlternatives == null ? recognitionConfig.maxAlternatives == null : this.maxAlternatives.equals(recognitionConfig.maxAlternatives)) &&
                (this.profanityFilter == null ? recognitionConfig.profanityFilter == null : this.profanityFilter.equals(recognitionConfig.profanityFilter)) &&
                (this.asrModel == null ? recognitionConfig.asrModel == null : this.asrModel.equals(recognitionConfig.asrModel)) &&
                (this.languageModel == null ? recognitionConfig.languageModel == null : this.languageModel.equals(recognitionConfig.languageModel));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.audioEncoding == null ? 0 : this.audioEncoding.hashCode());
        result = 31 * result + (this.sampleRateHertz == null ? 0 : this.sampleRateHertz.hashCode());
        result = 31 * result + (this.languageCode == null ? 0 : this.languageCode.hashCode());
        result = 31 * result + (this.maxAlternatives == null ? 0 : this.maxAlternatives.hashCode());
        result = 31 * result + (this.profanityFilter == null ? 0 : this.profanityFilter.hashCode());
        result = 31 * result + (this.asrModel == null ? 0 : this.asrModel.hashCode());
        result = 31 * result + (this.languageModel == null ? 0 : this.languageModel.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RecognitionConfig {\n");

        sb.append("  audioEncoding: ").append(audioEncoding).append("\n");
        sb.append("  sampleRateHertz: ").append(sampleRateHertz).append("\n");
        sb.append("  languageCode: ").append(languageCode).append("\n");
        sb.append("  maxAlternatives: ").append(maxAlternatives).append("\n");
        sb.append("  profanityFilter: ").append(profanityFilter).append("\n");
        sb.append("  asrModel: ").append(asrModel).append("\n");
        sb.append("  languageModel: ").append(languageModel).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
