package org.example;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        try{
            String inputFilePath= getInputFile();
            System.out.println("Выбранный файл для чтения: " + inputFilePath);
            String outFilePath = getInputFile();
            shifr(inputFilePath,outFilePath,menu());
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
    public static boolean menu(){
        Scanner in = new Scanner(System.in);
        boolean ToNormal=false;
        while(true){
            System.out.println("Главное меню:\n1.Зашифровать данные\n2. Расшифровать данные");
            int input=in.nextInt();
            if(input==1)
                break;
            if (input==2){
                ToNormal = true;
                break;
            }
            if(input>2 || input<=0) {
                System.out.println("Неверный выбор!");
            }}
        return ToNormal;
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
        in.close();
    }
}