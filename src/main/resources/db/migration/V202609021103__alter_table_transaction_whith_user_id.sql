ALTER TABLE transaction
ADD COLUMN id_user BIGINT;

ALTER TABLE transaction
ADD CONSTRAINT fk_transaction_user
FOREIGN KEY (id_user)
REFERENCES users(id);