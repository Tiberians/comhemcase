package test;

import common.CustomerData;

import java.util.ArrayList;
import java.util.List;

public class TestUsers {

    public List<CustomerData> createBunchOfUsers(){
        List<CustomerData> customerList = new ArrayList<>();
        CustomerData cd = new CustomerData("Anna", "Boovägen", "800220", 12345);
        customerList.add(cd);
        CustomerData cd1 = new CustomerData("Bo", "Krusvägen", "812220", 12355);
        customerList.add(cd1);
        CustomerData cd2 = new CustomerData("Cilla", "Nosvägen", "621019", 54321);
        customerList.add(cd2);
        return customerList;
    }
}
