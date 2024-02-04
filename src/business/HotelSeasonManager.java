package business;

import core.Helper;
import dao.HotelSeasonDao;
import entity.HotelPension;
import entity.HotelSeason;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HotelSeasonManager {

    private final HotelSeasonDao hotelSeasonDao;

    public HotelSeasonManager() {
        this.hotelSeasonDao = new HotelSeasonDao();
    }

    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> hotelRowList = new ArrayList<>();

        for(HotelSeason hotelSeason: this.findAll()){
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = hotelSeason.getId();
            rowObject[i++] = hotelSeason.getHotel_id();
            rowObject[i++] = hotelSeason.getStart_date();
            rowObject[i++] = hotelSeason.getFnsh_date();
            hotelRowList.add(rowObject);
        }
        return hotelRowList;
    }

    public ArrayList<HotelSeason> findAll(){
        return this.hotelSeasonDao.findAll();
    }

    public HotelSeason getById(int id){
        return  this.hotelSeasonDao.getByID(id);
    }

    public boolean save(HotelSeason hotelSeason){
        if(this.getById(hotelSeason.getId()) != null){
            Helper.showMsg("error");
            return false;
        }
        return this.hotelSeasonDao.save(hotelSeason);
    }
}
