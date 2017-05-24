

### Running

You need to download and install sbt for this application to run.

Once you have sbt installed, the following at the command prompt will start up Play in development mode:

```
sbt run
```

Play will start up on the HTTP port at http://localhost:9000/.   You don't need to deploy or reload anything -- changing any source code while the server is running will automatically recompile and hot-reload the application on the next HTTP request. 

### Usage
GET:
```
GET http://localhost:9000/CarAdv/1
```
return 1 record of Car advertising

```
GET:
```
GET http://localhost:9000/CarAdvs
```
return all car advertisings

```

POST:
```
POST http://localhost:9000/CarAdv
```
create mew car advertising
Body should be like this 
{         "id":1,
           "title":"vuong faiaaa car 222222222",
           "fuel":"gas",
           "price":100,
           "newCar":1,
           "mileage":100,
           "first_registration":1495497764108
}

```

PUT:
```
PUT http://localhost:9000/CarAdv/1
```
update a car advertising by id
Body should be like this 
Body should be like this 
{         "id":1,
           "title":"vuong faiaaa car 222222222",
           "fuel":"gas",
           "price":100,
           "newCar":1,
           "mileage":100,
           "first_registration":1495497764108
}

```
DELETE:
```
DELETE http://localhost:9000/CarAdv/1
```
delete a car advertising by id

```
