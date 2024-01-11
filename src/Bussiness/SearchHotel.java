/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import java.util.ArrayList;

/**
 *
 * @author Duy.Tran
 */
public class SearchHotel {
    public HotelInformation searchHotelById(ArrayList<HotelInformation> arr, String id) {
        for(HotelInformation hotelInfor : arr) {
            if(id.equals(hotelInfor.getHotel_Id())) {
                return hotelInfor;
            }
        }
        return null;
    }
}
