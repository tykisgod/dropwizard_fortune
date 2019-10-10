# FORTUNE

## Running The Application

To test the application run the following commands.

- To create the example, package the application using [Apache Maven](https://maven.apache.org/) from the directory of the project.

        mvn clean package

- To run the server run.

        java -jar target/dropwizard-example-1.4.0-SNAPSHOT.jar server example.yml

## RESTful API

1. Retrieve one fortune message ​randomly​. All the fortunes in the fortune pool should be retrieved with equal probability​.

        GET /fortune

   For Example, randomly get a fortune:

        curl -XGET localhost:8080/fortune

2. Create a new fortune in the fortune pool. This API takes a fortune message as input.

        POST /fortunes?fortune=[fortune]

   Return an ID of the input fortune.

   For Example, create fortune with fortune "Hello World":

        curl -XPOST localhost:8080/fortunes?fortune=Hello+World

3. Delete the fortune message with the given fortuneId from the fortune pool.

        DELETE /fortunes/{fortuneId}

   Return a message to tell if the fortune with the fortuneId was deleted succeessfully.

   For Example, delete fortune with fortune ID 1:

        curl -XDELETE localhost:8080/fortunes/1

4. Return all (when type_id is 0 or default) or only visible(when type_id is 1) fortunes in fortune container object.

        curl -XGET localhost:8080/test/list?type=[type_id]

5. Simulate sending 'GET /fortune' \[num_of_times\] times. Default testing times is 100.

        curl -XGET localhost:8080/test/frequency?times=[num_of_times]
  
   Return following information:

   1. Frequency of each fortune occurs during test;

   2. All visible fortunes in fortune container object;

   3. All fortunes in fortune container object.

## How this works

1. Considering we assume that all the data can fit in memory, all fortunes are stored in a FortuneContainer object. The object are serialized, named as 'fortuneContainer.fort' and stored on local disk under the dir of the project

2. When the project runs, it will read exising fortuneContainer.fort and desizialize it to our program. If it doesn't exisit, the program will create one automatically

3. Everytime we change the fortune in fortuneContainer, the change will be recorded and stored back to fortuneContainer.fort

## Key Data Structure - FortuneContainer

- This data structure is based on an ArrayList\<String\> **fortuneList**, which contains all the fortunes.
- fortuneList has two parts, one is the **visible part**, the other is the **invisible part**. These two parts are just abstract concepts.
  - Everytime we add a new fortune to the **fortuneList**, it will become a part of the visible part.
  - Everytime we delete a fortune from the **fortuneList**, it will be rewrite by the rightmost element of the **fortuneList**. And the rightmost value will become a part of the **invisible fortuneList**.
  - So It's easy to see that when we delete some element in the **fortuneList**, the size of the actual size **fortuneList** doesn't change.

### Time Complexity

- add, O(1), we just need to add item in ArrayList.
- delete, O(1), rewrite ArrayList at a given index.
- get, O(1), random get a value from ArrayList by given index. 

## Drawbacks and Others

- The id can be reused(good?) but also change often(bad?). We may want to create another HashMap to store the original id of each fortune as the value and an auto-incremental Integer as key.
- Even when we delete all fortunes in **fortuneList**, the size won't decrease. Sometimes this may be space cosuming.
- Using database may be a better choice in practical situation.
