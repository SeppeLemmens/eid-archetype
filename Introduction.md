# eID Archetype #

The eID Maven Archetype Project provides archetypes for the construction of web applications using different Java EE based technology stacks and with deep integration of the eID security functionality within the used frameworks.

Each application produced by the archetypes comes with two web portals:

  * one web portal can be used by administrators to manage the application. The administrator web portal is using the eID Applet for authenticating the users. The administrator can configure:
    * privileges of other administrators
    * the location and security settings of the used eID IdP instance

  * one web portal to be used by end users. This end user web portal is using the eID IdP for authenticating the users.

By using the eID Maven Archetype Project you directly gain a few days of framework configuration. Nonetheless, one needs to keep in mind that decent knowledge of the used technology stack is mandatory to be able to succeed the construction of a full-blown enterprise application.