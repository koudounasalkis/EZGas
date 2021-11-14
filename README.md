# EZGas
Final project for the Software Engineering exam at Politecnico di Torino about **EZGas**, a crowdsourcing service that allows users to collect prices of fuels in different gas stations and to locate gas stations in an area, along with the prices they practice. EZGas is supported by a web application (accessible both via smartphone or PC).

## Team members
- Giuliano Ettore
- Koudounas Alkis
- Pizzato Francesco

## Folders structure
```
\EZGas
    \docs               # Contains the documents produced for the several milestones
    \GUI_Sketch         # Contains some sketch produced for the milestones
    \GUI_Test           # Contains the .sikuli folders of the performed tests
        \testCreateGasStation
        \testCreateGasStationInvalid
        \testDeleteGasStation
        \testDeleteUser
        ...
    \src                
        \main           # Includes the code of the application
            \java       # Includes the backend Java code fully developed
                \exception
                \it
                    \controller
                    \converter
                    \dto
                    \entity
                    ...
            \resources  # Includes the frontend code given by the professors
        \test           # Includes the test code
```
