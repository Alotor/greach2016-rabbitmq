FROM rabbitmq:3.5.7-management

COPY load_definitions.json /etc/rabbitmq/load_definitions.json

RUN rabbitmq-plugins enable --offline rabbitmq_management
RUN rabbitmq-plugins enable --offline rabbitmq_stomp

RUN echo "[{rabbit, [{loopback_users, []}, {frame_max, 0}]}, {rabbitmq_management, [{load_definitions, \"/etc/rabbitmq/load_definitions.json\"}]}]." > /etc/rabbitmq/rabbitmq.config

EXPOSE 15672 61613
