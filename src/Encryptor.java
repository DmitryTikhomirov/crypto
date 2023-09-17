import java.util.Scanner;

public class Encryptor {
    static String ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
            "абвгдеёжзийклмнопрстуфхцчшщъыьэюя" +
            ".,\":-!? ";
    static String inputFile;
    static String outputFile;
    static String checkFile;  // файл для расчета статистики
    static int key;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                Введите:
                 1 - для зашифровки
                 2 - для расшифровки по ключу
                 3 - для расшифровки методом brute force
                 4 - для расшифровки методом статического анализа""");
        String whatToDo = scanner.nextLine();
        FileService fileService = new FileService();

        switch (whatToDo) {
            case "1" -> {
               
                encryption( scanner, fileService);

            }
            case "2" -> {

                decryption( scanner, fileService);

            }
            case "3" -> {
                decryptionBruteForce(scanner, fileService);


            }
            case "4" -> {
                decryptionStatistic(scanner, fileService);


            }
            default -> System.out.println("Вы ничего не выбрали. Выход из программы.");
        }
        scanner.close();
    }

    private static void decryptionStatistic(Scanner scanner, FileService fileService) {
        System.out.println("Введите имя файла для расшифровки статистическим методом.");
        inputFile = scanner.nextLine();
        System.out.println("Введите имя файла для записи: ");
        outputFile = scanner.nextLine();
        System.out.println("Введите имя файла для рассчета статистических данных: ");
        checkFile = scanner.nextLine();
        CryptoService.text = fileService.readFile(inputFile).toCharArray();
        CryptoService.textForCheck = fileService.readFile(checkFile).toCharArray();
        if (CryptoService.cryptographerStatistic()) {
            CryptoService.setSubstitutionTable(-key);
            CryptoService.cryptographer(CryptoService.text);
            fileService.writeFile(outputFile);
        } else {
            System.out.println("Расшифровка статистическим методом не удалась");
        }
    }

    private static void decryptionBruteForce(Scanner scanner, FileService fileService) {
        System.out.println("Введите имя файла для расшифровки методом brute force.");
        inputFile = scanner.nextLine();
        System.out.println("Введите имя файла для записи: ");
        outputFile = scanner.nextLine();
        CryptoService.text = fileService.readFile(inputFile).toCharArray();
        if (CryptoService.cryptographerBruteForce()) {
            fileService.writeFile(outputFile);
        } else {
            System.out.println("Расшифровка грубой силой не удалась");
        }
    }

    private static void encryption( Scanner scanner, FileService fileService) {
        System.out.println("Введите имя файла для зашифровки.");
        inputFile = scanner.nextLine();
        System.out.println("Введите имя файла для записи: ");
        outputFile = scanner.nextLine();
        CryptoService.text = fileService.readFile(inputFile).toCharArray();
        key = CryptoService.setKey(scanner);
        CryptoService.setSubstitutionTable(key);
        CryptoService.cryptographer(CryptoService.text);
        fileService.writeFile(outputFile);
    }
    private static void decryption( Scanner scanner, FileService fileService) {
        System.out.println("Введите имя файла для расшифровки по ключу.");
        inputFile = scanner.nextLine();
        System.out.println("Введите имя файла для записи: ");
        outputFile = scanner.nextLine();
        CryptoService.text = fileService.readFile(inputFile).toCharArray();
        key = CryptoService.setKey(scanner);
        CryptoService.setSubstitutionTable(-key);
        CryptoService.cryptographer(CryptoService.text);
        fileService.writeFile(outputFile);
    }
}


