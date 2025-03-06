@Backend
Feature: Ability for the system to support object creation via odata

  Scenario: Product created via odata and all required fields filled
    Given "Product" object is sent to odata using "productWithTwoSupercategories" body template
      | code           | cucumberProduct            |
      | name           | Cucumber Product           |
      | catalogId      | atotechChinaProductCatalog |
      | catalogVersion | Staged                     |
      | unitCode       | pieces                     |
      | firstCategory  | 1                          |
      | secondCategory | 1355                       |
    Then Product created with code "cucumberProduct" in catalog version "atotechChinaProductCatalog:Staged" and has next attributes
      | name                                         | Cucumber Product           |
      | unit.code                                    | pieces                     |
      | supercategories[0].code                      | 1                          |
      | supercategories[0].catalogVersion.catalog.id | atotechChinaProductCatalog |
      | supercategories[1].code                      | 1355                       |
      | supercategories[1].catalogVersion.catalog.id | atotechChinaProductCatalog |
      | approvalStatus.code                          | check                      |
