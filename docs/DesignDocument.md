# Design Document 


Authors: Giuliano Ettore, Koudounas Alkis, Pizzato Francesco

Date: 14/06/2020

Version: 3.0

List of changes

|             |                                      |
|-------------|--------------------------------------|
| UC1         | Added findByEmail() call             |
| UC7     	  | Added findOne() calls                |
|	          | Modified save() call to repository   |
| APIv2       | Modified low level design to be consistent with new API version |



# Contents

- [High level design](#high-level-design)
- [Low level design](#low-level-design)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document (see EZGas Official Requirements.md ). <br>
The design must comply with interfaces defined in package it.polito.ezgas.service (see folder ServicePackage ) <br>
UML diagrams **MUST** be written using plantuml notation.

# High level design 

The style selected is client - server. Clients can be smartphones, tablets, PCs.
The choice is to avoid any development client side. The clients will access the server using only a browser. 

The server has two components: the frontend, which is developed with web technologies (JavaScript, HTML, Css) and is in charge of collecting user inputs to send requests to the backend; the backend, which is developed using the Spring Framework and exposes API to the front-end.
Together, they implement a layered style: Presentation layer (front end), Application logic and data layer (back end). 
Together, they implement also an MVC pattern, with the V on the front end and the MC on the back end.



```plantuml
@startuml
package "Backend" {

}

package "Frontend" {

}


Frontend -> Backend
@enduml


```


## Front End

The Frontend component is made of: 

Views: the package contains the .html pages that are rendered on the browser and that provide the GUI to the user. 

Styles: the package contains .css style sheets that are used to render the GUI.

Controller: the package contains the JavaScript files that catch the user's inputs. Based on the user's inputs and on the status of the GUI widgets, the JavaScript controller creates REST API calls that are sent to the Java Controller implemented in the back-end.


```plantuml
@startuml
package "Frontend" {

    package "it.polito.ezgas.resources.views" {

    }


package "it.polito.ezgas.resources.controller" {

    }


package "it.polito.ezgas.resources.styles" {

    }



it.polito.ezgas.resources.styles -down-> it.polito.ezgas.resources.views

it.polito.ezgas.resources.views -right-> it.polito.ezgas.resources.controller


}
@enduml

```

## Back End

The backend  uses a MC style, combined with a layered style (application logic, data). 
The back end is implemented using the Spring framework for developing Java Entrerprise applications.

Spring was selected for its popularity and relative simplicity: persistency (M and data layer) and interactions are pre-implemented, the programmer needs only to add the specific parts.

See in the package diagram below the project structure of Spring.

For more information about the Spring design guidelines and naming conventions:  https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3



```plantuml
@startuml
package "Backend" {

package "it.polito.ezgas.service"  as ps {
   interface "GasStationService"
   interface "UserService"
} 


package "it.polito.ezgas.controller" {

}

package "it.polito.ezgas.converter" {

}

package "it.polito.ezgas.dto" {

}

package "it.polito.ezgas.entity" {

}

package "it.polito.ezgas.repository" {

}

    
}
note "see folder ServicePackage" as n
n -- ps
```



The Spring framework implements the MC of the MVC pattern. The M is implemented in the packages Entity and Repository. The C is implemented in the packages Service, ServiceImpl and Controller. The packages DTO and Converter contain classes for translation services.



**Entity Package**

Each Model class should have a corresponding class in this package. Model classes contain the data that the application must handle.
The various models of the application are organised under the model package, their DTOs(data transfer objects) are present under the dto package.

In the Entity package all the Entities of the system are provided. Entities classes provide the model of the application, and represent all the data that the application must handle.




**Repository Package**

This package implements persistency for each Model class using an internal database. 

For each Entity class, a Repository class is created (in a 1:1 mapping) to allow the management of the database where the objects are stored. For Spring to be able to map the association at runtime, the Repository class associated to class "XClass" has to be exactly named "XClassRepository".

Extending class JpaRepository provides a lot of CRUD operations by inheritance. The programmer can also overload or modify them. 



**DTO package**

The DTO package contains all the DTO classes. DTO classes are used to transfer only the data that we need to share with the user interface and not the entire model object that we may have aggregated using several sub-objects and persisted in the database.

For each Entity class, a DTO class is created (in a 1:1 mapping).  For Spring the Dto class associated to class "XClass" must be called "XClassDto".  This allows Spring to find automatically the DTO class having the corresponding Entity class, and viceversa. 




**Converter Package**

The Converter Package contains all the Converter classes of the project.

For each Entity class, a Converter class is created (in a 1:1 mapping) to allow conversion from Entity class to DTO class and viceversa.

For Spring to be able to map the association at runtime, the Converter class associated to class "XClass" has to be exactly named "XClassConverter".




**Controller Package**

The controller package is in charge of handling the calls to the REST API that are generated by the user's interaction with the GUI. The Controller package contains methods in 1:1 correspondance to the REST API calls. Each Controller can be wired to a Service (related to a specific entity) and call its methods.
Services are in packages Service (interfaces of services) and ServiceImpl (classes that implement the interfaces)

The controller layer interacts with the service layer (packages Service and ServieImpl) 
 to get a job done whenever it receives a request from the view or api layer, when it does it should not have access to the model objects and should always exchange neutral DTOs.

The service layer never accepts a model as input and never ever returns one either. This is another best practice that Spring enforces to implement  a layered architecture.



**Service Package**


The service package provides interfaces, that collect the calls related to the management of a specific entity in the project.
The Java interfaces are already defined (see file ServicePackage.zip) and the low level design must comply with these interfaces.


**ServiceImpl Package**

Contains Service classes that implement the Service Interfaces in the Service package.










# Low level design

<!-- Based on the official requirements and on the Spring Boot design guidelines, define the required classes (UML class diagram) of the back-end in the proper packages described in the high-level design section. -->


```plantuml
@startuml
scale max 1920 width
package "it.polito.ezgas.entity" {

    class GasStation {
        .. private data ..
        - Integer gasStationId
        - String gasStationName
        - String gasStationAddress
        - boolean hasDiesel
        - boolean hasSuper
        - boolean hasSuperPlus
        - boolean hasGas
        - boolean hasMethane
        - boolean hasPremiumDiesel
        - String carSharing 
        - double lat
        - double lon
        - Double dieselPrice
        - Double superPrice
        - Double superPlusPrice
        - Double gasPrice
        - Double methanePrice
        - Double premiumDieselPrice
        - Integer reportUser
        - String reportTimestamp
        - double reportDependability
        - User user
      
        __
      
        .. Constructor ..
        .. Simple Getters & Setters ..
    }

    class User {
        .. private data ..
        - Integer userId
        - String userName
        - String password
        - String email
        - Integer reputation
        - Boolean admin
      
        __
        
        .. Constructor ..
        .. Simple Getters & Setters ..
    }
/'
    class PriceReport {
        .. private data ..
        - Integer priceReportId
        - User user
	    - Double dieselPrice
        - Double superPrice
	    - Double superPlusPrice
	    - Double gasPrice
      
        __
        
        .. Constructor ..
        .. Simple Getters & Setters ..
    }
'/
    
}


package "it.polito.ezgas.dto" {

    class PriceReportDto {
        .. public data ..
        + Integer gasStationId
	    + Double dieselPrice
	    + Double superPrice
	    + Double superPlusPrice
	    + Double gasPrice
        + Double methanePrice
        + Double premiumDieselPrice
        + Integer userId
        __
      
        .. Constructor ..
        .. Getters & Setters ..
    }

    class LoginDto {
        .. protected data ..
        # Integer userId
        # String userName
        # String token
        # String email
        # Integer reputation
        # Boolean admin
      
        __
      
        .. Constructor ..
        .. Getters & Setters ..
    }
  
    class IdPwDto {
        .. private data ..
        + String user
	    + String pw	
      
        __
      
        .. Constructor ..
        .. Getters & Setters ..
    }

    class UserDto {
        .. protected data ..
        # Integer userId
        # String userName
        # String password
        # String email
        # Integer reputation
        # Boolean admin
      
        __
      
        .. Constructor ..
        .. Getters & Setters ..
    }
    
    class GasStationDto {
        .. protected data ..
        # Integer gasStationId
	    # String gasStationName
	    # String gasStationAddress
	    # boolean hasDiesel
        # boolean hasSuper
        # boolean hasSuperPlus
        # boolean hasGas
        # boolean hasMethane
        # boolean hasPremiumDiesel
        # double lat
        # double lon
        # Double dieselPrice
        # Double superPrice
        # Double superPlusPrice
        # Double gasPrice
        # Double methanePrice
        # Double premiumDieselPrice
        # Integer reportUser
        # UserDto userDto
        # String reportTimestamp
        # double reportDependability
        '+ List<PriceReportDto> priceReportDtos'
      
        .. private data ..
        -  String carSharing
      
        __
      
        .. Constructor ..
        .. Getters & Setters ..
    }
  
}


package "it.polito.ezgas.service" {
  
    interface UserService {
        + UserDto getUserById(Integer userId)
        + UserDto saveUser(UserDto userDto)
        + List<UserDto> getAllUsers()
        + Boolean deleteUser(Integer userId)
        + LoginDto login(IdPw credentials)
        + Integer increaseUserReputation(Integer userId)
        + Integer decreaseUserReputation(Integer userId)
  }
  
    interface GasStationService {
        + GasStationDto getGasStationById(Integer gasStationId)
        + GasStationDto saveGasStation(GasStationDto gasStationDto)
        + List<GasStationDto> getAllGasStations()
        + Boolean deleteGasStation(Integer gasStationId) 
        + List<GasStationDto> getGasStationsByGasolineType(String gasolinetype)
	    + List<GasStationDto> getGasStationsByProximity(double lat, double lon)
        + List<GasStationDto> getGasStationsByProximity(double lat, double lon, int radius)
	    + List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, int radius, 
       String gasolinetype, String carsharing)
	    + List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, 
       String carsharing)
	    + void setReport(Integer gasStationId, Double dieselPrice, Double superPrice, 
       Double superPlusPrice, Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId)
	    + List<GasStationDto> getGasStationByCarSharing(String carSharing)
  }

  GasStationDto -- GasStationService
  LoginDto -- UserService
  UserDto -- UserService
  IdPwDto -- UserService

  
}


package "it.polito.ezgas.serviceImpl" {
  
    class UserServiceImpl {
        .. private data ..
        - UserRepository repository
        - UserConverter uConverter
        __
        + UserServiceImpl(UserRepository repository, UserConverter uConverter)
        + UserDto getUserById(Integer userId)
        + UserDto saveUser(UserDto userDto)
        + List<UserDto> getAllUsers()
        + Boolean deleteUser(Integer userId)
        + LoginDto login(IdPw credentials)
        + Integer increaseUserReputation(Integer userId)
        + Integer decreaseUserReputation(Integer userId)
    }
  
    class GasStationServiceImpl {
         .. private data ..
        - UserRepository uRepository
        - GasStationRepository repository
        - GasStationConverter gsConverter
        __
        + GasStationServiceImpl(GasStationConverter gsConverte, GasStationRepository repository, UserRepository uRepository)
        + GasStationDto getGasStationById(Integer gasStationId)
        + GasStationDto saveGasStation(GasStationDto gasStationDto)
        + List<GasStationDto> getAllGasStations()
        + Boolean deleteGasStation(Integer gasStationId) 
        + List<GasStationDto> getGasStationsByGasolineType(String gasolinetype)
	    + List<GasStationDto> getGasStationsByProximity(double lat, double lon)
        + List<GasStationDto> getGasStationsByProximity(double lat, double lon, int radius)
	    + List<GasStationDto> getGasStationsWithCoordinates(double lat, double lon, int radius, 
       String gasolinetype, String carsharing)
	    + List<GasStationDto> getGasStationsWithoutCoordinates(String gasolinetype, String carsharing)
	    + void setReport(Integer gasStationId, Double dieselPrice, Double superPrice, 
       Double superPlusPrice, Double gasPrice, Double methanePrice, Double premiumDieselPrice, Integer userId)
	    + List<GasStationDto> getGasStationByCarSharing(String carSharing)
	    + GasStation updateDependability(GasStation gasStation)
    }
    UserService -- UserServiceImpl
    User -- UserServiceImpl
    UserDto -- UserServiceImpl
    IdPwDto -- UserServiceImpl
    LoginDto -- UserServiceImpl
        
    GasStationService -- GasStationServiceImpl
    GasStation -- GasStationServiceImpl
    GasStationDto -- GasStationServiceImpl

}  



package "it.polito.ezgas.controller" {
    
	class UserController {
        .. protected data ..
	    # UserService userService
        __
	    + UserDto getUserById(Integer userId)
        + List<UserDto> getAllUsers()
	    + UserDto saveUser(UserDto userDto)  
	    + Boolean deleteUser(Integer userId) 
	    + Integer increaseUserReputation(Integer userId) 
	    + Integer decreaseUserReputation(Integer userId) 
	    + LoginDto login(IdPw credentials) 
	}

	class HomeController {
	    + String admin() 
	    + String index() 
	    + String map() 
	    + String login() 
	    + String update() 
	    + String signup() 
	}

	class GasStationController {
         .. protected data ..
	    # GasStationService gasStationService
        __
	    + GasStationDto getGasStationById(Integer gasStationId) 
	    + List<GasStationDto> getAllGasStations() 
	    + void saveGasStation(GasStationDto gasStationDto) 	
	    + void deleteGasStation(Integer gasStationId) 
	    + List<GasStationDto> getGasStationsByGasolineType(String gasolinetype) 
	    + List<GasStationDto> getGasStationsByProximity(Double myLat, Double myLon, Integer myRadius) 
	    + List<GasStationDto> getGasStationsWithCoordinates(Double myLat, Double myLon, Integer myRadius,
        String gasolineType, String carSharing) 
	    + void setGasStationReport(PriceReportDto priceReportDto) 
	}

    UserService -- UserController
    GasStationService -- GasStationController
    PriceReportDto -- GasStationController

}


package "it.polito.ezgas.converter" {

    class UserConverter {
        + {static} UserDto convertToDto(User user)
        + {static} User convertToEntity(UserDto userDto)
    }
/'
    class PriceReportConverter {
        + {static} PriceReportDto toPriceReportDto(PriceReport priceReport)
        + {static} PriceReport toPriceReport(PriceReportDto priceReportDto)
    }
'/
    class GasStationConverter {
        + {static} GasStationDto convertToDto(GasStation gasStation)
        + {static} GasStation convertToEntity(GasStationDto gasStationDto)
    }

    UserConverter -- UserServiceImpl
    UserConverter -- User
    UserConverter -- UserDto
    GasStationConverter -- GasStationDto 
    GasStationConverter -- GasStationServiceImpl
    GasStationConverter -- GasStation
    GasStationConverter -- UserConverter
}


package "it.polito.ezgas.repository" {

    interface UserRepository {
        + User findByPasswordAndEmail(String pw, String user)
        + User findByEmail(String email)
        + List<User> findAll()
        + User findOne(Integer userId)
        + void delete(Integer userId)
        + User save(User)
    }

    interface GasStationRepository {
        + List<GasStation> findByHasDieselTrueOrderByDieselPrice()
        + List<GasStation> findByHasGasTrueOrderByGasPrice()
        + List<GasStation> findByHasMethaneTrueOrderByMethanePrice()
        + List<GasStation> findByHasSuperTrueOrderBySuperPrice()
        + List<GasStation> findByHasSuperPlusTrueOrderBySuperPlusPrice()
        + List<GasStation> findByHasPremiumDieselTrueOrderByPremiumDieselPrice()
        + List<GasStation> findByProximity(double latitude, double longitude, int radius)
        + List<GasStation> findByCarSharing(String carSharing)
        + List<GasStation> findAll()
        + GasStation findOne(Integer gasStationId)
        + void delete(Integer gasStationId)
        + GasStation save(GasStation)
    }
/'
    interface PriceReportRepository {
        + void setPriceReport(Integer priceReportId, User user, Double dieselPrice, Double superPrice, 
       Double superPlusPrice, Double gasPrice)
    }
'/
    UserServiceImpl -- UserRepository
    GasStationServiceImpl -- GasStationRepository
    GasStationServiceImpl -- UserRepository
    User -- UserRepository
    GasStation -- GasStationRepository
}
@enduml
```



# Verification traceability matrix

<!-- \<for each functional requirement from the requirement document, list which classes concur to implement it> -->


| FR / Classes                             | GasStation | User | LoginDto | IdPwDto | UserDto | GasStationDto |PriceReportDto | UserService | GasStationService | UserServiceImpl        | GasStationServiceImpl | UserController | HomeController | GasStationController | UserConverter | GasStationConverter | UserRepository | GasStationRepository |
| ---------------------------------------- | ---------- | ---- | -------- | ------- | ------- | ------------- | ------------- | ----------- | ----------------- | ---------------------- | --------------------- | -------------- | -------------- | -------------------- | ------------- | ------------------- | -------------- | -------------------- |
|  FR1 (FR1.1, FR1.2, FR1.3, FR1.4)        |            |  x   |          |         | x       |               |               |  x          |                   |      x                 |                       | x              | x              |                      |  x            |                     | x              |                      |
|  FR2                                     |            |  x   | x        | x       |  x      |               |               |  x          |                   |      x                 |                       | x              | x              |                      | x             |                     |  x             |                      |
|  FR3 (FR3.1, FR3.2, FR3.3)               | x          |      |          |         |         | x             |               |             | x                 |                        | x                     |                | x              | x                    |               | x                   |                | x                    |
|  FR4 (FR4.1, FR4.2, FR4.3, FR4.4, FR4.5) | x          |      |          |         |         | x             |               |             | x                 |                        | x                     |                | x              | x                    |               | x                   |                | x                    |
|  FR5 (FR5.1, FR5.2, FR5.3)               | x          |  x   |          |         | x       | x             |   x           |             | x                 |                        | x                     |                | x              | x                    |  x            | x                   |  x             | x                    |
              









# Verification sequence diagrams 
<!-- \<select key scenarios from the requirement document. For each of them define a sequence diagram showing that the scenario can be implemented by the classes and methods in the design> -->

**UC1 - Create User account.**
```plantuml
@startuml
/' UseCase 1'/
actor User
participant "UserController" as A
participant "UserServiceImpl" as B
participant "UserConverter" as C
participant "UserRepository" as D


User -> A: "[POST]../saveUser"
activate A
A -> B: saveUser(userDto)
activate B
B -> D: findByEmail(email)
activate D
D --> B
deactivate D
B -> C: convertToEntity(userDto)
activate C
C --> B
deactivate C
B -> D: save(user)
activate D
D --> C: convertToDto(user)
deactivate D
activate C
C --> B
deactivate C
B --> A
deactivate B
A --> User: UserDto
deactivate A

@enduml
```

**UC2 - Modify User account.**

The sequence diagram from the backend perspective is equal to the one of UC1.

**UC3 - Delete User account.**
```plantuml
@startuml
/' UseCase 3'/
actor User 
participant "UserController" as A
participant "UserServiceImpl" as B
participant "UserRepository" as D


User -> A: "[DELETE]../deleteUser/{userId}"
note left
User can delete only its
own account while admin
can delete any account
end note
activate A
A -> B: deleteUser(userId)
activate B
B -> D: delete(userId)
activate D
D --> B
deactivate D
B --> A
deactivate B
A --> User: Boolean
deactivate A

@enduml
```

**UC4 - Create Gas Station.**
```plantuml
@startuml
/' UseCase 4'/
actor Administrator
participant "GasStationController" as A
participant "GasStationServiceImpl" as B
participant "GasStationConverter" as C
participant "GasStationRepository" as D

Administrator -> A: "[POST]../saveGasStation"
activate A
A -> B: saveGasStation(gasStationDto)
activate B
B -> C: convertToEntity(gasStationDto)
activate C
C --> B
deactivate C
B -> D: save(gasStation)
activate D
D --> C: convertToDto(gasStation)
deactivate D
activate C
C --> B
deactivate C
B --> A
deactivate B
A --> Administrator: void
deactivate A
@enduml
```

**UC5 - Modify Gas Station information.**

The sequence diagram from the backend perspective is equal to the one of UC4.

**UC6 - Delete Gas Station.**
```plantuml
@startuml
/' UseCase 6'/
actor Administrator
participant "GasStationController" as A
participant "GasStationServiceImpl" as B
participant "GasStationRepository" as D

Administrator -> A: "[DELETE]../deleteGasStation/{gasStationId}"
activate A
A -> B: deleteGasStation(gasStationId)
activate B
B -> D: delete(gasStationId)
activate D
D --> B
deactivate D
B --> A
deactivate B
A --> Administrator: void
deactivate A
@enduml
```

**UC7 - Report fuel price for a gas station.**
```plantuml
@startuml
/' UseCase 7'/
actor User
participant "GasStationController" as A
participant "GasStationServiceImpl" as B
participant "UserRepository" as C
participant "GasStationRepository" as D


User -> A: "[POST]../setGasStationReport"
activate A
A -> B: setReport(...)
activate B
B -> D: findOne(gasStationId)
activate D
D --> B
deactivate D
B -> C: findOne(userId)
activate C
C --> B
deactivate C
B -> D: save(gasStation)
activate D
D --> B
deactivate D
B --> A
deactivate B
A --> User: void
deactivate A
@enduml
```

**UC8 - Obtain price of fuel for gas stations in a certain geographic area.**
```plantuml
@startuml
/' UseCase 8'/
actor User
participant "GasStationController" as A
participant "GasStationServiceImpl" as B
participant "GasStationConverter" as C
participant "GasStationRepository" as D

User -> A: "[GET]../searchGasStationByProximity/{myLat}/{myLon}/{myRadius}"
activate A
A -> B: getGasStationsByProximity(myLat,myLon,myRadius)
activate B
B -> D: findByProximity(myLat,myLon,myRadius)
activate D
D --> B
deactivate D
B -> B: updateDependability(gasStation)
B -> C: convertToDto(gasStation)
activate C
C --> B
deactivate C 
B --> A
deactivate B
A --> User: List<GasStationDto>
deactivate A
@enduml
```

**UC9 - Update trust level of price list.**
```plantuml
@startuml
/' UseCase 9'/
participant "GasStationServiceImpl:updateDependability" as E
participant "GasStation" as A
participant "User" as C


activate E
E -> A: getUser()
activate A
A -> C: getReputation()
activate C
C --> A
deactivate C
A --> E
deactivate A
E -> A: setReportDependability(newReportDependabiity)
activate A
A --> E
deactivate A
deactivate E
@enduml
```

**Scenario 10.1 - Evaluate price. Price is correct.**
```plantuml
@startuml
/' Screnario 10.1'/
actor User
participant "UserController" as A
participant "UserServiceImpl" as C
participant "UserConverter" as B
participant "UserRepository" as D
participant "GasStationController" as E
participant "GasStationServiceImpl" as G
participant "GasStationConverter" as F
participant "GasStationRepository" as H
User -> E: "[GET]../getGasStation/{gasStationId}"
activate E
E -> G: getGasStationById(gasStationId)
activate G
G -> H: findOne(gasStationId)
activate H
H --> F: convertToDto(gasStation)
deactivate H
activate F
F --> G
deactivate F
G --> E
deactivate G
E --> User: GasStationDto
deactivate E
User -> A: "[GET]../getUser/{userId}"
activate A
A -> C: getUserById(userId)
activate C
C -> D: findOne(userId)
activate D
D --> B: convertToDto(user)
deactivate D
activate B
B --> C
deactivate B
C --> A
deactivate C
A --> User: UserDto
deactivate A
User -> A: "[POST]../increaseUserReputation/{userId}"
activate A
A -> C: increaseUserReputation(userId)
activate C
C -> D: findOne(userId)
activate D
D --> C
deactivate D
C --> A
deactivate C
A --> User: Integer
deactivate A
@enduml
```

**Scenario 10.2 - Evaluate price. Price is wrong.**
```plantuml
@startuml
/' Screnario 10.2'/
actor User
participant "UserController" as A
participant "UserServiceImpl" as C
participant "UserConverter" as B
participant "UserRepository" as D
participant "GasStationController" as E
participant "GasStationServiceImpl" as G
participant "GasStationConverter" as F
participant "GasStationRepository" as H
User -> E: "[GET]../getGasStation/{gasStationId}"
activate E
E -> G: getGasStationById(gasStationId)
activate G
G -> H: findOne(gasStationId)
activate H
H --> F: convertToDto(gasStation)
deactivate H
activate F
F --> G
deactivate F
G --> E
deactivate G
E --> User: GasStationDto
deactivate E
User -> A: "[GET]../getUser/{userId}"
activate A
A -> C: getUserById(userId)
activate C
C -> D: findOne(userId)
activate D
D --> B: convertToDto(user)
deactivate D
activate B
B --> C
deactivate B
C --> A
deactivate C
A --> User: UserDto
deactivate A
User -> A: "[POST]../decreaseUserReputation/{userId}"
activate A
A -> C: decreaseUserReputation(userId)
activate C
C -> D: findOne(userId)
activate D
D --> C
deactivate D
C --> A
deactivate C
A --> User: Integer
deactivate A
@enduml
```