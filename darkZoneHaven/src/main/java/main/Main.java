package main;

import java.util.Scanner;
import ui.*;

public class Main {
    public static void main(String[] args) {
        getCustomerID();
    }

    public static void getCustomerID() {
        String prompt = """
                \n\n\n\n\n
                Which Customer?
                1. Jake
                2. Jean
                3. Admin
                [Press \"Q\" to exit]
                Please choose [1-3]: """ + " ";
        Scanner sc = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.print(prompt);
            choice = sc.nextLine();
            if (choice.isBlank()) continue;
            else if (choice.equalsIgnoreCase("q")) break;
            else if (choice.equals("1")) {
                storageUI(1);
                break;
            }
            else if (choice.equals("2")) {
                storageUI(2);
                break;
            }
            else if (choice.equals("3")) {
                storageUI(3);
                break;
            }
        }
    }
    public static void storageUI(int id) {
        String prompt = """
                \n\n\n\n\n
                Which type of storage do you wish to save?
                1. Database
                2. File
                3. Memory
                [Press \"Q\" to exit]
                Please choose [1-3]: """ + " ";
        Scanner sc = new Scanner(System.in);
        String choice;
        while (true) {
            System.out.print(prompt);
            choice = sc.nextLine();
            if (choice.isBlank()) continue;
            else if (choice.equalsIgnoreCase("q")) break;
            else if (choice.equals("1")) {
                var app = new AppUI(true, id);
                app.loginUI();
                break;
            }
            else if (choice.equals("2")) {
                var app = new AppUI(false, id);
                app.loginUI();
                break;
            }
            else if (choice.equals("3")) {
                var app = new AppUI(id);
                app.loginUI();
                break;
            }
        }
    }
}