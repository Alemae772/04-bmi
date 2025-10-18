package com.danhramtsov.java.essentials;
import java.util.Scanner;
public class BmiTask {
    //TODO: реализуйте код ниже
            public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите ваш вес (в кг): ");
            double weight = sc.nextDouble();
            System.out.print("Введите ваш рост (в метрах): ");
            double height = sc.nextDouble();
            double bmi = weight/(height*height);
            System.out.printf("Ваш индекс массы тела (ИМТ): %.2f%n", bmi);
            sc.close();
            }
}