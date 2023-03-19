ALTER TABLE employer
ADD CONSTRAINT employer_email_unique UNIQUE (email);