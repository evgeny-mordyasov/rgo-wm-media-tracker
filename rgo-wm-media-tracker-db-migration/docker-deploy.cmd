docker-compose up -d
docker rmi michisig/wm-media-db:%1
docker container commit media-db michisig/wm-media-db:%1
docker image push michisig/wm-media-db:%1
docker-compose stop
docker rm media-db
docker rmi media-db:latest