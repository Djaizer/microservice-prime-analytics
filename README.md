# Analytics prime application

## How to start project
Run kafka and zookeeper
```
docker-compose up

```

You can build java code and start it as stand alone app. In this case you need to provide MongoDB instance. Other way is to use docker-compose to start all needed dependencies as docker containers.



```
cd demo-shell
mvn clean install -DskipTests

```


### Stand alone Java applicatoin 

```
java -jar target\demo-shell-0.0.1-SNAPSHOT.jar
```

type to create a new user: :
```
user
number 50
repotr
status
```
type to get prime number  :
```
number [50]
```

type to get a report:
```
report
```

type to get a report status  :
```
status
```