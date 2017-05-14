package org.coner.core.dagger;

import javax.inject.Singleton;

import org.coner.core.resource.CompetitionGroupSetResource;
import org.coner.core.resource.CompetitionGroupSetsResource;
import org.coner.core.resource.CompetitionGroupsResource;
import org.coner.core.resource.DomainServiceExceptionMapper;
import org.coner.core.resource.EventRegistrationsResource;
import org.coner.core.resource.EventsResource;
import org.coner.core.resource.HandicapGroupResource;
import org.coner.core.resource.HandicapGroupSetsResource;
import org.coner.core.resource.HandicapGroupsResource;

import dagger.Component;

@Singleton
@Component(modules = { ConerModule.class, MapStructModule.class })
public interface JerseyRegistrationComponent {
    // Resources
    EventsResource eventsResource();
    EventRegistrationsResource eventRegistrationsResource();
    HandicapGroupResource handicapGroupResource();
    HandicapGroupsResource handicapGroupsResource();
    HandicapGroupSetsResource handicapGroupSetsResource();
    CompetitionGroupsResource competitionGroupsResource();
    CompetitionGroupSetResource competitionGroupSetResource();
    CompetitionGroupSetsResource competitionGroupSetsResource();

    // Exception Mappers
    DomainServiceExceptionMapper domainServiceExceptionMapper();
}
