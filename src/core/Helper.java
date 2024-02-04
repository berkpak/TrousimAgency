package core;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void showMsg(String str) {
        String msg;
        String title = switch (str) {
            case "fill" -> {
                msg = "Lutfen tum alanlari doldurunuz";
                yield "Hata !";
            }
            case "done" -> {
                msg = "Islem Basarili";
                yield "Sonuc";

            }
            case "notFound" -> {
                msg = "Kayit Bulunamadi";
                yield "Sonuc";

            }
            case "error" -> {
                msg = "Kayit Mevcut";
                yield "Hata !";

            }
            default -> {
                msg = str;
                yield "Mesaj";
            }
        };

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFiledListEmpty(JTextField[] fieldList){
        for(JTextField field : fieldList){
            if(isFieldEmpty(field)) return true;
        }
        return false;
    }

    //Ekrani ortalamak icin
    public static int getLocationPoint(String type, Dimension size){
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static boolean confirm(String str){
        String confirmMsg;
        if (str.equals("sure")){
            confirmMsg = "Bu islemi yapmak istedigini emin misin? ";
        }else{
            confirmMsg = str;
        }
        return JOptionPane.showConfirmDialog(null, confirmMsg, "Emin misin?", JOptionPane.YES_NO_OPTION) == 0;
    }
}
