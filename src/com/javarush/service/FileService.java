package com.javarush.service;

import com.javarush.exceptions.FileException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.javarush.constants.Const.LIST_OF_SYSTEM_FILES;
import static com.javarush.constants.Errors.*;

public class FileService {



    /**
     * Чтение текста из файла
     */
    public String readFile(String inputFile) throws FileException {
        String result;
            if(LIST_OF_SYSTEM_FILES.contains(inputFile)) {
                throw new FileException(ERROR_SYSTEM_FILE, inputFile);
            }
        try {
            result = Files.readString(Paths.get(inputFile));
        } catch (IOException e) {
            throw new FileException(ERROR_FILE_NOT_FOUND, inputFile);
        }
        return result;
    }
    /**
     *  Запись текста в файл
     */
    public void writeFile(String outputFile, String text) throws FileException {

        try (PrintWriter out = new PrintWriter(outputFile);){

            out.println(text);
        } catch (IOException e) {
            throw new FileException(ERROR_WRITE_TO_FILE, outputFile);
        }
    }
}
