package com.gauravdalvi.learnjapanese;

public class Words {

    private String englishTranslation;
    private String japaneseTranslation;
    private int resourceImageId = -1;
    private int audioResourceId;

    public Words(String e, String j, int id, int audio) {
        englishTranslation = e;
        japaneseTranslation = j;
        resourceImageId = id;
        audioResourceId = audio;
    }

    public Words(String e, String j,  int audio) {
        englishTranslation = e;
        japaneseTranslation = j;
        audioResourceId = audio;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    public String getJapaneseTranslation() {
        return japaneseTranslation;
    }

    public int getResourceImageId() {
        return resourceImageId;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }
}
