package view.entrypoints;



import common.AuthenticationData;
import integration.DatabaseConn;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class Users {
    private DatabaseConn databaseConn = new DatabaseConn();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean login(AuthenticationData authenticationData) {
        return databaseConn.login(authenticationData);
    }




}
