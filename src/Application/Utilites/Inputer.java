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
 *Process user input in a hotel management application.
 *Provides methods for inputting various types of data such as integers, strings, and yes/no choices.
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

    public int rating(String msg, int x, int y) {
        System.out.print(msg);
        boolean check = true;
        while (check) {
            try {
                input_int = Integer.parseInt(sc.nextLine());
                if(input_int == 0) {
                    return 0;
                }
                if (input_int < x || input_int > y) {
                    System.out.println("This number must be from " + x + " to " + y);
                    check = true;
                } else {
                    return input_int;
                }
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
    
    public String inputStringCanBlank(String msg) {
        String input_string = "";
            System.out.println(msg);
            input_string = sc.nextLine();
            input_string = processingString(input_string);
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
    
    public String inputStringPatternBlank(String msg, String pt) {
        String input_string = "";
        Pattern pattern = Pattern.compile(pt);
        do {
            System.out.println(msg);
            input_string = sc.nextLine();
        } while (!input_string.trim().isEmpty() && !pattern.matcher(input_string).matches());
        return input_string;
    }

    public boolean inputYN(String msg) {
        String choice;
        boolean check = true;
        while (check) {
            System.out.println(msg);
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("Y")) {
                return check;
            } else if (choice.equalsIgnoreCase("N")) {
                return check = false;
            } else {
                System.err.println("Must be Y or N");
            }
        }
        return check;

    }

    public String inputHotelID(ArrayList<HotelInformation> arr) {
        String id = "";
        boolean check = true;
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
        } while (check);
        return id;
    }

    public String processingString(String input_string) {
        if (input_string.isEmpty()) {
            return " ";
        }
        String[] words = input_string.trim().split("\\s+"); //Delete extra space
        StringBuilder processString = new StringBuilder(); //does not create new objects continuously
        //Capitalize the first characters
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
