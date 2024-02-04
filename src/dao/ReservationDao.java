package dao;

import core.Db;
import entity.Hotel;
import entity.HotelSeason;
import entity.Reservation;
import entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReservationDao {

    private final Connection con;

    private HotelDao hotelDao;
    private RoomDao roomDao;

    public ReservationDao() {
        this.con = Db.getInstance();
        this.hotelDao = new HotelDao();
        this.roomDao = new RoomDao();
    }
    public Reservation getByID(int id) {
        Reservation obj = null;
        String query = "SELECT * FROM public.reservation WHERE id = ?";
        try {
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public ArrayList<Reservation> findAll() {
        ArrayList<Reservation> reservationList = new ArrayList<>();
        String sql = "SELECT * FROM public.reservation ORDER BY id ASC";
        try {
            ResultSet rs = this.con.createStatement().executeQuery(sql);
            while (rs.next()) {
                reservationList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public Reservation match(ResultSet rs) throws SQLException {
        Reservation obj = new Reservation();
        obj.setId(rs.getInt("id"));
        obj.setRoomId(rs.getInt("room_id"));
        obj.setRoom(this.roomDao.getById(rs.getInt("room_id")));
        obj.setCheckInDate(LocalDate.parse(rs.getString("check_in_date")));
        obj.setCheckOutDate(LocalDate.parse(rs.getString("check_out_date")));
        obj.setTotalPrice(rs.getDouble("total_price"));
        obj.setGuestCount(rs.getInt("guest_count"));
        obj.setGuestName(rs.getString("guest_name"));
        obj.setGuestCitizenID(rs.getString("guest_citizen_id"));
        obj.setGuestMail(rs.getString("guest_mail"));
        obj.setGuestPhone(rs.getString("guest_phone"));
        return obj;
    }

    public boolean save(Reservation reservation) {
        String query = "INSERT INTO public.reservation " +
                "(" +
                "room_id ," +
                "check_in_date ," +
                "check_out_date ," +
                "total_price ," +
                "guest_count ," +
                "guest_name ," +
                "guest_citizen_id ," +
                "guest_mail ," +
                "guest_phone " +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,reservation.getRoomId());
            pr.setDate(2, Date.valueOf(reservation.getCheckInDate()));
            pr.setDate(3, Date.valueOf(reservation.getCheckOutDate()));
            pr.setDouble(4,reservation.getTotalPrice());
            pr.setInt(5, reservation.getGuestCount());
            pr.setString(6,reservation.getGuestName());
            pr.setString(7,reservation.getGuestCitizenID());
            pr.setString(8,reservation.getGuestMail());
            pr.setString(9,reservation.getGuestPhone());
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Reservation reservation) {
        String query = "UPDATE public.reservation SET " +
                "guest_name = ? ," +
                "guest_citizen_id = ? ," +
                "guest_mail = ? ," +
                "guest_phone = ? " +
                "WHERE id = ?";

        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1,reservation.getGuestName());
            pr.setString(2,reservation.getGuestCitizenID());
            pr.setString(3,reservation.getGuestMail());
            pr.setString(4,reservation.getGuestPhone());
            pr.setInt(5,reservation.getId());
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int userId) {
        String query = "DELETE FROM public.reservation WHERE id = ? ";
        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setInt(1,userId);
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
