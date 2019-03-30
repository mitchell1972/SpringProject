package springbootdemo;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.annotation.JsonValue;
import com.github.tomakehurst.wiremock.common.Json;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    JSONObject testDataPpo = new JSONObject("PPOB\":\"{\"address\": {\"line1\": \"VAT COC Line1\",\"line2\": \"VAT COC Line2\",\"postCode\": \"TF3 4ER\",\"countryCode\": \"GB\" },\"contactDetails\": {\"primaryPhoneNumber\": \"01475444556\",\"mobileNumber\": \"07985678987\",\"faxNumber\": \"01475444556\" } }");

    @Autowired
    private EmployeeDOA employeeDao;

    @GetMapping(path = "/", produces = "application/json")
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public String getEmployees() throws URISyntaxException {

        RestTemplate restTemplate = new RestTemplate();
        String uRl = "http://localhost:" + 9567 + "/vat-subscription/968501144/mandation-status";
        URI uri = new URI(uRl);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer BXQ3/Treo4kQCZvVcCqKPsEfRv6a75Khow0rc/zsoczH18Fjx7Vag8Y+FMwEzayb0R0mn8yiPF4f48ir9U6jxQn02r9pE69myORmqJMK2Ohlkqo48LJBe4/W+voYs5mfvS+fTywgHzIE8FJEtLCkDWNvPcD3yA2zGRqiEJaU6T79KwIkeIPK/mMlBESjue4V");
        HttpEntity requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        return result.getBody().toString();

    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        employee.setId(id);

        employeeDao.addEmployee(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
