package view.entrypoints;


import integration.DatabaseConn;
import org.glassfish.jersey.internal.inject.Custom;
import test.TestUsers;
import common.CustomerData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customers")
public class Customers {
private TestUsers  test = new TestUsers();
private DatabaseConn databaseConn = new DatabaseConn();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void registerNewCustomer(CustomerData customerData) {
        databaseConn.createCustomer(customerData);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCustomer(CustomerData customerData) {
        databaseConn.updateCustomer(customerData);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerData> getCustomers(){
        return test.createBunchOfUsers();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerData getSpecificCustomer(@PathParam("id") int id) {
        return databaseConn.searchCustomerById(id);
    }


    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteCustomer(@PathParam("id") int id){
        databaseConn.deleteCustomer(id);
    }

}
