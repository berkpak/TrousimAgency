package view;

import business.*;
import core.Db;
import core.Helper;
import entity.Reservation;
import entity.Room;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.io.ObjectStreamException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeView extends Layout {
    private JPanel container;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JTable tbl_hotel;
    private JButton btn_hotel_add;
    private JPanel pnl_btn;
    private JPanel pnl_hotel;
    private JScrollPane scrl_hotel;
    private JTable tbl_pension;
    private JTable tbl_season;
    private JScrollPane scrl_pension;
    private JScrollPane scrl_season;
    private JPanel pnl_room;
    private JTable tbl_room;
    private JButton btn_room_add;
    private JPanel pnl_room_top;
    private JScrollPane scrl_room;
    private JPanel pnl_room_bottom;
    private JButton btn_search_room;
    private JButton btn_clear_room;
    private JTextField fld_hotel;
    private JTextField fld_adress;
    private JFormattedTextField fld_start_date;
    private JFormattedTextField fld_finish_date;
    private JTextField fld_adult;
    private JTextField fld_child;
    private JTable tbl_reservation;
    private JScrollPane scrl_book;
    private User user;
    private Room room;

    //Popup menu
    private JPopupMenu hotelPopMenu;
    private JPopupMenu reservationPopMenu;
    private JPopupMenu reservationUpdatePopMenu;

    //tablo islemleri
    private DefaultTableModel tmdl_hotel = new DefaultTableModel();
    private DefaultTableModel tmdl_room = new DefaultTableModel();
    private DefaultTableModel tmdl_hotelPension = new DefaultTableModel();
    private DefaultTableModel tmdl_hotelSeason = new DefaultTableModel();
    private DefaultTableModel tmdl_reservation = new DefaultTableModel();
    private HotelManager hotelManager;
    private RoomManager roomManager;
    private HotelPensionManager hotelPensionManager;
    private HotelSeasonManager hotelSeasonManager;
    private ReservationManager reservationManager;


    //Deneme
    private Object[] col_pension;
    private Object[] col_room;


    public EmployeeView(User user){
        this.hotelManager = new HotelManager();
        this.hotelPensionManager = new HotelPensionManager();
        this.hotelSeasonManager = new HotelSeasonManager();
        this.reservationManager = new ReservationManager();
        this.roomManager = new RoomManager();
        this.add(container);

        this.guiInitilaze(1500,500);
        this.user = user;

        if(user == null){
            dispose();
        }
        this.lbl_welcome.setText("Hosgeldiniz : " + this.user.getUsername());


        loadComponent();
        //Hotel
        loadHotelTable();
        loadHotelComponent();

        //HotelPension
        loadHotelPensionTable(null);

        //HotelSeason
        loadHotelSeasonTable();

        //Room
        loadRoomTable(null);
        loadRoomComponent();

        //Reservation
        loadReservationTable();
        loadReservationComponent();






        //Otel ekle butonu ile acilan yeni pencere
        btn_hotel_add.addActionListener(e -> {
            HotelAddView hotelAddView = new HotelAddView();
            hotelAddView.setVisible(true);
            hotelAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelTable();
                }
            });
        });

        //oda ekleme
        btn_room_add.addActionListener(e -> {
            RoomAddView roomAddView = new RoomAddView();
            roomAddView.setVisible(true);
            roomAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                }
            });
        });


        //oda filtreleme
        btn_search_room.addActionListener(e -> {
            LocalDate.parse(this.fld_start_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate.parse(this.fld_finish_date.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            ArrayList<Room> roomList = this.roomManager.searchForTable(
                    fld_hotel.getText(),
                    fld_adress.getText(),
                    this.fld_start_date.getText(),
                    this.fld_finish_date.getText(),
                    fld_adult.getText(),
                    fld_child.getText()
                    );
            ArrayList<Object[]> searchForRoomFilter = this.roomManager.getForTable(this.col_room.length, roomList);
            loadRoomTable(searchForRoomFilter);
        });
    }

    public void loadComponent(){
        this.btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
    }


    public void loadHotelComponent(){
        //tableRowSelect(this.tbl_hotel);
        //Tiklanan satiri secili hale getiriyor
        this.tbl_hotel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_hotel.rowAtPoint(e.getPoint());
                tbl_hotel.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        //Popup menu icin
        this.hotelPopMenu = new JPopupMenu();
        this.hotelPopMenu.add("Pansiyon ekle").addActionListener(e -> {
            PensionAddView pensionAddView = new PensionAddView();
            pensionAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelPensionTable(null);
                }
            });

        });
        this.hotelPopMenu.add("Sezon Ekle").addActionListener(e -> {
            int selectHOtelId = this.getTableSelectedRow(this.tbl_hotel,0);
            SeasonAddView seasonAddView = new SeasonAddView(
                    this.hotelManager.getbyId(selectHOtelId)
            );
            seasonAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadHotelSeasonTable();
                }
            });
        });
        this.tbl_hotel.setComponentPopupMenu(hotelPopMenu);
    }
    public void loadHotelTable() {
        //tablo kolonlari
        Object[] col_hotel = {"Otel ID", "Otel Adi", "Otel Adres", " Otel Mail", "Otel Telefon", "Otel Yildizi", "Araba Parki" , "Wifi", "Havuz", "Fitness", "concierge", "spa", "room service"};
        ArrayList<Object[]> hotelList = this.hotelManager.getForTable(col_hotel.length);

        this.createTable(this.tmdl_hotel,this.tbl_hotel,col_hotel,hotelList);
    }

    public void loadHotelPensionTable(ArrayList<Object[]> pensionList){
        this.col_pension = new Object[]{"ID", "Otel ID", "Pansyion Tipi"};
        if(pensionList == null){
            pensionList = this.hotelPensionManager.getForTable(this.col_pension.length, this.hotelPensionManager.findAll());
        }
        createTable(this.tmdl_hotelPension,this.tbl_pension, col_pension, pensionList);

    }

    public void loadHotelSeasonTable(){
        Object[] col_hotelSeason = {"ID", "Otel ID", "Baslangic", "Bitis"};
        ArrayList<Object[]> hotelSeasonList = this.hotelSeasonManager.getForTable(col_hotelSeason.length);
        this.createTable(this.tmdl_hotelSeason,this.tbl_season,col_hotelSeason,hotelSeasonList);
    }

    public void loadRoomTable(ArrayList<Object[]> roomList){
        col_room = new Object[] {"ID", "Otel Adi", "Pansiyon", "Oda Tipi", "Stok", "Yetiskin Fiyat", "Cocuk Fiyat", "Yatak Kapasitesi", "m2", "Tv", "Minibar", "Konsol", "Kasa", "Projeksiyon"};
        if(roomList == null){
            roomList = this.roomManager.getForTable(col_room.length,this.roomManager.findAll());
        }
        this.createTable(this.tmdl_room,this.tbl_room,col_room,roomList);


    }

    public void loadRoomComponent(){
        this.tbl_room.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_room.rowAtPoint(e.getPoint());
                tbl_room.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        this.reservationPopMenu = new JPopupMenu();
        this.reservationPopMenu.add("Rezervasyon Yap").addActionListener(e -> {
            int selectedRoomId = this.getTableSelectedRow(this.tbl_room,0);
            ReservationAddView reservationAddView = new ReservationAddView(
                    this.roomManager.getById(selectedRoomId),
                    fld_start_date.getText(),
                    fld_finish_date.getText(),
                    fld_adult,
                    fld_child
            );
            reservationAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable();
                    loadRoomTable(null);
                }
            });

        });
        this.tbl_room.setComponentPopupMenu(reservationPopMenu);
    }

    public void loadReservationTable(){
        //tablo kolonlari
        Object[] col_reservation = {"ID", "Oda Id", "Giris Tarihi", "Cikis Tarihi", "Fiyat", "Misafir Sayisi", "Misafir Adi" , "Misafir Kimlik No", "Mail", "Telefon"};
        ArrayList<Object[]> reservationList = this.reservationManager.getForTable(col_reservation.length);

        this.createTable(this.tmdl_reservation,this.tbl_reservation,col_reservation,reservationList);
    }

    public void loadReservationComponent(){
        this.tbl_reservation.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_reservation.rowAtPoint(e.getPoint());
                tbl_reservation.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });
        this.reservationUpdatePopMenu = new JPopupMenu();
        this.reservationUpdatePopMenu.add("Guncelle").addActionListener(e ->{
            int selectedReservation = this.getTableSelectedRow(this.tbl_reservation,0);
            ReservationUpdateView reservationUpdateView= new ReservationUpdateView(this.reservationManager.getById(selectedReservation));
            reservationUpdateView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable();
                }
            });
        });
        this.reservationUpdatePopMenu.add("Iptal Et").addActionListener(e ->{
            if(Helper.confirm("sure")){
                int selectedReservationId = this.getTableSelectedRow(tbl_reservation,0);
                Reservation reservation = this.reservationManager.getById(selectedReservationId);
                int selectedRoomId = reservation.getRoomId();
                Room selectedRoom = this.roomManager.getById(selectedRoomId);
                if(this.reservationManager.delete(selectedReservationId)){
                    Helper.showMsg("done");
                    int stock = selectedRoom.getStock() +1;
                    selectedRoom.setStock(stock);
                    this.roomManager.updateRoomStock(selectedRoom);
                    loadReservationTable();
                    loadRoomTable(null);
                }else{
                    Helper.showMsg("error");
                }

            }
        });
        this.tbl_reservation.setComponentPopupMenu(reservationUpdatePopMenu);
    }


    private void createUIComponents() throws ParseException {
        this.fld_start_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_start_date.setText("01/01/2024");
        this.fld_finish_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_finish_date.setText("06/01/2024");
    }
}
