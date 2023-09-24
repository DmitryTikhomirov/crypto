package com.javarush.service;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner;

    public ConsoleService() {
        this.scanner = new Scanner(System.in);
    }

    public String readFromConsole(String message) {
        String console;
        System.out.println(message);
        do {
            console = scanner.nextLine();
        }while (console.equals(""));
        return console;


    }
    public  int readIntegerFromConsole(String message){
        System.out.println(message);
        return scanner.nextInt();
    }
}
