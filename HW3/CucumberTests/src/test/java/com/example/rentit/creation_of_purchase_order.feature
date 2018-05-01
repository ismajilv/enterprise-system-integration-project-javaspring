Feature: Creation of Purchase Order
  As a Rentit's customer
  So that I start with the construction project
  I want hire all the required machinery
  As a BuildIt's customer
  So that I avoid excessive or unnecessary plant hiring
  I want to approve the plant hire requests

  Background: Plant catalog
    Given the following plant catalog
      | id | name           | description                      | price  |
      |  1 | Mini Excavator | 1.5 Tonne Mini excavator         | 150.00 |
      |  2 | Mini Excavator | 3 Tonne Mini excavator           | 200.00 |
      |  3 | Midi Excavator | 5 Tonne Midi excavator           | 250.00 |
      |  4 | Midi Excavator | 8 Tonne Midi excavator           | 300.00 |
      |  5 | Maxi Excavator | 15 Tonne Large excavator         | 400.00 |
      |  6 | Maxi Excavator | 20 Tonne Large excavator         | 450.00 |
      |  7 | HS Dumper      | 1.5 Tonne Hi-Swivel Dumper       | 150.00 |
      |  8 | FT Dumper      | 2 Tonne Front Tip Dumper         | 180.00 |
    And the following inventory
      | id | plantInfo | serialNumber | equipmentCondition |
      |  1 |     1     | exc-mn1.5-01 | SERVICEABLE        |
      |  2 |     2     | exc-mn3.0-01 | SERVICEABLE        |
      |  3 |     3     | exc-md5.0-01 | SERVICEABLE        |
      |  4 |     4     | exc-md8.0-01 | SERVICEABLE        |
      |  5 |     5     | exc-max15-01 | SERVICEABLE        |
      |  6 |     6     | exc-max20-01 | SERVICEABLE        |
      |  7 |     7     | dmp-hs1.5-01 | SERVICEABLE        |
      |  8 |     8     | dmp-ft2.0-01 | SERVICEABLE        |
    And a BuildIt's user is in the "Plant Catalog" web page
    And a Rentit's user is in the "Plant Catalog" web page
    And no purchase order exists in the system

  Scenario: Creating and accepting plant hire request
    When the BuildIt's user queries the plant catalog for an "Excavator" available from "5/20/2018" to "5/22/2018" from "Supplier 1" to be used on site "Site 1"
    Then 6 plants are shown
    When the BuildIt's user selects a "3 Tonne Mini excavator"
    Then the BuildIt's user sees the price and availability of the the selected plant
    When the BuildIt's user accepts the plant hire request
    Then the RentIt's user sees a plant hire request for "3 Tonne Mini excavator"
    When the RentIt's user accepts the plant hire request
    Then the BuildIt's user sees that the state of the plant hire request is "Accepted"

  Scenario: Creating a plant hire request that will be rejected by RentIt
    When the BuildIt's user queries the plant catalog for an "Excavator" available from "5/20/2018" to "5/22/2018" from "Supplier 1" to be used on site "Site 1"
    Then 6 plants are shown
    When the BuildIt's user selects a "3 Tonne Mini excavator"
    Then the BuildIt's user sees the price and availability of the the selected plant
    When the BuildIt's user accepts the plant hire request
    Then the RentIt's user sees a plant hire request for "3 Tonne Mini excavator"
    When the RentIt's user rejects the plant hire request
    Then the BuildIt's user sees that the state of the plant hire request is "Rejected"

  Scenario: Creating a plant hire request that will be rejected by BuildIt user
    When the BuildIt's user queries the plant catalog for an "Excavator" available from "5/20/2018" to "5/22/2018" from "Supplier 1" to be used on site "Site 1"
    Then 6 plants are shown
    When the BuildIt's user selects a "3 Tonne Mini excavator"
    Then the BuildIt's user sees the price and availability of the the selected plant
    When the BuildIt's user reject the plant hire request
    Then the RentIt's user doesn't see a plant hire request for "3 Tonne Mini excavator"