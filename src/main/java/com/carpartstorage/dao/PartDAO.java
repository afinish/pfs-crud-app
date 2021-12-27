package com.carpartstorage.dao;

import com.carpartstorage.bean.Part;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartDAO {
    public PartDAO(){}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/partsdb?useSSL=false", "root", "password");
        } catch (SQLException e) {e.printStackTrace();}
        catch (ClassNotFoundException e) {e.printStackTrace();}
        return connection;
    }

    public void insertPart(Part part) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO parts" + " (name, type, brand) VALUES" + " (?, ?, ?);")) {
            preparedStatement.setString(1, part.getName());
            preparedStatement.setString(2, part.getType());
            preparedStatement.setString(3, part.getBrand());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Part selectPart(int id) {
        Part part = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select id, name, type, brand from parts where id = ?");) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                String brand = rs.getString("brand");
                part = new Part(id, name, type, brand);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return part;
    }
    public List<Part> filterBrand(String filter) {
        List<Part> parts = new ArrayList<Part>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement("select * from parts where brand='"+filter+"';");) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String brand = rs.getString("brand");
                parts.add(new Part(id, name, type, brand));
            }
        }catch(Exception e){System.out.println(e);}
        return parts;
    }

    public List<Part> selectAllParts(int start, int total, String order) {
        List<Part> parts = new ArrayList<Part>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement=connection.prepareStatement("select * from parts order by "+order+" limit "
                     +(start-1)+","+total);) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String brand = rs.getString("brand");
                parts.add(new Part(id, name, type, brand));
            }
        }catch(Exception e){System.out.println(e);}
        return parts;
    }

    public boolean deletePart(int id) throws SQLException {
        boolean deleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("delete from parts where id = ?");) {
            statement.setInt(1, id);
            deleted = statement.executeUpdate() > 0;
        }
        return deleted;
    }

    public boolean updatePart(Part part) throws SQLException {
        boolean updated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("update parts set name = ?, type = ?, brand = ? where id = ?;");) {
            System.out.println("updated Part:"+statement);
            statement.setString(1, part.getName());
            statement.setString(2, part.getType());
            statement.setString(3, part.getBrand());
            statement.setInt(4, part.getId());
            updated = statement.executeUpdate() > 0;
        }
        return updated;
    }

    private void printSQLException(SQLException exception) {
        for (Throwable e : exception) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = exception.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    public int getRowCount() throws SQLException {
        int count = 1;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM parts;")) {
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
        } catch(Exception e){System.out.println(e);}
        return count;
    }
}