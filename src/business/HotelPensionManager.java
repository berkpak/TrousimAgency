package business;

import core.Helper;
import dao.HotelPensionDao;
import entity.Hotel;
import entity.HotelPension;
import entity.User;

import java.util.ArrayList;

public class HotelPensionManager {

    private final HotelPensionDao hotelPensionDao;

    public HotelPension getById(int id){
        return  this.hotelPensionDao.getByID(id);
    }

    public HotelPensionManager() {
        this.hotelPensionDao = new HotelPensionDao();
    }

    public ArrayList<Object[]> getForTable(int size,ArrayList<HotelPension> pensionList){
        ArrayList<Object[]> hotelRowList = new ArrayList<>();

        for(HotelPension obj : pensionList){
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel_id();
            //rowObject[i++] = obj.getHotel().getId();
            //rowObject[i++] = obj.getHotel().getName();
            rowObject[i++] = obj.getPension_type();
            hotelRowList.add(rowObject);
        }
        return hotelRowList;
    }

    public ArrayList<HotelPension> findAll(){
        return this.hotelPensionDao.findAll();
    }

    public boolean save(HotelPension hotelPension){
        if(this.getById(hotelPension.getId()) != null){
            Helper.showMsg("error");
            return false;
        }
        return this.hotelPensionDao.save(hotelPension);
    }
}

