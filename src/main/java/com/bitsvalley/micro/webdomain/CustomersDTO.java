package com.bitsvalley.micro.webdomain;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.utils.AccountStatus;

import java.util.ArrayList;

public class CustomersDTO {

    ArrayList<User> allActiveCustomers = new ArrayList<>();
    ArrayList<User> allInActiveCustomers = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();

    public ArrayList<User> getAllActiveCustomers() {
        return allActiveCustomers;
    }

    public void setAllActiveCustomers(ArrayList<User> allActiveCustomers) {
        this.allActiveCustomers = allActiveCustomers;
    }

    public ArrayList<User> getAllInActiveCustomers() {
        return allInActiveCustomers;
    }

    public void setAllInActiveCustomers(ArrayList<User> allInActiveCustomers) {
        this.allInActiveCustomers = allInActiveCustomers;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
