package uk.co.paulpop.services.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.paulpop.services.exception.handler.HttpExceptionResponse;
import uk.co.paulpop.services.model.FlightInfo;
import uk.co.paulpop.services.model.Hello;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@Api("Java Spring Service API")
@RestController
@RequestMapping("/api")
class JavaSpringServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaSpringServiceController.class);

    @GetMapping("flightinfo")
    @ResponseBody
    @ApiOperation("Gives flight info")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses({
            @ApiResponse(code = SC_BAD_REQUEST, message = "Bad request", response = HttpExceptionResponse.class),
            @ApiResponse(code = SC_INTERNAL_SERVER_ERROR, message = "Internal server error", response = HttpExceptionResponse.class)})
    public ResponseEntity<FlightInfo> flightInfo(final @PathVariable Integer flightId) {

        FlightInfo flightInfo = new FlightInfo();

        return ResponseEntity.ok(
                FlightInfo.builder().flightId(4)
                        .flightNo(flightInfo.getFlightNo())
                        .departureTime(flightInfo.getDepartureTime())
                        .arrivalTime(flightInfo.getArrivalTime())
                        .gate(flightInfo.getGate())
                        .destination(flightInfo.getDestination())
                        .status(flightInfo.getStatus())
                        .build());

    }

//        return ResponseEntity.ok(
//                FlightInfo.builder().flightId(4)
//                        .flightNo("AF")
//                        .departureTime(1010)
//                        .arrivalTime(2020)
//                        .gate("A")
//                        .destination("FB")
//                        .status("Fine")
//                        .build());

//    }



//    @PutMapping("/{flightId}")
//    public RequestEntity<FlightInfo> updateFlightInfo(@PathVariable Integer flightId, @RequestBody FlightInfo requestFlightInfo){
//
//        return ResponseEntity.ok(
//                FlightInfo.builder()
//                .flightId(String.format("This is flight %s", flightId))
//                .build());
//
//    }




}
