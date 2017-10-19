package com.example.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignServiceRequest {
    @JsonProperty("campaign")
    private Campaign  campaign;
}
