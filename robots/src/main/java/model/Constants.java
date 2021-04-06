package model;



public class Constants {
    public static class MainApplicationFrame {
        public static final int WIDTH = 400;
        public static final int HEIGHT = 400;
        public static final int INSET = 50;

        public static final String TEST_MENU = "Тесты";
        public static final String TEST_MENU_DESCRIPTION = "Тестовые команды";
        public static final String TESTS_MESSAGE_TO_LOG = "Сообщение в лог";
        public static final String LOG_MESSAGE = "Новая строка";

        public static final String DISPLAY_MODE_MENU = "Режим отображения";
        public static final String  DISPLAY_MODE_MENU_DESCRIPTION = "Управление режимом отображения приложения";
        public static final String UNIVERSAL_SCHEME = "Универсальная схема";
        public static final String SYSTEM_SCHEME = "Системная схема";

        public static final String EXIT_MENU = "Выход";

        public static final String PROTOCOL_WORKING = "Протокол работает";
    }

    public static class LogWindow {
        public static final int WIDTH = 200;
        public static final int HEIGHT = 500;

        public static final String WINDOW_TITLE = "Протокол работы";
    }

    public static class ExitPaneOptions {
        public static final String YES = "Да";
        public static final String NO = "Нет";
        public static final String WINDOW_TITLE = "Подтверждение";
        public static final String WINDOW_MESSAGE = "Вы действительно хотите закрыть окно?";
    }

    public static class GameWindow {
        public static final String WINDOW_TITLE = "Игровое поле";
    }

    public static class Robot {
        public static final double MAX_VELOCITY = 0.1;
        public static final double MAX_ANGULAR_VELOCITY = 0.001;
    }
}
