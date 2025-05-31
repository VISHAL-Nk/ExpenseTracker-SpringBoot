-- Updated data for ExpenseTracker application with PostgreSQL compatibility
-- Using sequences for auto-generated IDs

-- Insert users (let PostgreSQL auto-generate IDs)
INSERT INTO app_user (name, email, is_admin) VALUES ('Siamak', 'Codeengine11@gmail.com', false);
INSERT INTO app_user (name, email, is_admin) VALUES ('John', 'John@john.com', false);
INSERT INTO app_user (name, email, is_admin) VALUES ('Adam', 'adam@adam.com', false);
INSERT INTO app_user (name, email, is_admin) VALUES ('Admin', 'admin@auth.com', true);

-- Insert categories (let PostgreSQL auto-generate IDs)
INSERT INTO category (name) VALUES ('Travel');
INSERT INTO category (name) VALUES ('Auto Loan');
INSERT INTO category (name) VALUES ('Student Loan');

-- Insert transactions (let PostgreSQL auto-generate IDs)
INSERT INTO transaction (description) VALUES ('New York Business Trip');
INSERT INTO transaction (description) VALUES ('Ford Mustang Payment');
INSERT INTO transaction (description) VALUES ('Grand Canyon Trip With Family');

-- Insert expenses with references to auto-generated IDs
-- Note: In production, these would reference the actual generated IDs
-- For demo purposes, we'll create expenses directly without the inheritance complexity
-- INSERT INTO expense (amount, date, location, category_id, user_id) VALUES (1500.00, '2019-06-16', 'New York', 1, 1);
-- INSERT INTO expense (amount, date, location, category_id, user_id) VALUES (450.00, '2019-06-15', 'Los Angeles', 2, 2);
-- INSERT INTO expense (amount, date, location, category_id, user_id) VALUES (800.00, '2019-06-15', 'Arizona', 3, 1);