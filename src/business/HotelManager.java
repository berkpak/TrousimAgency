package business;

import core.Helper;
import dao.HotelDao;
import entity.Hotel;
import entity.User;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDao hotelDao;

    public HotelManager() {
        this.hotelDao = new HotelDao();
    }

    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> hotelRowList = new ArrayList<>();

        for(Hotel hotel: this.findAll()){
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = hotel.getId();
            rowObject[i++] = hotel.getName();
            rowObject[i++] = hotel.getAdress();
            rowObject[i++] = hotel.getMail();
            rowObject[i++] = hotel.getPhone();
            rowObject[i++] = hotel.getStar();
            rowObject[i++] = hotel.isCarPark();
            rowObject[i++] = hotel.isWifi();
            rowObject[i++] = hotel.isPool();
            rowObject[i++] = hotel.isFitness();
            rowObject[i++] = hotel.isConcierge();
            rowObject[i++] = hotel.isSpa();
            rowObject[i++] = hotel.isRoomService();
            hotelRowList.add(rowObject);
        }
        return hotelRowList;
    }

    public ArrayList<Hotel> findAll(){
        return this.hotelDao.findAll();
    }

    public Hotel findHotelByName(String name) {
        for (Hotel hotel : this.findAll()) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        return null; // Otel bulunamazsa null döndür
    }

    public boolean save(Hotel hotel){
        if(hotel.getId() != 0){
            Helper.showMsg("error");
            return false;
        }
        return this.hotelDao.save(hotel);
    }

    public Hotel getbyId(int id){
        return this.hotelDao.getById(id);
    }
}
