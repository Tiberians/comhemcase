package view.entrypoints;

import common.CustomerData;
import integration.DatabaseConn;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/search")
public class Search {

    private DatabaseConn databaseConn = new DatabaseConn();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<CustomerData> searchCustomer(CustomerData customerData){
        return databaseConn.searchCustomers(customerData);
    }
}
