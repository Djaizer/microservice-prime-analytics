# Analytics prime application

## How to start project

You can build java code and start it as stand alone app. In this case you need to provide MongoDB instance. Other way is to use docker-compose to start all needed dependencies as docker containers.

To build code just run 

```
mvn clean package

```

to build code and created docker image run 

```
mvn clean install
```


### Stand alone Java applicatoin 

```
java -jar target/graphql-vs-rest-1.0-SNAPSHOT.jar
```

If you want to also initalize some init data run with additiaonal paramater

```
java -jar target/graphql-vs-rest-1.0-SNAPSHOT.jar initdata

```

### Using Docker compose

After you build code and docker image is created just run this command

``` bash
docker-compose up
```

This will start 3 docker images. One with MongoDB database, other with Java application 


## REST End points

There are multipel REST end points that are exposed

### Authours end point
- [http://localhost:8180/authors](http://localhost:8180/authors)

to retrive all authours run this command
```
curl http://localhost:8180/authors
```

to retrieve info about specific author run this command

```
curl http://localhost:8180/authors/<author id>
```

### Posts end point
- [http://localhost:8180/posts](http://localhost:8180/posts)

to retrive all posts run this command

```
curl http://localhost:8180/posts
```

To retrieve all post from specific author run this command

```
curl http://localhost:8180/posts?author_id=<author id>
```

### Comments end point
- [http://localhost:8180/comments](http://localhost:8180/comments)

to retrive all comments run this command

```
curl http://localhost:8180/comments
```

To retrieve comments for specific port run this command

```
curl http://localhost:8180/comments?post_id=<post id>
```

##  End point

Once application is up and running, hit [http://localhost:8180/](http://localhost:8180/) with browser and you will get UI.
Here you can enter queries and get back response

