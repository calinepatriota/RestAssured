import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static requests.UserEndpoints.*;

public class LoginUserTests extends TestBase{

    private User validUser;
    private User invalidUser;

    @BeforeClass
    public void postUser(){
        validUser   = new User("Mario joao2", "mariojoao2@email.com", "1234A@", "true");
        postUserRequest(SPEC, validUser);
       // invalidUser = new User("Mariana", "mariana@email.com", "asdas", "true");
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }
    @Test
    public void shouldReturnSuccessMessageAuthTokenAndStatus200(){
        Response loginSuccessResponse = authenticateUserRequest(SPEC, validUser);
        loginSuccessResponse.
                then().
                assertThat().
                statusCode(200).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_LOGIN)).
                body("authorization", notNullValue());

    }


    public void shouldReturnFailureMessageAndStatus401(){

        Response loginFailureResponse = authenticateUserRequest(SPEC, invalidUser);
        loginFailureResponse.
                then().
                assertThat().
                statusCode(401).
                body("message", equalTo(Constants.MESSAGE_FAILED_LOGIN)).
                body("authorization", nullValue());
    }
}