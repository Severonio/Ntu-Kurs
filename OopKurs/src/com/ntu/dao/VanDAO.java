package com.ntu.dao;

import com.ntu.domain.Van;
import javafx.collections.ObservableList;

public interface VanDAO {

    Van getVanById(long id);

    ObservableList<Van> getVanByTitleAndCustomer(String title, String customer);

    ObservableList<Van> getAllVans();

    boolean insertVan(Van van);

    boolean updateVan(Van van);

    boolean deleteVan(long id);

    Van getFirstVan();
}