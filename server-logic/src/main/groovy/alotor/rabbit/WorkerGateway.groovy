package alotor.rabbit

interface WorkerGateway {
    void broadcastMessage(String topic, Map message)
}