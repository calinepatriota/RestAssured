package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static requests.RequestBase.getValueFromResponse;

public class UserEndpoints {

    public static Response authenticateUserRequest(RequestSpecification spec, User user){
        Response loginResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        and().
                        body(user.getUserCredentials()).
                        when().post("login");

        user.setAuthToken(getValueFromResponse(loginResponse, "authorization"));
        return loginResponse;
    }

    public static Response getUsersRequest(RequestSpecification spec, String query){
        Response getUsersResponse =
                given().
                        spec(spec).
                        when().
                        get("usuarios"+query);
        return getUsersResponse;
    }

    public static Response getAllUsersRequest(RequestSpecification spec){
        Response getUsersResponse =
                given().
                        spec(spec).
                        when().
                        get("usuarios");
        return getUsersResponse;
    }

    public static Response postUserRequest(RequestSpecification spec, User user){
        JSONObject userJsonRepresentation = new JSONObject();
        userJsonRepresentation.put("nome", user.nome);
        userJsonRepresentation.put("email",user.email);
        userJsonRepresentation.put("password", user.password);
        userJsonRepresentation.put("administrador",user.administrador);

        Response postUserRequest =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        and().
                        body(userJsonRepresentation.toJSONString()).
                        when().
                        post("usuarios");

        user.setUserId(getValueFromResponse(postUserRequest, "_id"));
        return postUserRequest;
    }

    public static Response deleteUserRequest(RequestSpecification spec, User user){
        Response deleteUserResponse =
                given().
                        spec(spec).
                        pathParam("_id",user._id).
                        when().
        delete("usuarios/{_id}");
        return deleteUserResponse;
    }
}