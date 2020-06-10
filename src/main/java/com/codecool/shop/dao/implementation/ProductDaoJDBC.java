package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.datasource.dbConnection;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {
    DataSource dataSource = dbConnection.connect();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Product> products = new ArrayList<>();
    SupplierDao supplierDao = SupplierDaoJDBC.getInstance();
    ProductCategoryDao productCategoryDao = ProductCategoryDaoJDBC.getInstance();
    private static ProductDaoJDBC instance =null;

    public ProductDaoJDBC() throws SQLException {
    }

    public static ProductDaoJDBC getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Product product) {
        try {
            Connection connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO product (name, price, currency, image, description, supplier_id, category_id) VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getDefaultPrice());
            preparedStatement.setString(3, product.getDefaultCurrency());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getSupplier().getId());
            preparedStatement.setInt(7, product.getProductCategory().getId());
            preparedStatement.executeQuery();
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    @Override
    public Product find(int id) {
        try {
            Connection connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE id=?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                int supplierId = resultSet.getInt("supplier_id");
                int categoryId = resultSet.getInt("category_id");
                Product product = new Product(name, description, price, currency, productCategoryDao.find(categoryId), supplierDao.find(supplierId), image);
                product.setId(id);
                return product;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public void remove(int id) {
        try {
            Connection connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

    @Override
    public List<Product> getAll() {
        try {
            products.clear();
            Connection connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM product");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                int supplierId = resultSet.getInt("supplier_id");
                int categoryId = resultSet.getInt("category_id");
                Product product = new Product(name, description, price, currency, productCategoryDao.find(categoryId), supplierDao.find(supplierId), image);
                product.setId(id);
                products.add(product);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return products;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        try{
        products.clear();
        Connection connection = dataSource.getConnection();
        preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE supplier_id=?");
        preparedStatement.setInt(1, supplier.getId());
        resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                int categoryId = resultSet.getInt("category_id");
                Product product = new Product(name, description, price, currency, productCategoryDao.find(categoryId), supplierDao.find(supplier.getId()), image);
                product.setId(id);
                products.add(product);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return products;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        try{
            products.clear();
            Connection connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE category_id=?");
            preparedStatement.setInt(1, productCategory.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Float price = resultSet.getFloat("price");
                String currency = resultSet.getString("currency");
                String image = resultSet.getString("image");
                String description = resultSet.getString("description");
                int supplierId = resultSet.getInt("supplier_id");
                Product product = new Product(name, description, price, currency, productCategoryDao.find(productCategory.getId()), supplierDao.find(supplierId), image);
                product.setId(id);
                products.add(product);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
            return products;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }
}