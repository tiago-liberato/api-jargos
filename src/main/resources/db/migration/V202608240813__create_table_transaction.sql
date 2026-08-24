CREATE TABLE transaction(
    id varchar(36) PRIMARY KEY not null,
    description varchar(500) not null,
    amount BIGINT not null,
    category varchar(10) not null,
    date DATE not null
)engine=innoDB;