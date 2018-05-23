package com.ntu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ntu.ConnectionFactory;

import com.ntu.domain.OwnerList;
import com.ntu.domain.Van;
import com.ntu.domain.VanRegister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VanRegisterDAOImpl implements VanRegisterDAO {

    @Override
    public VanRegister getVanRegisterById(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vanRegister WHERE id=" + id);
        )
        {



            if(rs.next())
            {

                return extractVanRegisterFromResultSet(rs);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }


        return null;
    }

    @Override
    public ObservableList<VanRegister> getVanRegisterByBroughtDt(Date broughtDt) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM vanRegister WHERE broughtDt=? "))
        {

            ps.setDate(1, broughtDt);

            ResultSet rs = ps.executeQuery();

            //List<BookRegister> bookRegisters = new ArrayList<>();
            ObservableList<VanRegister> vanRegisters = FXCollections.observableArrayList();

            while(rs.next())
            {
                VanRegister loadRegister = extractVanRegisterFromResultSet(rs);
                vanRegisters.add(loadRegister);  //������
            }

            return vanRegisters; //��������� ������

        } catch (SQLException ex) {
            ex.printStackTrace();

        }


        return null;
    }

    @Override
    public ObservableList<VanRegister> getAllVanRegisters() {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vanRegister");
        )
        {

            ObservableList<VanRegister> vanRegisters = FXCollections.observableArrayList();

            while(rs.next())
            {
                VanRegister vanRegister = extractVanRegisterFromResultSet(rs);
                vanRegisters.add(vanRegister);  //������
            }

            return vanRegisters; //��������� ������

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;
    }

    @Override
    public boolean insertVanRegister(VanRegister vanRegister) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(""
                     + "INSERT INTO vanRegister(vanId, broughtDt, ownerListId, takenDt) "
                     + "VALUES (?, ?, ?, ?)");
        )
        {

            ps.setLong(1, vanRegister.getVan().getId());
            ps.setDate(2, vanRegister.getBroughtDt());
            ps.setLong(3, vanRegister.getOwnerList().getId());
            ps.setDate(4, vanRegister.getTakenDt());

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
    public boolean updateVanRegister(VanRegister vanRegister) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE VanRegister set vanId = ?, broughtoDt = ?, ownerListId = ?, takenDt = ? WHERE id=?");
        )
        {

            ps.setLong(1, vanRegister.getVan().getId());
            ps.setDate(2, vanRegister.getBroughtDt());
            ps.setLong(3, vanRegister.getOwnerList().getId());
            ps.setDate(4, vanRegister.getTakenDt());
            ps.setLong(5, vanRegister.getId());

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
    public boolean deleteVanRegister(long id) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM VanRegister WHERE id=?");
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
    public VanRegister getFirstVanRegister() {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vanRegister ORDER BY id limit 1" );
        )
        {


            if(rs.next())
            {

                return extractVanRegisterFromResultSet(rs);


            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
        return null;
    }

    private VanRegister extractVanRegisterFromResultSet(ResultSet rs) throws SQLException {

        VanDAO vanDAO =  new VanDAOImpl();
        OwnerListDAO ownerListDAO =  new OwnerListDAOImpl();

        Van van = vanDAO.getVanById(rs.getLong("VanId"));
        OwnerList ownerList = ownerListDAO.getOwnerListById(rs.getLong("ownerListId"));

        VanRegister vanRegister = new VanRegister();
        van.setId(rs.getLong("id"));
        vanRegister.setVan(van );
        vanRegister.setBroughtDt( rs.getDate("broughtDt"));
        vanRegister.setOwnerList(ownerList);
        vanRegister.setTakenDt( rs.getDate("takenDt"));


        return vanRegister;

    }

}