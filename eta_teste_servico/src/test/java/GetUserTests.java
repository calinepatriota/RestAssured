import io.restassured.response.Response;
import models.User;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;
import static requests.UserEndpoints.*;

import org.testng.annotations.DataProvider;

public class GetUserTests extends TestBase{

    private User validUser1;
 /*   private User validUser2;
    private User validUser3;
    private User invalidUser1;*/


    @BeforeClass
    public void generateTestData(){
        validUser1   = new User("Joao Silva", "joaosilva@email.com", "1234@A", "true");
        postUserRequest(SPEC, validUser1);
        /*validUser2   = new User("Maria Silva", "mariasilvao@email.com", "1234@B", "true");
        postUserRequest(SPEC, validUser2);
        validUser3   = new User("Josefa Silva", "josefasilva@email.com", "123456", "false");
        postUserRequest(SPEC, validUser3);
        invalidUser1 = new User("Roberto Silva", "robertosilva@email.com", "123456", "0");    }
*/}
    @AfterClass
    public void reqmoveTestData(){
        deleteUserRequest(SPEC, validUser1);
/*        deleteUserRequest(SPEC, validUser2);
        deleteUserRequest(SPEC, validUser3);*/
    }

    @DataProvider(name = "usersData")
    public Object[][] createTestData() {
        return new Object[][] {
               // {"?nome="+validUser1.nome, 1}
               {"?_id="+validUser1._id, 1},
                /*  {"?email="+validUser3.email, 1},
                 {"?nome="+invalidUser1.nome +"&email="+invalidUser1.email, 0}*/
        };
    }

    @Test(dataProvider = "usersData")
    public void shouldReturnUsersAndStatus200(String query, int totalUsers){
        Response getUserResponse = getUsersRequest(SPEC, query);
        getUserResponse.
                then().
                assertThat().
                statusCode(200).
                body("quantidade", equalTo(1));
       // body("quantidade", equalTo(totalUsers));
    }

}