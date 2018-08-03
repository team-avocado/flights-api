package uk.co.paulpop.services.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.co.paulpop.services.model.FlightInfo;
import uk.co.paulpop.services.model.Hello;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class JavaSpringServiceControllerTest {

    private JavaSpringServiceController controller;

    @Before
    public void setUp() {
        controller = new JavaSpringServiceController();
    }

    @Test
    public void whenMethodCalledWithEmptyStringThenSayHello() {
        ResponseEntity<Hello> response = controller.sayHello("");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(Hello.builder().message("Hello ").build()));
    }

    @Test
    public void whenMethodCalledWithSpaceThenSayHello() {
        ResponseEntity<Hello> response = controller.sayHello(" ");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(Hello.builder().message("Hello  ").build()));
    }

    @Test
    public void whenMethodCalledWithFullNameThenSayHello() {
        ResponseEntity<Hello> response = controller.sayHello("Paul Pop");

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo(Hello.builder().message("Hello Paul Pop").build()));
    }
}