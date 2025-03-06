# Atotech Commerce Repository

# Front End Development Requirements
- Angular CLI: Version 10.1 or later, < 11.
- Node.js: The most recent 12.x version is recommended, < 13.
- Yarn: Version 1.15 or later. 

  
  
# Back End Development Requirements
- Hybris version -  2011.6. ([Download link](https://epam-my.sharepoint.com/personal/myles_bunbury_epam_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fmyles%5Fbunbury%5Fepam%5Fcom%2FDocuments%2FShared%20with%20Everyone%2FSAP%20Hybris%20Software%2FSAP%20Commerce%20Cloud%202011))
- Cloud Extension Pack - 2102.1 ([Download link](https://epam-my.sharepoint.com/personal/myles_bunbury_epam_com/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fmyles%5Fbunbury%5Fepam%5Fcom%2FDocuments%2FShared%20with%20Everyone%2FSAP%20Hybris%20Software%2FCommerce%20Cloud%20Extension%20Pack))

# Installation  
How to install project locally - [link](https://www.sap.com/cxworks/article/486232623/build_and_deploy_your_first_sap_commerce_cloud_project#BuildandDeployYourFirstSAPCommerceCloudProject-GetitWorkingLocally).

- Step 9 should be skipped;  
- For step 14 the next commands should be executed:  
yarn install  
yarn start
  
# Tests  
Navigate to /core-customize/bootstrap and execute  
```
. ./setantenv.sh (linux)
OR
setantenv.bat (Windows)
```
To run unit tests (report location - /core-customize/hybris/log/tests/unittests):  
```
ant rununittests
```
To run api tests (report location - /core-customize/hybris/log/tests/apitests):  
```
ant runapitests
```
To run cucumber tests (report location - /core-customize/hybris/log/tests/cucumbertests):  
```
ant runbackendcucumbertests
```
# Internationalization (i18n)
We serve different JSON files for each specific language and chunk
```
js-storefront/atotech-store/src/assets/i18n-assets/{{lng}}/{{ns}}.json
```

- For adding translation for specific feature we need to find feature "translations" folder in ([Spartacus repository](https://github.com/SAP/spartacus)), convert existing ts files to JSON and add to "i18n-assets", for example savedCart.json

This approach works for both CSR and SSR.
