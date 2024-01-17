/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import java.util.ArrayList;

/**
 * Class searches for hotel information based on ID or Name in the list. Support
 * for Service class.
 *
 * @author Duy.Tran
 */
public class SearchHotel {

    /**
     * Searches for a hotel by its ID in the list.
     *
     * @param arr The ArrayList of HotelInformation to search.
     * @param id The hotel ID to search for.
     * @return The HotelInformation object if found, otherwise null.
     */
    public HotelInformation searchHotelById(ArrayList<HotelInformation> arr, String id) {
        for (HotelInformation hotelInfor : arr) {
            if (hotelInfor.getHotel_Id().equalsIgnoreCase(id)) {
                return hotelInfor;
            }
        }
        return null;
    }

    /**
     * Searches for a hotel by its name in the list.
     *
     * @param arr The ArrayList of HotelInformation to search.
     * @param name The hotel name to search for.
     * @return The HotelInformation object if found, otherwise null.
     */
    public HotelInformation searchHotelByName(ArrayList<HotelInformation> arr, String name) {
        for (HotelInformation hotelInfor : arr) {
            if (hotelInfor.getHotel_Name().equalsIgnoreCase(name)) {
                return hotelInfor;
            }
        }

        return null;
    }

}
