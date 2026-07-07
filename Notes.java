package com.tnf.product_service;

/*
    Restful web services

    Architectural Constraints:
    1. Client-Server: The client and server are separate entities that communicate over a network.
    2. Stateless: Each request from the client to the server must contain all the information needed to understand and process the request. The server does not store any client context between requests.
    3. Cacheable: Responses from the server can be cached by the client to improve performance and reduce the number of requests to the server.
    4. Uniform Interface: The interface between the client and server is standardized, allowing for easy communication and interaction.
        -resource identification via URI
        -resource manipulation via representations (JSON/ XML)
        -self-descriptive messages (HTTP methods, status codes, headers)
        HATEOAS (Hypermedia as the Engine of Application State): The server provides hypermedia links in its responses, allowing the client to navigate and interact with the application dynamically.
    5. Layered System: The architecture can be composed of multiple layers, with each layer having a specific responsibility. This allows for scalability, security, and separation of concerns.
    6. code on demand (optional): The server can provide executable code to the client, allowing for dynamic behavior and customization of the client application.

    Principles of microservice
        1. Single Responsibility Principle per service
        2. Database per service
        3. Decentralized governance and data management
        4. Design for failure
        5. Independent deployment
        6. Externalized configuration
        7. Obervability
        8. domain driven design
        9. Automation (CI/CD)


    BEST PRACTICE : Always create services in lowercase

    Order of execution of microservices:
        1. Eureka server
        2. Config server
        3. Product service
        4. API Gateway
*/

public class Notes {
    
}
