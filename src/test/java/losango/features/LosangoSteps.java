package losango.features;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import losango.Application;
import losango.domain.Coordinate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by marciomarinho on 8/10/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest

//TODO: Refactor this class to use http://hc.apache.org/httpcomponents-core-4.4.x/tutorial/html/fundamentals.html#d5e56
//For some reason could not download the latest jar.
public class LosangoSteps {

    private String url = "http://localhost:8080/hexagon";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    private ResponseEntity<String> response;
    private Coordinate coordinate;
    private String serviceResult;

    @Before
    public void setup() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
    }

    @Given("^I have latitude (\\d+) and longitude (\\d+)$")
    public void i_have_latitude_and_longitude(int latitude, int longitude) throws Throwable {
        this.coordinate = new Coordinate(latitude, longitude);
    }

    @When("^I request a losango$")
    public void i_request_a_losango() throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(coordinate);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<String> response = new ResponseEntity<String>(headers, HttpStatus.OK);
        response = rest.postForEntity(url, requestEntity, String.class);
        this.status = response.getStatusCode();
        this.serviceResult = response.getBody();
    }

    @Then("^I should see \"([^\"]*)\"$")
    public void i_should_see(String parameter) throws Throwable {
        assertThat(this.status.value(), is(200));
//        assertThat(this.serviceResult, is("{\"code\":\"" + parameter + "\"}"));
    }

}
