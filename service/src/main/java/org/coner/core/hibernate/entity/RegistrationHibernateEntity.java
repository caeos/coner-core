package org.coner.core.hibernate.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "registrations")
@NamedQueries({
        @NamedQuery(
                name = RegistrationHibernateEntity.QUERY_FIND_ALL_WITH_EVENT,
                query = "FROM RegistrationHibernateEntity r "
                        + "WHERE r.event.id = :" + RegistrationHibernateEntity.PARAMETER_EVENT_ID
        )
})
public class RegistrationHibernateEntity extends HibernateEntity {

    public static final String QUERY_FIND_ALL_WITH_EVENT = "org.coner.core.hibernate.entity.RegistrationHibernateEntity"
            + ".findAllWithEvent";
    public static final String PARAMETER_EVENT_ID = "eventId";

    private String id;
    private PersonHibernateEntity person;
    private CarHibernateEntity car;
    private EventHibernateEntity event;
    private HandicapGroupHibernateEntity handicapGroup;
    private CompetitionGroupHibernateEntity competitionGroup;
    private String number;
    private boolean checkedIn;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public PersonHibernateEntity getPerson() {
        return person;
    }

    public void setPerson(PersonHibernateEntity person) {
        this.person = person;
    }

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    public CarHibernateEntity getCar() {
        return car;
    }

    public void setCar(CarHibernateEntity car) {
        this.car = car;
    }

    @ManyToOne(optional = false)
    public EventHibernateEntity getEvent() {
        return event;
    }

    public void setEvent(EventHibernateEntity event) {
        this.event = event;
    }

    @ManyToOne(optional = false)
    public HandicapGroupHibernateEntity getHandicapGroup() {
        return handicapGroup;
    }

    public void setHandicapGroup(HandicapGroupHibernateEntity handicapGroup) {
        this.handicapGroup = handicapGroup;
    }

    @ManyToOne(optional = false)
    public CompetitionGroupHibernateEntity getCompetitionGroup() {
        return competitionGroup;
    }

    public void setCompetitionGroup(CompetitionGroupHibernateEntity competitionGroup) {
        this.competitionGroup = competitionGroup;
    }

    public String getNumber() {
        return number;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
