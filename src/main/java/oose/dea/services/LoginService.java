package oose.dea.services;

import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.domain.LoginHandler;
import oose.dea.services.dto.LoginRequestDTO;
import oose.dea.services.dto.ResponseRequestDTO;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@RequestScoped
public class LoginService {

    @Inject
    private LoginHandler loginHandler;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response loginUser(LoginRequestDTO loginRequestDTO){
        try {
            ResponseRequestDTO responseRequestDTO = loginHandler.getLoginResponse(loginRequestDTO);
            return Response.ok(responseRequestDTO).build();
        } catch (DatabaseException e) {
            return Response.status(e.getStatusCode()).build();
        }
    }
}
