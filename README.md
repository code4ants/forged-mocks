# forged-mocks
Extensive set of libraries providing service mocks for most common protocols and 3rd party services.

# Goals
The goal is to build a Java library that provides service mocks for most common type of services that are used when building applications: HTTP servers, SOAP/REST web services, JDBC, JMS, SMTP, etc. 

The mocks should be easy to setup and operate, in order to help with writing integration tests and creating scenarios that are as close and as useful as possible, with respect to the functions that are implemented in the application or the code units that are tested.

# Monetizing
The project is composed of two tiers, each with a clear set of components:
- the free/community tier
    - the Java libraries (Apache License)
    - the forged-mocks documentation
    - free access to the code4ants community forum, where forged-mocks topics can be discussed and help provided
- the paid/premium tier
    - the complete forged-mocks examples documentation: a set of documents that are describing in much detail examples of how each service mock can be configured and used
    - the ForgedMocks Visual Cue, a UI application that helps with customization and troubleshooting of projects that are using the forged-mocks library

# Library structure
The initial library structure plan could be the following:
- the library is distributed as Maven artifacts, available from the central repo
- the library is composed of the following artifacts:
    - code4ants.forgedmocks:forgedmocks-base:<version> - the common classes of each other library. This module also provides the basic model to be used in the other libraries
    - code4ants.forgedmocks:forgedmocks-http:<version> - the HTTP service mock
    - code4ants.forgedmocks:forgedmocks-smtp:<version> - the SMTP/mail service mock
    - code4ants.forgedmocks:forgedmocks-soap:<version> - the SOAP webservice mock
    - code4ants.forgedmocks:forgedmocks-rest:<version> - the REST webservice mock
    - code4ants.forgedmocks:forgedmocks-jdbc:<version> - the JDBC/database mock
