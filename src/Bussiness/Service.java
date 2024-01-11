/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import Application.Utilites.Inputer;
import Data.DataFile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duy.Tran
 */
public class Service {

    private final List<HotelInformation> arrHotel = new ArrayList<>();
    private final DataFile DF = new Data.DataFile();
    private final Inputer inputer = new Inputer();
    private SearchHotel sd = new SearchHotel();
    private String pathName;

    public Service(String fileName) {
        
        this.pathName = fileName;
        try {
            DF.loadData(arrHotel, pathName);
        } catch (Exception e) {
            System.out.println("Empty list");
        }
        if (arrHotel.isEmpty()) {
            System.out.println("Empty list, add new:");
            addHotel();
        }
    }

    public void addHotel() {
        String hotel_Id;
        String hotel_Name;
        int hotel_Room_Available;
        String hotel_Address;
        String hotel_Phone;
        int hotel_Rating;
        boolean choice = true;
        while (choice) {
            hotel_Id = inputer.inputHotelID((ArrayList<HotelInformation>) arrHotel);
            hotel_Name = inputer.inputStringPattern("Enter hotel name:", "^[a-zA-Z\\\\s]+$");
            hotel_Room_Available = inputer.inputInt("Enter the number of available rooms:");
            hotel_Address = inputer.inputString("Enter hotel address:");
            hotel_Rating = inputer.inputInt("Enter hotel rating (1-5):");
            hotel_Phone = inputer.inputStringPattern("Enter phone number(0xxxxxxxxx):", "0\\d{9}");
            arrHotel.add(new HotelInformation(hotel_Id, hotel_Name, hotel_Room_Available, hotel_Address, hotel_Phone, hotel_Rating));
            DF.saveData(arrHotel, pathName, "Hotel information has been saved.");
            choice = inputer.inputYN("Do you want to continue(Y/N): ");

        }
    }

    public void checkExitsHotel() {

        boolean choice = true;
        while (choice) {
            String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
            ArrayList<HotelInformation> arTemp = new ArrayList<>();
            DF.loadData(arTemp, pathName);
            HotelInformation CE = sd.searchHotelById(arTemp, hotel_Id);
            if (CE != null) {
                System.out.println("Exist Hotel");
            } else {
                System.out.println("No Hotel Found!");
            }
            choice = inputer.inputYN("Do you want to continue(Y/N): ");
        }
    }

    public void updateHotelInfor() {

    }

    public void deleteHotel() {
        
    }
    public void searchHotel() {

    }

    public void displayHotel() {

        System.out.printf("|%9s|%17s|%25s|%80s|%20s|%20s|\n", "Hotel_ID", "Hotel_Name", "Hotel_Room_Available", "Hotel_Address", "Hotel_Phone", "Hotel_Rating");

        if (!arrHotel.isEmpty()) {
            for (HotelInformation hotel : arrHotel) {
                System.out.println(hotel);
            }
            System.out.println("Hotel information displayed.");
        } else {
            System.out.println("No hotel information available.");
        }
    }

    
}
