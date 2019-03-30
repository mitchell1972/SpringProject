package Requests;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import io.restassured.response.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import io.restassured.specification.RequestSpecification;

import java.net.URISyntaxException;


public class RequestStub {



    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9156);



    JSONObject testData = null;

    @Test
    public void exampleTest() throws JSONException {


            testData = new JSONObject("{\"mandationStatus\":\"3\"}");

            stubFor(get(urlEqualTo("/vat/customer/vrn/968501144/information"))
                    .willReturn(okJson(testData.toString())));


            RequestSpecification httpRequest = RestAssured.given();

            httpRequest.header("Authorization", "Bearer BXQ3/Treo4kQCZvVcCqKPhcfnANE32v/98vEo68Q4RfFUCADfg+euX35LJP5s13F8TcizDrN/zM/LUGniOyXvu9z+9vzQoljadM9FiR76D48k4b8Kh9weJ4dlAKRXqNEw7P7vjiOsNJr3dRJyT67MVAUGGpQbkSQAGKBNUCjjzn9KwIkeIPK/mMlBESjue4V");
            Response rs = httpRequest.request(Method.GET, "http://localhost:" + 9567 + "/vat-subscription/968501144/mandation-status");

            System.out.println(rs.asString());
            Assert.assertEquals(200, rs.getStatusCode());
            Assert.assertEquals(testData.toString(), rs.getBody().jsonPath().get("body"));

    }

}
