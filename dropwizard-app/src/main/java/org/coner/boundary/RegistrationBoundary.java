package org.coner.boundary;

import org.coner.core.domain.Registration;

public class RegistrationBoundary extends AbstractBoundary<
        org.coner.api.entity.Registration,
        Registration,
        org.coner.hibernate.entity.Registration> {

    private final EventBoundary eventBoundary;

    public RegistrationBoundary(EventBoundary eventBoundary) {
        super();
        this.eventBoundary = eventBoundary;
    }

    @Override
    protected EntityMerger<org.coner.api.entity.Registration, Registration> buildApiToDomainMerger() {
        return new ReflectionEntityMerger<>();
    }

    @Override
    protected EntityMerger<Registration, org.coner.api.entity.Registration> buildDomainToApiMerger() {
        return new ReflectionEntityMerger<>();
    }

    @Override
    protected EntityMerger<Registration, org.coner.hibernate.entity.Registration> buildDomainToHibernateMerger() {
        return new ReflectionEntityMerger<>((sourceEntity, destinationEntity) -> {
            destinationEntity.setEvent(eventBoundary.toHibernateEntity(sourceEntity.getEvent()));
        });
    }

    @Override
    protected EntityMerger<org.coner.hibernate.entity.Registration, Registration> buildHibernateToDomainMerger() {
        return new ReflectionEntityMerger<>((sourceEntity, destinationEntity) -> {
            destinationEntity.setEvent(eventBoundary.toDomainEntity(sourceEntity.getEvent()));
        });
    }
}
