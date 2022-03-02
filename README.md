# reading-is-good


cd readingisgood

# Create fat jar. open cmd or terminal run the following command

mvn clean install

# Create mongo container

docker pull mongo
docker run --name mongodb -p 27017:27017 mongo

# Dockerize fat jar, and run :)

docker build -t readingisgood .
docker run -p 8080:8080 readingisgood
