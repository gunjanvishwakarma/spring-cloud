# Base image sleswithjdk
from openjdk:latest

ADD maven/currency-exchange-service-0.0.1-SNAPSHOT.jar /root/currency-exchange-service-0.0.1.jar

ADD entry-script.sh /root/
RUN chmod 755 /root/entry-script.sh

ENTRYPOINT ["/root/entry-script.sh"]

EXPOSE 8000

