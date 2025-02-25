package ua.com.integrity.bpm.camunda.study.service;

import ua.com.integrity.bpm.camunda.study.dto.trip.TripDto;

import java.util.List;

public interface TripService {
    List<TripDto> getAllTrips();
    TripDto newTrip(TripDto tripDto);
}
