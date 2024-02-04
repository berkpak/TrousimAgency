package view;

import business.ReservationManager;
import core.Helper;
import entity.Reservation;

import javax.swing.*;

public class ReservationUpdateView extends Layout {
    private JPanel container;
    private JTextField fld_guest_name;
    private JTextField fld_guest_id;
    private JTextField fld_mail;
    private JTextField fld_phone;
    private JButton btn_update;
    private JLabel lbl_guest_name;

    private Reservation reservation;
    private ReservationManager reservationManager;

    public ReservationUpdateView(Reservation reservation){
        this.reservation = reservation;
        this.reservationManager = new ReservationManager();
        this.add(container);
        this.guiInitilaze(300,400);

        if(reservation != null){
            fld_guest_name.setText(reservation.getGuestName());
            fld_guest_id.setText(reservation.getGuestCitizenID());
            fld_mail.setText(reservation.getGuestMail());
            fld_phone.setText(reservation.getGuestPhone());
        }

        btn_update.addActionListener(e ->{
            boolean result;

            this.reservation.setGuestName(fld_guest_name.getText());
            this.reservation.setGuestCitizenID(fld_guest_id.getText());
            this.reservation.setGuestMail(fld_mail.getText());
            this.reservation.setGuestPhone(fld_phone.getText());

            if(this.reservation.getId() != 0){
                result = this.reservationManager.update(this.reservation);
            }else{
                result = this.reservationManager.save(this.reservation);
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
