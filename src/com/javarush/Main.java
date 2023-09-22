package com.javarush;

import com.javarush.service.CryptoService;
import com.javarush.service.FileService;

import java.util.Scanner;

import static com.javarush.constants.Consts.MAIN_MENU;

public class Main {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CryptoService cryptoService = new CryptoService(new FileService());
        String taskNumber = readStringFromConsole(scanner);
        chooseTask(scanner, cryptoService, taskNumber);
        scanner.close();
    }

    private static void chooseTask(Scanner scanner, CryptoService cryptoService, String taskNamber) {
        switch (taskNamber) {
            case "1" -> cryptoService.encryption(scanner);
            case "2" -> cryptoService.decryption(scanner);
            case "3" -> cryptoService.decryptionBruteForce(scanner);
            case "4" -> cryptoService.decryptionStatistic(scanner);
            default -> System.out.println("Вы ничего не выбрали. Выход из программы.");
        }
    }

    private static String readStringFromConsole(Scanner scanner) {
        System.out.println(MAIN_MENU);
        return scanner.nextLine();
    }


}


