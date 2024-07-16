ALTER TABLE authors ADD COLUMN role varchar(255);

ALTER TABLE authors ADD CONSTRAINT fk_role_name FOREIGN KEY (role) REFERENCES roles (name);