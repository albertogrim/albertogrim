-- Populate the tables

-- Customer
INSERT INTO Tailor_feda.customer (email, name, surname, password, phone, addresses, newsletter, get_to_know, sizes, lifestyle) VALUES
('paolorossi@gmail.com', 'Paolo', 'Rossi', md5('pwd56789'), '3368495859', 'Via Roma 1, 12345 Roma (Italia)', FALSE, NULL, NULL, NULL ),
('tiziocaio@libero.it', 'Tizio', 'Caio',  md5('pwd78901'), '33474748476', 'Via Mazzini 6, 25373 Milano (Italia)', TRUE, 'Friends', '52', 'Athlete'),
('mary.poppins@hotmail.com', 'Mary', 'Poppins', md5('superCalifragi1'), '+102135689073', 'Chimney road 1232, 283472 London (Great Britain)', TRUE, 'Advertisement', '38', 'Vintage'),
('kingjames@gmail.com', 'Lebron', 'James',  md5('lbjgoat23'), '4363829272', 'Normandie Ave, 90044 Los Angeles California(CA)', FALSE, 'friends', NULL, 'NBA player'),
('massimo.gualtieri@gmail.com', 'Massimo', 'Gualtieri',  md5('forzanapoli92'), '3366689897', 'Via Gioacchino Murat 77, 80058 Torre Annunziata(Italia)', TRUE, 'Google', NULL, 'Streetwear');

-- Status
INSERT INTO Tailor_feda.status (name) VALUES
('Pending'),
('Accepted'),
('Processing'),
('Delivering'),
('Completed'),
('Refunded');

-- Order_customer
INSERT INTO Tailor_feda.order_customer (id, tot_price, address, p_method, delivery_mode, is_cancelled, invoice, customer, status) VALUES
('f2571408-08d5-440e-bb16-283fe1c4c09b', 3014.99, 'Via Roma 1, 12345 Roma (Italia)', 'Bank transfer', 'Home', false, 'ABC123-ZZZ1', 'paolorossi@gmail.com', 'Accepted'),
('8472809c-b737-4303-aa5b-ef6f8b3e565e', 99.99, 'Viale Garibaldi 25, 54321 Milano (Italia)', 'Credit/debit card', 'Home', false, 'ABC123-ZZZ4', 'paolorossi@gmail.com', 'Pending'),
('8ef8683b-d918-4be2-9381-bbf51113382c', 1500.00, 'Viale Venezia 253, 54321 Milano (Italia)', 'PayPal', 'Home', false, 'ABC123-ZZZ2', 'tiziocaio@libero.it', 'Processing'),
('c3ac527a-6550-4e6a-99c4-f03b0aa6525c', 149.99, 'via Livorno 34, 63524 Torino (Italia)', 'PayPal', 'Shop', FALSE, 'ABC123-ZZZ6', 'kingjames@gmail.com', 'Completed'),
('5e9e5b20-e0a1-4737-90ce-4624ffc1c2fa', 99.99, 'Via Duomo 237/A, 80138 Napoli (Italia)', 'PayPal', 'Shop', FALSE, 'ABC987-ZZZ1', 'massimo.gualtieri@gmail.com', 'Processing');

