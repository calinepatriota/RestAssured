import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static requests.UserEndpoints.*;
import org.testng.annotations.DataProvider;
import java.util.List;

public class GetUserTests extends TestBase{

    private User validUser1;
    private User validUser2;


    @BeforeClass
    public void generateTestData(){
        validUser1   = new User("Pedro Silva", "pedrosilva@email.com", "1234@A", "true");
        postUserRequest(SPEC, validUser1);
        validUser2   = new User("Ione Silva", "ionesilva@email.com", "1234@B", "true");
        postUserRequest(SPEC, validUser2);
    }

    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser1);
        deleteUserRequest(SPEC, validUser2);
    }

    @DataProvider(name = "userQueryData")
    public Object[][] createTestData() {
        return new Object[][] {
                   {"?nome="+validUser1.nome},
                   {"?nome="+validUser2.nome},
                   {"?_id="+validUser2._id},
                   {"?email="+validUser1.email},
        };
    }

@Test
    public void shouldReturnAllUsersAndStatus200(){
    Response getUserResponse = getAllUsersRequest(SPEC);
    getUserResponse.
            then().
            assertThat().
            statusCode(200).
            body("quantidade", instanceOf(Integer.class)).
            body("quantidade", equalTo(103)).
            body("usuarios", instanceOf(List.class));
    }

    @Test(dataProvider = "userQueryData")
    public void shouldReturnUsersAndStatus200(String query){
        Response getUserResponse = getUsersRequest(SPEC, query);
        getUserResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", instanceOf(Integer.class)).
                body("quantidade", equalTo(1)).
                body("usuarios", instanceOf(List.class));
        getUserResponse.then().log().all();
    }

    @Test
    public void shouldReturnUsers2AndStatus200(){
        SPEC.param("nome",validUser1.nome);
        Response getUserResponse = getAllUsersRequest(SPEC);
        getUserResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", instanceOf(Integer.class)).
                body("quantidade", equalTo(1)).
                body("usuarios", instanceOf(List.class));
        getUserResponse.then().log().all();
    }
}