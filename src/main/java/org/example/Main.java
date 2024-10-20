package org.example;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

import static java.lang.System.in;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try{
            String inputFilePath= getInputFile();
            System.out.println("Выбранный файл для чтения: " + inputFilePath);
            String outFilePath = getInputFile();
            menu(inputFilePath,outFilePath);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

    }




    public static String getInputFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt"));
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            String inputFilePath = selectedFile.getAbsolutePath();
            return inputFilePath;
        }
        else
        {
            throw new IllegalArgumentException("Выбор файла для сохранения отменен.");
        }
    }
    public static void menu(String inputFilePath, String outFilePath){
        Scanner in = new Scanner(System.in);
        boolean ToNormal=false;
        while(true){
            System.out.println("Главное меню:\n1.Зашифровать данные\n2. Расшифровать данные, используя ключ\n 3.Расшифровать данные подбором");
            int input=in.nextInt();
            if(input==1)
                shifr(inputFilePath,outFilePath,false);
            if (input==2){
                shifr(inputFilePath,outFilePath,true);

            }
            if(input==3)
                forcedeShifr(inputFilePath,outFilePath);
            if(input>3 || input<=0) {
                System.out.println("Неверный выбор!");
            }}

    }

    public static void shifr(String inputFilePath, String outputFilePath,boolean ToNormal) {
        Scanner in = new Scanner(System.in);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            System.out.print(!ToNormal? "Введите количество сдвигов дя шифрования":"Введите ключ");
            int count = in.nextInt();
            if(count>=25)
                throw new IllegalArgumentException("Введен недопустимый ключ");
            else {
            StringBuilder result;

            while ((line = br.readLine()) != null) {
                result = new StringBuilder();

                for (char ch : line.toCharArray()) {
                    if (Character.isLetter(ch)) {
                        char base = Character.isUpperCase(ch) ? 'A' : 'a';
                        char encryptedChar =!ToNormal?  (char) ((ch - base + count) % 26 + base): (char)((ch - base - count + 26) % 26 + base) ;
                        result.append(encryptedChar);
                    } else {
                        result.append(ch);
                    }
                }

                bw.write(result.toString());
                bw.newLine();
            }

            System.out.println("Шифрование завершено. Результат записан в " + outputFilePath);}

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
    public static void forcedeShifr(String inputFilePath, String outputFilePath)
    {
        Scanner in = new Scanner(System.in);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (int i = 1; i < 26; i++) {
                StringBuilder result = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        for (char ch : line.toCharArray()) {
                            if (Character.isLetter(ch)) {
                                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                                char decryptedChar = (char) ((ch - base - i + 26) % 26 + base);
                                result.append(decryptedChar);
                            } else {
                                result.append(ch);
                            }
                        }
                    }
                }
                bw.write("Ключ: " + i);
                bw.newLine();
                bw.write(result.toString());
                bw.newLine();
                bw.newLine();
            }
            System.out.println("Шифрование завершено. Результат записан в " + outputFilePath);}

     catch (IOException e) {
        System.err.println("Ошибка при чтении файла: " + e.getMessage());
    }
    }
}