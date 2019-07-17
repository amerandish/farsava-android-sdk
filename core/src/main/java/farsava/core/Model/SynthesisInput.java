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

/**
 * The Synthesizer requires either plain text or SSML as input. Only provide text OR ssml. Providing both will result in a bad request response.
 **/
public class SynthesisInput {

    @SerializedName("text")
    private String text = null;
    @SerializedName("ssml")
    private String ssml = null;

    /**
     * The raw text to be synthesized.
     **/
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * The SSML document to be synthesized. The SSML document must be valid and well-formed.
     **/
    public String getSsml() {
        return ssml;
    }

    public void setSsml(String ssml) {
        this.ssml = ssml;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SynthesisInput synthesisInput = (SynthesisInput) o;
        return (this.text == null ? synthesisInput.text == null : this.text.equals(synthesisInput.text)) &&
                (this.ssml == null ? synthesisInput.ssml == null : this.ssml.equals(synthesisInput.ssml));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.text == null ? 0 : this.text.hashCode());
        result = 31 * result + (this.ssml == null ? 0 : this.ssml.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class SynthesisInput {\n");

        sb.append("  text: ").append(text).append("\n");
        sb.append("  ssml: ").append(ssml).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}