# CISQ1: Lingo Trainer
[![Java CI](https://github.com/Fare-alkhadrawy/cisq1-lingo/actions/workflows/build.yml/badge.svg?branch=DeelOpdracht4)](https://github.com/Fare-alkhadrawy/cisq1-lingo/actions/workflows/build.yml)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Fare-alkhadrawy_cisq1-lingo&metric=coverage)](https://sonarcloud.io/dashboard?id=Fare-alkhadrawy_cisq1-lingo)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Fare-alkhadrawy_cisq1-lingo&metric=bugs)](https://sonarcloud.io/dashboard?id=Fare-alkhadrawy_cisq1-lingo)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Fare-alkhadrawy_cisq1-lingo&metric=code_smells)](https://sonarcloud.io/dashboard?id=Fare-alkhadrawy_cisq1-lingo)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Fare-alkhadrawy_cisq1-lingo&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Fare-alkhadrawy_cisq1-lingo)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Fare-alkhadrawy_cisq1-lingo&metric=security_rating)](https://sonarcloud.io/dashboard?id=Fare-alkhadrawy_cisq1-lingo)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Fare-alkhadrawy_cisq1-lingo&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Fare-alkhadrawy_cisq1-lingo)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Fare-alkhadrawy_cisq1-lingo&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Fare-alkhadrawy_cisq1-lingo)

#Vulnerability Analysis
## A1:Injection
### Description
Injectiekwetsbaarheden, zoals SQL, NoSQL, OS en LDAP injectie,
 komen voor wanneer onvertrouwde data-invoer als code uitgevoerd kan worden

### Risk
de risico is heel klein bij dit project want want er is geen gevolige gegeven die wordt in de database opgeslagen,
 ook de input van de gebruiker wordt niet direct naar een SQL query gestuurd 
### Counter-measures
in dit project alle database action wordt behandeld door springboot en JPA.
dit framework lost dit probleem op.

## A9: XML External Entities
### Description
Een XML External Entity-aanval is een type aanval op een toepassing die XML-invoer parseert. Deze aanval vindt plaats wanneer XML-invoer die een verwijzing naar een externe entiteit bevat, wordt verwerkt door een zwak geconfigureerde XML-parser.
  Deze aanval kan leiden tot de openbaarmaking van vertrouwelijke gegevens, denial of service, vervalsing van verzoeken aan serverzijde, poortscannen vanuit het perspectief van de machine waarop de parser zich bevindt, en andere systeemeffecten. 
### Risk
de risico is heel klein bij dit soort problemen in dit projct want er wordt geen gebruik gemaakt van XML request of response.
### Counter-measures
Alleen Json type wordt geaccepteerd

## A4: Using Components with Known Vulnerabilities
### Description
gebruik maken voor oude of kwetsbaar Libraries en frameworks
### Risk
de risico is heel klein bij dit soort problemen in dit projct want er wordt gebruik gemakt van nieuwe en betrouwbaar Libraries en frameworks.

### Counter-measures
er wordt gebruik gemaakt van Maven dependency checker, GitHub dependabot en static analysis (i.e. SonarCloud)