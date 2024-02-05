package business;

import core.Db;
import core.Helper;
import dao.RoomDao;
import entity.Hotel;
import entity.Reservation;
import entity.Room;
import entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RoomManager {

    private final RoomDao roomDao;

    public RoomManager() {
        this.roomDao = new RoomDao();
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



    // ornek query
    //"SELECT * from public.room r\n" +
    //"LEFT JOIN public.hotel h ON r.hotel_id = h.id\n" +
    //"WHERE r.stock > 0 AND h.name = 'patika'";

    /*
    SELECT * from public.room r
    LEFT JOIN public.hotel h ON r.hotel_id = h.id

    WHERE r.stock > 0
    AND h.name ILIKE '%k%'
    AND h.address ILIKE '%l%'
     */


    public ArrayList<Room> searchForTable(String hotelName, String hotelAdress, String strt_date, String fnsh_date, String adult , String child){
        String query = "SELECT * from public.room r LEFT JOIN public.hotel h ON r.hotel_id = h.id ";
        ArrayList<String> whereList = new ArrayList<>();

        whereList.add("WHERE r.stock > 0 ");
        if(hotelName != null){
            whereList.add(" h.name ILIKE '%" + hotelName + "%' " );
        }
        if(hotelAdress!= null){
            whereList.add(" h.address ILIKE '%" + hotelAdress + "%' " );
        }
        query += String.join("AND",whereList);


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
