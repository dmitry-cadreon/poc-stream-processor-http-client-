package com.example.client;

import com.example.client.model.Campaign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

@Slf4j
@SpringBootApplication
@EnableBinding(Processor.class)
public class Application {
    @Autowired
    private CampaignService campaignService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public Campaign processCampaign(Campaign campaign) {
        log.info("Processing campaign: " + campaign);
        campaign = campaignService.synchronizeCampaign(campaign).getCampaign();
        log.info("Output campaign: " + campaign);
        return campaign;
    }
}
