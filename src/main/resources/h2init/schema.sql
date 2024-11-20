CREATE TABLE IF NOT EXISTS EMP
(
    userId  integer not null,
    name    varchar(255) not null,
    password   varchar(255),
    tenantId   varchar(255),
    primary key(userId)
);