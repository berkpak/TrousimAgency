package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JLabel lbl_header;
    private JLabel lbl_header2;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JTextField fld_pass;
    private JButton btn_login;
    private JLabel lbl_username;
    private JLabel lbl_pass;

    //
    private final UserManager userManager;

    public LoginView() {
        this.userManager = new UserManager();

        this.add(container);
        this.guiInitilaze(400, 400);

        //Giris butonu
        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_pass};
            if (Helper.isFiledListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
            } else {
                User loginUser = this.userManager.findByLogin(this.fld_username.getText(), this.fld_pass.getText());
                if (loginUser == null) {
                    Helper.showMsg("notFound");
                } else {
                    switch (loginUser.getRole()) {
                        case "admin":
                            AdminView adminView = new AdminView(loginUser);
                           // HotelAddView hotelAddView = new HotelAddView(loginUser);
                            break;
                        case "employee":
                            EmployeeView employeeView = new EmployeeView(loginUser);
                        break;
                    }
                    dispose();
                }
            }
        });
    }
}
