package com.codecool.shop.datasource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.InputStream;
import java.util.Properties;


public class dbConnection {

    private static String DatabaseName;
    private static String DbUser;
    private static String DbPassword;
    private InputStream inputStream;



    private String getPropValues() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "connection.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            DatabaseName = prop.getProperty("database");
            DbUser = prop.getProperty("user");
            DbPassword = prop.getProperty("password");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return null;
    }


    public static DataSource connect() throws SQLException, IOException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dbConnection dbConnection = new dbConnection();
        dbConnection.getPropValues();

        dataSource.setDatabaseName(DatabaseName);
        dataSource.setUser(DbUser);
        dataSource.setPassword(DbPassword);

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

}
