package org.coner.core.gateway;

import com.google.common.base.Preconditions;
import org.coner.boundary.EventBoundary;
import org.coner.boundary.RegistrationBoundary;
import org.coner.core.domain.Event;
import org.coner.core.domain.Registration;
import org.coner.hibernate.dao.RegistrationDao;

import java.util.List;

/**
 * RegistrationGateway wraps persistence layer interactions for Registration domain entities.
 */
public class RegistrationGateway {

    private final RegistrationBoundary registrationBoundary;
    private final EventBoundary eventBoundary;
    private final RegistrationDao registrationDao;

    /**
     * Constructor for RegistrationGateway.
     *
     * @param registrationBoundary the RegistrationBoundary for converting Domain entities to/from Hibernate entities
     * @param eventBoundary        the EventBoundary for converting Domain entities to/from Hibernate entities
     * @param registrationDao      the RegistrationDao for interacting with the persistence layer
     */
    public RegistrationGateway(
            RegistrationBoundary registrationBoundary,
            EventBoundary eventBoundary,
            RegistrationDao registrationDao) {
        this.registrationBoundary = registrationBoundary;
        this.eventBoundary = eventBoundary;
        this.registrationDao = registrationDao;
    }

    /**
     * Get list of Registrations for a given Event.
     *
     * @param event event to find Registrations for
     * @return List of Registrations for Event
     */
    public List<Registration> getAllWith(Event event) {
        Preconditions.checkNotNull(event);
        org.coner.hibernate.entity.Event hibernateEvent = eventBoundary.toHibernateEntity(event);
        List<org.coner.hibernate.entity.Registration> registrations = registrationDao.getAllWith(hibernateEvent);
        return registrationBoundary.toDomainEntities(registrations);
    }
}
