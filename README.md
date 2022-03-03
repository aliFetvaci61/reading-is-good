# reading-is-good

I am using Java (SDK 11), Spring Framework (spring boot) , mongodb

I created swagger.json for open api documentation and also I pushed postman collection. You can use it.

# Change directory
git clone https://github.com/aliFetvaci61/reading-is-good.git /n
cd readingisgood

# Create fat jar. open cmd or terminal run the following command

mvn clean install

# Create mongo container

docker pull mongo

docker run --name mongodb -p 27017:27017 mongo

# Dockerize fat jar, and run :)

docker build -t readingisgood .

docker run -p 8080:8080 readingisgood

(I am using docker desktop)
