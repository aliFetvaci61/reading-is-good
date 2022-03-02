# reading-is-good



mvn clean install

docker pull mongo
docker run --name mongodb -p 27017:27017 mongo


docker build -t readingisgood .

docker run -p 8080:8080 readingisgood
