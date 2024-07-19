ALTER TABLE authors ADD COLUMN active BOOLEAN;

UPDATE authors SET active = true;