# Country and currency information System

Please follow below instruction to run this project:

###Browse swagger:
http://localhost:8080/swagger-ui.html

Technology stack:
1. Java - 11
2. Spring Boot
3. Spring Data
4. Maven
5. MongoDB
6. Docker
7. Zalando (Problem Library)
8. Swagger

Run Commands:
1. mvn clean
2. mvn install
3. mvn spring-boot:run

Generate and run jar file:
1. mvn clean install.
2. cd target
3. java -jar country.jar
    
### Create docker image and run
1. docker build -t country .
2. docker run -p 8080:8080 country
3. docker container run --name country -p 8080:8080 -d country
4. docker start <container id>
5. docker logs <container id>

### To find currency exchange rate in INR
```
    1. You need Subscription Plan to find currency exchange rates based on a symbol 
    2. Use below URL if you have Subscription Plan
       Example: http://data.fixer.io/api/latest?access_key=334e9ad1306c2fb9feae0c7ef001a7d3&symbols=INR
       (CURRENCY_LATEST_EXCHANGE_RATE_URL = CURRENCY_LATEST_EXCHANGE_RATE_URL + "&symbols=INR";)
```
```
     1. To find currency exchange rate in INR with baseCurrency
     2. You need Subscription Plan to find currency exchange rates based on a specific base and symbol
     3. Use below line if you have Subscription Plan
        Example: http://data.fixer.io/api/latest?access_key=334e9ad1306c2fb9feae0c7ef001a7d3&base=USD&symbols=INR
      (CURRENCY_LATEST_EXCHANGE_RATE_URL = CURRENCY_LATEST_EXCHANGE_RATE_URL + "&base=" + baseCurrency + "&symbols=INR")
```
  
``` 
     1. To find all currency exchange rates, You do not need Subscription Plan to find default currency exchange rates.
       Example: (http://data.fixer.io/api/latest?access_key=334e9ad1306c2fb9feae0c7ef001a7d3)
```

