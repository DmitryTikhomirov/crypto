package com.javarush.service;

import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner;

    public ConsoleService() {
        this.scanner = new Scanner(System.in);
    }

    public String readFromConsole(String messag) {
        System.out.println(messag);
        return scanner.nextLine();
    }
    public  int readIntegerFromConsole(String messag){
        System.out.println(messag);
        return scanner.nextInt();
    }
}
