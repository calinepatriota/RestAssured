import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static requests.UserEndpoints.*;
import static requests.UserEndpoints.postUserRequest;

public class PostUserTests extends TestBase{

    private User validUser;


    @Test
    public void shouldPostUsers2AndStatus200(){
        validUser   = new User("Mario joao2", "mariojoao2@email.com", "1234A@", "true");
        Response postUserRequest = postUserRequest(SPEC, validUser);
        postUserRequest.
                then().
                assertThat().
                statusCode(201).
                body("message", equalTo(Constants.MESSAGE_SUCCESS_CREATE));
        postUserRequest.then().log().all();
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }
}