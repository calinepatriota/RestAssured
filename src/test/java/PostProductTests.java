import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static requests.ProductEndpoints.postProductRequest;
import static requests.UserEndpoints.deleteUserRequest;
import static requests.UserEndpoints.postUserRequest;

public class PostProductTests extends TestBase{


    private Product validProduct1;
    private User validUser;


    @BeforeClass
    public void generateTestData(){
        validUser   = new User("Mario joao2", "mariojoao2@email.com", "1234A@", "true");
        Response postUserRequest = postUserRequest(SPEC, validUser);
        postUserRequest.
                then().
                assertThat().
                statusCode(201);
        postUserRequest.then().log().all();
    }

    @Test
    public void shouldPostProductAndStatus200(){
        validProduct1   = new Product(" camamesaebanho", 142,"camamesaebanho", 1);
        Response postProductRequest= postProductRequest(SPEC, validProduct1,validUser);
        postProductRequest.
                then().
                assertThat().
                statusCode(201);
        postProductRequest.then().log().all();
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
    }
}
