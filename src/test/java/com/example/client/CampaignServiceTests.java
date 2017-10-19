package com.example.client;

import com.example.client.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"com.example:internal-http-server-dsl:+:stubs:8080"}, workOffline = true)
@DirtiesContext
public class CampaignServiceTests {
    @Autowired
    private CampaignService service;

	@Test
	public void should_return_validation_error() {
		// given:
		Campaign campaign = Campaign.builder()
            .date(null)
            .entityA(EntityA.builder().fieldInt(9999).build())
            .entityB(EntityB.builder().fieldString("Hello world").build())
            .build();
		// when:
		CampaignSynchronizationResult result = service.synchronizeCampaign(campaign);
		// then:
		assertThat(result.getSynchronizationStatus())
				.isEqualTo(CampaignSynchronizationStatus.FAILED);
		assertThat(result.getErrorMessage()).isEqualTo("validation.failed");
	}

    @Test
    public void should_synchronize_successfully() {
        // given:
        Campaign campaign = Campaign.builder()
            .date(new Date())
            .entityA(EntityA.builder().fieldInt(9999).build())
            .entityB(EntityB.builder().fieldString("Hello world").build())
            .build();
        // when:
        CampaignSynchronizationResult result = service.synchronizeCampaign(campaign);
        // then:
        assertThat(result.getSynchronizationStatus())
            .isEqualTo(CampaignSynchronizationStatus.SUCCESS);
        assertThat(result.getErrorMessage()).isNullOrEmpty();
    }
}
