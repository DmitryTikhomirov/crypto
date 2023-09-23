package com.javarush;

import com.javarush.exceptions.FileException;
import com.javarush.service.ConsoleService;
import com.javarush.service.CryptoService;
import com.javarush.service.FileService;

import static com.javarush.constants.Const.WYAT_TO_DO;
import static com.javarush.constants.Const.MAIN_MENU;

public class Main {


    public static void main(String[] args) throws FileException {
        ConsoleService consoleService = new ConsoleService();
        CryptoService cryptoService = new CryptoService(new FileService(), consoleService);

        try {
            chooseTask(cryptoService, consoleService);
        }   catch (FileException e){
            System.out.println(" File error");
        }
       

    }

    private static void chooseTask(CryptoService cryptoService, ConsoleService consoleService) throws FileException {
        while (true) {
            switch (consoleService.readFromConsole(MAIN_MENU)) {
                case "1" -> cryptoService.encryption();
                case "2" -> cryptoService.decryption();
                case "3" -> cryptoService.decryptionBruteForce();
                case "4" -> cryptoService.decryptionStatistic();
                case "5" -> {
                    return;
                }
                default -> System.out.println(WYAT_TO_DO);

            }
        }


    }}


