package com.atotech.apitests.api.occ.dto

trait IgnoreUnknownProperties {

    def propertyMissing(String name, value){
        // do nothing
    }
}