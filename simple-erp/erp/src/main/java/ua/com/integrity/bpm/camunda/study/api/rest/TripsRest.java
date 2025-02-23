package ua.com.integrity.bpm.camunda.study.api.rest;

import ua.com.integrity.bpm.camunda.study.dto.trip.TripDto;
import ua.com.integrity.bpm.camunda.study.service.TripService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("trips")
@Produces(MediaType.APPLICATION_JSON)
public class TripsRest {

    @Inject
    private TripService tripService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public TripDto addTrip(@Valid TripDto tripDto){
        return tripService.newTrip(tripDto);
    }

    @GET
    public List<TripDto> allTrips() {
        return tripService.getAllTrips();
    }


}
