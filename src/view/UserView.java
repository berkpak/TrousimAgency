package view;

import business.UserManager;
import core.ComboItem;
import core.Helper;
import entity.User;
import entity.User.Role;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends Layout{
    private JPanel container;
    private JLabel lbl_user;
    private JTextField fld_user_name;
    private JTextField fld_password;
    private JLabel lbl_password;
    private JLabel lbl_user_role;
    private JComboBox<User.Role> cmb_user_role;
    private JButton btn_user_save;
    private JLabel lbl_user_name;

    private User user;

    private UserManager userManager;

    public UserView(User user){
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(300, 300);
        this.user = user;

       /* for(User users : this.userManager.findAll()){
            this.cmb_user_role.addItem(new ComboItem(user.getId(), user.getRole()));

        } */

        //user enumdan admin ve emp i cmbbox a ekle
        this.cmb_user_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));

        //Guncelle yaparken secili satirdaki isim kutucuga otomatik geliyor
        if(user != null){
            fld_user_name.setText(user.getUsername());
        }

        //Kullanici kaydetme butonu
        btn_user_save.addActionListener(e -> {
            if(Helper.isFiledListEmpty(new JTextField[]{this.fld_user_name, this.fld_password})){
                Helper.showMsg("fill");
            }else{
                boolean result;

                this.user.setUsername(fld_user_name.getText());
                this.user.setPassword(fld_password.getText());
                this.user.setRole((String) cmb_user_role.getSelectedItem().toString());

                if(this.user.getId() != 0){
                    result = this.userManager.update(this.user);

                }else{
                    result = this.userManager.save(this.user);
                }
                if(result){
                    Helper.showMsg("done");
                    dispose();
                }else{
                    Helper.showMsg("error");
                }
            }
        });
    }
}
