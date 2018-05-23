package com.ntu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ntu.ConnectionFactory;

import com.ntu.domain.OwnerList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OwnerListDAOImpl implements OwnerListDAO{

    @Override
    public OwnerList getOwnerListById(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM OwnerList WHERE id=" + id);
        )
        {



            if(rs.next())
            {
                return extractOwnerListFromResultSet(rs);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;

    }

    @Override
    public ObservableList<OwnerList> getVanByFirstNameAndLastName(String firstName, String lastName) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM OwnerList WHERE firstName=? AND lastName=?"))
        {

            ps.setString(1, firstName);
            ps.setString(2, lastName);

            ResultSet rs = ps.executeQuery();

            ObservableList<OwnerList> ownerLists = FXCollections.observableArrayList();

            while(rs.next())
            {
                OwnerList ownerList = extractOwnerListFromResultSet(rs);
                ownerLists.add(ownerList);  //������ ������ ������ �� ������
            }

            return ownerLists; //��������� ������ �������

        } catch (SQLException ex) {
            ex.printStackTrace();

        }


        return null;
    }

    @Override
    public ObservableList<OwnerList> getAllOwnerLists() {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM OwnerList");
        )
        {
            ObservableList<OwnerList> ownerLists = FXCollections.observableArrayList();

            while(rs.next())
            {
                OwnerList ownerList = extractOwnerListFromResultSet(rs);
                ownerLists.add(ownerList);  //������ ������ ������ �� ������
            }

            return ownerLists; //��������� ������ �������

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;
    }

    @Override
    public boolean insertOwnerList(OwnerList ownerList) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO OwnerList(firstName, middleName, lastName, phone, birthDt, serialOfPassport, numOfPassport,  address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        )
        {

            ps.setString(1, ownerList.getFirstName());
            ps.setString(2, ownerList.getMiddleName());
            ps.setString(3, ownerList.getLastName());
            ps.setString(4, ownerList.getPhone());
            ps.setDate(5, ownerList.getBirthDt());
            ps.setString(6, ownerList.getSerialOfPassport());
            ps.setInt(7, ownerList.getNumOfPassport());
            ps.setString(8, ownerList.getAddress());


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
    public boolean updateOwnerList(OwnerList ownerList) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(""
                     + " UPDATE OwnerList "
                     + "    set firstName = ?, "
                     + "        middleName = ?, "
                     + "        lastName = ?, "
                     + "        phone = ?, "
                     + "        birthDt = ?, "
                     + "        serialOfPassport = ?, "
                     + "        numOfPassport = ?,  "
                     + "        address = ? "
                     + "  WHERE id=?");
        )
        {
            ps.setString(1, ownerList.getFirstName());
            ps.setString(2, ownerList.getMiddleName());
            ps.setString(3, ownerList.getLastName());
            ps.setString(4, ownerList.getPhone());
            ps.setDate(5, ownerList.getBirthDt());
            ps.setString(6, ownerList.getSerialOfPassport());
            ps.setInt(7, ownerList.getNumOfPassport());
            ps.setString(8, ownerList.getAddress());
            ps.setLong(9, ownerList.getId());

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
    public boolean deleteOwnerList(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM OwnerList WHERE id=?");
        )
        {

            ps.setLong(1, id);

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
    public OwnerList getFirstOwnerList() {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM ownerList ORDER BY id limit 1" );
        )
        {


            if(rs.next())
            {

                return extractOwnerListFromResultSet(rs);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return null;
    }


    private OwnerList extractOwnerListFromResultSet(ResultSet rs) throws SQLException {

        OwnerList ownerList = new OwnerList();

        ownerList.setId(rs.getLong("id"));
        ownerList.setFirstName( rs.getString("firstName") );
        ownerList.setMiddleName( rs.getString("middleName") );
        ownerList.setLastName( rs.getString("lastName") );
        ownerList.setPhone( rs.getString("phone") );
        ownerList.setBirthDt( rs.getDate("birthDt") );
        ownerList.setSerialOfPassport( rs.getString("serialOfPassport") );
        ownerList.setNumOfPassport( rs.getInt("numOfPassport") );
        ownerList.setAddress( rs.getString("address") );

        return ownerList;

    }

}

