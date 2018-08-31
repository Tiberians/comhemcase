package view.entrypoints;

import common.UserData;
import integration.DatabaseConn;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/highscore")
public class HighScore {
    private DatabaseConn databaseConn = new DatabaseConn();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public int getHighScore(UserData userData){
        return databaseConn.getHighScore(userData);
    }
}
