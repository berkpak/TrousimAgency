package business;

import core.Helper;
import dao.ReservationDao;
import entity.HotelSeason;
import entity.Reservation;
import entity.User;

import java.util.ArrayList;

public class ReservationManager {

    private final ReservationDao reservationDao;

    public ReservationManager() {
        this.reservationDao = new ReservationDao();
    }

    public ArrayList<Reservation> findAll(){
        return this.reservationDao.findAll();
    }
    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> reservationRowList = new ArrayList<>();

        for(Reservation reservation: this.findAll()){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = reservation.getId();
            rowObject[i++] = reservation.getRoomId();
            rowObject[i++] = reservation.getCheckInDate();
            rowObject[i++] = reservation.getCheckOutDate();
            rowObject[i++] = reservation.getTotalPrice();
            rowObject[i++] = reservation.getGuestCount();
            rowObject[i++] = reservation.getGuestName();
            rowObject[i++] = reservation.getGuestCitizenID();
            rowObject[i++] = reservation.getGuestMail();
            rowObject[i++] = reservation.getGuestPhone();
            reservationRowList.add(rowObject);
        }
        return reservationRowList;
    }

    public Reservation getById(int id){
        return  this.reservationDao.getByID(id);
    }

    public boolean save(Reservation reservation){
        if(this.getById(reservation.getId()) != null){
            Helper.showMsg("error");
            return false;
        }
        return this.reservationDao.save(reservation);
    }

    public boolean update(Reservation reservation){
        if(this.getById(reservation.getId()) == null){
            Helper.showMsg("notFound");
            return false;
        }
        return this.reservationDao.update(reservation);
    }



    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg(id + "Kullanici bulunamadi");
            return false;
        }
        return this.reservationDao.delete(id);
    }

}
