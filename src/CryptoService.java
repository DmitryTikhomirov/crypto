import java.util.Scanner;

public class CryptoService {


    static char[] alphabetCharArray = Encryptor.ALPHABET.toCharArray();   // переводим ALPHABET  в массив char[]
    static final int POWER_ALPHABET = alphabetCharArray.length;  // мощность алфавита
    static char[][] substitutionTable = new char[POWER_ALPHABET][2]; // таблица для замены символов
    static char[] text;                                              // текст из файла для расшифровки
    static char[] textForCheck;                                     // текст из проверочного файла для расчета стстистики

      static void cryptographer(char[] text) {                   // зашифровка текста, используя таблицу подстановки

        for (int i = 0; i < text.length; i++) {
            for (int j = 0; j < POWER_ALPHABET; j++) {
                if (text[i] == substitutionTable[j][0]) {
                    text[i] = substitutionTable[j][1];
                    j = POWER_ALPHABET;
                }
            }
        }
    }
    static void setSubstitutionTable(int key) {  // Формирование таблицы подстановки символов.
                                                 // Если key положительный - зашифровка, если отрицательный - расшифровка

        for (int i = 0; i < POWER_ALPHABET; i++) {
            substitutionTable[i][0] = alphabetCharArray[i];
            if (i + key < 0) {
                substitutionTable[i][1] = alphabetCharArray[i + key + POWER_ALPHABET];
            } else if (i + key >= POWER_ALPHABET) {
                substitutionTable[i][1] = alphabetCharArray[i + key - POWER_ALPHABET];
            } else {
                substitutionTable[i][1] = alphabetCharArray[i + key];
            }
        }
    }
    static boolean cryptographerBruteForce() {   // расшифровка грубой силой
                                                // Критерий открытия кода - после знаков препинания должен идти пробел в 66% случаев
        int checkOk = 0;
        int checkWrong = 0;
        setSubstitutionTable(-1);
        for (int i = 1; i < POWER_ALPHABET; i++) {

            cryptographer(text);
            for (int j = 0; j < text.length - 1; j++) {

                if (text[j] == ',' || text[j] == '.' || text[j] == '?' || text[j] == '!' || text[j] == ':') {
                    if (text[j + 1] == ' ') {
                        checkOk++;
                    } else {
                        checkWrong++;
                    }
                }
            }
            if (checkOk > checkWrong * 2) {
                System.out.println("Расшифровка методом brute force удалась :)");
                return true;
            } else {
                checkOk = 0;
                checkWrong = 0;
            }
        }
        System.out.println("Расшифровка методом brute force не удалась :(");
        return false;
    }
        static boolean cryptographerStatistic() {             // расшифровка статическим методом
        int[] statCloseText = new int[POWER_ALPHABET];     // статистика зашифрованного текста
        int[] statOpenText = new int[POWER_ALPHABET];      // статистика проверочного текста
        final  int CHAR_TO_CHECK = 4;                      // число символов по которым будет вычисляться ключ
        int sumCloseText = 0;
        int sumOpenText = 0;

        for (char c : text) {                         // считаем число повторений символов зашифрованного текста
            for (int j = 0; j < POWER_ALPHABET; j++) {
                if (alphabetCharArray[j] == c) {
                    statCloseText[j] += 1;
                    j = alphabetCharArray.length;
                }
            }
        }
        for (char c : textForCheck) {                  // считаем число повторений символов проверочного текста
            for (int j = 0; j < alphabetCharArray.length; j++) {
                if (alphabetCharArray[j] == c) {
                    statOpenText[j] += 1;
                    j = alphabetCharArray.length;
                }
            }

        }

        for (int i = 0; i < POWER_ALPHABET; i++) {     // Считаем число всех символов в текстах
            sumCloseText += statCloseText[i];
            sumOpenText += statOpenText[i];
        }
        for (int i = 0; i < POWER_ALPHABET; i++) {        // Получаем статистику приведенную к десятым долям процента
            statCloseText[i] = statCloseText[i] * 1000 / sumCloseText;
            statOpenText[i] = statOpenText[i] * 1000 / sumOpenText;
        }
        int key1;
        int key2;
        int sumKey = 0;
        int temp ;

        for (int i = 0; i < CHAR_TO_CHECK; i++) {

            key1 = indexMaxElement(statCloseText);        // вычисляем индекс символа с максимальной статистикой для закрытого текста
            statCloseText[key1] = 0;                      // и слазу обнуляем эту статистику для поиска следующего символа

            key2 = indexMaxElement(statOpenText);         // вычисляем индекс символа с максимальной статистикой для открытого текста
            statOpenText[key2] = 0;                       // и слазу обнуляем эту статистику для поиска следующего символа

            temp = (key1 - key2);                         //вычисляем матрицу ключей сдвигов
            temp = temp < 0 ? temp + POWER_ALPHABET: temp;
            temp = temp > POWER_ALPHABET ? temp - POWER_ALPHABET: temp;
             sumKey += temp;
        }

        if (sumKey > 0){
            Encryptor.key = Math.round( ((float) sumKey) / CHAR_TO_CHECK);//делим сумму сдвигов на число символов для проверки
            return true;
        }  else {
            return false;
        }
    }

    static int indexMaxElement(int[] array) {    //возвращает индекс максимального элемента в массиве
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

    static int setKey(Scanner scanner) {    // Вводим ключ
        int key;
        boolean isKeyWrong = true;
        System.out.println("Введите ключ: ");
        do {
            key = scanner.nextInt();
            if (key > 0 && key <= POWER_ALPHABET) {
                isKeyWrong = false;
            } else {
                System.out.println("Неверный ключ. Введите значение от 1 до " + POWER_ALPHABET);
            }
        } while (isKeyWrong);
        return key;
    }

}
