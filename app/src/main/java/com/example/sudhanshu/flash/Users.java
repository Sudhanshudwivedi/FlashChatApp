package com.example.sudhanshu.flash;

/**
 * Created by Sudhanshu on 11-Oct-17.
 */

public class Users {

    public String name;
    public String image;
    public String status;

    public Users()
    {

    }
    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public Users(String name, String image, String status) {
        this.name = name;
        this.image = image;
        this.status = status;
    }
}
