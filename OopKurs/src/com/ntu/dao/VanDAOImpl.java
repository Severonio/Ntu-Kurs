package com.ntu.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ntu.ConnectionFactory;

import com.ntu.domain.Van;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VanDAOImpl implements VanDAO {

    @Override
    public Van getVanById(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM van WHERE id=" + id);
        ) {


            if (rs.next()) {

                return extractVanFromResultSet(rs);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;
    }

    @Override
    public ObservableList<Van> getVanByTitleAndCustomer(String title, String customer) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM van WHERE title=? AND customer=?")) {

            ps.setString(1, title);
            ps.setString(2, customer);

            ResultSet rs = ps.executeQuery();
            ObservableList<Van> vans = FXCollections.observableArrayList();

            while (rs.next()) {
                Van van = extractVanFromResultSet(rs);
                vans.add(van);  //l2
            }

            return vans; //l3

        } catch (SQLException ex) {
            ex.printStackTrace();

        }


        return null;
    }

    @Override
    public ObservableList<Van> getAllVans() {

        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM van");
        )
        {

            ObservableList<Van> vans = FXCollections.observableArrayList();

            while(rs.next())
            {
                Van van = extractVanFromResultSet(rs);
                vans.add(van);  //l4
            }

            return vans; //l5

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;
    }

    @Override
    public boolean insertVan(Van book) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO van(title, customer, made, weight) VALUES (?, ?, ?, ?)");
        )
        {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getCustomer());
            ps.setInt(3, book.getMade());
            ps.setInt(4, book.getWeight());

            int i = ps.executeUpdate(); // for INSERT, UPDATE & DELETE

            if(i == 1) {

                return true;

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return false;
    }

    @Override
    public boolean updateVan(Van van) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE van set title = ?, customer = ?, made = ?, weight = ? WHERE id=?");
        ) {
            ps.setString(1, van.getTitle());
            ps.setString(2, van.getCustomer());
            ps.setInt(3, van.getMade());
            ps.setInt(4, van.getWeight());
            ps.setLong(5, van.getId());

            int i = ps.executeUpdate(); // for INSERT, UPDATE & DELETE

            if (i == 1) {

                return true;

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return false;
    }

    @Override
    public boolean deleteVan(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM van WHERE id=?");
        ) {

            ps.setLong(1, id);

            int i = ps.executeUpdate(); // for INSERT, UPDATE & DELETE

            if (i == 1) {

                return true;

            }

        } catch (SQLException ex) {

            ex.printStackTrace();

        }

        return false;
    }

    @Override
    public Van getFirstVan() {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM van ORDER BY id limit 1");
        ) {


            if (rs.next()) {

                return extractVanFromResultSet(rs);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return null;
    }


    private Van extractVanFromResultSet(ResultSet rs) throws SQLException {

        Van van = new Van();
        van.setId(rs.getLong("id"));
        van.setTitle(rs.getString("title"));
        van.setCustomer(rs.getString("customer"));
        van.setMade(rs.getInt("made"));
        van.setWeight(rs.getInt("weight"));

        return van;

    }
}




