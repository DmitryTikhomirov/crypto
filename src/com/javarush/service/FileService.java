package com.javarush.service;

import com.javarush.service.CryptoService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileService {

    private static final List<String> notValidFile = List.of(".bash_history",
            ".bash_logout", ".bash_profile");


    String readFile(String inputFile) {   // чтение текста из файла
        String result = null;
        try {
            if(notValidFile.contains(inputFile)) throw new IOException();
            result = Files.readString(Paths.get(inputFile));

        } catch (IOException e) {
            System.out.printf("\nФайл %s не найден", inputFile);

        }
        return result;
    }


    void writeFile(String outputFile) {             //    Запись текста в файл

        try {
            PrintWriter out = new PrintWriter(outputFile);
            out.println(String.valueOf(CryptoService.text));
            out.close();
        } catch (IOException e) {
            System.out.printf("\nФайл %s не записан.", outputFile);
        }
    }
}
