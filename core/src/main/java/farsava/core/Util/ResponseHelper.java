package farsava.core.util;

import farsava.core.data.model.ASRResponseBody;
import farsava.core.data.model.SpeechRecognitionResult;
import farsava.core.data.model.WordInfo;

/**
 * Created by MHK on 2019-08-21.
 * www.MHKSoft.com
 */
public class ResponseHelper {
    public static String compileResultToString(ASRResponseBody responseBody) {
        String result = "";
        for (SpeechRecognitionResult recognitionResult : responseBody.getResults()) {
            for (WordInfo wordInfo : recognitionResult.getWords()) {
                result += " " + wordInfo.getWord();
            }
        }
        return result.substring(1);
    }
}
