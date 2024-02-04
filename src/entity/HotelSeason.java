package entity;

import core.ComboItem;

import java.time.LocalDate;

public class HotelSeason {

    private int id;
    private int hotel_id;
    private LocalDate start_date;
    private LocalDate fnsh_date;

    public HotelSeason(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getFnsh_date() {
        return fnsh_date;
    }

    public void setFnsh_date(LocalDate fnsh_date) {
        this.fnsh_date = fnsh_date;
    }

    public ComboItem getComboItem(){
        return new ComboItem(this.getId(),this.getStart_date() + " - " + this.getFnsh_date());
    }
}
