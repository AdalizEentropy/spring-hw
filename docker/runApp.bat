docker run --rm --name bankapp ^
--network="host" ^
-p 8080:8080 ^
-p 8082:8082 ^
bankapp-image:1.0.0