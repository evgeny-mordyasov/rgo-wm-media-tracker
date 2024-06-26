FROM postgres:15-alpine

RUN mkdir -p /usr/local/media/pgdata; \
    mkdir -p /usr/local/media/pgindex; \

    chown -R postgres:postgres /usr/local; \
    chmod -R u+rwx /usr/local;

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=media

RUN mkdir -p /var/lib/postgresql-static/data
ENV PGDATA /var/lib/postgresql-static/data