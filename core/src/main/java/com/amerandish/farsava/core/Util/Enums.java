package com.amerandish.farsava.core.Util;

public class Enums {
    public enum Status {
        Running,
        Warnings,
        Critical
    }

    public enum AudioEncoding {
        LINEAR16,
        FLAC,
        MP3
    }

    public enum LanguageCode {
        fa,
        en
    }

    public enum SpeechRecognition {
        video,
        command_and_search,
        phone_call
    }

    public enum VoiceGender {
        male,
        female
    }

    public enum ASRStatus {
        queued,
        processing,
        done,
        partial
    }

    public enum LMStatus {
        queued,
        training,
        trained
    }
}
