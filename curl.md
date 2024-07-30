## 1. Curl request for get meal by id method: 
> curl -X GET "http://localhost:8080/topjava/rest/meals/100009" -H "Accept: application/json"

## 2. Curl request for delete meal by id method:
> curl -X DELETE "http://localhost:8080/topjava/rest/meals/100009"

## 3. Curl request for getAll meals method:
> curl -X GET "http://localhost:8080/topjava/rest/meals" -H "Accept: application/json"

## 4. Curl request for create new meal method:
> curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\": \"2024-07-30T10:00:00\", \"description\": \"Business-lunch\", \"calories\": 1000}" "http://localhost:8080/topjava/rest/meals"

## 5. Curl request for update meal method:
> curl -X PUT -H "Content-Type: application/json" -d "{\"dateTime\": \"2020-01-30T13:00:00\", \"description\": \"Обновленный обед\", \"calories\": 1100}" "http://localhost:8080/topjava/rest/meals/100004"

## 6. Curl request for get meal between date method:
> curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=00:00&endDate=2020-01-30&endTime=23:59"