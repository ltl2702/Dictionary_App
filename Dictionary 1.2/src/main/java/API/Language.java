package API;

public interface Language {
    String getTranslatedItem();

    void setLanguageFrom(String languageFrom);

    void setLanguageTo(String languageTo);

    void setTextForTranslate(String textForTranslate);
}
