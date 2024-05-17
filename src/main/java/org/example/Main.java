package org.example;
import java.util.Scanner;

public class Main {
    static final int EXIT = 0;
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("0 - Exit the program.\n" +
                    "1 - learn how to find objection");
            choice = input.nextInt();
            input.nextLine();
            switch (choice){
                case 0 ->{
                    System.out.println("Bye bye");
                }
                case 1->{
                    askChatGptAboutObjection();
                }
            }
        }while (choice!= EXIT);
    }

    private static void askChatGptAboutObjection() {
        System.out.println("Product you are selling:");
        String productName = input.nextLine();
        System.out.println("Product description:");
        String productDescription = input.nextLine();
        System.out.println("Objection you're facing:");
        String objection = input.nextLine();
        String userPrompt = "Product name: " + productName + " Product description: " + productDescription + " Objection:" + objection;
        System.out.println(ChatGPTAPI.chatGPT(userPrompt));
    }
}