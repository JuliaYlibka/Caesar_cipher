package org.example;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt"));
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String inputFilePath = selectedFile.getAbsolutePath();
            System.out.println("Выбранный файл для чтения: " + inputFilePath);

            int saveReturnValue = fileChooser.showSaveDialog(null);
            if (saveReturnValue == JFileChooser.APPROVE_OPTION) {
                File outputFile = fileChooser.getSelectedFile();
                shifr(inputFilePath, outputFile.getAbsolutePath());

            } else {
                System.out.println("Выбор файла для сохранения отменен.");
            }

        } else {
            System.out.println("Выбор файла отменен.");
        }

        in.close();
    }

    public static void shifr(String inputFilePath, String outputFilePath) {
        Scanner in = new Scanner(System.in);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            System.out.print("Введите количество сдвигов: ");
            int count = in.nextInt();
            StringBuilder result;

            while ((line = br.readLine()) != null) {
                result = new StringBuilder();

                for (char ch : line.toCharArray()) {
                    if (Character.isLetter(ch)) {
                        char base = Character.isUpperCase(ch) ? 'A' : 'a';
                        char encryptedChar = (char) ((ch - base + count) % 26 + base);
                        result.append(encryptedChar);
                    } else {
                        result.append(ch);
                    }
                }

                bw.write(result.toString());
                bw.newLine();
            }

            System.out.println("Шифрование завершено. Результат записан в " + outputFilePath);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } finally {
            in.close();
        }
    }
}