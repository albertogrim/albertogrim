-- Create the Database
CREATE DATABASE feda_db OWNER tailorshop ENCODING = 'UTF8';

-- Extension UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Extention Cryptography
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Create new schema
DROP SCHEMA IF EXISTS Tailor_feda CASCADE;
CREATE SCHEMA  Tailor_feda;

-- Create new domains
CREATE DOMAIN Tailor_feda.passwd AS VARCHAR(256)
    CONSTRAINT properpassword CHECK (((VALUE)::text ~* '[A-Za-z0-9._%!]{8,}'::text));

CREATE DOMAIN Tailor_feda.emailaddress AS VARCHAR(256)
    CONSTRAINT properemail CHECK (((VALUE)::text ~* '^[A-Za-z0-9._%]+@[A-Za-z0-9.]+[.][A-Za-z]+$'::text));

CREATE DOMAIN Tailor_feda.color AS CHAR(7)
    CONSTRAINT propercolor CHECK (((VALUE)::text ~* '[#][a-fA-F0-9]{6}'::text));

CREATE DOMAIN Tailor_feda.vatnumber AS VARCHAR(15)
    CONSTRAINT properVAT CHECK (((VALUE)::text ~* '[A-Za-z0-9]{4,}'::text));

-- Create new data types
CREATE TYPE Tailor_feda.orderstatus AS ENUM (
	'Pending',
	'Accepted',
	'Processing',
	'Delivering',
	'Completed',
	'Refunded'
);

CREATE TYPE Tailor_feda.paymentmethod AS ENUM (
	'Cash',
	'Credit/debit card',
	'Bank transfer',
	'PayPal'
);

CREATE TYPE Tailor_feda.deliverymode AS ENUM (
	'Home',
	'Shop'
);

CREATE TYPE Tailor_feda.worktype AS ENUM (
	'From scratch',
	'Tailor'
);

-- Create the tables 

-- Customer
CREATE TABLE Tailor_feda.customer (
                                      email Tailor_feda.EMAILADDRESS,
                                      name VARCHAR(64) NOT NULL,
                                      surname VARCHAR(64) NOT NULL,
                                      password TEXT NOT NULL,
                                      phone VARCHAR(15) NOT NULL,
                                      addresses  VARCHAR(128) NOT NULL,
                                      newsletter BOOLEAN DEFAULT FALSE NOT NULL,
                                      get_to_know VARCHAR(64),
                                      sizes VARCHAR(64),
                                      lifestyle VARCHAR(64),
                                      PRIMARY KEY (email)
);

-- Status
CREATE TABLE Tailor_feda.status (
                                    name Tailor_feda.ORDERSTATUS,
                                    PRIMARY KEY (name)
);

-- Order_customer
CREATE TABLE Tailor_feda.order_customer (
                                            id UUID DEFAULT uuid_generate_v4(),
                                            tot_price NUMERIC(8,2) NOT NULL,
                                            address VARCHAR(128) NOT NULL,
                                            p_method Tailor_feda.PAYMENTMETHOD NOT NULL,
                                            delivery_mode Tailor_feda.DELIVERYMODE NOT NULL,
                                            is_cancelled BOOLEAN DEFAULT FALSE NOT NULL,
                                            invoice VARCHAR NOT NULL UNIQUE,
                                            customer Tailor_feda.EMAILADDRESS NOT NULL,
                                            status Tailor_feda.ORDERSTATUS NOT NULL,
                                            PRIMARY KEY (id),
                                            FOREIGN KEY (customer) REFERENCES Tailor_feda.customer(email),
                                            FOREIGN KEY (status) REFERENCES Tailor_feda.status(name)
);

-- Custom_product
CREATE TABLE Tailor_feda.custom_product (
                                            id UUID DEFAULT uuid_generate_v4(),
                                            model VARCHAR(128),
                                            work_time SMALLINT CHECK (work_time>0),
                                            fabric VARCHAR(32),
                                            work_type Tailor_feda.WORKTYPE NOT NULL,
                                            size VARCHAR(64) NOT NULL,
                                            color Tailor_feda.COLOR,
                                            customer Tailor_feda.EMAILADDRESS NOT NULL,
                                            PRIMARY KEY (id),
                                            FOREIGN KEY (customer) REFERENCES Tailor_feda.customer(email)
);

-- Appointment
CREATE TABLE Tailor_feda.appointment (
                                         id UUID DEFAULT uuid_generate_v4(),
                                         schedule TIMESTAMP NOT NULL,
                                         description TEXT,
                                         accepted BOOLEAN DEFAULT FALSE NOT NULL,
                                         customer Tailor_feda.EMAILADDRESS NOT NULL,
                                         PRIMARY KEY (id),
                                         FOREIGN KEY (customer) REFERENCES Tailor_feda.customer(email)
);


-- Product to bo cancelled
CREATE TABLE Tailor_feda.product (
                                     p_code UUID DEFAULT uuid_generate_v4(),
                                     price NUMERIC(8,2) CHECK(price>=0),
                                     fabric VARCHAR(32) NOT NULL,
                                     pictures BYTEA ARRAY,
                                     size VARCHAR(64) NOT NULL,
                                     color Tailor_feda.COLOR,
                                     description TEXT NOT NULL,
                                     model VARCHAR(128) NOT NULL,
                                     out_of_stock BOOLEAN DEFAULT FALSE NOT NULL,
                                     PRIMARY KEY (p_code)

);

-- Product
CREATE TABLE Tailor_feda.product (
                                     p_code UUID DEFAULT uuid_generate_v4(),
                                     price NUMERIC(8,2) CHECK(price>=0),
                                     fabric VARCHAR(32) NOT NULL,
                                     pictures VARCHAR(32) NOT NULL,
                                     size VARCHAR(64) NOT NULL,
                                     color Tailor_feda.COLOR,
                                     description TEXT NOT NULL,
                                     model VARCHAR(128) NOT NULL,
                                     out_of_stock BOOLEAN DEFAULT FALSE NOT NULL,
                                     PRIMARY KEY (p_code)
);

-- Contain
CREATE TABLE Tailor_feda.contain (
                                     order_customer UUID,
                                     product UUID,
                                     PRIMARY KEY (order_customer, product),
                                     FOREIGN KEY (order_customer) REFERENCES Tailor_feda.order_customer(id),
                                     FOREIGN KEY (product) REFERENCES Tailor_feda.product(p_code)
);

-- Book
CREATE TABLE Tailor_feda.book (
                                  customer Tailor_feda.emailaddress,
                                  appointment UUID,
                                  PRIMARY KEY (customer, appointment),
                                  FOREIGN KEY (customer) REFERENCES Tailor_feda.customer(email),
                                  FOREIGN KEY (appointment) REFERENCES Tailor_feda.appointment(id)
);

-- Employee
CREATE TABLE Tailor_feda.employee (
                                      email Tailor_feda.emailaddress,
                                      name VARCHAR(64) NOT NULL,
                                      surname VARCHAR(64) NOT NULL,
                                      password TEXT NOT NULL,
                                      phone VARCHAR(15) NOT NULL,
                                      role VARCHAR(15) NOT NULL,
                                      PRIMARY KEY (email)
);

-- Manage
CREATE TABLE Tailor_feda.manage (
                                    product UUID,
                                    employee Tailor_feda.emailaddress,
                                    PRIMARY KEY (product, employee),
                                    FOREIGN KEY (product) REFERENCES Tailor_feda.product(p_code),
                                    FOREIGN KEY (employee) REFERENCES Tailor_feda.employee(email)
                                    ON DELETE CASCADE


);