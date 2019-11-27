# rhsso-puk-authenticator-spi

This authentication SPI allows to implement 2factor authentication with PUK Code. 

Note: The SPI is work in progress, pending for the PUK API to be available. The SPI currently reads the PUK code length and no. of digits to allow user to enter from RHSSO SPI configuration. The PUK code value is assumed to be 1234567. 


## Pre-requiste

* RHSSO instance
* mvn 3.6.2+
* java jdk 1.8
* PUK configuration - PUK Code length, no. of digits to allow user to enter.
	
	* Provide  PUK configuration as part of RHSSO SPI configuration


## Build 

	mvn clean install

## Deploy on RHSSO instance running on openshift:

    Login to cluster:

        oc login  <<cluster url >> -u <<username>> -p <<password>>
    
    Get the SSO POD

      oc get pods -n <<sso-project=namespace>>

    Add the jar to the rhsso server:
        $ oc cp target/rhsso-puk-authenticator-jar-with-dependencies.jar <<sso-pod-name>>:/opt/eap/standalone/deployments

    Add two templates to the rhsso server:
        $ cp themes/rhsso/login <<sso-pod-name>>:/opt/eap/themes/rhsso/login


## Hot Deploy on RHSSO running locally:
	
	Add the jar to the rhsso server:
		mvn clean install wildfly:deploy
		
	Add two templates to the rhsso server:
        $ cp themes/rhsso/login _RHSSO_HOME_/themes/rhsso/login

## Configure RHSSO to use SMS as Two Factor Authentication.
 
1. Login to RHSSO Admin Console
2. Select or Create a new Realm (eg. PUK 2FA).
3. Under the selected realm > Authentication > Flows:
    * Copy ‘Browser’ Flow and set name as ‘PUK Browser Flow’.
    * Under the new flow, set the auth execution
          Actions > Add execution > Select ‘PUK Flow’
    * Under the new flow, set ‘PUK Flow’ as Required.
    * Set Configuration for the PUK Browser flow by clicking on “Actions > Config” dropdown.

4. Under the selected realm > Authentication > Bindings, select ‘PUK Browser Flow’ as the browser flow for the realm

5. Under the selected realm > Themes, set login theme as dafm-theme or the theme
              where you copied the above templates.

6. Create a client for the mobile/web application.