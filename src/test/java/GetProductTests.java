import io.restassured.response.Response;
import models.Product;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static requests.ProductEndpoints.*;
import static requests.UserEndpoints.*;

public class GetProductTests extends TestBase{

    private Product validProduct1;
    private User validUser1;

    @BeforeClass
    public void generateTestData(){
        validUser1   = new User("Bia Silvaa", "biasilva@email.com", "1234@A", "true");
        postUserRequest(SPEC, validUser1);
        validProduct1   = new Product(" mesaban22hotoalha2", 142,"mes2aba2nhotoalha2", 1);
        postProductRequest(SPEC, validProduct1,validUser1);
    }
    @AfterClass
    public void removeTestData(){
        deleteProductRequest(SPEC,validProduct1,validUser1);
        deleteUserRequest(SPEC, validUser1);
    }

    @DataProvider(name = "productQueryData")
    public Object[][] createTestData() {
        return new Object[][] {
                {"?nome="+validProduct1.nome},
                {"?_id="+validProduct1._id},
        };
    }

    @Test
    public void shouldReturProductsAndStatus200(){
        Response getProductResponse = getProductsRequest(SPEC);
        getProductResponse.
                then().
                assertThat().
                statusCode(200);
    }

    @Test(dataProvider = "productQueryData")
    public void shouldReturProductAndStatus200(String query){
        Response getProductResponse = getProductRequest(SPEC,query);
        getProductResponse.
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void shouldPostProductAndValidateQuantity(){
        Response getProductResponse = getProductsRequest(SPEC);
        getProductResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", equalTo(getProductResponse.body().path("quantidade")));
        getProductResponse.then().log().all();
    }
}