package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.given;
import static requests.RequestBase.getValueFromResponse;
import static requests.UserEndpoints.authenticateUserRequest;

public class ProductEndpoints  {

    public static Response getProductsRequest(RequestSpecification spec){
        Response getProductsResponse =
                given().
                        spec(spec).
                        when().
                        get("produtos");
        return getProductsResponse;
    }

    public static Response getProductRequest(RequestSpecification spec, String query){
        Response getProductsResponse =
                given().
                        spec(spec).
                        when().
                        get("produtos"+query);
        return getProductsResponse;
    }

    public static Response postProductRequest(RequestSpecification spec, Product product,User user){
        JSONObject productJsonRepresentation = new JSONObject();
        productJsonRepresentation.put("nome", product.nome);
        productJsonRepresentation.put("preco",product.preco);
        productJsonRepresentation.put("descricao", product.descricao);
        productJsonRepresentation.put("quantidade",product.quantidade);
        Response authenticateUserRequest = authenticateUserRequest(spec,user);
       String token= authenticateUserRequest.body().path("authorization");
        Response postProductRequest =
                given().
                        spec(spec).
                        headers("Authorization",token,
                                "Content-Type","application/json").
                        and().
                        body(productJsonRepresentation.toJSONString()).
                        when().
                        post("produtos");
        postProductRequest.then().log().all();
        product.setProductId(getValueFromResponse(postProductRequest, "_id"));
        return postProductRequest;
    }

    public static Response deleteProductRequest(RequestSpecification spec, Product product,User user){
        Response authenticateUserRequest = authenticateUserRequest(spec,user);
        String token= authenticateUserRequest.body().path("authorization");

        Response deleteProductResponse =
                given().
                        spec(spec).
                        header("Authorization",token).
                        pathParam("_id",product._id).
                        when().
                        delete("produtos/{_id}");
        return deleteProductResponse;
    }
}

