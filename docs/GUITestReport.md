# GUI  Testing Documentation 

Authors: Giuliano Ettore, Koudounas Alkis, Pizzato Francesco

Date: 30/05/2020

Version: 1.0

# GUI testing

This part of the document reports about testing at the GUI level. Tests are end to end, so they should cover the Use Cases, and corresponding scenarios.

## Coverage of Scenarios and FR


<!-- Complete this table (from IntegrationApiTestReport.md) with the column on the right. In the GUI Test column, report the name of the .py  file with the test case you created. -->


### 
         
| Scenario ID                                                                                   | Functional Requirements covered | GUI Test(s)                                        |
| --------------------------------------------------------------------------------------------- | ------------------------------- | -------------------------------------------------- | 
| UC1 - Create user account                                                                     | FR1.1                         | _`testUserSignup.py`_                                |
| UC2.1 - User modify his/her account                                                           | FR1.1, FR2                    | _`testUserModifyAccount.py`_                         |
| UC2.2 - Admin modify user account                                                             | FR1.1, FR1.3, FR2             | _`testAdminModifyAccount.py`_                        |
| UC3 - Delete user account                                                                     | FR1.2, FR2                    | _`testDeleteUser.py`_                                |
| UC4 - Create Gas Station                                                                      | FR3.1, FR2                    | _`testCreateGasStation.py`_                          |
| UC4.1 - Create Gas Station with invalid coordinates                                           | FR3.1, FR2                    | _`testCreateGasStationInvalid.py`_                   |
| UC5 - Modify Gas Station information                                                          | FR3.1, FR2, FR3.3             | _`testModifyGasStation.py`_                          |
| UC6 - Delete Gas Station                                                                      | FR3.2, FR2                    | _`testDeleteGasStation.py`_                          |
| UC7 - Report fuel price for a gas station                                                     | FR5.1, FR1.4, FR2             | _`testReport.py`_                                    |
| UC7.1 - Report invalid fuel price for a gas station                                           | FR5.1, FR1.4, FR2             | _`testInvalidReport.py`_                             |
| UC8.1 - Obtain fuel prices in a certain geographic area                                       | FR4.1, FR4.2, FR4.3, FR4.4    | _`testFindGasStations.py`_                           |
| UC8.2 - Obtain fuel prices in a certain geographic area filtered by car sharing               | FR4.1, FR4.2, FR4.3, FR4.4, FR4.5 | _`testFindGasStationsCarSharing.py`_             |
| UC8.3 - Obtain fuel prices in a certain geographic area filtered by fuel type                 | FR4.1, FR4.2, FR4.3, FR4.4, FR4.5 | _`testFindGasStationsFuelType.py`_               |
| UC8.4 - Obtain fuel prices in a certain geographic area filtered by fuel type and car sharing | FR4.1, FR4.2, FR4.3, FR4.4, FR4.5 | _`testFindGasStationsCarSharingFuelType.py`_     |
| UC9 - Update trust level of price list                                                        | FR5.2                         | _`testEvaluteCorrectPrice.py`_                       |
|                                                                                               |                               | _`testEvaluteWrongPrice.py`_                         |
| UC10.1 - Evaluate correct price                                                               | FR5.3, FR1.4, FR2             | _`testEvaluteCorrectPrice.py`_                       |
| UC10.2 - Evaluate wrong price                                                                 | FR5.3, FR1.4, FR2             | _`testEvaluteWrongPrice.py`_                         |
         


# REST  API  Testing

This part of the document reports about testing the REST APIs of the back end. The REST APIs are implemented by classes in the Controller package of the back end. 
Tests should cover each function of classes in the Controller package

## Coverage of Controller methods


<Report in this table the test cases defined to cover all methods in Controller classes >

All the REST API tests can be found in TestController class.

| class.method name                                          | Functional Requirements covered   | REST  API Test(s)                      | 
| ---------------------------------------------------------- | --------------------------------- | -------------------------------------  | 
| _`UserController.saveUser()`_                              | FR1.1                             | _`testSaveUser()`_                     | 
| _`UserController.deleteUser()`_                            | FR1.2                             | _`testDeleteUser()`_                   |
| _`UserController.getAllUsers()`_                           | FR1.3                             | _`testGetAllUsers()`_                  |
| _`UserController.getUserById()`_                           | FR1.4                             | _`testGetUser()`_                      | 
| _`UserController.login()`_                                 | FR2                               | _`testLogin()`_                        |
| _`UserController.increaseUserReputation()`_                | FR5.3                             | _`testIncreaseUserReputation()`_       | 
| _`UserController.decreaseUserReputation()`_                | FR5.3                             | _`testDecreaseUserReputation()`_       | 
| _`GasStationController.saveGasStation()`_                  | FR3.1                             | _`testSaveGasStation()`_               |   
| _`GasStationController.deleteGasStation()`_                | FR3.2                             | _`testDeleteGasStation()`_             | 
| _`GasStationController.getAllGasStations()`_               | FR3.3                             | _`testGetAllGasStations()`_            |
| _`GasStationController.getGasStationById()`_               | FR4                               | _`testGetGasStationById()`_            | 
| _`GasStationController.getGasStationsByProximity()`_       | FR4.1, FR4.3                      | _`testGetGasStationsByProximity()`_    | 
| _`GasStationController.getGasStationsByGasolineType()`_    | FR4.3, FR4.4, FR4.5               | _`testGetGasStationsByGasolineType()`_ | 
| _`GasStationController.getGasStationsWithCoordinates()`_   | FR4.1, FR4.3, FR4.4, FR4.5        | _`testGetGasStationsWithCoordinates()`_| 
| _`GasStationController.setGasStationReport()`_             | FR5.1                             | _`testSetGasStationReport()`_          | 


           