package dao;

import core.Db;
import entity.HotelPension;
import entity.HotelSeason;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HotelSeasonDao {

    private Connection con;

    public HotelSeasonDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<HotelSeason> findAll() {
        return  this.selectByQuery("SELECT * FROM public.hotel_season");
    }
    public ArrayList<HotelSeason> selectByQuery(String query){
        ArrayList<HotelSeason> hotelSeasons = new ArrayList<>();
        try{
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next()){
                hotelSeasons.add(this.match(rs));
            }
        }catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return hotelSeasons;
    }

    public HotelSeason match(ResultSet rs) throws SQLException {
        HotelSeason obj = new HotelSeason();
        obj.setId(rs.getInt("id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setStart_date(LocalDate.parse(rs.getString("start_date")));
        obj.setFnsh_date(LocalDate.parse(rs.getString("finish_date")));
        return obj;
    }

    public HotelSeason getByID(int id) {
        HotelSeason obj = null;
        String query = "SELECT * FROM public.hotel_season WHERE id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public boolean save(HotelSeason hotelSeason) {
        String query = "INSERT INTO public.hotel_season " +
                "(" +
                "hotel_id ," +
                "start_date ," +
                "finish_date " +
                ")" +
                "VALUES (?,?,?)";
        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,hotelSeason.getHotel_id());
            pr.setDate(2, Date.valueOf(hotelSeason.getStart_date()));
            pr.setDate(3, Date.valueOf(hotelSeason.getFnsh_date()));
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
