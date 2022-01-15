import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static requests.UserEndpoints.*;
import static requests.UserEndpoints.postUserRequest;

public class PostUserTests extends TestBase{

    private User validUser;
    private User invalidUser;

    @Test
    public void postUser(){
        validUser   = new User("Mario joao2", "mariojoao2@email.com", "1234A@", "true");
        Response postUserRequest = postUserRequest(SPEC, validUser);
        postUserRequest.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo("Cadastro realizado com sucesso"));
        postUserRequest.then().log().all();
       // invalidUser = new User("Mariana", "mariana@email.com", "asdas", "true");
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }
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