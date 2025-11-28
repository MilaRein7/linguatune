package com.linguatune.model;

public class Word {

    private String text;
    private String translation;

    public Word(String text, String translation) {
        this.text = text;
        this.translation = translation;
    }

    public String getText() {
        return text;
    }

    public String getTranslation() {
        return translation;
    }
}