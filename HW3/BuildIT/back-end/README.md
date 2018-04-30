BuildIt backend
============

NB!
------------
RentIt back-end needs to be running in parallel to BuildIt for integrations between the two to work!

Sample data
------------

Sample data is added in

> /src/main/java/com/buildit/test/TestDataProvider.java

Add more data to suit your needs!

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
	"plantHref": "http://ramirent.ee:9550/api/plants/2",
	"rentalPeriod" : {
	    "startDate" : "2018-04-29",
	    "endDate" : "2018-05-03"
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

  * Documentation about design decisions;
  * Do integrations in RentItIntegrationsService;

Design decisions
------------

In general, design followed Occam's razor paradigm. The application is still not ready for the real world after this homework, so some corners were cut to keep reduce complexity with the price of changes later. Still, there are many endpoints and corresponding service methods that have currently no real value for the real use cases. Still they helped at least me (Kristjan) to sanity check the database state, assembling DTOs etc. during the development. Some examples are CommentController or enabling to retrieve all purchase orders. 

Handling of employees was added, keeping in mind that security will be added in future.

Requesting and approving engineer are therefore added with the sample data to the system and retrieving logged in user gets mocked.

Later on, employee's role will be checked in order to authorize for the operations. It may also be necessary to enable several roles for the same employee row.

Total was made into value object, keeping open the possibility to add currency.

There are JSON objects RentItPOStatusUpdateDTO, StringDTO that serve the sole purpose to hold a value. There are also some DTOs like CreatePlantHireRequestDTO or RentItPurchaseOrderDTO that only serve to unmarshal necessary data from front-end or RentIt.

Maybe DTOs could be removed for business period and money, but currently the responsibilities of belonging to domain and transferring data were split up.

Comments are not meant to be modified, but were still kept as full domain citizens for the need to provide them with IDs and binding to a plant inventory hire request. The same is valid for construction site.
 
A decision was made to return data transfers already from the service layer. It could be argued that service layer should be the last layer that can edit the domain objects graph to support this decision.

A callback endpoint is provided in RentItCallbackController for RentIt to update its purchase order. 

A major nuisance were hateoas links, that did not get generated correctly. This area needs a rework.

Transaction boundaries were marked at the service layer methods.

Updating a resource is implemented in PlantHireRequestService.updateRequest that gets the same data as creating one, but now optionally fields can be left empty and only the data that is provided gets updated.

Integrations with RentIt were written with keeping open the possibility that later on, connecting to other teams may cause disruptions in terms of data transfer formats, enum literals etc. So some inconsistencies between our internal versions of RentIt and BuildIt were introduced on purpose to make the design better support these external integrations from early on.    