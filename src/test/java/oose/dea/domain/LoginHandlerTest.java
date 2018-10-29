package oose.dea.domain;

import oose.dea.datasource.dto.User;
import oose.dea.services.dto.LoginRequestDTO;
import oose.dea.services.dto.ResponseRequestDTO;
import oose.dea.crosscutting.exceptions.DatabaseException;
import oose.dea.datasource.sql.dao.UserDAO;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class LoginHandlerTest {

    @Test
    public void loginUserTest() throws DatabaseException {

        String user = "Stijn";
        String token = "101010";
        String password = "Password";

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser(user);
        loginRequestDTO.setPassword(password);

        User userDTO = new User();
        userDTO.setUserId(Integer.parseInt(token));
        userDTO.setUser(user);
        userDTO.setPassWord(password);

        UserDAO mockedUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockedUserDAO.getUserData(user, password)).thenReturn(userDTO);

        LoginHandler loginHandler = new LoginHandler(mockedUserDAO);
        ResponseRequestDTO responseRequestDTO = loginHandler.getLoginResponse(loginRequestDTO);

        Mockito.verify(mockedUserDAO, Mockito.times( 1 ) ).getUserData(user, password);
        Assert.assertEquals(responseRequestDTO.getUser(),user);
        Assert.assertEquals(responseRequestDTO.getToken(),token);
    }
    }

