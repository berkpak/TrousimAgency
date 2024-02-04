package view;

import business.HotelSeasonManager;
import core.Helper;
import entity.Hotel;
import entity.HotelSeason;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SeasonAddView extends Layout{
    private JPanel container;
    private JLabel lbl_hotel_info;
    private JFormattedTextField fld_strt_date;
    private JFormattedTextField fld_fnsh_date;
    private JButton btn_season_save;

    //------
    private Hotel hotel;
    private HotelSeason hotelSeason;

    private HotelSeasonManager hotelSeasonManager;

    private int id;

    public SeasonAddView(Hotel selectedHotel){
        this.hotel = selectedHotel;
        this.add(container);
        this.guiInitilaze(300,400);
        this.hotelSeasonManager = new HotelSeasonManager();
        this.hotelSeason = new HotelSeason();



        // Secili otel bilgilerini cekmek icin
        this.lbl_hotel_info.setText("Otel :" +
                this.hotel.getId() + " / " +
                this.hotel.getName());



        btn_season_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_strt_date,
                    this.fld_fnsh_date
            };
            if(Helper.isFiledListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            }else{
                hotelSeason.setStart_date((LocalDate.parse(fld_strt_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                hotelSeason.setFnsh_date((LocalDate.parse(fld_fnsh_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))));

                //otel id yi setleme
                hotelSeason.setHotel_id(hotel.getId());
            }

            if(this.hotelSeasonManager.save(hotelSeason)){
                Helper.showMsg("done");
                dispose();
            }else{
                Helper.showMsg("error");
            }

        });
    }
    public SeasonAddView(){
    }

    //Custom create date icin
    private void createUIComponents() throws ParseException {
        this.fld_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        //this.fld_strt_date.setText("01/01/2024");
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
       //this.fld_fnsh_date.setText("01/01/2024");
    }
}
