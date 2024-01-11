/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.Utilites;

import Bussiness.HotelInformation;
import Bussiness.SearchHotel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Duy.Tran
 */
public class Inputer {

    private int input_int = 0;
    SearchHotel sd = new SearchHotel();
    Scanner sc = new Scanner(System.in);

    public int inputInt(String msg) {
        System.out.print(msg);
        boolean check = true;
        while (check) {
            try {
                input_int = Integer.parseInt(sc.nextLine());

                return input_int;
            } catch (NumberFormatException e) {
                System.err.println("This must be number!");
                check = true;
            }

        }
        return 0;
    }
    public String inputUpperString(String msg) {
        String input_string = "";
        do {
            System.out.println(msg);
            input_string = sc.nextLine().toUpperCase();
        } while (input_string.trim().isEmpty());
        return input_string;
    }
    
    
    
    public String inputString(String msg) {
        String input_string = "";
        do {
            System.out.println(msg);
            input_string = sc.nextLine();
            input_string = processingString(input_string);
        } while (input_string.trim().isEmpty());
        return input_string;
    }

    public String inputStringPattern(String msg, String pt) {
        String input_string = "";
        Pattern pattern = Pattern.compile(pt);
        do {
            System.out.println(msg);
            input_string = sc.nextLine();
        } while (input_string.trim().isEmpty() || !pattern.matcher(input_string).matches());
        return input_string;
    }

    public boolean inputYN(String msg) {
        String choice;
        while (true) {
            System.out.println(msg);
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            } else if (choice.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.err.println("Must be Y or N");
            }
        }

    }

    public String inputHotelID(ArrayList<HotelInformation> arr) {
        String id = "";
        do {
            System.out.println("Enter hotel ID:");
            id = sc.nextLine().toUpperCase();
            if (sd.searchHotelById(arr, id) != null) {
                System.err.println("hotel ID already exists. Try another one.");
            } else if (id.trim().isEmpty()) {
                System.err.println("ID can not empty!");
            } else {
                return id.toUpperCase();
            }
        } while (true);
    }

    public String processingString(String input_string) {
        if (input_string == null) {
            return " ";
        }
        String[] words = input_string.trim().split("\\s+");
        StringBuilder processString = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                processString.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return processString.toString().trim();
    }
}
