package view;

import business.HotelManager;
import core.Helper;
import entity.Hotel;
import entity.User;

import javax.swing.*;

public class HotelAddView extends Layout{
    private JPanel container;
    private JPanel pnl_hotel_add_header;
    private JTextField fld_hotel_add_name;
    private JTextField fld_hotel_add_mail;
    private JTextField fld_hotel_add_phone;
    private JTextField fld_hotel_add_adress;
    private JRadioButton rbtn_car_park;
    private JRadioButton rbtn_wifi;
    private JRadioButton rbtn_pool;
    private JRadioButton rbtn_fitness;
    private JRadioButton rbtn_concierge;
    private JRadioButton rbtn_spa;
    private JRadioButton rbtn_room_service;
    private JButton btn_hotel_save;
    private JTextField fld_hotel_add_star;

    private Hotel hotel;
    private HotelManager hotelManager;

    //giris yapabilmek icin simdilik admin uzerinden
   // private User user; public HotelAddView(User user)

    public HotelAddView(){
        this.hotel = new Hotel();
        this.hotelManager = new HotelManager();
        this.add(container);

        this.guiInitilaze(500,400);
       // this.user = user;

        //otel kaydet butonu
        btn_hotel_save.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_hotel_add_name,this.fld_hotel_add_mail,this.fld_hotel_add_phone,this.fld_hotel_add_adress,this.fld_hotel_add_star};

            if(Helper.isFiledListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            }else{
                boolean result;
                this.hotel.setName(this.fld_hotel_add_name.getText());
                this.hotel.setMail(this.fld_hotel_add_mail.getText());
                this.hotel.setPhone(this.fld_hotel_add_phone.getText());
                this.hotel.setAdress(this.fld_hotel_add_adress.getText());
                this.hotel.setStar(this.fld_hotel_add_star.getText());
                this.hotel.setCarPark(this.rbtn_car_park.isSelected());
                this.hotel.setWifi(this.rbtn_wifi.isSelected());
                this.hotel.setPool(this.rbtn_pool.isSelected());
                this.hotel.setFitness(this.rbtn_fitness.isSelected());
                this.hotel.setConcierge(this.rbtn_concierge.isSelected());
                this.hotel.setSpa(this.rbtn_spa.isSelected());
                this.hotel.setRoomService(this.rbtn_room_service.isSelected());
                result = this.hotelManager.save(this.hotel);
                if(result){
                    Helper.showMsg("done");
                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }
        });
    }
}
