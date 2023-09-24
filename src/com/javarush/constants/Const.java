package com.javarush.constants;

import java.util.List;

/**
 * Константы
 */
public class Const {

    public static final String MAIN_MENU = """
            Введите:
             1 - для зашифровки
             2 - для расшифровки по ключу
             3 - для расшифровки методом brute force
             4 - для расшифровки методом статического анализа
             5 - для выхода""";
    public static final List<String> LIST_OF_SYSTEM_FILES
            = List.of(".bash_history",
            ".bash_logout", ".bash_profile");
    public static final String BRUTE_FORCE_DONE = "Расшифровка методом brute force удалась :)";
    public static final String BRUTE_FORCE_WRONG = "Расшифровка методом brute force не удалась :(";
    public static final String ENTER_KEY = "Введите ключ: ";
    public static final String WRONG_KEY = "Неверный ключ. Введите значение от 1 до ";
    public static final String NAME_OUTPUT_FILE = "Введите имя файла для записи: ";
    public static final String WYAT_TO_DO = "Вы ничего не выбрали.";
    public static final String FILE_NAME_BRUTE_FORCE = "Введите имя файла для расшифровки методом brute force.";
    public static final String FILE_NAME_ENCRYPTION = "Введите имя файла для зашифровки.";
    public static final String FILE_NAME_DECRYPTION_FOR_KEY = "Введите имя файла для расшифровки по ключу.";
    public static final String WRONG_DECRYPTION_STATISTIC = "Расшифровка статистическим методом не удалась";
    public static final String FILE_NAME_FOR_STATISTIK_DATA = "Введите имя файла для расчета статистических данных: ";
    public static final String FILE_NAME_FOR_DECRYPT_STAT_METHOD = "Введите имя файла для расшифровки статистическим методом.";
    public static final String DECRYPT_STATISTIC_IS_OK = "Расшифровка статистическим методом удалась.";
    public static final String FILE_ENCRYPT = "Файл зашифрован.";
    public static final String FILE_DECRYPT = "Файл расшифрован.";




    private Const() {
    }
}
