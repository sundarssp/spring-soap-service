package com.example.springintro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.springintro.population.City;
import com.example.springintro.population.GetPopulationRequest;
import com.example.springintro.population.GetPopulationResponse;

@Endpoint
public class PopulationEndpoint {
	private static final String NAMESPACE_URI = "http://www.example.com/springintro/population";

	private CityRepository cityRepository;

	@Autowired
	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPopulationRequest")
	public @ResponsePayload GetPopulationResponse getPopulation(@RequestPayload GetPopulationRequest request) {
		City city = cityRepository.getCity(request.getName());

		GetPopulationResponse response = new GetPopulationResponse();
		response.setCity(city);

		return response;
	}
}
