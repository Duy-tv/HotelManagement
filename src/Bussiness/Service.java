/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import Application.Utilites.Inputer;
import Data.DataFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *This class represents the service layer for managing hotel information.
 *It provides methods for adding, updating, deleting, and searching hotel data.
 *
 * @author Duy.Tran
 */
public class Service implements IService {

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

    @Override
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
            hotel_Name = inputer.inputStringPattern("Enter hotel name:", "^[a-zA-Z\\s]+$");
            hotel_Room_Available = inputer.inputInt("Enter the number of available rooms:");
            hotel_Address = inputer.inputString("Enter hotel address:");
            hotel_Rating = inputer.rating("Enter hotel rating (1-5):", 1, 5);
            hotel_Phone = inputer.inputStringPattern("Enter phone number(0xxxxxxxxx):", "0\\d{9}");
            arrHotel.add(new HotelInformation(hotel_Id, hotel_Name, hotel_Room_Available, hotel_Address, hotel_Phone, hotel_Rating));
            DF.saveData(arrHotel, pathName, "Hotel information has been saved.");
            choice = inputer.inputYN("Do you want to continue(Y/N): ");

        }
    }

    @Override
    public void checkExitsHotel() {

        boolean choice = true;
        while (choice) {
            String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
            DF.loadData(arrHotel, pathName);
            HotelInformation CE = sd.searchHotelById((ArrayList<HotelInformation>) arrHotel, hotel_Id);
            if (CE != null) {
                //Filter data using Stream API
                System.out.println("Exist Hotel");
                List<HotelInformation> temp = arrHotel.stream() //convert original list to stream
                        .filter(hotel -> hotel.getHotel_Id().equalsIgnoreCase(hotel_Id))
                        .collect(Collectors.toList()); //collects the elements of the stream into a new list
                display();
                for (HotelInformation hotel : temp) {
                    System.out.println(hotel); //print list
                }
            } else {
                System.out.println("No Hotel Found!");
            }
            choice = inputer.inputYN("Do you want to continue(Y/N): ");
        }
    }

    @Override
    public void updateHotelInfor() {
        String hotel_Name;
        int hotel_Room_Available;
        String hotel_Address;
        String hotel_Phone;
        int hotel_Rating;
        String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
        DF.loadData(arrHotel, pathName);
        HotelInformation CE = sd.searchHotelById((ArrayList<HotelInformation>) arrHotel, hotel_Id);
        if (CE != null) {
            hotel_Name = inputer.inputStringPatternBlank("Enter new hotel name:", "^[a-zA-Z\\s]+$");
            hotel_Room_Available = inputer.inputInt("Enter new number of available rooms:");
            hotel_Address = inputer.inputStringCanBlank("Enter new hotel address:");
            hotel_Rating = inputer.rating("Enter new hotel rating (1-5):", 1, 5);
            hotel_Phone = inputer.inputStringPatternBlank("Enter new phone number(0xxxxxxxxx):", "0\\d{9}");
            if (!hotel_Name.isEmpty()) {
                CE.setHotel_Name(hotel_Name);
            }
            if (hotel_Room_Available != 0) {
                CE.setHotel_Room_Available(hotel_Room_Available);
            }
            if (!hotel_Address.isEmpty()) {
                CE.setHotel_Address(hotel_Address);
            }
            if (hotel_Rating != 0) {
                CE.setHotel_Rating(hotel_Rating);
            }
            if (!hotel_Phone.isEmpty()) {
                CE.setHotel_Phone(hotel_Phone);
            }
            DF.saveData(arrHotel, pathName, "Hotel information updated successfully.");

            displayHotel();

        } else {
            System.out.println("Hotel does not exist");
        }
    }

    @Override
    public void deleteHotel() {
        String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
        HotelInformation CE = sd.searchHotelById((ArrayList<HotelInformation>) arrHotel, hotel_Id);
        if (CE != null) {
            System.out.println("Do you ready want to delete this hotel ?");
            if (inputer.inputYN("(Choose Y or N):") == true) {
                arrHotel.remove(CE); //remove the list that matches the entered ID
                DF.saveData(arrHotel, pathName, "Success.");
            } else {
                System.out.println("Fail.");
            }
        } else {
            System.out.println("No hotel with ID containing \"" + hotel_Id + "\" found.");
        }

    }

    @Override
    public void checkHotelById() {
        String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
        HotelInformation CE = sd.searchHotelById((ArrayList<HotelInformation>) arrHotel, hotel_Id);
        if (CE != null) {
            //Filter data using Stream API
            List<HotelInformation> temp = arrHotel.stream() //convert original list to stream
                    .filter(hotel -> hotel.getHotel_Id().contains(hotel_Id))
                    .collect(Collectors.toList()); //collects the elements of the stream into a new list
            if (!temp.isEmpty()) {
                Collections.sort(temp, Comparator.comparing(HotelInformation::getHotel_Id).reversed());
            }
            display();
            for (HotelInformation hotel : temp) {
                System.out.println(hotel);
            }
        } else {
            System.out.println("No hotel with ID containing \"" + hotel_Id + "\" found.");
        }
    }

    @Override
    public void checkHotelbyName() {
        String hotel_Name = inputer.inputStringPattern("Enter hotel name:", "^[a-zA-Z\\s]+$");
        HotelInformation CE = sd.searchHotelByName((ArrayList<HotelInformation>) arrHotel, hotel_Name);
        if (CE != null) {
            //Filter data using Stream API
            List<HotelInformation> temp = arrHotel.stream() //convert original list to stream
                    .filter(hotel -> hotel.getHotel_Name().contains(hotel_Name))
                    .collect(Collectors.toList()); //collects the elements of the stream into a new list
            if (!temp.isEmpty()) {
                Collections.sort(temp, Comparator.comparing(HotelInformation::getHotel_Name));
            }
            display();
            for (HotelInformation hotel : temp) {
                System.out.println(hotel);
            }
        } else {
            System.out.println("No hotel with name containing \"" + hotel_Name + "\" found.");
        }
    }

    @Override
    public void displayHotel() {
        display();
        //display data in file with descending by Hotel_Name
        if (!arrHotel.isEmpty()) {
            Collections.sort(arrHotel, Comparator.comparing(HotelInformation::getHotel_Name).reversed());
            for (HotelInformation hotel : arrHotel) {
                System.out.println(hotel);
            }
            System.out.println("Hotel information displayed.");
        } else {
            System.out.println("No hotel information available.");
        }
    }

    private void display() {
        System.out.printf("|%9s|%17s|%25s|%80s|%20s|%20s|\n\n", "Hotel_ID", "Hotel_Name", "Hotel_Room_Available", "Hotel_Address", "Hotel_Phone", "Hotel_Rating");
    }

}
