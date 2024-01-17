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
 * Process user input in a hotel management application. Provides methods for
 * inputting various types of data such as integers, strings, and yes/no
 * choices.
 *
 * @author Duy.Tran
 */
public class Inputer {

    private int input_int = 0;
    SearchHotel searchHotel = new SearchHotel();
    Scanner sc = new Scanner(System.in);

    /**
     * Reads an integer from the user.
     *
     * @param msg The message to display before taking input.
     * @return The entered integer.
     */
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

    /**
     * Reads an integer within a specified range from the user.
     *
     * @param msg The message to display before taking input.
     * @param x The minimum value of the range.
     * @param y The maximum value of the range.
     * @return The entered integer within the specified range.
     */
    public int rating(String msg, int x, int y) {
        System.out.print(msg);
        boolean check = true;
        while (check) {
            try {
                input_int = Integer.parseInt(sc.nextLine());
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

    /**
     * Reads an uppercase string from the user
     *
     * @param msg The message to display before taking input.
     * @return The entered uppercase string
     */
    public String inputUpperString(String msg) {
        String input_string = "";
        do {
            System.out.println(msg);
            input_string = sc.nextLine().toUpperCase();
        } while (input_string.trim().isEmpty());
        return input_string;
    }

    /**
     * Reads a non-empty string from the user and processes it.
     *
     * @param msg The message to display before taking input.
     * @return The entered and processed string.
     */
    public String inputString(String msg) {
        String input_string = "";
        do {
            System.out.println(msg);
            input_string = sc.nextLine();
            input_string = processingString(input_string);
        } while (input_string.trim().isEmpty());
        return input_string;
    }

    /**
     * Reads a string from the user (which can be blank) and processes it.
     *
     * @param msg The message to display before taking input.
     * @return The entered and processed string.
     */
    public String inputStringCanBlank(String msg) {
        String input_string = "";
        System.out.println(msg);
        input_string = sc.nextLine();
        input_string = processingString(input_string);
        return input_string;
    }

    /**
     * Reads a string from the user that matches a specified pattern.
     *
     * @param msg The message to display before taking input.
     * @param pt The regular expression pattern.
     * @return The entered string that matches the pattern.
     */
    public String inputStringPattern(String msg, String pt) {
        String input_string = "";
        Pattern pattern = Pattern.compile(pt);
        do {
            System.out.println(msg);
            input_string = sc.nextLine();
        } while (input_string.trim().isEmpty() || !pattern.matcher(input_string).matches());
        return input_string;
    }

    /**
     * Reads a string from the user that matches a specified pattern (which can
     * be blank) and processes it.
     *
     * @param msg The message to display before taking input.
     * @param pt The regular expression pattern.
     * @return The entered and processed string that matches the pattern.
     */
    public String inputStringPatternBlank(String msg, String pt) {
        String input_string = "";
        Pattern pattern = Pattern.compile(pt);
        do {
            System.out.println(msg);
            input_string = sc.nextLine();
            input_string = processingString(input_string);
        } while (!input_string.trim().isEmpty() && !pattern.matcher(input_string).matches());
        return input_string;
    }

    /**
     * Reads a yes/no choice from the user.
     *
     * @param msg The message to display before taking input.
     * @return True if the choice is 'Y', false if the choice is 'N'.
     */
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

    /**
     * Reads a hotel ID from the user, checking for uniqueness and non-empty
     * input.
     *
     * @param arr The ArrayList of HotelInformation to check for existing IDs.
     * @return The entered and validated hotel ID.
     */
    public String inputHotelID(ArrayList<HotelInformation> arr) {
        String id = "";
        boolean check = true;
        do {
            System.out.println("Enter hotel ID:");
            id = sc.nextLine().toUpperCase();
            if (searchHotel.searchHotelById(arr, id) != null) {
                System.err.println("hotel ID already exists. Try another one.");
            } else if (id.trim().isEmpty()) {
                System.err.println("ID can not empty!");
            } else {
                return id.toUpperCase();
            }
        } while (check);
        return id;
    }

    /**
     * Processes a string by capitalizing the first characters of each word.
     *
     * @param input_string The input string to be processed.
     * @return The processed string.
     */
    public String processingString(String input_string) {
        if (input_string.isEmpty()) {
            return "";
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
