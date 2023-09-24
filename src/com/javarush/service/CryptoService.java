package com.javarush.service;

import com.javarush.exceptions.FileException;

import static com.javarush.constants.Const.*;

public class CryptoService {
    private final FileService fileService;
    private final ConsoleService consoleService;

    public CryptoService(FileService fileService, ConsoleService consoleService) {
        this.fileService = fileService;
        this.consoleService = consoleService;
    }

    private final char[] alphabetCharArray = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ".toCharArray();
    private final int ALPHABET_LENGTH = alphabetCharArray.length;
    /**
     * Таблица для замены символов: в первом массиве - незашифрованный алфавит,
     * во втором массиве каждому незашифрованному символу из первого массива присваивается соответствующий шифрующий символ
     */
    private final char[][] substitutionTable = new char[ALPHABET_LENGTH][2];

    public void decryptionStatistic() throws FileException {
        String inputFile = consoleService.readFromConsole(FILE_NAME_FOR_DECRYPT_STAT_METHOD);
        String outputFile = consoleService.readFromConsole(NAME_OUTPUT_FILE);
        String checkFile = consoleService.readFromConsole(FILE_NAME_FOR_STATISTIK_DATA);
        String stringText = fileService.readFile(inputFile);
        String stringTextForCheck = fileService.readFile(checkFile);
        int key = calculateKeyForStatistics(stringText.toCharArray(), stringTextForCheck.toCharArray());
        if (key > 0) {
            setSubstitutionTable(-1 * key);
            fileService.writeFile(outputFile, new String(crypt(stringText.toCharArray())));
            System.out.println(DECRYPT_STATISTIC_IS_OK);
        } else {
            System.out.println(WRONG_DECRYPTION_STATISTIC);
        }
    }

    public void decryptionBruteForce() throws FileException {
        String inputFile = consoleService.readFromConsole(FILE_NAME_BRUTE_FORCE);
        String outputFile = consoleService.readFromConsole(NAME_OUTPUT_FILE);
        String stringText = fileService.readFile(inputFile);
        fileService.writeFile(outputFile, new String(cryptographerBruteForce(stringText.toCharArray())));
    }

    public void encryption() throws FileException {
        String inputFile = consoleService.readFromConsole(FILE_NAME_ENCRYPTION);
        String outputFile = consoleService.readFromConsole(NAME_OUTPUT_FILE);
        String stringText = fileService.readFile(inputFile);
        setSubstitutionTable(readKey());
        fileService.writeFile(outputFile, new String(crypt(stringText.toCharArray())));
       System.out.println(FILE_ENCRYPT);
    }

    public void decryption() throws FileException {
        String inputFile = consoleService.readFromConsole(FILE_NAME_DECRYPTION_FOR_KEY);
        String outputFile = consoleService.readFromConsole(NAME_OUTPUT_FILE);
        String stringText = fileService.readFile(inputFile);
        setSubstitutionTable(-1 * readKey());
        fileService.writeFile(outputFile, new String(crypt(stringText.toCharArray())));
        System.out.println(FILE_DECRYPT);
    }

    /**
     * Расшифровка грубой силой
     * Критерий открытия кода - после знаков препинания должен идти пробел в 66% случаев
     */
    private char[] cryptographerBruteForce(char[] text) {
        int checkOk = 0, checkWrong = 0;
        setSubstitutionTable(-1);
        for (int i = 1; i < ALPHABET_LENGTH; i++) {
            crypt(text);
            for (int j = 0; j < text.length - 1; j++) {
                if (text[j] == ',' || text[j] == '.' || text[j] == '?' || text[j] == '!' || text[j] == ':') {
                    if (text[j + 1] == ' ') {
                        checkOk++;
                    } else {
                        checkWrong++;
                    }
                }
            }
            if (calculatePercent(checkOk, checkWrong)) {
                System.out.println(BRUTE_FORCE_DONE);
                return text;
            } else {
                checkOk = 0;
                checkWrong = 0;
            }
        }
        System.out.println(BRUTE_FORCE_WRONG);
        return text;
    }

    private boolean calculatePercent(int checkOk, int checkWrong) {
        return checkOk > checkWrong * 2;
    }

