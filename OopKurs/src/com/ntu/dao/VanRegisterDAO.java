package com.ntu.dao;

import com.ntu.domain.Van;
import com.ntu.domain.VanRegister;
import javafx.collections.ObservableList;

import java.sql.Date;

public interface VanRegisterDAO {
    VanRegister getVanRegisterById(long id);
    ObservableList<VanRegister> getVanRegisterByBroughtDt(Date broughtDt);
    ObservableList<VanRegister> getAllVanRegisters();
    boolean insertVanRegister(VanRegister vanRegister);
    boolean updateVanRegister(VanRegister vanRegister);
    boolean deleteVanRegister(long id);
    ///������ ��� UI
    VanRegister getFirstVanRegister();
}