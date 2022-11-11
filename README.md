# rore-demo


### Create
curl --location --request POST 'localhost:8080/battery/create' \
--header 'Content-Type: application/json' \
--data-raw '{
"name":"f",
"postcode":6,
"watt":"6"
}'

### postcode-range
curl --location --request POST 'localhost:8080/battery/postcode-range' \
--header 'Content-Type: application/json' \
--data-raw '{
"postcodeStart":1,
"postcodeEnd":5
}'