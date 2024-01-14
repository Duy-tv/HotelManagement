/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import java.util.ArrayList;

/**
 *Class searches for hotel information based on ID or Name in the list.
 *Support for Service class.
 * @author Duy.Tran
 */
public class SearchHotel {

    public HotelInformation searchHotelById(ArrayList<HotelInformation> arr, String id) {
        for (HotelInformation hotelInfor : arr) {
            if (hotelInfor.getHotel_Id().equalsIgnoreCase(id)) {
                return hotelInfor;
            }
        }
        return null;
    }

    public HotelInformation searchHotelByName(ArrayList<HotelInformation> arr, String name) {
        for (HotelInformation hotelInfor : arr) {
            if (hotelInfor.getHotel_Name().equalsIgnoreCase(name)) {
                return hotelInfor;
            }
        }

        return null;
    }

}
