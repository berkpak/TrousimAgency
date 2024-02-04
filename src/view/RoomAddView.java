package view;

import business.HotelManager;
import business.HotelPensionManager;
import business.HotelSeasonManager;
import business.RoomManager;
import core.ComboItem;
import core.Helper;
import entity.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomAddView extends Layout {
    private JPanel container;
    private JPanel pnl_room_add_header;
    private JComboBox cmb_slct_hotel;
    private JComboBox cmb_slct_pension;
    private JComboBox cmb_slct_season;
    private JComboBox<Room.Type> cmb_slct_room_type;
    private JTextField fld_room_stok;
    private JTextField fld_adult_price;
    private JTextField fld_child_price;
    private JTextField fld_bed;
    private JTextField fld_square_meter;
    private JRadioButton rbtn_tv;
    private JRadioButton rbtn_minibar;
    private JRadioButton rbtn_console;
    private JRadioButton rbtn_case;
    private JRadioButton rbtn_projection;
    private JButton btn_room_save;
    private JPanel pnl_room_right;
    private JPanel pnl_room_left;

    private Room room;
    private RoomManager roomManager;
    private HotelManager hotelManager;

    //DENEME-----
    private HotelPensionManager hotelPensionManager;
    private HotelSeasonManager hotelSeasonManager;
    private HotelPension hotelPension;



    public RoomAddView(){
        this.room = new Room();
        this.roomManager = new RoomManager();
        this.hotelPensionManager = new HotelPensionManager();
        this.hotelSeasonManager = new HotelSeasonManager();
        this.add(container);
        this.guiInitilaze(650,400);

        //cmbbox ta var olan otelleri listeleme findAll ile
        HotelManager hotelManager = new HotelManager();
        ArrayList<Hotel> hotelList = hotelManager.findAll();

        for (Hotel hotel : hotelList) {
            cmb_slct_hotel.addItem(hotel.getComboItem());
        }

        //Deneme------
        cmb_slct_hotel.addActionListener(new ActionListener() {
           /* @Override
            public void actionPerformed(ActionEvent e) {
                // Seçilen otelin nesnesini al
                Hotel selectedHotel = (Hotel) cmb_slct_hotel.getSelectedItem();


                // Seçilen otelin id'sini al
                int selectedHotelId = selectedHotel.getId();

                // cmb_slct_pension'ın içeriğini güncelle, sadece seçilen otelin pansiyonlarını ekleyin
                updatePensionComboBox(selectedHotelId);
            }*/
           @Override
           public void actionPerformed(ActionEvent e) {
               // Seçilen otelin adını al
               ComboItem selectedHotelItem = (ComboItem) cmb_slct_hotel.getSelectedItem();

               // Seçilen otelin id'sini al
               int selectedHotelId = selectedHotelItem.getKey();

               selectPensionComboBox(selectedHotelId);
               selectSeasonComboBox(selectedHotelId);
           }
        });
        //ComboBox a oda tiplerini ekle
        this.cmb_slct_room_type.setModel(new DefaultComboBoxModel<>(Room.Type.values()));


        //Oda kaydet butonu
        btn_room_save.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_room_stok,this.fld_adult_price,this.fld_child_price,this.fld_bed,this.fld_square_meter};

            if(Helper.isFiledListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            }else{
                boolean result;
                ComboItem selectedHotelInfo = (ComboItem) cmb_slct_hotel.getSelectedItem();
                this.room.setHotel_id(selectedHotelInfo.getKey());
                ComboItem selectedPensionInfo = (ComboItem) cmb_slct_pension.getSelectedItem();
                this.room.setPension_id(selectedPensionInfo.getKey());
                ComboItem selectedSeasonInfo = (ComboItem) cmb_slct_season.getSelectedItem();
                this.room.setSeason_id(selectedSeasonInfo.getKey());
                this.room.setType(String.valueOf(this.cmb_slct_room_type.getSelectedItem()));
                this.room.setStock(Integer.parseInt(this.fld_room_stok.getText()));
                this.room.setAdultPrice(Integer.parseInt(this.fld_adult_price.getText()));
                this.room.setChildPrice(Integer.parseInt(this.fld_child_price.getText()));
                this.room.setBed(Integer.parseInt(this.fld_bed.getText()));
                this.room.setSquare_meter(Integer.parseInt(this.fld_square_meter.getText()));
                this.room.setTv(this.rbtn_tv.isSelected());
                this.room.setMinibar(this.rbtn_minibar.isSelected());
                this.room.setGameConsole(this.rbtn_console.isSelected());
                this.room.setCashBox(this.rbtn_case.isSelected());
                this.room.setProjection(this.rbtn_projection.isSelected());
                result = this.roomManager.save(this.room);
                if(result){
                    Helper.showMsg("done");
                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }
        });
    }
    private void selectPensionComboBox(int selectedHotelId) {
        cmb_slct_pension.removeAllItems();
        // Otele ait pansiyonları cmb_slct_pensiona ekle
        for (HotelPension pension : hotelPensionManager.findAll()) {
            if (pension.getHotel_id() == selectedHotelId) {
                cmb_slct_pension.addItem(pension.getComboItem());
            }
        }
    }
    private void selectSeasonComboBox(int selectedHotelId) {
        cmb_slct_season.removeAllItems();
        // Otele ait sezonları cmb_slct_seasona ekle
        for (HotelSeason season : hotelSeasonManager.findAll()) {
            if (season.getHotel_id() == selectedHotelId) {
                // tarih format donusumu
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedStartDate = season.getStart_date().format(formatter);
                String formattedEndDate = season.getFnsh_date().format(formatter);

                cmb_slct_season.addItem(season.getComboItem());
            }
        }
    }
}
