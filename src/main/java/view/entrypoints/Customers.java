package view.entrypoints;


import test.TestUsers;
import common.CustomerData;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customers")
public class Customers {
private TestUsers  test = new TestUsers();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void registerNewCustomer(CustomerData customerData) {
        System.out.println(customerData.getName());
        System.out.println(customerData.getAddress());
        System.out.println(customerData.getZipCode());
        System.out.println(customerData.getSocialSecurityNo());


    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCustomer(@PathParam("id") int id) {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerData> getCustomers(){
        return test.createBunchOfUsers();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void getSpecificCustomer(@PathParam("id") int id) {
        System.out.println(id);
    }


    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteCustomer(@PathParam("id") int id){
        System.out.println(id);
    }

}
