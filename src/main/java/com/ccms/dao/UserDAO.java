package com.ccms.dao;

import com.ccms.config.DBConnection;
import com.ccms.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public boolean registerUser(String name, String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(name,email,password) VALUES (?,?,?)"
            );
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public User login(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            System.out.println("Connection class: " + con.getClass());
            PreparedStatement ps = con.prepareStatement(
                    "SELECT id, role FROM users WHERE email=? AND password=?"
            );
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
