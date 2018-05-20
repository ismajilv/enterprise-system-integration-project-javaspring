FORMAT: 1A
HOST: http://localhost

# ESI18-RentIt
Excerpt of RentIt's API

# Group Purchase Orders
Notes related resources of the **Plants API**

## Plant Catalog [/api/sales/plants{?name,startDate,endDate}]
### Retrieve Plants [GET]
+ Parameters
    + name (optional,string) ... Name of the plant
    + startDate (optional,date) ... Starting date for rental
    + endDate (optional,date) ... End date for rental

+ Response 200 (application/json)

        [
            {"_id":13,"name":"D-Truck","description":"15 Tonne Articulating Dump Truck","price":250.00,"_links":{"self":{"href":"http://localhost:8090/api/inventory/plants/13"}}},
            {"_id":14,"name":"D-Truck","description":"30 Tonne Articulating Dump Truck","price":300.00,"_links":{"self":{"href":"http://localhost:8090/api/inventory/plants/14"}}}
        ]

## Purchase Order - Container [/api/sales/orders]
### Create Purchase Order [POST]
+ Request (application/json)

        {
            "plant":{
                "_links":{"self":{"href":"http://localhost:8090/api/inventory/plants/13"}}
            },
            "rentalPeriod": {"startDate":"2018-04-12", "endDate":"2018-04-14"}
        }

+ Response 201 (application/json)

    + Headers

            Location: http://localhost:8090/api/sales/orders/100

    + Body

            {
                "plant": {
                    "name": "D-Truck",
                    "description": "15 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://localhost:8090/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {"startDate": "2018-04-12", "endDate": "2018-04-14"},
                "status": "OPEN",
                "_links": {
                    "self": {"href": "http://localhost:8090/api/sales/orders/100"},
                    "extensions": {"href": "http://localhost:8090/api/sales/orders/100/extensions"}
                }
            }

### Retrieve All Purchase Order [GET]

+ Response 200 (application/json)

        [
            {
                "plant": {
                    "name": "D-Truck",
                    "description": "15 Tonne Articulating Dump Truck",
                    "price": 250,
                    "_links": {
                        "self": {"href": "http://localhost:8090/api/inventory/plants/13"}
                    }
                },
                "rentalPeriod": {"startDate": "2018-04-12", "endDate": "2018-04-14"},
                "status": "OPEN",
                "_links": {
                    "self": {"href": "http://localhost:8090/api/sales/orders/100"},
                    "extensions": {"href": "http://localhost:8090/api/sales/orders/100/extensions"}
                }
            }
        ]

## Purchase Order - Instance [/api/sales/orders/{id}]
### Retrieve Purchase Order [GET]
+ Parameters
    + id: 100 (required, number) - Purchase order ID in form of a long integer

+ Response 200 (application/json)

        {
            "plant": {
                "name": "D-Truck",
                "description": "15 Tonne Articulating Dump Truck",
                "price": 250,
                "_links": {
                    "self": {"href": "http://localhost:8090/api/inventory/plants/13"}
                }
            },
            "rentalPeriod": {"startDate": "2018-04-12", "endDate": "2018-04-14"},
            "status": "OPEN",
            "_links": {
                "self": {"href": "http://localhost:8090/api/sales/orders/100"},
                "extensions": {"href": "http://localhost:8090/api/sales/orders/100/extensions"}
            }
        }

+ Response 409 (application/json)

        {
            "plant": {
                "name": "D-Truck",
                "description": "15 Tonne Articulating Dump Truck",
                "price": 250,
                "_links": {
                    "self": {"href": "http://localhost:8090/api/inventory/plants/13"}
                }
            },
            "rentalPeriod": {"startDate": "2018-04-12", "endDate": "2018-04-14"},
            "status": "REJECTED",
            "_links": {
                "self": {"href": "http://localhost:8090/api/sales/orders/100"},
                "resubmit": {"href": "http://localhost:8090/api/sales/orders/100", "method": "PUT"}
            }
        }
