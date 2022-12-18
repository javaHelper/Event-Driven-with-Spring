# Event-driven Architectures on Apache Kafka with Spring Boot

````sh
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 6 --partitions 1 --topic ProductOrders
Created topic ProductOrders.
````

Link: https://www.youtube.com/watch?v=xyaFygU9C2Q&t=1510s

HTTP GET

````sh
http://localhost:8080/shop/analytics
````

````
{
    "totalOrders": "85.0",
    "book": "18%",
    "food": "20%",
    "music": "24%",
    "toy": "25%",
    "video": "14%"
}
````

<img width="1372" alt="Screenshot 2022-12-18 at 5 24 20 PM" src="https://user-images.githubusercontent.com/54174687/208296947-91c9460e-14af-4ea1-b5ce-bf87001e0fa4.png">
