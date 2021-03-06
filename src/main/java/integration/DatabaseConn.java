package integration;

import common.AuthenticationData;
import common.CustomerData;
import common.UserData;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConn {

    private Connection dbConnection = null;
    private static final String INSERT_QUERY = "INSERT INTO customer (name, address, zipCode, socialSecurityNo) VALUES (?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM customer WHERE id = ?";
    private static final String SEARCH_CUSTOMER_QUERY = "SELECT * FROM customer WHERE ";

    private void connectToDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String url = "jdbc:mysql://" + "atlas.dsv.su.se" + "/" + "db_17921331";
        String userName = "usr_17921331";
        String password = "921331";
        try {
            dbConnection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println("SQL exception connect: " + e.getMessage());
        }
    }

    public void createCustomer(CustomerData customerData) {
        connectToDatabase();
        PreparedStatement stmt = null;

        try {
            stmt = dbConnection.prepareStatement(INSERT_QUERY);
            stmt.setString(1, customerData.getName());
            stmt.setString(2, customerData.getAddress());
            stmt.setInt(3, customerData.getZipCode());
            stmt.setString(4, customerData.getSocialSecurityNo());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("SQL exception insert: " + e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void updateCustomer(CustomerData cd) {
        connectToDatabase();
        Statement stmt = null;
        boolean update = false;

        String query = "UPDATE customer SET ";

        if (cd.getName() != null) {
            query += ("name = " + "'" + cd.getName() + "'");
            update = true;
        }
        if (cd.getAddress() != null){
            if(update){
                query += (", ");
            }
            query += ("address = " + "'" + cd.getAddress() + "'");
            update = true;
        }
        if (cd.getZipCode() != 0){
            if(update){
                query += (", ");
            }
            query += ("zipCode = " + "'" + cd.getZipCode() + "'");
            update = true;
        }

        if(update) {

            query += " WHERE id = " + cd.getId();
            System.out.println(query);

            try {
                stmt = dbConnection.createStatement();
                stmt.execute(query);
            } catch (SQLException e) {
                System.out.println("SQL exception: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    public ArrayList<CustomerData> searchCustomers (CustomerData cd){
        String query = SEARCH_CUSTOMER_QUERY;
        boolean firstChange = true;

        if (cd.getName() != null && !cd.getName().isEmpty()) {
            query += ("name = " + "'" + cd.getName() + "'");
            firstChange = false;
        }

        if (cd.getAddress() != null && !cd.getAddress().isEmpty()){
            if(!firstChange){
                query += (" AND ");
            }
            query += ("address = " + "'" + cd.getAddress() + "'");
            firstChange = false;
        }

        if (cd.getSocialSecurityNo() != null && !cd.getSocialSecurityNo().isEmpty()){
            if(!firstChange){
                query += (" AND ");
            }
            query += ("socialSecurityNo = " + "'" + cd.getSocialSecurityNo() + "'");
            firstChange = false;
        }

        if (cd.getZipCode() != 0){
            if(!firstChange){
                query += (" AND ");
            }
            query += ("zipCode = " + "'" + cd.getZipCode() + "'");
        }

        connectToDatabase();
        Statement stmt = null;
        ArrayList<CustomerData> customers = new ArrayList<>();

        try{
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int zipCode = rs.getInt("zipCode");
                String socialSecurityNo = rs.getString("socialSecurityNo");
                customers.add(new CustomerData(name, address, socialSecurityNo, zipCode, id));
            }
        }catch (SQLException e){
            System.out.println("SQL exception: " + e.getMessage());
        }finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return customers;
    }

    public CustomerData searchCustomerById(int id){
        connectToDatabase();
        Statement stmt = null;
        ArrayList<CustomerData> customers = new ArrayList<>();

        try{
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SEARCH_CUSTOMER_QUERY + "id = " + "'" + id + "'");

            while(rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                int zipCode = rs.getInt("zipCode");
                String socialSecurityNo = rs.getString("socialSecurityNo");
                customers.add(new CustomerData(name, address, socialSecurityNo, zipCode, id));
            }
        }catch (SQLException e){
            System.out.println("SQL exception: " + e.getMessage());
        }finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if(customers.size() < 1){
            return null;
        }else{
            return customers.get(0);
        }

    }

    public void deleteCustomer(int id){
        connectToDatabase();
        PreparedStatement stmt = null;

        try{
            stmt = dbConnection.prepareStatement(DELETE_QUERY);
            stmt.setInt(1, id);
            stmt.execute();
        }catch (SQLException e){
            System.out.println("SQL exception insert: " + e.getMessage());
        } finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public boolean login(AuthenticationData authenticationData){
        connectToDatabase();
        Statement stmt = null;
        String query = "SELECT password FROM user WHERE userName = " + "'" + authenticationData.getUsername() + "'";
        String passwordCheck = "";

        try{
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                passwordCheck = rs.getString("password");
            }
        }catch(SQLException e){
            System.out.println("SQL Exception: " + e.getMessage());
        }finally {
            if(stmt != null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return authenticationData.getPassword().equals(passwordCheck) ? true : false;
    }

    public int getHighScore(UserData userData){
        connectToDatabase();
        Statement stmt = null;
        int score = 0;

        try{
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT score FROM highScore WHERE sellerId = '" + userData.getId() + "'");
            while(rs.next()){
                score = rs.getInt("score");
            }
        }catch (SQLException e){
            System.out.println("SQL exception: " + e.getMessage());
        }finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return score;
    }
}
