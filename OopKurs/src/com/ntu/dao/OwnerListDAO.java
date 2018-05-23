package com.ntu.dao;

import com.ntu.domain.OwnerList;
import javafx.collections.ObservableList;
import java.util.List;

public interface OwnerListDAO {
    OwnerList getOwnerListById(long id);
    ObservableList<OwnerList> getVanByFirstNameAndLastName(String firstName, String lastName);
    ObservableList<OwnerList> getAllOwnerLists();
    boolean insertOwnerList(OwnerList ownerList);
    boolean updateOwnerList(OwnerList ownerList);
    boolean deleteOwnerList(long id);
    ///������ ��� UI
    OwnerList getFirstOwnerList();
}
