package org.coner.core.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.coner.core.util.TestConstants.HANDICAP_GROUP_ID;
import static org.coner.core.util.TestConstants.HANDICAP_GROUP_SET_ID;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.coner.core.api.entity.HandicapGroupSetApiEntity;
import org.coner.core.api.request.AddHandicapGroupSetRequest;
import org.coner.core.domain.entity.HandicapGroup;
import org.coner.core.domain.entity.HandicapGroupSet;
import org.coner.core.domain.payload.HandicapGroupSetAddPayload;
import org.coner.core.domain.service.HandicapGroupEntityService;
import org.coner.core.domain.service.HandicapGroupSetService;
import org.coner.core.domain.service.exception.EntityNotFoundException;
import org.coner.core.mapper.HandicapGroupSetMapper;
import org.coner.core.util.ApiEntityTestUtils;
import org.coner.core.util.DomainEntityTestUtils;
import org.coner.core.util.JacksonUtil;
import org.coner.core.util.UnitTestUtils;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.FixtureHelpers;
import io.dropwizard.testing.junit.ResourceTestRule;

public class HandicapGroupSetsResourceTest {

    HandicapGroupSetService handicapGroupSetService = mock(HandicapGroupSetService.class);
    HandicapGroupSetMapper handicapGroupSetMapper = mock(HandicapGroupSetMapper.class);
    HandicapGroupEntityService handicapGroupEntityService = mock(HandicapGroupEntityService.class);

    private ObjectMapper objectMapper;

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HandicapGroupSetsResource(
                    handicapGroupSetService,
                    handicapGroupSetMapper,
                    handicapGroupEntityService
            ))
            .addResource(new DomainServiceExceptionMapper())
            .build();

    @Before
    public void setup() {
        reset(handicapGroupSetService, handicapGroupSetMapper);

        objectMapper = Jackson.newObjectMapper();
        JacksonUtil.configureObjectMapper(objectMapper);
    }

    @Test
    public void whenAddValidHandicapGroupSetItShouldAdd() throws Exception {
        Response response = postHandicapGroupSet();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED_201);
        assertThat(response.getHeaderString(HttpHeader.LOCATION.asString()))
                .containsSequence("/handicapGroups/sets/", HANDICAP_GROUP_SET_ID);
        assertThat(UnitTestUtils.getEntityIdFromResponse(response)).isEqualTo(HANDICAP_GROUP_SET_ID);
    }

    private Response postHandicapGroupSet() throws Exception {
        AddHandicapGroupSetRequest request = objectMapper.readValue(
                FixtureHelpers.fixture("fixtures/api/request/handicap_group_set_add-request.json"),
                AddHandicapGroupSetRequest.class
        );
        Entity<AddHandicapGroupSetRequest> requestEntity = Entity.json(request);

        HandicapGroupSetAddPayload addPayload = mock(HandicapGroupSetAddPayload.class);
        when(handicapGroupSetMapper.toDomainAddPayload(request)).thenReturn(addPayload);
        HandicapGroupSet domainEntity = mock(HandicapGroupSet.class);
        when(handicapGroupSetService.add(addPayload)).thenReturn(domainEntity);
        HandicapGroupSetApiEntity apiEntity = mock(HandicapGroupSetApiEntity.class);
        when(handicapGroupSetMapper.toApiEntity(domainEntity)).thenReturn(apiEntity);
        when(apiEntity.getId()).thenReturn(HANDICAP_GROUP_SET_ID);

        Response response = resources.client()
                .target("/handicapGroups/sets")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(requestEntity);

        verify(handicapGroupSetService).add(addPayload);

        return response;
    }

    @Test
    public void itShouldGetHandicapGroupSet() throws EntityNotFoundException {
        HandicapGroupSet domainEntity = DomainEntityTestUtils.fullHandicapGroupSet();
        HandicapGroupSetApiEntity apiEntity = ApiEntityTestUtils.fullHandicapGroupSet();

        // sanity check test
        assertThat(domainEntity.getId()).isSameAs(HANDICAP_GROUP_SET_ID);
        assertThat(apiEntity.getId()).isSameAs(HANDICAP_GROUP_SET_ID);

        when(handicapGroupSetService.getById(HANDICAP_GROUP_SET_ID)).thenReturn(domainEntity);
        when(handicapGroupSetMapper.toApiEntity(domainEntity)).thenReturn(apiEntity);

        Response responseContainer = resources.client()
                .target("/handicapGroups/sets/" + HANDICAP_GROUP_SET_ID)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        verify(handicapGroupSetService).getById(HANDICAP_GROUP_SET_ID);
        verify(handicapGroupSetMapper).toApiEntity(domainEntity);
        verifyNoMoreInteractions(handicapGroupSetService, handicapGroupSetMapper);

        assertThat(responseContainer).isNotNull();
        assertThat(responseContainer.getStatus()).isEqualTo(HttpStatus.OK_200);

        HandicapGroupSetApiEntity response = responseContainer.readEntity(
                HandicapGroupSetApiEntity.class
        );
        assertThat(response)
                .isNotNull()
                .isEqualTo(apiEntity);
    }

    @Test
    public void itShouldRespondNotFoundWhenNotFound() throws EntityNotFoundException {
        when(handicapGroupSetService.getById(HANDICAP_GROUP_SET_ID))
                .thenThrow(EntityNotFoundException.class);

        Response response = resources.client()
                .target("/handicapGroups/sets/" + HANDICAP_GROUP_SET_ID)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        verify(handicapGroupSetService).getById(HANDICAP_GROUP_SET_ID);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND_404);
    }

    @Test
    public void itShouldAddHandicapGroupToSet() throws EntityNotFoundException {
        HandicapGroupSet domainSetEntity = DomainEntityTestUtils.fullHandicapGroupSet();
        HandicapGroup domainEntity = DomainEntityTestUtils.fullHandicapGroup();
        when(handicapGroupSetService.getById(HANDICAP_GROUP_SET_ID)).thenReturn(domainSetEntity);
        when(handicapGroupEntityService.getById(HANDICAP_GROUP_ID)).thenReturn(domainEntity);
        HandicapGroupSetApiEntity apiSetEntity = ApiEntityTestUtils.fullHandicapGroupSet();
        when(handicapGroupSetMapper.toApiEntity(domainSetEntity)).thenReturn(apiSetEntity);

        Response response = resources.client()
                .target("/handicapGroups/sets/" + HANDICAP_GROUP_SET_ID + "/handicapGroups/" + HANDICAP_GROUP_ID)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null);

        verify(handicapGroupSetService).addToHandicapGroups(domainSetEntity, domainEntity);

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    }

}
