package com.example.client;

import com.example.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CampaignService {

    private final RestTemplate restTemplate;
    private int port = 8080;

    @Autowired
    public CampaignService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public CampaignSynchronizationResult synchronizeCampaign(Campaign campaign) {
       CampaignServiceRequest request =
           CampaignServiceRequest.builder()
               .campaign(campaign)
               .build();

       CampaignServiceResponse response =
           sendRequestToCampaignService(request);

        return buildResultFromCampaignServiceResponse(response);
    }

    private CampaignServiceResponse sendRequestToCampaignService(CampaignServiceRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<CampaignServiceResponse> response =
            restTemplate.exchange("http://localhost:" + port + "/campaign", HttpMethod.POST,
                new HttpEntity<>(request, httpHeaders),
                CampaignServiceResponse.class);

        return response.getBody();
    }

    private CampaignSynchronizationResult buildResultFromCampaignServiceResponse(CampaignServiceResponse response) {

        return CampaignSynchronizationResult.builder()
            .campaign(response.getCampaign())
            .synchronizationStatus(response.getStatus())
            .errorMessage(response.getMessage())
            .build();
    }


}
