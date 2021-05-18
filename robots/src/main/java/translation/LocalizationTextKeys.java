package translation;

public enum LocalizationTextKeys {
    EXIT_DIALOG_TITLE("exit_dialog_title"),
    EXIT_DIALOG_MESSAGE("exit_dialog_message"),
    EXIT_DIALOG_YES("exit_dialog_yes"),
    EXIT_DIALOG_NO("exit_dialog_no"),

    TOOLBAR_PROGRAM("toolbar_program"),
    TOOLBAR_VIEW_MODE("toolbar_view_mode"),
    TOOLBAR_TESTS("toolbar_tests"),
    TOOLBAR_LANGUAGE("toolbar_language"),

    PROGRAM_EXIT("program_exit"),
    PROGRAM_MENU_DESCRIPTION("program_menu_description"),

    VIEW_MODE_MENU_DESCRIPTION("view_mode_menu_description"),
    VIEW_MODE_SYSTEM_SCHEME("view_mode_system_scheme"),
    VIEW_MODE_UNIVERSAL_SCHEME("view_mode_universal_scheme"),

    TESTS_MENU_DESCRIPTION("tests_menu_description"),
    TESTS_MESSAGE_TO_LOG("tests_message_to_log"),

    LANGUAGE_MENU_DESCRIPTION("language_menu_description"),
    LANGUAGE_RUSSIAN("language_russian"),
    LANGUAGE_ENGLISH("language_english"),
    LANGUAGE_CZECH("language_czech");
    
    private final String key;
    
    LocalizationTextKeys(String key){
        this.key = key;
    }
    
    public String getKey(){
        return key;
    }
}
