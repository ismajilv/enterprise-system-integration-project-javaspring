RentIt backend
============

Sample data
------------

Sample data is added in

> src/main/java/com/rentit/test/TestDataProvider.java

Add more data to suit your needs!

NB!
------------

If some of the examples below don't work: 

  * Check port (starting your Spring boot app prints out if the port is 8080, 8090 or third);
  * Include header 'Content-Type' with value 'application/json';
  * Include header 'Accept' with value 'application/json';
  * Check IDs, browsing the test data.  

How to fetch pending purchase orders
------------

To retrieve pending purchase orders, send a GET to http://localhost:8090/api/sales/orders

How to accept a purchase order
------------

To accept, send a POST to http://localhost:8080/api/sales/orders/{id}/accept?piiId={piiId}

Parameter piiId of accept request should be the plant inventory item ID (corresponding to requested plant inventory entry) that is to be allocated for order.

Example for sample data would be POST to http://localhost:8080/api/sales/orders/9/accept?piiId=5

How to reject a purchase order
------------
To reject, send a POST to http://localhost:8080/api/sales/orders/{id}/reject

**Note: The following endpoints are mainly for machine-to-machine communication!**

How to browse the plant catalog
------------

Sample HTTP query to browse the plant catalog:

GET http://localhost:8090/api/sales/plants?name=exc&startDate=2018-01-01&endDate=2018-10-10

Include the part of plant name to search in parameter 'name'.

How to create a purchase order
------------

To create a purchase order, send a POST to http://localhost:8090/api/sales/orders

**Payload for sample data**

~~~json
{
"plant": {
"_id": 3
},
"rentalPeriod": {
"startDate": "2018-06-01",
"endDate": "2018-06-08"
}
}
~~~

How to fetch a purchase order by ID
------------

Send a GET to http://localhost:8090/api/sales/orders/{id} .

**URL for sample data (after creating PO)**

http://localhost:8090/api/sales/orders/9

TODOs
------------

Here is a list of known ongoing issues:

  * Explain extensions in this README;
  * Do integrations for BuildIt;