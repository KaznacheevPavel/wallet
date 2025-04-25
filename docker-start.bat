docker compose -f .\docker-compose.yml down
call mvn clean package
docker compose -f .\docker-compose.yml up -d --build