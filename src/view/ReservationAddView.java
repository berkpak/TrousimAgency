package view;

import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import entity.Hotel;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ReservationAddView extends Layout {
    private JPanel container;
    private JLabel lbl_hotel_info;
    private JTextField fld_guest_name;
    private JTextField fld_guest_id;
    private JTextField fld_mail;
    private JTextField fld_phone;
    private JButton btn_save_reservation;

    private Room room;
    private Hotel hotel;
    private Reservation reservation;
    private RoomManager roomManager;
    private ReservationManager reservationManager;

    public ReservationAddView(Room selectedRoom, String startDate, String finishDate, JTextField adult, JTextField child) {
        this.room = selectedRoom;
        this.add(container);
        this.guiInitilaze(300, 450);
        this.roomManager = new RoomManager();
        this.reservationManager = new ReservationManager();
        this.hotel = selectedRoom.getHotel();


        //Secilen otel bilgileri
        this.lbl_hotel_info.setText("Otel :" +
                this.hotel.getName() + " / " +
                this.hotel.getAdress() + " / " +
                this.hotel.getStar()
        );

        //Gun sayisi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate enterDate = LocalDate.parse(startDate, formatter);
        LocalDate exitDate = LocalDate.parse(finishDate, formatter);
        long days = ChronoUnit.DAYS.between(enterDate, exitDate);

        int adultNumber = Integer.parseInt(adult.getText());
        int childNumber = Integer.parseInt(child.getText());


        //Rezervasyon kayit butonu
        this.btn_save_reservation.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_guest_name,
                    this.fld_guest_id,
                    this.fld_mail,
                    this.fld_phone
            };
            if (Helper.isFiledListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
            } else {
                Reservation reservation = new Reservation();
                reservation.setGuestCount(guestCount(adultNumber, childNumber));
                reservation.setRoomId(this.room.getId());
                reservation.setGuestName(this.fld_guest_name.getText());
                reservation.setGuestCitizenID(this.fld_guest_id.getText());
                reservation.setGuestMail(this.fld_mail.getText());
                reservation.setGuestPhone(this.fld_phone.getText());
                reservation.setCheckInDate(LocalDate.parse(startDate, formatter));
                reservation.setCheckOutDate(LocalDate.parse(finishDate, formatter));
                reservation.setTotalPrice(totalPrice(adultNumber, childNumber, days));


                if (reservationManager.save(reservation)) {
                    Helper.showMsg("done");

                    int stock = room.getStock() - 1;
                    room.setStock(stock);
                    this.roomManager.updateRoomStock(room);
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    public double totalPrice(int adult, int child, long days) {
        double v = ((this.room.getAdultPrice() * adult) + (this.room.getChildPrice() * child)) * days;
        return v;
    }

    public int guestCount(int adult, int child) {
        return adult + child;
    }
}
