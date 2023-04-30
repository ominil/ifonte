CREATE TABLE roles (
    role_id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE privileges (
    privilege_id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE roles_privileges (
    role_id BIGSERIAL NOT NULL,
    privilege_id BIGSERIAL NOT NULL,
    CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
    CONSTRAINT privilege_fk FOREIGN KEY (privilege_id) REFERENCES privileges (privilege_id)
);

ALTER TABLE customer ADD enabled BOOLEAN DEFAULT NULL;

CREATE TABLE customer_roles (
    customer_id BIGSERIAL NOT NULL,
    role_id BIGSERIAL NOT NULL,
    CONSTRAINT customer_fk FOREIGN KEY (customer_id) REFERENCES customer (id),
    CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (role_id)

)