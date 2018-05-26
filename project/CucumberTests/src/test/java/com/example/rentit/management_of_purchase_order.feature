Feature: Management of Purchase Order
  As a RentIt's plant depot employee
  So that I avoid renting unavailable plants
  I want to be able to update Purchase Order status

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
      |  9 | FT Dumper      | 3 Tonne Front Tip Dumper         | 180.00 |
      |  10 | FT Dumper     | 4 Tonne Front Tip Dumper         | 180.00 |
      |  11 | FT Dumper     | 5 Tonne Front Tip Dumper         | 180.00 |
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
      |  9 |     9     | dmp-ft3.0-01 | SERVICEABLE        |
      |  10|     10    | dmp-ft4.0-01 | SERVICEABLE        |
    And the following suppliers
      | id | name     |
      |  1 | Ramirent |
      |  2 | Cramo    |
    And the following construction sites
      | id | address          |
      |  1 | Viru 1, Tallinn  |
      |  2 | Pepleri 1, Tartu |
      |  3 | Kase 12, Narva   |
    And plant hire requests have been set up
    And purchase orders have been set up
   # And the following Purchase Orders
   #   | id | plantInfo        | status                |
   #   | 1  | 1                | PENDING               |
   #   | 2  | 2                | ACCEPTED              |
   #   | 3  | 3                | REJECTED              |
   #   | 4  | 4                | DISPATCHED            |
   #   | 5  | 5                | DELIVERED             |
   #   | 6  | 6                | RETURNED              |
   #   | 7  | 7                | REJECTED_BY_CUSTOMER  |
    And employee is in the RentIt webpage "Query Catalog" tab
    And RentIt's employee is in the RentIt webpage "Plant Depot Employee" tab

  Scenario: Dispatching purchase order
    When employee selects "Plant Depot Employee" tab
    Then 7 purchase orders are shown
    And 1 of them has status "ACCEPTED"
    When employee dispatches "ACCEPTED" purchase order with id 2
    Then the status of po with id 2 changes to "DISPATCHED"
    And button "Deliver" is enabled for po with id 2
    And button "Rejected by customer" is enabled for po with id 2
    And button "Dispatch" is disabled for po with id 2
    When purchase order status is not "Accepted"
    Then button "Dispatch" is disabled
    When disabled "Dispatch" button is clicked
    Then status of the purchase order is not changed