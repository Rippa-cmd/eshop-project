package ru.geekbrains.steps;

public class Test {
    public static void main(String[] args) {
        String a = "623.00";
        String b = "623";
        Double first = Double.valueOf(a);
        Double second = Double.valueOf(b);
        System.out.println(first.equals(second));
    }
}
