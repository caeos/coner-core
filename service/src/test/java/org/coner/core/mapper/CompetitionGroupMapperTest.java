package org.coner.core.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.coner.core.api.entity.CompetitionGroupApiEntity;
import org.coner.core.api.request.AddCompetitionGroupRequest;
import org.coner.core.domain.entity.CompetitionGroup;
import org.coner.core.domain.payload.CompetitionGroupAddPayload;
import org.coner.core.util.ApiEntityTestUtils;
import org.coner.core.util.DomainEntityTestUtils;
import org.coner.core.util.TestConstants;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class CompetitionGroupMapperTest {

    private CompetitionGroupMapper mapper = Mappers.getMapper(CompetitionGroupMapper.class);

    @Test
    public void whenToDomainAddPayloadFromApiAddRequest() {
        AddCompetitionGroupRequest apiAddRequest = new AddCompetitionGroupRequest();
        apiAddRequest.setName(TestConstants.COMPETITION_GROUP_NAME);
        apiAddRequest.setGrouping(TestConstants.COMPETITION_GROUP_GROUPING);
        apiAddRequest.setHandicapFactor(TestConstants.COMPETITION_GROUP_HANDICAP_FACTOR);
        apiAddRequest.setResultTimeType(TestConstants.COMPETITION_GROUP_RESULT_TIME_TYPE.name());
        CompetitionGroupAddPayload expected = new CompetitionGroupAddPayload();
        expected.setName(TestConstants.COMPETITION_GROUP_NAME);
        expected.setGrouping(TestConstants.COMPETITION_GROUP_GROUPING);
        expected.setHandicapFactor(TestConstants.COMPETITION_GROUP_HANDICAP_FACTOR);
        expected.setResultTimeType(TestConstants.COMPETITION_GROUP_RESULT_TIME_TYPE.name());

        CompetitionGroupAddPayload actual = mapper.toDomainAddPayload(apiAddRequest);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenToApiEntityFromDomainEntity() {
        CompetitionGroup competitionGroup = DomainEntityTestUtils.fullCompetitionGroup();
        CompetitionGroupApiEntity expected = ApiEntityTestUtils.fullCompetitionGroup();

        CompetitionGroupApiEntity actual = mapper.toApiEntity(competitionGroup);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenToApiEntityListFromDomainEntityList() {
        List<CompetitionGroup> domainEntityList = Arrays.asList(DomainEntityTestUtils.fullCompetitionGroup());
        List<CompetitionGroupApiEntity> expected = Arrays.asList(ApiEntityTestUtils.fullCompetitionGroup());

        List<CompetitionGroupApiEntity> actual = mapper.toApiEntityList(domainEntityList);

        assertThat(actual).isEqualTo(expected);
    }
}
