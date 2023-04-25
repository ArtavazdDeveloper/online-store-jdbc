package manager;




import eshop.db.DBConnectionProvider;
import eshop.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void save(Category category) {
        String sql = "INSERT INTO category(id,name) VALUES(?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, category.getId());
            ps.setString(2, category.getName());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            while (generatedKeys.next()) {
                category.setId(generatedKeys.getInt(1));
            }
            System.out.println("Category inserted into DB");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(Category category) {
        String sql = "UPDATE category Set name = ? where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
            System.out.println("Table id and name edit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> all() {
        List<Category> categoryList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from category");
            while (resultSet.next()) {
                categoryList.add(getCompanyFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public Category getById(int id) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("Select * from category where id = " + id);
            if (resultSet.next()) {
                return getCompanyFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(Category category) {
        String sql = "DELETE FROM category where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, category.getId());
            ps.executeUpdate();
            System.out.println("deleted category");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Category getCompanyFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getInt("id"));
        category.setName(resultSet.getString("name"));
        return category;
    }
}

