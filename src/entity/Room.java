package entity;

public class Room {
    private int id;
    private int hotel_id;
    private int pension_id;
    private int season_id;
    private int stock;
    private String type;
    private double adultPrice;
    private double childPrice;
    private int bed;
    private int square_meter;
    private boolean tv;
    private boolean minibar;
    private boolean gameConsole;
    private boolean cashBox;
    private boolean projection;

    //--gethotel fonk icin
    private Hotel hotel;

    private HotelPension hotelPension;
    private HotelSeason hotelSeason;

    public Room(){

    }
    public enum Type{
        Tek_Kisilik_Oda,
        Cift_kisilik_Oda,
        Junior_Suit,
        Suit
    }
    public Room(int hotel_id, int pension_id, int season_id, int stock, String type, double adultPrice, double childPrice, int bed, int square_meter, boolean tv, boolean minibar, boolean gameConsole, boolean cashBox, boolean projection) {
        this.hotel_id = hotel_id;
        this.pension_id = pension_id;
        this.season_id = season_id;
        this.stock = stock;
        this.type = type;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.bed = bed;
        this.square_meter = square_meter;
        this.tv = tv;
        this.minibar = minibar;
        this.gameConsole = gameConsole;
        this.cashBox = cashBox;
        this.projection = projection;
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

    public int getPension_id() {
        return pension_id;
    }

    public void setPension_id(int pension_id) {
        this.pension_id = pension_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(double childPrice) {
        this.childPrice = childPrice;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public int getSquare_meter() {
        return square_meter;
    }

    public void setSquare_meter(int square_meter) {
        this.square_meter = square_meter;
    }

    public boolean isTv() {
        return tv;
    }

    public void setTv(boolean tv) {
        this.tv = tv;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public boolean isGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(boolean gameConsole) {
        this.gameConsole = gameConsole;
    }

    public boolean isCashBox() {
        return cashBox;
    }

    public void setCashBox(boolean cashBox) {
        this.cashBox = cashBox;
    }

    public boolean isProjection() {
        return projection;
    }

    public void setProjection(boolean projection) {
        this.projection = projection;
    }

    //-----Otel adini cekmek icin

    public Hotel getHotel(){
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public HotelPension getHotelPension() {
        return hotelPension;
    }

    public void setHotelPension(HotelPension hotelPension) {
        this.hotelPension = hotelPension;
    }

    public HotelSeason getHotelSeason() {
        return hotelSeason;
    }

    public void setHotelSeason(HotelSeason hotelSeason) {
        this.hotelSeason = hotelSeason;
    }
}
