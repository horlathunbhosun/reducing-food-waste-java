#!/bin/bash
export RABBITMQ_USERNAME=myuser
export RABBITMQ_PASSWORD=mypassword
export SPRING_DATASOURCE_URL=jdbc:sqlserver://wastedbb.database.windows.net:1433;database=wastedb;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
export SPRING_DATASOURCE_USERNAME=AdminOla@wastedbb
export SPRING_DATASOURCE_PASSWORD=Olatunbosun02@

docker build -t my-app .

docker compose up
