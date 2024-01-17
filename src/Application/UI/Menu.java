/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.UI;

import Application.Utilites.Inputer;
import Bussiness.Service;

/**
 * Menu class for displaying options.
 *
 * @author Duy.Tran
 */
public class Menu extends Inputer {

    Service service = new Service("Hotel.dat");

    /**
     * Display the main menu with multiple options
     */
    public void menuDisplay() {
        System.out.println("\n1. Adding new Hotel.");
        System.out.println("2. Checking exits Hotel.");
        System.out.println("3. Updating Hotel information.");
        System.out.println("4. Deleting Hotel.");
        System.out.println("5. Searching Hotel.");
        System.out.println("6. Displaying a hotel list.");
        System.out.println("Others: Quit.\n");

    }

    /**
     * Take user input to choose an options from menu.
     *
     */
    public int choose() {
        return inputInt("Choose your option: ");
    }

    /**
     * Display sub-menu for searching hotels.
     */
    public void searchingHotel() {
        System.out.println("\n1. Searching by Hotel_id.");
        System.out.println("2. Searching by Hotel_name.\n");

    }

    /**
     * Displays the searching hotel menu and performs corresponding actions
     * based on user input.
     */
    public void searchingHotelMenu() {
        searchingHotel();
        switch (choose()) {
            case 1:
                service.checkHotelById();
                break;
            case 2:
                service.checkHotelbyName();
                break;
        }
    }
}
