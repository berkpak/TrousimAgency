package core;

import entity.User;

public class ComboItem {

    //Combobox icin key value degeri gosterme

    private int key;
    private String value;

    public ComboItem(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String toString(){
        return value; //ekrana bastirilacak seyleri dondur.
    }



}