-- Custom_product
INSERT INTO Tailor_feda.custom_product (id, model, work_time, fabric,work_type, size, color, customer) VALUES
('91f33983-8ccb-4d9e-bc28-af314e3d5ef9', 'T-shirt summer 2020', 10, 'Cotton', 'Tailor', 'L', '#FF671A', 'tiziocaio@libero.it'),
('e91a2b7a-ec38-4969-bbc6-e41b32e5cd01', 'Smoking', 160,  'Wool', 'From scratch', '38','#FFFFF0', 'paolorossi@gmail.com'),
('a6f7eeef-5928-43b0-bddb-7dd525e64892', 'Bride dress', 200, 'Silk', 'Tailor', '42','#000000', 'massimo.gualtieri@gmail.com'),
('f62fe25e-3366-4dfc-b6ea-3bffbfed8133', 'Shorts', 5, 'Jeans', 'Tailor', 'S','#000000', 'mary.poppins@hotmail.com'),
('354ce2ef-4bd9-484c-a648-b50c57a22a97', 'Jumper Christmas 2020', 20, 'Wool', 'Tailor', 'XL', '#FF1100', 'kingjames@gmail.com'),
('e372c8f2-c9d7-40cc-8560-768eea6081b1', 'Casual Blazer', 80, 'Cotton','From scratch','52', '#FF1100', 'paolorossi@gmail.com'),
('7d4e1f71-95b1-46e1-b86f-d9c71e1505b9', 'Gold Curtain', NULL, 'Cotton','From scratch','120 x 230 cm', '#FF671A', 'massimo.gualtieri@gmail.com');

-- Appointment
INSERT INTO Tailor_feda.appointment (id, schedule, description, accepted, customer) VALUES
('75d0f606-b5aa-4852-b4dc-e159a1560831', '2010-02-07 15:00:00+02', NULL, false, 'paolorossi@gmail.com'),
('fff0a2cf-c05a-47ed-bdb9-1d5f2a40afef', '2010-05-09 10:00:00+02', NULL, false, 'tiziocaio@libero.it'),
('9cc564de-b189-43da-ac25-b2bc410668ab', '2010-03-19 10:30:00+02', NULL, false, 'massimo.gualtieri@gmail.com'),
('7686350c-09f9-4f83-a7bb-cc75d6d40646', '2010-10-09 11:00:00+02', 'If possible, I would like jeans with rips in them to be more trandy', false, 'mary.poppins@hotmail.com'),
('e815654c-a893-4ee9-829c-e2529480761d', '2010-12-12 16:30:00+02', NULL, false, 'kingjames@gmail.com'),
('69fa7e0e-49bd-42bc-b610-3907293ba168', '2010-05-18 09:00:00+02', NULL, false, 'paolorossi@gmail.com'),
('65df52a2-a54f-475c-8b16-ae160169c63e', '2010-06-09 17:30:00+02', NULL, false, 'massimo.gualtieri@gmail.com');

-- Product
INSERT INTO Tailor_feda.product (p_code, price, fabric, pictures, size, color, description, model, out_of_stock) VALUES
('15796786-df9c-41e7-b78f-44c78a7a4e7a', 14.99, 'Cotton', 'tshirt.jpeg', 'M', '#FF671A', 'A casual T-shirt', 'T-shirt summer 2020', false),
('205bad6e-b3ea-44c0-8719-abb8a5e80b4e',3000.00, 'Silk', 'bride.jpeg', '40', '#FFFFF0', 'Elegant dress for brides', 'Bride dress', false),
('9c659e9f-5a78-479b-bbb0-b1f3a391aae7',1500.00, 'Wool', 'smoking.jpeg', '48', '#000000', 'Elegant dress for formal occasions', 'Smoking', false),
('848fb568-70e5-4322-a414-54a2c60ace47', 99.99, 'Wool', 'christmas.jpeg', 'XL', '#FF1100', 'Trendy juper with Christmas drawings', 'Jumper Christmas 2020', false),
('1c65f78e-aff7-47a9-9a45-f701cef6919e',149.99, 'Cotton', 'casual-blazer.jpeg', '46', '#0000FF', 'Casual blazer for everyday life', 'Casual Blazer', false),
('0c75bbc0-c0b8-44b5-a36f-24f791533e75', 149.99, 'Cotton', 'casual-blazer.jpeg', '52', '#808080', 'Casual blazer for everyday life', 'Casual Blazer', false),
('11670643-94c2-4a93-918d-fa1b862b4209',59.99, 'Cotton', 'gold-curtain.jpeg', '140 x 260 cm', '#FAD25A', 'Gold curtain 140 x 260 cm', 'Gold Curtain', false),
('8597eea6-30a1-467a-bdde-f005c45cf6e2', 14.99, 'Cotton',  'tshirt.jpeg', 'L', '#FF671A', 'Online shop', 'T-shirt summer 2020', false);

