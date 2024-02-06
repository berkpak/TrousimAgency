package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class AdminView extends Layout {
    private JPanel container;
    private JLabel lbl_header;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JButton btn_logout;
    private JPanel pnl_user;
    private JScrollPane scrl_user;
    private JTable tbl_user;
    private JComboBox cmb_s_user_role;
    private JButton btn_search_role;

    private User user;
    private Object[] col_user;

    //Tablo
    private DefaultTableModel tmdl_user = new DefaultTableModel();
    private UserManager userManager;

    //Popup menu
    private JPopupMenu userPopMenu;

    public AdminView(User user) {
        this.userManager = new UserManager();
        this.add(container);

        this.guiInitilaze(1000, 500);
        this.user = user;

        if (user == null) {
            dispose();
        }
        this.lbl_header.setText("Hosgeldiniz : " + this.user.getUsername());

        //User
        loadUserTable(null);
        loadUserComponent();
        loadUserRoleFilter();
    }

    public void loadUserTable(ArrayList<Object[]> userList) {
        //Tablo
        this.col_user = new Object[] {"ID", "Kullanici Adi", "Kullanici Sifresi", "Kullanici Rolu"};
        if(userList == null){
        userList = this.userManager.getForTable(col_user.length, this.userManager.findAll());
        }
        this.createTable(this.tmdl_user, this.tbl_user, col_user, userList);
    }

    public void loadUserComponent() {
        //Tiklanan satiri secili hale getiriyor
        this.tbl_user.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_user.rowAtPoint(e.getPoint());
                tbl_user.setRowSelectionInterval(selectedRow, selectedRow);
            }
        });

        //Popup Menu
        this.userPopMenu = new JPopupMenu();
        this.userPopMenu.add("Ekle").addActionListener(e -> {
            //Calisan ekleme ekrani
            UserView userView = new UserView(new User());
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });
        this.userPopMenu.add("Guncelle").addActionListener(e -> {
            int selectUserId = this.getTableSelectedRow(tbl_user, 0);
            UserView userView = new UserView(this.userManager.getById(selectUserId));
            userView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });

        });
        this.userPopMenu.add("Sil").addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selectUserId = this.getTableSelectedRow(tbl_user, 0);
                if (this.userManager.delete(selectUserId)) {
                    Helper.showMsg("done");
                    loadUserTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_user.setComponentPopupMenu(userPopMenu);
        this.btn_logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginView loginView = new LoginView();
            }
        });
        //Role gore ara butonu
        this.btn_search_role.addActionListener(e -> {
            User.Role selectedUserRole = (User.Role) cmb_s_user_role.getSelectedItem();
            ArrayList<User> searchedUserList = userManager.findByRole(selectedUserRole.toString());
            ArrayList<Object[]> searchedUserRowList = userManager.getForTable(col_user.length,searchedUserList );
            loadUserTable(searchedUserRowList);
        });
    }
    public void loadUserRoleFilter() {
        this.cmb_s_user_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));
        this.cmb_s_user_role.setSelectedItem(null);

    }
}
