package uk.co.paulpop.services.controller;

import com.amazonaws.Response;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.message.DefaultSnsMessageHandler;
import com.amazonaws.services.sns.message.SnsMessage;
import com.amazonaws.services.sns.message.SnsMessageManager;
import com.amazonaws.services.sns.message.SnsNotification;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.paulpop.services.exception.handler.HttpExceptionResponse;
import uk.co.paulpop.services.model.FlightInfo;
import uk.co.paulpop.services.model.FlightInfoRepository;
import uk.co.paulpop.services.model.FlightUpdate;
import uk.co.paulpop.services.model.Hello;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@Api("Java Spring Service API")
@RestController
@RequestMapping("/api")
class JavaSpringServiceController {

    @Autowired
    private Environment environment;

    @Value("${AWS_ACCESS_ID:nope}") // value after ':' is the default
    String AWS_ACCESS_ID;

    @Value("${AWS_SECRET_KEY:nopetwo}")
    String AWS_SECRET_KEY;

    @Autowired
    private FlightInfoRepository flightInfoRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSpringServiceController.class);

    @GetMapping("/{name}")
    @ResponseBody
    @ApiOperation("Says hello to the given name")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity<Hello> sayHello(final @PathVariable String name) {

        LOGGER.info("Received request to say hello to {}", name);

        return ResponseEntity.ok(Hello.builder()
                .message(String.format("Hello %s", name))
                .build());
    }

    @PostMapping("/flights")
    public ResponseEntity addFlightInfo (@RequestBody FlightInfo flightJson) {
        flightInfoRepository.save(flightJson);
        return new ResponseEntity(flightJson, HttpStatus.OK);
    }

    @PutMapping("/flights/{id}")
    public ResponseEntity updateFlightInfo (@PathVariable Integer id, @RequestBody FlightInfo flightJson) {

        FlightInfo flight = flightInfoRepository.findOne(id);

        flight.setFlightNo(flightJson.getFlightNo());
        flight.setDepartureTime(flightJson.getDepartureTime());
        flight.setArrivalTime(flightJson.getArrivalTime());
        flight.setGate(flightJson.getGate());
        flight.setDestination(flightJson.getDestination());
        flight.setStatus(flightJson.getStatus());

        flightInfoRepository.save(flight);

        sendEventToSNS(flight);

        return new ResponseEntity(flight, HttpStatus.OK);
    }

    private void sendEventToSNS(FlightInfo flight) {
        LOGGER.info("AWS: " + AWS_ACCESS_ID + ":" + AWS_SECRET_KEY);

        AmazonSNSClient snsClient = new AmazonSNSClient(new BasicAWSCredentials(AWS_ACCESS_ID,  AWS_SECRET_KEY));
        snsClient.setRegion(Region.getRegion(Regions.EU_WEST_1));

        PublishRequest publishRequest = new PublishRequest()
                .withTopicArn("arn:aws:sns:eu-west-1:122275815213:flightUpdates")
                .withMessage(flight.getFlightId().toString());

        PublishResult result = snsClient.publish(publishRequest);
        System.out.println(result.toString());
    }
}