    /**
     * Расшифровка статическим методом
     */
    private int calculateKeyForStatistics(char[] text, char[] textForCheck) {
        // статистика зашифрованного текста
        int[] statCloseText = new int[ALPHABET_LENGTH];
        // статистика проверочного текста
        int[] statOpenText = new int[ALPHABET_LENGTH];
        // число символов по которым будет вычисляться ключ
        final int CHAR_TO_CHECK = 4;
        int sumCloseText = 0;
        int sumOpenText = 0;
        // считаем число повторений символов зашифрованного текста
        countDuplicateSymbols(text, ALPHABET_LENGTH, statCloseText);
        // считаем число повторений символов проверочного текста
        countDuplicateSymbols(textForCheck, alphabetCharArray.length, statOpenText);
        // Считаем число всех символов в текстах
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            sumCloseText += statCloseText[i];
            sumOpenText += statOpenText[i];
        }
        // Получаем статистику приведенную к десятым долям процента
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            statCloseText[i] = statCloseText[i] * 1000 / sumCloseText;
            statOpenText[i] = statOpenText[i] * 1000 / sumOpenText;
        }
        int key1, key2, sumKey = 0, temp;
        for (int i = 0; i < CHAR_TO_CHECK; i++) {
            // вычисляем индекс символа с максимальной статистикой для закрытого текста
            key1 = indexMaxElement(statCloseText);
            // и сразу обнуляем эту статистику для поиска следующего символа
            statCloseText[key1] = 0;
            // вычисляем индекс символа с максимальной статистикой для открытого текста
            key2 = indexMaxElement(statOpenText);
            // и сразу обнуляем эту статистику для поиска следующего символа
            statOpenText[key2] = 0;
            //вычисляем матрицу ключей сдвигов
            temp = (key1 - key2);
            temp = temp < 0 ? temp + ALPHABET_LENGTH : temp;
            temp = temp > ALPHABET_LENGTH ? temp - ALPHABET_LENGTH : temp;
            sumKey += temp;
        }
        if (sumKey > 0) {
            return Math.round(((float) sumKey) / CHAR_TO_CHECK);
        } else {
            return -1;
        }
    }

    private void countDuplicateSymbols(char[] text, int alphabetLenght, int[] statCloseText) {
        for (char c : text) {
            for (int j = 0; j < alphabetLenght; j++) {
                if (alphabetCharArray[j] == c) {
                    statCloseText[j] += 1;
                    j = alphabetCharArray.length;
                }
            }
        }
    }

    /**
     * Возвращает индекс максимального элемента в массиве
     */
    private int indexMaxElement(int[] array) {
        int index = array[0];
        int out = 0;
        for (int i = 1; i < array.length; i++) {
            if (index < array[i]) {
                index = array[i];
                out = i;
            }
        }
        return out;
    }

    /**
     * Вводим ключ
     */
    private int readKey() {
        int key;
        boolean isKeyWrong = true;
        do {
            key = consoleService.readIntegerFromConsole(ENTER_KEY);
            if (key > 0 && key <= ALPHABET_LENGTH) {
                isKeyWrong = false;
            } else {
                System.out.println(WRONG_KEY + ALPHABET_LENGTH);
            }
        } while (isKeyWrong);
        return key;
    }

    /**
     * Формирование таблицы подстановки символов.
     * Если key положительный - зашифровка, если отрицательный - расшифровка
     */
    private void setSubstitutionTable(int key) {
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            substitutionTable[i][0] = alphabetCharArray[i];
            if (i + key < 0) {
                substitutionTable[i][1] = alphabetCharArray[i + key + ALPHABET_LENGTH];
            } else if (i + key >= ALPHABET_LENGTH) {
                substitutionTable[i][1] = alphabetCharArray[i + key - ALPHABET_LENGTH];
            } else {
                substitutionTable[i][1] = alphabetCharArray[i + key];
            }
        }
    }

    /**
     * Зашифровка текста, используя таблицу подстановки
     */
    private char[] crypt(char[] text) {
        for (int i = 0; i < text.length; i++) {
            for (int j = 0; j < ALPHABET_LENGTH; j++) {
                if (text[i] == substitutionTable[j][0]) {
                    text[i] = substitutionTable[j][1];
                    j = ALPHABET_LENGTH;
                }
            }
        }
        return text;
    }
}