import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static requests.ProductEndpoints.getProductsRequest;
import static requests.ProductEndpoints.postProductRequest;
import static requests.UserEndpoints.*;

public class GetProductTests extends TestBase{

    private Product validProduct1;
    private User validUser1;

    @BeforeClass
    public void generateTestData(){
        validUser1   = new User("Joao Silva", "joaosilva@email.com", "1234@A", "true");
        postUserRequest(SPEC, validUser1);
        authenticateUserRequest(SPEC, validUser1);
        validProduct1   = new Product(" Mouse", 100,"Teste", 1);
        postProductRequest(SPEC, validProduct1);



}
    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser1);
/*        deleteUserRequest(SPEC, validUser2);
        deleteUserRequest(SPEC, validUser3);*/
    }



    @Test
    public void shouldReturProductsAndStatus200(){
        Response getProductResponse = getProductsRequest(SPEC);
        getProductResponse.
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void shouldReturProductAndStatus200(){
        Response getProductResponse = getProductsRequest(SPEC);
        getProductResponse.
                then().
                assertThat().
                statusCode(200);
    }

}