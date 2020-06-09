package com.codecool.shop.datasource;

import javax.sql.DataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.postgresql.ds.PGSimpleDataSource;

import static java.sql.DriverManager.println;


public class dbConnection {

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        // Done
        dataSource.setDatabaseName("codecoolshop");
        dataSource.setUser("iulian");
        dataSource.setPassword("vlad");

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

//    Optional testing connection
//    public static void main(String[] args) throws SQLException {
//        dbConnection dbConnection = new dbConnection();
//
//        dbConnection.connect();
//        System.out.println("Merge?");
//
//    }
}
