package dao;

import com.sun.source.tree.BreakTree;
import core.Db;
import entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao {
    private final Connection con;

    public UserDao() {
        this.con = Db.getInstance();
    }

    public ArrayList<User> findAll() {
        return this.selectByQuery("SELECT * FROM public.user ORDER BY user_id ASC");
    }

    public ArrayList<User> selectByQuery(String query){
        ArrayList<User> userList = new ArrayList<>();
        try{
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next()){
                userList.add(this.match(rs));
            }
        }catch(SQLException throwable){
            throwable.printStackTrace();
        }
        return userList;
    }

    public User findByLogin(String username, String password) {
        User obj = null;
        String query = "SELECT * FROM public.user WHERE user_name = ? AND user_pass = ?";
        try {
            PreparedStatement pr = this.con.prepareStatement(query);
            pr.setString(1, username);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public User getById(int id){
      User obj = null;
      String query = "SELECT * FROM public.user WHERE user_id = ?";
      try{
          PreparedStatement pr =this.con.prepareStatement(query);
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

    public ArrayList<User> findByRole(String userSearchRole) {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM public.user WHERE user_role = " + "'" + userSearchRole + "'";
        try{
            ResultSet rs = this.con.createStatement().executeQuery(query);
            while(rs.next()){
                userList.add(this.match(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userList;
    }

    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("user_id"));
        obj.setUsername(rs.getString("user_name"));
        obj.setPassword(rs.getString("user_pass"));
        obj.setRole(rs.getString("user_role"));

        return obj;
    }


    public boolean save(User user) {
        String query = "INSERT INTO public.user " +
                "(" +
                "user_name ," +
                "user_pass ," +
                "user_role" +
                ")" +
                "VALUES (?,?,?)";
        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1,user.getUsername());
            pr.setString(2,user.getPassword());
            pr.setString(3,user.getRole());
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(User user) {
        String query = "UPDATE public.user SET " +
                "user_name = ? ," +
                "user_pass = ? ," +
                "user_role = ? " +
                "WHERE user_id = ?";

        try{
            PreparedStatement pr = con.prepareStatement(query);
            pr.setString(1,user.getUsername());
            pr.setString(2,user.getPassword());
            pr.setString(3,user.getRole());
            pr.setInt(4,user.getId());
            return pr.executeUpdate() != -1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(int userId) {
        String query = "DELETE FROM public.user WHERE user_id = ? ";
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
