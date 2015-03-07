package org.coner.hibernate.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * Hibernate entity for the persistence of Registrations.
 */
@Entity
@Table(name = "registrations")
@NamedQueries({
        @NamedQuery(
                name = Registration.QUERY_FIND_ALL_WITH_EVENT,
                query = "FROM Registration r WHERE r.event.id = :" + Registration.PARAMETER_EVENT_ID
        )
})
public class Registration extends HibernateEntity {

    public static final String QUERY_FIND_ALL_WITH_EVENT = "org.coner.hibernate.entity.Registration.findAllWithEvent";
    public static final String PARAMETER_EVENT_ID = "eventId";

    private String id;
    private String firstName;
    private String lastName;
    private Event event;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ManyToOne
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
