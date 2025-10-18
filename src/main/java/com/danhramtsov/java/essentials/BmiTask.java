package com.danhramtsov.java.essentials;
import java.util.Scanner;
public class BmiTask {
    //TODO: реализуйте код ниже
            public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите ваш вес (в кг): ");
            String weight = sc.nextLine();
            System.out.print("Введите ваш рост (в метрах): ");
            double bmi = sc.nextDouble();
            String height = sc.nextLine();

            sc.nextLine();
            System.out.printf("Ваш индекс массы тела (ИМТ): %.2f%n", bmi);

            sc.close();
}