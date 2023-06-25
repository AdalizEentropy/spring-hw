docker run --rm --name pg-docker ^
-e MYSQL_ALLOW_EMPTY_PASSWORD=true ^
-e MYSQL_PASSWORD=password ^
-e MYSQL_USER=user ^
-e MYSQL_DATABASE=bankappdb ^
-p 3306:3306 ^
mysql:5.6