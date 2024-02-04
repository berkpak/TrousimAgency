package business;

import core.Helper;
import dao.UserDao;

import entity.Hotel;
import entity.User;

import java.util.ArrayList;

public class UserManager {

    private final UserDao userDao;

    public UserManager() {
        this.userDao = new UserDao();
    }

    public User findByLogin(String username, String password){
        //farkli islem yapilabilir
        return this.userDao.findByLogin(username,password);
    }

    public ArrayList<User> findAll(){
        return this.userDao.findAll();
    }

    public ArrayList<Object[]> getForTable(int size){
        ArrayList<Object[]> userRowList = new ArrayList<>();

        for(User user: this.findAll()){
            Object[] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = user.getId();
            rowObject[i++] = user.getUsername();
            rowObject[i++] = user.getPassword();
            rowObject[i++] = user.getRole();
            userRowList.add(rowObject);
        }
        return userRowList;
    }

    public boolean save(User user){
        if(user.getId() != 0){
            Helper.showMsg("error");
            return false;
        }
        return this.userDao.save(user);
    }

    public User getById(int id){
        return this.userDao.getById(id);
    }

    public boolean update(User user){
        if(this.getById(user.getId()) == null){
            Helper.showMsg("notFound");
            return false;
        }
        return this.userDao.update(user);
    }

    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg(id + "Kullanici bulunamadi");
            return false;
        }
        return this.userDao.delete(id);
    }

   /* public ArrayList<User> SearchUserTable(User.Role role){
        String select = "SELECT * FROM public.user";

        ArrayList<String> whereList = new ArrayList<>();

        if(role != null){
            whereList.add("user_role = '" + role.toString() + "'");
        }
        String whereStr = String.join("AND", whereList);
        String query = select;
        if (whereStr.length() > 0) {
            query += " WHERE " + whereStr;
        }
        return this.userDao.se */

}