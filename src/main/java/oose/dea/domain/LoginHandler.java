package oose.dea.domain;

import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.dto.User;
import oose.dea.datasource.sql.dao.UserDAO;
import oose.dea.services.dto.LoginRequestDTO;
import oose.dea.services.dto.ResponseRequestDTO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class LoginHandler {

    private UserDAO userDAO;

    @Inject
    public LoginHandler(UserDAO userDAO ){
        this.userDAO = userDAO;
    }

    public ResponseRequestDTO getLoginResponse(LoginRequestDTO loginRequestDTO) throws DatabaseException {
        User user =  userDAO.getUserData(loginRequestDTO.getUser(), loginRequestDTO.getPassword());
        return createResponseRequestDTO(String.valueOf(user.getUserId()), user.getUser());
    }

    private ResponseRequestDTO createResponseRequestDTO(String token, String user) {
        ResponseRequestDTO responseRequestDTO = new ResponseRequestDTO();
        responseRequestDTO.setToken(token);
        responseRequestDTO.setUser(user);
        return responseRequestDTO;
    }
}
