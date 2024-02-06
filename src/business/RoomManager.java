package business;

import core.Db;
import core.Helper;
import dao.HotelSeasonDao;
import dao.RoomDao;
import entity.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RoomManager {

    private final RoomDao roomDao;
    private final HotelSeasonDao hotelSeasonDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
        this.hotelSeasonDao = new HotelSeasonDao();
    }

    public ArrayList<Object[]> getForTable(int size,ArrayList<Room> rooms){
        ArrayList<Object[]> roomRowList = new ArrayList<>();

        for(Room room: rooms){
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = room.getId();
            //rowObject[i++] = room.getHotel_id();
            rowObject[i++] = room.getHotel().getName();
            rowObject[i++] = room.getHotelPension().getPension_type();
            rowObject[i++] = room.getType();
            rowObject[i++] = room.getStock();
            rowObject[i++] = room.getAdultPrice();
            rowObject[i++] = room.getChildPrice();
            rowObject[i++] = room.getBed();
            rowObject[i++] = room.getSquare_meter();
            rowObject[i++] = room.isTv();
            rowObject[i++] = room.isMinibar();
            rowObject[i++] = room.isGameConsole();
            rowObject[i++] = room.isCashBox();
            rowObject[i++] = room.isProjection();
            roomRowList.add(rowObject);
        }
        return roomRowList;
    }

    public Room getById(int id){
        return this.roomDao.getById(id);
    }

    public ArrayList<Room> findAll(){
        return this.roomDao.findAll();
    }

    public boolean save(Room room){
        if(room.getId() != 0){
            Helper.showMsg("error");
            return false;
        }
        return this.roomDao.save(room);
    }

    public ArrayList<Room> searchForTable(String hotelName, String hotelAdress, String strt_date, String fnsh_date, String adult , String child){

        strt_date = LocalDate.parse(strt_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
        fnsh_date = LocalDate.parse(fnsh_date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

        // Otelleri ve otel sezonlarını içeren sorgu
        String query = "SELECT * FROM public.room r " +
                "LEFT JOIN public.hotel h ON r.hotel_id = h.id " +
                "LEFT JOIN public.hotel_season hs ON h.id = hs.hotel_id ";

        ArrayList<String> whereList = new ArrayList<>();

        // Odaların stok durumu kontrolü
        whereList.add("r.stock > 0");

        // Otellerin adı ve adresi filtreleme
        if(hotelName != null){
            whereList.add("h.name ILIKE '%" + hotelName + "%'");
        }
        if(hotelAdress != null){
            whereList.add("h.address ILIKE '%" + hotelAdress + "%'");
        }
        // Otellerin sezonlarına göre tarih filtreleme
        if(strt_date != null && fnsh_date != null){
            whereList.add("hs.start_date <= '" + strt_date + "' AND hs.finish_date >= '" + fnsh_date + "'");
        }
        // WHERE koşullarını birleştirme
            query += " WHERE " + String.join(" AND ", whereList);

        // Sonuçları döndürme
        return this.roomDao.selectByQuery(query);
    }
    public boolean updateRoomStock(Room room){
        if(this.getById(room.getId()) == null){
            Helper.showMsg("notFound");
            return false;
        }
        return this.roomDao.updateRoomStock(room);
    }

}
