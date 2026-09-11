CREATE TABLE users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(40) not null,
    date DATE not null,
    cpf VARCHAR(14)
)engine = innoDB;