package repository;

import connection.Connection;
import models.Model;

import java.util.ArrayList;

public interface Repository {
    ArrayList<Model> find(String column, String[] conditions, boolean join, String joinTable, Connection connection);

    Model findOne(String column, String[] conditions, boolean join, String joinTable, Connection connection);

    Model insert(String[] data, Connection connection);
}
