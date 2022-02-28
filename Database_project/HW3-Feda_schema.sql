-- Create the Database
CREATE DATABASE feda_db OWNER postgres ENCODING = 'UTF8';

-- Connect to the new db
\c feda_db

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

CREATE TYPE Tailor_feda.productorigin AS ENUM (
	'Customer',
	'Tailor'
);

CREATE TYPE Tailor_feda.worktype AS ENUM (
	'From scratch',
	'Tailor',
	'Online shop'
);

CREATE TYPE Tailor_feda.employeerole AS ENUM(
    'Employee',
    'Manager'
);

-- Create the tables 

-- Tailor's shop
CREATE TABLE Tailor_feda.tailor_shop (
    id UUID DEFAULT uuid_generate_v4(),
    phone VARCHAR(15) NOT NULL,
    email Tailor_feda.EMAILADDRESS NOT NULL,
    address VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
);

-- Customer
CREATE TABLE Tailor_feda.customer (
	email Tailor_feda.EMAILADDRESS,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    password Tailor_feda.PASSWD,
    phone VARCHAR(15) NOT NULL,
    addresses  VARCHAR(128) ARRAY,
    newsletter BOOLEAN DEFAULT FALSE NOT NULL,
    get_to_know VARCHAR(64),
    sizes VARCHAR(64),
    lifestyle VARCHAR(64),
    shop UUID NOT NULL,
    PRIMARY KEY (email),
    FOREIGN KEY (shop) REFERENCES Tailor_feda.tailor_shop (id) 
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

-- Product type
CREATE TABLE Tailor_feda.product_t (
	model VARCHAR(128),
	work_time SMALLINT CHECK (work_time>0),
	price NUMERIC(8,2) CHECK(price>=0),
	fabric VARCHAR(32),
	origin Tailor_feda.PRODUCTORIGIN NOT NULL,
	description TEXT NOT NULL,
	PRIMARY KEY (model)
);

-- Product
CREATE TABLE Tailor_feda.product (
	p_code UUID DEFAULT uuid_generate_v4(), 
	pictures BYTEA ARRAY, 
	size VARCHAR(64) NOT NULL,
	color Tailor_feda.COLOR,
    work_type Tailor_feda.WORKTYPE NOT NULL,
	model VARCHAR(128) NOT NULL,
	PRIMARY KEY (p_code),
	FOREIGN KEY (model) REFERENCES Tailor_feda.product_t(model)
);

-- Request quote
CREATE TABLE Tailor_feda.request_quote (
    model VARCHAR(128),
    customer Tailor_feda.EMAILADDRESS,
    quote NUMERIC(8,2) CHECK (quote>=0),
    PRIMARY KEY (model, customer),
    FOREIGN KEY (model) REFERENCES Tailor_feda.product_t(model),
    FOREIGN KEY (customer) REFERENCES Tailor_feda.customer(email)
);

-- Contain
CREATE TABLE Tailor_feda.contain ( 
	order_customer UUID,       
	product UUID,
	PRIMARY KEY (order_customer, product),
	FOREIGN KEY (order_customer) REFERENCES Tailor_feda.order_customer(id),
	FOREIGN KEY (product) REFERENCES Tailor_feda.product(p_code)
);

-- Employee
CREATE TABLE Tailor_feda.employee (
    email Tailor_feda.emailaddress,
    name VARCHAR(64) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    password Tailor_feda.PASSWD NOT NULL,
    phone VARCHAR(15) NOT NULL,
    role Tailor_feda.employeerole NOT NULL,
    PRIMARY KEY (email)
);

-- Article
CREATE TABLE Tailor_feda.article (
    id UUID DEFAULT uuid_generate_v4(),     
    topic TEXT NOT NULL,
    article_text TEXT NOT NULL,
    employee Tailor_feda.emailaddress NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (employee) REFERENCES Tailor_feda.employee(email)
);

-- Manage
CREATE TABLE Tailor_feda.manage (
    product UUID,
    employee Tailor_feda.emailaddress,
    PRIMARY KEY (product, employee),
    FOREIGN KEY (product) REFERENCES Tailor_feda.product(p_code),
    FOREIGN KEY (employee) REFERENCES Tailor_feda.employee(email)
);

-- Work
CREATE TABLE Tailor_feda.work (
    employee Tailor_feda.emailaddress,
    shop UUID,
    PRIMARY KEY (employee, shop),
    FOREIGN KEY (employee) REFERENCES Tailor_feda.employee(email),
    FOREIGN KEY (shop) REFERENCES Tailor_feda.tailor_shop(id)
);

-- Supplier
CREATE TABLE Tailor_feda.supplier (
    VAT_number Tailor_feda.VATNUMBER ,
    name VARCHAR(64) NOT NULL,
    email Tailor_feda.EMAILADDRESS NOT NULL,
    PRIMARY KEY(VAT_number)
);

-- Order supplier
CREATE TABLE Tailor_feda.order_supplier (
    id UUID DEFAULT uuid_generate_v4(),
    total_price NUMERIC(8,2) NOT NULL CHECK (total_price>=0),
    employee Tailor_feda.EMAILADDRESS NOT NULL,
    supplier Tailor_feda.VATNUMBER NOT NULL, 
    PRIMARY KEY(id),
    FOREIGN KEY(employee) REFERENCES Tailor_feda.employee(email),
    FOREIGN KEY(supplier) REFERENCES Tailor_feda.supplier(VAT_number) 
);

-- Review
CREATE TABLE Tailor_feda.review (
	id UUID DEFAULT uuid_generate_v4(),
	description TEXT NOT NULL,
	reply TEXT,
    customer Tailor_feda.EMAILADDRESS NOT NULL,
    product UUID UNIQUE NOT NULL,
    PRIMARY KEY(id),
	FOREIGN KEY(customer) REFERENCES Tailor_feda.customer(email),
	FOREIGN KEY(product) REFERENCES Tailor_feda.product(p_code)
);