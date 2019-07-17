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
 * Contains audio source URI with the encoding specified in the RecognitionConfig.    *For asrlongrunning endpoint only uri is accepted.*      Property | Description   ------------ | -------------   uri | URI that points to a file that contains audio data bytes as specified in RecognitionConfig. The file must not be compressed (for example, gzip).
 **/
public class RecognitionAudioURI {

    @SerializedName("uri")
    private String uri = null;

    /**
     * URI that points to a file that contains audio data bytes as specified in RecognitionConfig. The file must not be compressed (for example, gzip).
     **/
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecognitionAudioURI recognitionAudioURI = (RecognitionAudioURI) o;
        return (this.uri == null ? recognitionAudioURI.uri == null : this.uri.equals(recognitionAudioURI.uri));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.uri == null ? 0 : this.uri.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RecognitionAudioURI {\n");

        sb.append("  uri: ").append(uri).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}