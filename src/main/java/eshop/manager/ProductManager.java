package manager;
import manager.CategoryManager;


import eshop.db.DBConnectionProvider;

import eshop.model.Product;

import java.sql.*;
import java.util.*;

public class ProductManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private CategoryManager categoryManager = new CategoryManager();

    public void save(Product product) {
        String sql = "INSERT INTO product(id,name,description,price,quantity,category_id) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, product.getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getQuantity());
            ps.setInt(6, product.getCategory().getId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            while (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
            System.out.println("product inserted into DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Product getById(int id) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("Select * from product where id = " + id);
            if (resultSet.next()) {
                return getProductFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void edit(Product product) {
        String sql = "UPDATE product Set name = ?,description = ?,price = ?,quantity = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getQuantity());
            ps.setInt(5, product.getId());
            ps.executeUpdate();
            System.out.println("Table id and name edit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Product product) {
        String sql = "DELETE FROM product where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, product.getId());
            ps.executeUpdate();
            System.out.println("deleted product");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> all() {
        List<Product> productList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from product");
            while (resultSet.next()) {
                productList.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public void printSumOfProduct() {
        String sql = "SELECT COUNT(id) FROM product";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery(sql);
            while (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                System.out.println(anInt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMaxOfPrice() {
        String sql = "SELECT MAX(price) FROM product";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery(sql);
            while (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                System.out.println(anInt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printMinOfPrice() {
        String sql = "SELECT MIN(price) FROM product";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery(sql);
            while (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                System.out.println(anInt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAvgOfPrice() {
        String sql = "SELECT AVG(price) FROM product";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery(sql);
            while (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                System.out.println(anInt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setDescription(resultSet.getString("description"));
        product.setPrice(resultSet.getDouble("price"));
        product.setQuantity(resultSet.getInt("quantity"));
        int categoryId = resultSet.getInt("category_id");
        product.setCategory(categoryManager.getById(categoryId));
        return product;
    }

}

