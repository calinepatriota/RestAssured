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
        validUser   = new User("Teste Testando", "testetestando2@email.com", "1234A@", "true");
        postUserRequest(SPEC, validUser);
       invalidUser = new User("Mariana", " ", "asdas", "false");
       postUserRequest(SPEC, invalidUser);
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
        loginSuccessResponse.
                then().log().all();
        System.out.println(loginSuccessResponse.body().path("authorization"));
    }

@Test
    public void shouldReturnFailureMessageAndStatus401(){

        Response loginFailureResponse = authenticateUserRequest(SPEC, invalidUser);
        loginFailureResponse.
                then().
                assertThat().
                statusCode(400);
    }
}