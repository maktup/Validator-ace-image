##  **ACE IMAGE VALIDATOR**

This functionality has been developed in JAVA that covers the CUSTOMER CU when they say:


**I. USE CASE:**

***"I WANT TO BE ALWAYS UPDATED WITH THE LATEST VERSION OF ACE IMAGE, BUT I DON'T WANT TO BE MANUALLY VALIDATING EVERYTHING".***

Due to this, it was thought to try to solve this CU by implementing a base solution that can be reusable.



**II. USED ​​TECHNOLOGY:**

- JAVA 11.
- MAVEN.
- ECLIPSE IDE v2022-09.
- SELENIUM (chromedriver).



**III. STEPs:**

This has been the implementation sequence.

- Execute from JAVA a command for AUTHENTICATION (KUBERNETES / OPENSHIFT) based on the name of the INTEGRATION-SERVER deployed.
- The name of the IMAGE installed locally on the platform is obtained and the VERSION of the ACE is obtained.
- The IBM Website is consulted where the latest versions of ACE (Table) with SELENIUM are loaded.
- The entire existing list is obtained and loaded into an array to split each record.
- The 1st record is selected, which is the last existing one, which would be the IMAGE available for download.
- The VERSIONS of LOCAL IMAGE vs AVAILABLE IMAGE are compared to know which one is GREATER.
- A final recommendation message is sent based on the previous validation.




**IV. RECOMMENDATION:**
- This functionality is MAVENIZED and can be reused in any IDE that supports MAVEN.
- This functionality (version) currently works with Selenium with the Chrome browser and requires defining a local path to locate the component: chromedriver.
- This functionality can be taken to any Microservice and/or WebService in JAVA, define a Request/Response scheme and turn it into a service.





**V. TEST:**
Este debería ser el resultdo de la prueba al ejecutarse, todo el reporte con la recomendación es mostrada en LOGs.

![Alt text](https://github.com/maktup/Validator-ace-image/blob/main/image/Validator-ace-image.jpg?raw=true "Title")
