package com.atotech.apitests.api.occ.dto

class AuthorizationDto {

    private String username
    private String pwd

    String getUsername() {
        username
    }

    String getPwd(){
        pwd
    }

    static Builder getBuilder() {
        return new Builder()
    }

    static class Builder {

        private String username
        private String pwd

        def username(String username) {
            this.username = username
            this
        }

        def pwd(String pwd) {
            this.pwd = pwd
            this
        }

        AuthorizationDto build() {
            def result = new AuthorizationDto()
            AuthorizationDto.class.declaredFields.findAll {!it.synthetic}
                .collect {it.name}
                .each {
                    result.setProperty(it, getProperty(it))
                }
            return result
        }
    }

}
