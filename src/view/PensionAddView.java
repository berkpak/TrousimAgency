package view;

import business.HotelManager;
import business.HotelPensionManager;
import core.ComboItem;
import core.Helper;
import entity.Hotel;
import entity.HotelPension;

import javax.swing.*;

public class PensionAddView extends Layout{
    private JPanel container;
    private JComboBox cmb_pension_type;
    private JButton btn_pension_save;
    private JComboBox cmb_hotel;

    //-----
    private HotelPension hotelPension;
    private HotelManager hotelManager;
    private HotelPensionManager hotelPensionManager;

    public PensionAddView(){
        this.hotelPension = new HotelPension();
        this.hotelManager= new HotelManager();
        this.hotelPensionManager = new HotelPensionManager();
        this.add(container);
        this.guiInitilaze(300, 300);

        //enumdaki verileri cmbbox a at
        this.cmb_pension_type.setModel(new DefaultComboBoxModel<>(HotelPension.Type.values()));

        for(Hotel hotel: this.hotelManager.findAll()){
            this.cmb_hotel.addItem(hotel.getName());
        }

        //Kayit butonu
        btn_pension_save.addActionListener(e ->{

            boolean result = false;

            this.hotelPension.setPension_type((String) cmb_pension_type.getSelectedItem().toString());
            //ComboItem selectedHotel = (ComboItem) cmb_hotel.getSelectedItem();

            String selectedHotelName = (String) cmb_hotel.getSelectedItem();

            Hotel selectedHotel = hotelManager.findHotelByName(selectedHotelName);

            this.hotelPension.setHotel_id(selectedHotel.getId());

            if(this.hotelPension.getId() == 0){
                result = this.hotelPensionManager.save(this.hotelPension);
            }
            if(result){
                Helper.showMsg("done");
                dispose();
            }else{
                Helper.showMsg("error");
            }
        });
    }
}
