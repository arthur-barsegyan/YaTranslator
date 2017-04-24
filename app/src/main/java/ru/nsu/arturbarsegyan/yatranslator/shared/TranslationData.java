package ru.nsu.arturbarsegyan.yatranslator.shared;


public class TranslationData {
    private String originalText;
    private String translation;

    private String srcLang;
    private String dstLang;

    public TranslationData() {

    }

    public TranslationData(String originalText, String translation, String srcLang, String dstLang) {
        this.originalText = originalText;
        this.translation = translation;
        this.srcLang = srcLang;
        this.dstLang = dstLang;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTranslation() {
        return translation;
    }

    public String getSrcLang() {
        return srcLang;
    }

    public String getDstLang() {
        return dstLang;
    }
}