-- Contain
INSERT INTO Tailor_feda.contain (order_customer, product) VALUES
('f2571408-08d5-440e-bb16-283fe1c4c09b', '205bad6e-b3ea-44c0-8719-abb8a5e80b4e'),
('f2571408-08d5-440e-bb16-283fe1c4c09b', '15796786-df9c-41e7-b78f-44c78a7a4e7a'),
('8472809c-b737-4303-aa5b-ef6f8b3e565e', '848fb568-70e5-4322-a414-54a2c60ace47'),
('8ef8683b-d918-4be2-9381-bbf51113382c', '9c659e9f-5a78-479b-bbb0-b1f3a391aae7'),
('c3ac527a-6550-4e6a-99c4-f03b0aa6525c', '0c75bbc0-c0b8-44b5-a36f-24f791533e75'),
('5e9e5b20-e0a1-4737-90ce-4624ffc1c2fa', '11670643-94c2-4a93-918d-fa1b862b4209');

-- Book
INSERT INTO Tailor_feda.book (customer, appointment) VALUES
('paolorossi@gmail.com', '75d0f606-b5aa-4852-b4dc-e159a1560831'),
('tiziocaio@libero.it', 'fff0a2cf-c05a-47ed-bdb9-1d5f2a40afef'),
('mary.poppins@hotmail.com', '9cc564de-b189-43da-ac25-b2bc410668ab'),
('massimo.gualtieri@gmail.com', '7686350c-09f9-4f83-a7bb-cc75d6d40646'),
('kingjames@gmail.com', 'e815654c-a893-4ee9-829c-e2529480761d');

-- Employee
INSERT INTO Tailor_feda.employee (email, name, surname, password, phone, role) VALUES
('mariorossi@feda.it', 'Mario', 'Rossi',  md5('pwd12345'), '3473837363', 'Manager'),
('filipponeri@feda.it', 'Filippo', 'Neri',  md5('pwd23456'), '3473877763', 'Employee'),
('tonibaracca@feda.it', 'Toni', 'Baracca',  md5('pwd95729'), '3451237643', 'Employee'),
('enricoverdi@feda.it', 'Enrico', 'Verdi',  md5('pwd34567'), '3473222363', 'Employee'),
('maurodaros@feda.it', 'Mauro', 'Da Ros',  md5('pwd78646'), '3368272927', 'Employee'),
('giacomo.brambilla2@feda.it', 'Giacomo', 'Brambilla',  md5('pwd12345dffr6'), '3477363383', 'Manager');

-- Manage
INSERT INTO Tailor_feda.manage (product, employee ) VALUES
('15796786-df9c-41e7-b78f-44c78a7a4e7a', 'mariorossi@feda.it'),
('205bad6e-b3ea-44c0-8719-abb8a5e80b4e', 'enricoverdi@feda.it'),
('9c659e9f-5a78-479b-bbb0-b1f3a391aae7', 'filipponeri@feda.it'),
('848fb568-70e5-4322-a414-54a2c60ace47', 'enricoverdi@feda.it'),
('0c75bbc0-c0b8-44b5-a36f-24f791533e75', 'maurodaros@feda.it'),
('11670643-94c2-4a93-918d-fa1b862b4209', 'giacomo.brambilla2@feda.it'),
('8597eea6-30a1-467a-bdde-f005c45cf6e2', 'enricoverdi@feda.it');


-- Insert one image for a specific product which already exists
UPDATE Tailor_feda.product
SET pictures[0] = pg_read_binary_file('path_to the images')::bytea
WHERE p_code = '15796786-df9c-41e7-b78f-44c78a7a4e7a';