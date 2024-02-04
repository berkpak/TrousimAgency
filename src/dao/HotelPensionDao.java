package dao;

import core.Db;
import entity.Hotel;
import entity.HotelPension;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HotelPensionDao {

    private Connection con;

    private final HotelDao hotelDao = new HotelDao();

    public HotelPensionDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<HotelPension> findAll() {
        ArrayList<HotelPension> hotelPensionList = new ArrayList<>();
        String sql = "SELECT * FROM public.hotel_pension";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()) {
                hotelPensionList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelPensionList;
    }


    public HotelPension getByID(int id) {
        HotelPension obj = null;
        String query = "SELECT * FROM public.hotel_pension WHERE id = ?";
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

    public HotelPension match(ResultSet rs) throws SQLException {
        HotelPension obj = new HotelPension();
        obj.setId(rs.getInt("id"));
        obj.setHotel(this.hotelDao.getById(rs.getInt("hotel_id")));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setPension_type(rs.getString("pension_type"));
        return obj;
    }

    public boolean save(HotelPension hotelPension) {
        String query = "INSERT INTO public.hotel_pension " +
                "(" +
                "hotel_id ," +
                "pension_type " +
                ")" +
                "VALUES (?,?)";
        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,hotelPension.getHotel_id());
            pr.setString(2,hotelPension.getPension_type());
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
