/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application.UI;

import Bussiness.Service;
import java.io.EOFException;

/**
 *Displays the main menu and handles user interactions.
 * @author Duy.Tran
 */
public class HotelManagement {

    /**
     * @param args the command line arguments
     * @throws java.io.EOFException
     */
    public static void main(String[] args) throws EOFException {
        Menu menu = new Menu();
        Service service = new Service("Hotel.dat");

        int choice = 0;
        do {
            menu.menuDisplay();
            choice = menu.choose();
            switch (choice) {
                case 1:
                    service.addHotel();
                    break;
                case 2:
                    service.checkExitsHotel();
                    break;
                case 3:
                    service.updateHotelInfor();
                    break;
                case 4:
                    service.deleteHotel();
                    break;
                case 5:
                    menu.searchingHotelMenu();
                    break;
                case 6:
                    service.displayHotel();
                    break;
                default:
                    System.out.println("Bye!!!");
                    break;

            }

        } while (0 < choice && choice < 7);
    }

}
