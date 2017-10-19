package com.example.client.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CampaignSynchronizationResult {
    private Campaign campaign;
    private CampaignSynchronizationStatus synchronizationStatus;
    private String errorMessage;
}
