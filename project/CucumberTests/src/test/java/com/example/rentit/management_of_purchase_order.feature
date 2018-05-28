Feature: Management of Purchase Order
  As a RentIt's plant depot employee
  So that I avoid renting unavailable plants
  I want to be able to update Purchase Order status

  Background: Plant catalog
    Given the test data has been set up
    And RentIt's employee is in the RentIt webpage "Plant Depot Employee" tab

  Scenario: Dispatching, delivering and returning purchase order
    When employee selects "Plant Depot Employee" tab
    Then 1 purchase orders are shown
    And po with for "excavator type 2" of them has status "ACCEPTED"
    When employee clicks "dispatch" button PO with name "excavator type 2"
    Then po with for "excavator type 2" of them has status "PLANT_DISPATCHED"
    When employee clicks "deliver" button PO with name "excavator type 2"
    Then po with for "excavator type 2" of them has status "PLANT_DELIVERED"
    When employee clicks "returnButton" button PO with name "excavator type 2"
    Then po with for "excavator type 2" of them has status "PLANT_RETURNED"

  Scenario: Client rejects plant
    When employee selects "Plant Depot Employee" tab
    Then 1 purchase orders are shown
    And po with for "excavator type 2" of them has status "ACCEPTED"
    When employee clicks "dispatch" button PO with name "excavator type 2"
    Then po with for "excavator type 2" of them has status "PLANT_DISPATCHED"
    When employee clicks "reject" button PO with name "excavator type 2"
    Then po with for "excavator type 2" of them has status "REJECTED_BY_CUSTOMER"

