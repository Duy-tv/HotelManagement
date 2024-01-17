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
 * This class represents the service layer for managing hotel information. It
 * provides methods for adding, updating, deleting, and searching hotel data.
 *
 * @author Duy.Tran
 */
public class Service implements IService {

    private final List<HotelInformation> arrHotel = new ArrayList<>();
    private final DataFile data = new Data.DataFile();
    private final Inputer inputer = new Inputer();
    private SearchHotel searchHotel = new SearchHotel();
    private String pathName;

    /**
     * Constructor for the Service class. Loads hotel data from the specified
     * file, and if the list is empty, prompts the user to add new hotels.
     *
     * @param fileName The file name to load hotel data from.
     */
    public Service(String fileName) {

        this.pathName = fileName;
        try {
            data.loadData(arrHotel, pathName);
        } catch (Exception e) {
            System.out.println("Empty list");
        }
        if (arrHotel.isEmpty()) {
            System.out.println("Empty list, add new:");
            addHotel();
        }
    }

    /**
     * Adds a new hotel to the list based on user input.
     */
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
            hotel_Rating = inputer.rating("Enter hotel rating (0-5):", 0, 5);
            hotel_Phone = inputer.inputStringPattern("Enter phone number(0xxxxxxxxx):", "0\\d{9}");
            arrHotel.add(new HotelInformation(hotel_Id, hotel_Name, hotel_Room_Available, hotel_Address, hotel_Phone,
                    hotel_Rating));
            data.saveData(arrHotel, pathName, "Hotel information has been saved.");
            choice = inputer.inputYN("Do you want to continue(Y/N): ");

        }
    }

    /**
     * Checks and displays information for existing hotels based on user input.
     */
    @Override
    public void checkExitsHotel() {

        boolean choice = true;
        while (choice) {
            String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
            data.loadData(arrHotel, pathName);
            HotelInformation search = searchHotel.searchHotelById((ArrayList<HotelInformation>) arrHotel, hotel_Id);
            if (search != null) {
                // Filter data using Stream API
                System.out.println("Exist Hotel\n");
                List<HotelInformation> temp = arrHotel.stream() // convert original list to stream
                        .filter(hotel -> hotel.getHotel_Id().equalsIgnoreCase(hotel_Id))
                        .collect(Collectors.toList()); // collects the elements of the stream into a new list
                display();
                for (HotelInformation hotel : temp) {
                    System.out.println(hotel); // print list
                }
            } else {
                System.out.println("No Hotel Found!");
            }
            choice = inputer.inputYN("Do you want to continue(Y/N): ");
        }
    }

    /**
     * Updates hotel information based on user input.
     */
    @Override
    public void updateHotelInfor() {
        String hotel_Name;
        String Room_Available;
        String hotel_Address;
        String hotel_Phone;
        String Rating;
        String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
        data.loadData(arrHotel, pathName);
        HotelInformation search = searchHotel.searchHotelById((ArrayList<HotelInformation>) arrHotel, hotel_Id);
        if (search != null) {
            hotel_Name = inputer.inputStringPatternBlank("Enter new hotel name:", "^[a-zA-Z\\s]+$");
            Room_Available = inputer.inputStringCanBlank("Enter new number of available rooms:");
            hotel_Address = inputer.inputStringCanBlank("Enter new hotel address:");
            Rating = inputer.inputStringCanBlank("Enter new hotel rating (0-5):");
            hotel_Phone = inputer.inputStringPatternBlank("Enter new phone number(0xxxxxxxxx):", "0\\d{9}");

            // check name empty
            if (!hotel_Name.isEmpty()) {
                search.setHotel_Name(hotel_Name);
            }

            // check room empty
            if (!Room_Available.isEmpty()) {
                if (Room_Available.matches("\\d+")) {
                    int Hotel_Room_Available = Integer.parseInt(Room_Available);
                    search.setHotel_Room_Available(Hotel_Room_Available);
                }
            }

            // check address empty
            if (!hotel_Address.isEmpty()) {
                search.setHotel_Address(hotel_Address);
            }

            // check rate empty
            if (!Rating.isEmpty()) {
                if (Rating.matches("\\d+")) {
                    if (Rating.matches("^[0-5]$")) {
                        int hotel_Rating = Integer.parseInt(Rating);
                        search.setHotel_Rating(hotel_Rating);
                    }
                }
            }

            // check phone empty
            if (!hotel_Phone.isEmpty()) {
                search.setHotel_Phone(hotel_Phone);
            }

            data.saveData(arrHotel, pathName, "Hotel information updated successfully.\n");
            displayHotel();
        } else {
            System.out.println("Hotel does not exist");
        }
    }

    /**
     * Deletes a hotel based on user input.
     */
    @Override
    public void deleteHotel() {
        String hotel_Id = inputer.inputUpperString("Enter hotel ID:");
        HotelInformation search = searchHotel.searchHotelById((ArrayList<HotelInformation>) arrHotel, hotel_Id);
        if (search != null) {
            System.out.println("Do you ready want to delete this hotel ?");
            if (inputer.inputYN("(Choose Y or N):") == true) {
                arrHotel.remove(search); // remove the list that matches the entered ID
                data.saveData(arrHotel, pathName, "Success.\n");
            } else {
                System.out.println("Fail.\n");
            }
        } else {
            System.out.println("No hotel with ID containing \"" + hotel_Id + "\" found.");
        }

    }

    /**
     * Checks and displays hotel information based on user input (search by ID).
     */
    @Override
    public void checkHotelById() {
        String hotel_Id = inputer.inputUpperString("Enter hotel ID:");

        // Filter data using Stream API
        List<HotelInformation> temp = arrHotel.stream() // convert original list to stream
                .filter(hotel -> hotel.getHotel_Id().contains(hotel_Id))
                .collect(Collectors.toList()); // collects the elements of the stream into a new list
        if (!temp.isEmpty()) {
            Collections.sort(temp, Comparator.comparing(HotelInformation::getHotel_Id).reversed());

            display();
            temp.forEach((hotel) -> {
                System.out.println(hotel);
            });

        } else {
            System.out.println("No hotel with ID containing \"" + hotel_Id + "\" found.");
        }
    }

    /**
     * Checks and displays hotel information based on user input (search by
     * name).
     */
    @Override
    public void checkHotelbyName() {
        String hotel_Name = inputer.inputStringPattern("Enter hotel name:", "^[a-zA-Z\\s]+$");

        // Filter data using Stream API
        List<HotelInformation> temp = arrHotel.stream() // convert original list to stream
                .filter(hotel -> hotel.getHotel_Name().contains(hotel_Name))
                .collect(Collectors.toList()); // collects the elements of the stream into a new list
        if (!temp.isEmpty()) {
            Collections.sort(temp, Comparator.comparing(HotelInformation::getHotel_Name));

            display();
            temp.forEach((hotel) -> {
                System.out.println(hotel);
            });

        } else {
            System.out.println("No hotel with name containing \"" + hotel_Name + "\" found.");
        }
    }

    /**
     * Displays hotel information in a formatted table.
     */
    @Override
    public void displayHotel() {
        display();
        // display data in file with descending by Hotel_Name
        if (!arrHotel.isEmpty()) {
            Collections.sort(arrHotel, Comparator.comparing(HotelInformation::getHotel_Name).reversed());
            arrHotel.forEach((hotel) -> {
                System.out.println(hotel);
            });
            System.out.println("Hotel information displayed.");
        } else {
            System.out.println("No hotel information available.");
        }
    }

    /**
     * Displays the header of the hotel information table. The table includes
     * columns for Hotel_ID, Hotel_Name, Hotel_Room_Available, Hotel_Address,
     * Hotel_Phone, and Hotel_Rating.
     */
    private void display() {
        System.out.printf("|%9s|%17s|%25s|%80s|%20s|%20s|\n\n", "Hotel_ID", "Hotel_Name", "Hotel_Room_Available",
                "Hotel_Address", "Hotel_Phone", "Hotel_Rating");
    }

}
