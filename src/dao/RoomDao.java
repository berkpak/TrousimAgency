package dao;

import core.Db;
import entity.Hotel;
import entity.HotelPension;
import entity.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomDao {

    private final Connection con;

    private HotelDao hotelDao;
    private HotelPensionDao hotelPensionDao;

    public RoomDao() {
        this.con = Db.getInstance();
        this.hotelDao = new HotelDao();
        this.hotelPensionDao = new HotelPensionDao();
    }

    public Room getById(int id){
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE id = ?";
        try{
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                obj = this.match(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<Room> findAll() {
        return this.selectByQuery("SELECT * FROM public.room");
    }
    public ArrayList<Room> selectByQuery(String query){
        ArrayList<Room> modelList = new ArrayList<>();
        try{
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next()){
                modelList.add(this.match(rs));
            }
        }catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return modelList;
    }
    public Room match(ResultSet rs) throws SQLException {
        Room obj = new Room();
        obj.setId(rs.getInt("id"));
        obj.setHotel(this.hotelDao.getById(rs.getInt("hotel_id")));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setHotelPension(this.hotelPensionDao.getByID(rs.getInt("pension_id")));
        obj.setPension_id(rs.getInt("pension_id"));
        obj.setSeason_id(rs.getInt("season_id"));
        obj.setType(rs.getString("type"));
        obj.setStock(rs.getInt("stock"));
        obj.setAdultPrice(rs.getDouble("adult_price"));
        obj.setChildPrice(rs.getDouble("child_price"));
        obj.setBed(rs.getInt("bed_capacity"));
        obj.setSquare_meter(rs.getInt("square_meter"));
        obj.setTv(rs.getBoolean("television"));
        obj.setMinibar(rs.getBoolean("minibar"));
        obj.setGameConsole(rs.getBoolean("game_console"));
        obj.setCashBox(rs.getBoolean("cash_box"));
        obj.setProjection(rs.getBoolean("projection"));
        return obj;
    }

    public boolean save(Room room) {
        String query = "INSERT INTO public.room " +
                "(" +
                "hotel_id ," +
                "pension_id ," +
                "season_id ," +
                "type ," +
                "stock ," +
                "adult_price ," +
                "child_price ," +
                "bed_capacity ," +
                "square_meter ," +
                "television ," +
                "minibar ," +
                "game_console ," +
                "cash_box ," +
                "projection" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,room.getHotel_id());
            pr.setInt(2,room.getPension_id());
            pr.setInt(3,room.getSeason_id());
            pr.setString(4,room.getType());
            pr.setInt(5,room.getStock());
            pr.setDouble(6,room.getAdultPrice());
            pr.setDouble(7,room.getChildPrice());
            pr.setInt(8,room.getBed());
            pr.setInt(9,room.getSquare_meter());
            pr.setBoolean(10,room.isTv());
            pr.setBoolean(11,room.isMinibar());
            pr.setBoolean(12,room.isGameConsole());
            pr.setBoolean(13,room.isCashBox());
            pr.setBoolean(14,room.isProjection());
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean updateRoomStock (Room room){
        String query = "UPDATE room SET stock = ? WHERE id = ?";
        try {
            PreparedStatement pr = Db.getInstance().prepareStatement(query);
            pr.setInt(1,room.getStock());
            pr.setInt(2,room.getId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


}
