/* Message Queuing Notes 

    Kafka
        It is event streaming platform (also called message broker)
        It just sends messages to the queue and does not care about the consumer
        It stores the messages in the queue for a certain period of time (configurable)
        It follows pub-sub model (publish-subscribe model)

        How this happens:
            1. Producer (Any service because of which an event is generated) sends messages to the topic (a topic is a category or feed name to which records are published)
            2. Kafka stores the messages in the topic for a certain period of time (configurable)
            3. Consumer subscribes to the topic and consumes the messages from the topic

        Before Kafka:
            order-service has to communicate with 
                1. payment-service
                2. inventory-service
                3. product-service
                4. delivery-service
                etc. (all the services which are dependent on order-service)
        After JMS (Kafka):
            producer -> Kafka -> consumer
            order-service -> sends message -> kafka -> payment-service, inventory-service, product-service, delivery-service (all the services which are dependent on order-service) consume the message from kafka

        How Kafka does this?
            

*/

public class MessageQueuing_Notes {

}
