package integration;

import common.CustomerData;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConn {

    private Connection dbConnection = null;
    private static final String INSERT_QUERY = "INSERT INTO customer (name, address, zipCode, socialSecurityNo) VALUES (?, ?, ?, ?)";
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

    public void updateCustomer(int id, String name, String address, int zipCode) {
        connectToDatabase();
        Statement stmt = null;
        boolean update = false;

        String query = "UPDATE customer SET ";

        if (name != null) {
            query += ("name = " + "'" + name + "'");
            update = true;
        }
        if (address != null){
            if(update){
                query += (", ");
            }
            query += ("address = " + "'" + address + "'");
            update = true;
        }
        if (zipCode != 0){
            if(update){
                query += (", ");
            }
            query += ("zipCode = " + "'" + zipCode + "'");
            update = true;
        }

        if(update) {

            query += " WHERE id = " + id;
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

    public CustomerData searchCustomerById(int id){
        ArrayList<CustomerData> customers = searchCustomer("id", String.valueOf(id));

        if(customers.size() < 1){
            return null;
        }else{
            return customers.get(0);
        }
    }

    public ArrayList<CustomerData> searchCustomerByName(String name){
        return searchCustomer("name", name);
    }

    public ArrayList<CustomerData> searchCustomerByZipCode(int zipCode){
        return searchCustomer("zipCode", String.valueOf(zipCode));
    }

    public ArrayList<CustomerData> searchCustomerByAdress(String address){
        return searchCustomer("address", address);
    }

    public ArrayList<CustomerData> searchCustomerBySocialSecurityNo(String socialSecurityNo){
        return searchCustomer("socialSecurityNo", socialSecurityNo);
    }

    private ArrayList<CustomerData> searchCustomer(String searchCriteria, String searchTerm){
        connectToDatabase();
        Statement stmt = null;
        ArrayList<CustomerData> customers = new ArrayList<>();

        try{
            stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(SEARCH_CUSTOMER_QUERY + searchCriteria + " = " + "'" + searchTerm + "'");

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
}
