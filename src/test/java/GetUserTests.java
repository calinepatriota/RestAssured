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
 /*   private User validUser2;
    private User validUser3;
    private User invalidUser1;*/


    @BeforeClass
    public void generateTestData(){
        validUser1   = new User("Joao Silva", "joaosilva@email.com", "1234@A", "true");
        postUserRequest(SPEC, validUser1);
        validUser2   = new User("Mari Silva", "mariasilva@email.com", "1234@B", "true");
        postUserRequest(SPEC, validUser2);
        /*validUser2   = new User("Maria Silva", "mariasilvao@email.com", "1234@B", "true");
        postUserRequest(SPEC, validUser2);
        validUser3   = new User("Josefa Silva", "josefasilva@email.com", "123456", "false");
        postUserRequest(SPEC, validUser3);
        invalidUser1 = new User("Roberto Silva", "robertosilva@email.com", "123456", "0");    }
*/}
    @AfterClass
    public void removeTestData(){
        deleteUserRequest(SPEC, validUser1);
        deleteUserRequest(SPEC, validUser2);
        /*  deleteUserRequest(SPEC, validUser3);*/
    }

    @DataProvider(name = "userQueryData")
    public Object[][] createTestData() {
        return new Object[][] {
                   {"?nome="+validUser1.nome, 1},
                   {"?nome="+validUser2.nome, 1},
                   {"?_id="+validUser2._id, 1},
                   {"?email="+validUser1.email, 1},
                // {"?nome="+invalidUser1.nome +"&email="+invalidUser1.email, 0}
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
            body("quantidade", equalTo(98)).
            body("usuarios", instanceOf(List.class));
    }

    @Test(dataProvider = "userQueryData")
    public void shouldReturnUsersAndStatus200(String query, int totalUsers){
        Response getUserResponse = getUsersRequest(SPEC, query);
        getUserResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", instanceOf(Integer.class)).
                body("quantidade", equalTo(1)).
                body("usuarios", instanceOf(List.class));
        getUserResponse.then().log().all();
       // body("quantidade", equalTo(totalUsers));
    }

    @Test
    public void shouldReturnUsers2AndStatus200( ){
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
        // body("quantidade", equalTo(totalUsers));
    }
}