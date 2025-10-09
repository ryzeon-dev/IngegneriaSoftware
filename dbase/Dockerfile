FROM postgres:latest

ENV POSTGRES_DB=swedb
ENV POSTGRES_USER=master
ENV POSTGRES_PASSWORD=swe-db-dlv

COPY template.sql /docker-entrypoint-initdb.d/