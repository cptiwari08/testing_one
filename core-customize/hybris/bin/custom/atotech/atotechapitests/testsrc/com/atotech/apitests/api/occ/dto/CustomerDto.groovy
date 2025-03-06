package com.atotech.apitests.api.occ.dto

import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

class CustomerDto implements IgnoreUnknownProperties {

    private String name
    private String uid
    private String firstName
    private String lastName

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getUid() {
        return uid
    }

    void setUid(String uid) {
        this.uid = uid
    }

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    boolean equals(other) {
        EqualsBuilder.reflectionEquals(this, other)
    }

    int hashCode() {
        HashCodeBuilder.reflectionHashCode(this)
    }

    static Builder builder() {
        return new Builder()
    }

    static class Builder {

        private String name
        private String uid
        private String firstName
        private String lastName

        def name(String name) {
            this.name = name
            this
        }

        def uid(String uid) {
            this.uid = uid
            this
        }

        def firstName(String firstName) {
            this.firstName = firstName
            this
        }

        def lastName(String lastName) {
            this.lastName = lastName
            this
        }

        CustomerDto build() {
            def result = new CustomerDto()
            CustomerDto.class.declaredFields.findAll {!it.synthetic}
                    .collect {it.name}
                    .each {
                        result.setProperty(it, getProperty(it))
                    }
            return result
        }
    }
}
