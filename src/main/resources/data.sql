-- Updated data for ExpenseTracker application with new OOP structure
-- Works with Transaction/Expense inheritance and Long IDs

-- Insert users with Long IDs (using sequences) - includes admin field
INSERT INTO app_user (id, name, email, is_admin) VALUES (1, 'Siamak', 'Codeengine11@gmail.com', false);
INSERT INTO app_user (id, name, email, is_admin) VALUES (2, 'John', 'John@john.com', false);
INSERT INTO app_user (id, name, email, is_admin) VALUES (3, 'Adam', 'adam@adam.com', false);
-- Add admin user
INSERT INTO app_user (id, name, email, is_admin) VALUES (4, 'Admin', 'admin@auth.com', true);

-- Insert categories
INSERT INTO category (id, name) VALUES (1, 'Travel');
INSERT INTO category (id, name) VALUES (2, 'Auto Loan');
INSERT INTO category (id, name) VALUES (3, 'Student Loan');

-- Insert transactions (base class entries)
INSERT INTO transaction (id, description) VALUES (100, 'New York Business Trip');
INSERT INTO transaction (id, description) VALUES (101, 'Ford Mustang Payment');
INSERT INTO transaction (id, description) VALUES (102, 'Grand Canyon Trip With Family');

-- Insert expenses (extending transactions) with LocalDate format and amount field
INSERT INTO expense (id, amount, date, location, category_id, user_id) VALUES (100, 1500.00, '2019-06-16', 'New York', 1, 1);
INSERT INTO expense (id, amount, date, location, category_id, user_id) VALUES (101, 450.00, '2019-06-15', 'Los Angeles', 2, 2);
INSERT INTO expense (id, amount, date, location, category_id, user_id) VALUES (102, 800.00, '2019-06-15', 'Arizona', 3, 1);