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
    private User validUserNotAdm;


    @BeforeClass
    public void generateTestData(){
        validUser   = new User("Mario joao2", "mariojoao2@email.com", "1234A@", "true");
        postUserRequest(SPEC, validUser);
        validUserNotAdm = new User("Mario joao2", "mariojoao2@email.com", "1234A@", "false");
        postUserRequest(SPEC, validUserNotAdm);
    }

    @Test
    public void shouldPostProductAndStatus200(){
        validProduct1   = new Product(" camamesa2ebanhot", 142,"camame2saebatnho", 1);
        Response postProductRequest= postProductRequest(SPEC, validProduct1,validUser);
        postProductRequest.
                then().
                assertThat().
                statusCode(201);
        postProductRequest.then().log().all();
    }

    @Test
    public void shouldPostProductAndStatus403(){
        validProduct1   = new Product("sofat", 142,"sofat", 1);
        Response postProductRequest= postProductRequest(SPEC, validProduct1,validUserNotAdm);
        postProductRequest.
                then().
                assertThat().
                statusCode(403);
        postProductRequest.then().log().all();
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser);
        deleteUserRequest(SPEC, validUser);
    }
}
