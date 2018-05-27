BuildIt backend
============

NB!
------------
RentIt back-end needs to be running in parallel to BuildIt for integrations between the two to work!

You can specify RentIt base URL in application.properties

Sample data
------------

Sample data is added in

> /src/main/java/com/buildit/test/TestDataProvider.java

Add more data to suit your needs!

How to browse the plants catalog
------------

Query GET on */api/plants* with search term and availability period.

**Sample URL for querying excavators**

http://localhost:8080/api/plants?name=exc&startDate=2018-01-01&endDate=2018-10-10

How to create a plant hire request
------------

Sample HTTP queries to prepare for creation of a plant hire request:

  * Site: GET http://localhost:8080/api/sites , pick a site and extract its ID;
  * Supplier: GET http://localhost:8080/api/suppliers , pick a supplier;
  * Plant: GET http://localhost:8080/api/plants , pick a plant and extract href;

To create plant hire request, send a POST to http://localhost:8080/api/requests

**Payload with sample data**

~~~json
{
	"constructionSiteId": 2,
	"supplierId": 4,
	"plantHref": "http://localhost:8090/api/plants/3",
	"rentalPeriod" : {
	    "startDate" : "2018-05-25",
	    "endDate" : "2018-05-30"
	}
}
~~~

How to update a plant hire request
------------

The same content as for create, but you only need to provide the fields that need to be updated. 

To update plant hire request, send a PUT to http://localhost:8080/api/requests/{id}

**Payload and address for sample data**

PUT to http://localhost:8080/api/requests/7

~~~json
{
	"constructionSiteId": 2,
	"supplierId": 4,
	"plantHref": "http://ramirent.ee:9550/api/plants/2",
	"rentalPeriod" : {
	    "startDate" : "2018-05-29",
	    "endDate" : "2018-07-03"
	}
}
~~~

How to add a comment to a plant hire request
------------

Send a POST to http://localhost:8080/api/requests/{id}/addComment with text in JSON field "value".

**URL and payload for sample data**

http://localhost:8080/api/requests/3/addComment

~~~json
{
"value": "abc"
}
~~~

How to accept or reject a plant hire request
------------

To accept, send a POST to http://localhost:8080/api/requests/{id}/accept

To reject, send a POST to http://localhost:8080/api/requests/{id}/reject

**URL for accept with sample data**

POST to http://localhost:8080/api/requests/3/accept

How to notify of purchase order decision from RentIt
------------

Send a POST to http://localhost:8080/callbacks/orderStateChanged

JSON contents should be RentIt self href together with the new status.

**JSON with sample data**

~~~json
{
"href": "http://ramirent.ee:5999/api/orders-list/584",
"value": "APPROVED"
}
~~~

TODOs
------------

Here is a list of known ongoing issues:

    