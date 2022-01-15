package requests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Product;
import models.User;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static requests.RequestBase.getValueFromResponse;

public class ProductEndpoints {


    public static Response getProductsRequest(RequestSpecification spec){
        Response getProductsResponse =
                given().
                        spec(spec).
                        when().
                        get("produtos");
        return getProductsResponse;
    }

   public static Response postProductRequest(RequestSpecification spec, Product product){
        JSONObject productJsonRepresentation = new JSONObject();
       productJsonRepresentation.put("nome", product.nome);
       productJsonRepresentation.put("preco",product.preco);
       productJsonRepresentation.put("descricao", product.descricao);
       productJsonRepresentation.put("quantidade",product.quantidade);

        Response postProductRequest =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        and().
                        body(productJsonRepresentation.toJSONString()).
                        when().
                        post("produtos");

        product.setProductId(getValueFromResponse(postProductRequest, "_id"));
        return postProductRequest;
    }

    public static Response deleteUserRequest(RequestSpecification spec, User user){
        Response deleteUserResponse =
                given().
                        spec(spec).
                        header("Content-Type","application/json").
                        when().
                        delete("usuarios/"+user._id);
        return deleteUserResponse;
    }
}