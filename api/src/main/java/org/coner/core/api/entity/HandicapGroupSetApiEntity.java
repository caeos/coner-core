package org.coner.core.api.entity;

import java.util.Set;

public class HandicapGroupSetApiEntity extends ApiEntity {

    private String id;
    private String name;
    private Set<HandicapGroupApiEntity> handicapGroups;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<HandicapGroupApiEntity> getHandicapGroups() {
        return handicapGroups;
    }

    public void setHandicapGroups(Set<HandicapGroupApiEntity> handicapGroups) {
        this.handicapGroups = handicapGroups;
    }
}
