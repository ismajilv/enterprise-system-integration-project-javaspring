Feature: Creation of Purchase Order
  As a BuildIt's site engineer
  So that I start with the construction project
  I want hire all the required machinery
  As a BuildIt's work engineer
  So that I avoid excessive or unnecessary plant hiring
  I want to approve the plant hire requests
  As a RentIt's employee
  So that I avoid renting unavailable plants
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
    And the following suppliers
      | id | name     |
      |  1 | Ramirent |
      |  2 | Cramo    |
    And the following construction sites
      | id | address          |
      |  1 | Viru 1, Tallinn  |
      |  2 | Pepleri 1, Tartu |
      |  3 | Kase 12, Narva   |
    And site engineer is in the BuildIt webpage "Query Catalog" tab
    And work engineer is in the BuildIt webpage "Approval" tab
    And RentIt's employee is in the RentIt webpage "Plant Hire Requests" tab
    And no purchase order exists in the RentIts system
    And no plant hire requests exists in the BuildIt system

  Scenario: Creating plant hire request that is accepted by work engineer and RentIt
    When site engineer queries the plant catalog for an "Excavator" available from "5/20/2018" to "5/22/2018"
    Then 6 plants are shown including "3 Tonne Mini excavator" with price 600
    When site engineer selects a "3 Tonne Mini excavator"
    Then tab is changed to "Review order"
    When site engineer selects supplier "Cramo"
    And selects construction site "Viru 1, Tallinn"
    And pushes "Create Plant Hire Request" button
    Then 1 pending plant hire request is/are shown for the work engineer
    When work engineer accepts the plant hire request
    Then 1 pending plant hire request is/are shown for the RentIt's employee
    When the RentIt's employee accepts the plant hire request
    Then the site engineer sees that the state of the plant hire request is "Accepted"

  #Scenario: Creating plant hire request that is accepted by work engineer but rejected by RentIt
  #  When site engineer queries the plant catalog for an "Excavator" available from "5/20/2018" to "5/22/2018"
  #  Then 6 plants are shown including "3 Tonne Mini excavator" with price 600
  #  When site engineer selects a "3 Tonne Mini excavator"
  #  Then tab is changed to "Review order"
  #  When site engineer selects supplier "Cramo"
  #  And selects construction site "Viru 1, Tallinn"
  #  And pushes "Create Plant Hire Request" button
  #  Then 1 pending plant hire request is/are shown for the work engineer
  #  When work engineer rejects the plant hire request
  #  Then 0 pending plant hire request is/are shown for the RentIt's employee
  #  When the RentIt's employee rejects the plant hire request
  #  Then the site engineer sees that the state of the plant hire request is "Rejected"

#  Scenario: Creating a plant hire request that is rejected by work engineer
 #   When site engineer queries the plant catalog for an "Excavator" available from "5/20/2018" to "5/22/2018"
  #  Then 6 plants are shown including "3 Tonne Mini excavator" with price 600
   # When site engineer selects a "3 Tonne Mini excavator"
    #Then tab is changed to "Review order"
    #When site engineer selects supplier "Cramo"
    #And selects construction site "Viru 1, Tallinn"
    #And pushes "Create Plant Hire Request" button
    #Then 1 pending plant hire request is/are shown for the work engineer
    #When work engineer rejects the plant hire request
    #Then 0 pending plant hire request is/are shown for the RentIt's employee