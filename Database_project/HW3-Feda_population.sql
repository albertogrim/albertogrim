-- Connect to the database
\c feda_db

-- Populate the tables
-- Tailor's shop
INSERT INTO Tailor_feda.tailor_shop (id, phone, email, address) VALUES
    ('6c4dbb99-ab18-4891-a190-3bf125324177', '3485678792', 'roma@feda.it', 'Piazza Navona 5, 12345 Roma (Italia)'),
    ('0fe07604-0115-4bdf-89b9-56eb327a7c69', '3567868866', 'milano@feda.it', 'Via Monte Napoleone 35, 64783 Milano (Italia)'),
    ('750f3f46-9c95-4b8c-b1fc-54c1c55daf5d', '0413462923', 'venezia@feda.it', 'Campo dei Sarti 291, 30123 Venezia (Italia)'),
    ('744456aa-e5a2-4886-8e14-abeaa28f7026','343536373', 'torino@feda.it', 'via Livorno 34, 63524 Torino (Italia)'),
    ('31959806-fd52-49d5-9dbd-eff7f1ed56f0','3465659792', 'napoli@feda.it', 'Via Duomo 237/A, 80138 Napoli (Italia)');

-- Status
INSERT INTO Tailor_feda.status (name) VALUES
    ('Pending'),
    ('Accepted'),
    ('Processing'),
    ('Delivering'),
    ('Completed'),
    ('Refunded');

-- Product_t    
INSERT INTO Tailor_feda.product_t (model, work_time, price, fabric, origin, description) VALUES
    ('T-shirt summer 2020', 10, 14.99, 'Cotton', 'Tailor', 'A simple and cheap T-shirt'),
    ('Smoking', 160, 1500.00, 'Wool', 'Customer', 'Elegant dress for formal occasions'),
    ('Bride dress', 200, 3000.00, 'Silk', 'Customer', 'Elegant dress for brides'),
    ('Shorts', 5, 4.99, 'Jeans', 'Tailor', 'Casual shorts'),
    ('Jumper Christmas 2020', 20, 99.99, 'Wool', 'Tailor', 'Trendy juper with Christmas drawings'),
    ('Casual Blazer', 80, 149.99, 'Cotton', 'Tailor', 'Casual blazer for everyday life'),
    ('Gold Curtain', NULL, 99.99, 'Cotton', 'Tailor', 'Gold curtain 140 x 260 cm');

-- Product    
INSERT INTO Tailor_feda.product (p_code, pictures, size, color, work_type, model) VALUES
    ('15796786-df9c-41e7-b78f-44c78a7a4e7a', NULL, 'M', '#FF671A', 'Online shop', 'T-shirt summer 2020'),
    ('205bad6e-b3ea-44c0-8719-abb8a5e80b4e', NULL, '40', '#FFFFF0', 'From scratch', 'Bride dress'),
    ('9c659e9f-5a78-479b-bbb0-b1f3a391aae7', NULL, '48', '#000000', 'Tailor', 'Smoking'),
    ('848fb568-70e5-4322-a414-54a2c60ace47', NULL, 'XL', '#FF1100', 'Online shop', 'Jumper Christmas 2020'),
    ('1c65f78e-aff7-47a9-9a45-f701cef6919e', NULL, '46', '#0000FF', 'Online shop', 'Casual Blazer'),
    ('0c75bbc0-c0b8-44b5-a36f-24f791533e75', NULL, '52', '#808080', 'Online shop', 'Casual Blazer'),
    ('11670643-94c2-4a93-918d-fa1b862b4209', NULL, '140 x 260 cm', '#FAD25A', 'Online shop', 'Gold Curtain'),
    ('8597eea6-30a1-467a-bdde-f005c45cf6e2', NULL, 'L', '#FF671A', 'Online shop', 'T-shirt summer 2020');
    
-- Insert one image for a specific product which already exists
UPDATE Tailor_feda.product
SET pictures[0] = pg_read_binary_file('E:\UNIPD\Foundations of Databases\HW 3\tshirt.jpg')::bytea
WHERE p_code = '15796786-df9c-41e7-b78f-44c78a7a4e7a';

-- Customer
INSERT INTO Tailor_feda.customer (email, name, surname, password, phone, addresses, newsletter, get_to_know, sizes, lifestyle, shop) VALUES
    ('paolorossi@gmail.com', 'Paolo', 'Rossi', crypt(('pwd56789')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3368495859', '{"Via Roma 1, 12345 Roma (Italia)", "Viale Garibaldi 25, 54321 Milano (Italia)"}', FALSE, NULL, NULL, NULL, '6c4dbb99-ab18-4891-a190-3bf125324177'),
    ('tiziocaio@libero.it', 'Tizio', 'Caio', crypt(('pwd78901')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '33474748476', '{"Via Mazzini 6, 25373 Milano (Italia)"}', TRUE, 'Friends', '52', 'Athlete', '0fe07604-0115-4bdf-89b9-56eb327a7c69'),
    ('mary.poppins@hotmail.com', 'Mary', 'Poppins',crypt(('superCalifragi1')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '+102135689073', '{"Chimney road 1232, 283472 London (Great Britain)"}', TRUE, 'Advertisement', '38', 'Vintage', '750f3f46-9c95-4b8c-b1fc-54c1c55daf5d'),
    ('kingjames@gmail.com', 'Lebron', 'James', crypt(('lbjgoat23')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '4363829272', '{"Normandie Ave, 90044 Los Angeles California(CA)"}', FALSE, 'friends', NULL, 'NBA player', '744456aa-e5a2-4886-8e14-abeaa28f7026'),
    ('massimo.gualtieri@gmail.com', 'Massimo', 'Gualtieri', crypt(('forzanapoli92')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3366689897', '{"Via Gioacchino Murat 77, 80058 Torre Annunziata(Italia)"}', TRUE, 'Google', NULL, 'Streetwear', '31959806-fd52-49d5-9dbd-eff7f1ed56f0');

-- Order_customer
INSERT INTO Tailor_feda.order_customer (id, tot_price, address, p_method, delivery_mode, is_cancelled, invoice, customer, status) VALUES
    ('f2571408-08d5-440e-bb16-283fe1c4c09b', 3014.99, 'Via Roma 1, 12345 Roma (Italia)', 'Bank transfer', 'Home', false, 'ABC123-ZZZ1', 'paolorossi@gmail.com', 'Accepted'),
    ('8472809c-b737-4303-aa5b-ef6f8b3e565e', 99.99, 'Viale Garibaldi 25, 54321 Milano (Italia)', 'Credit/debit card', 'Home', false, 'ABC123-ZZZ4', 'paolorossi@gmail.com', 'Pending'),
    ('8ef8683b-d918-4be2-9381-bbf51113382c', 1500.00, 'Viale Venezia 253, 54321 Milano (Italia)', 'PayPal', 'Home', false, 'ABC123-ZZZ2', 'tiziocaio@libero.it', 'Processing'),
    ('c3ac527a-6550-4e6a-99c4-f03b0aa6525c', 149.99, 'via Livorno 34, 63524 Torino (Italia)', 'PayPal', 'Shop', FALSE, 'ABC123-ZZZ6', 'kingjames@gmail.com', 'Completed'),
    ('5e9e5b20-e0a1-4737-90ce-4624ffc1c2fa', 99.99, 'Via Duomo 237/A, 80138 Napoli (Italia)', 'PayPal', 'Shop', FALSE, 'ABC987-ZZZ1', 'massimo.gualtieri@gmail.com', 'Processing');

-- Contain    
INSERT INTO Tailor_feda.contain (order_customer, product) VALUES
    ('f2571408-08d5-440e-bb16-283fe1c4c09b', '205bad6e-b3ea-44c0-8719-abb8a5e80b4e'),
    ('f2571408-08d5-440e-bb16-283fe1c4c09b', '15796786-df9c-41e7-b78f-44c78a7a4e7a'),
    ('8472809c-b737-4303-aa5b-ef6f8b3e565e', '848fb568-70e5-4322-a414-54a2c60ace47'),
    ('8ef8683b-d918-4be2-9381-bbf51113382c', '9c659e9f-5a78-479b-bbb0-b1f3a391aae7'),
    ('c3ac527a-6550-4e6a-99c4-f03b0aa6525c', '0c75bbc0-c0b8-44b5-a36f-24f791533e75'),
    ('5e9e5b20-e0a1-4737-90ce-4624ffc1c2fa', '11670643-94c2-4a93-918d-fa1b862b4209');

-- Employee
INSERT INTO Tailor_feda.employee (email, name, surname, password, phone, role) VALUES
    ('mariorossi@feda.it', 'Mario', 'Rossi', crypt(('pwd12345')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3473837363', 'Manager'),
    ('filipponeri@feda.it', 'Filippo', 'Neri', crypt(('pwd23456')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3473877763', 'Employee'),
    ('tonibaracca@feda.it', 'Toni', 'Baracca', crypt(('pwd95729')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3451237643', 'Employee'),
    ('enricoverdi@feda.it', 'Enrico', 'Verdi', crypt(('pwd34567')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3473222363', 'Employee'),
    ('maurodaros@feda.it', 'Mauro', 'Da Ros', crypt(('pwd78646')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3368272927', 'Employee'),
    ('giacomo.brambilla2@feda.it', 'Giacomo', 'Brambilla', crypt(('pwd12345dffr6')::Tailor_feda.PASSWD, gen_salt('bf', 4)), '3477363383', 'Manager');
    
-- Article
INSERT INTO Tailor_feda.article (topic, article_text, employee) VALUES
    ('Fashion Week', 'The former fashion writer and contributor to major online luxury fashion destinationsâ”€ever heard of Net-a-Porter and Matchesfashion has undoubtedly become the face of cool, French girl style.', 'mariorossi@feda.it'),
    ('Fashion materials', 'From fast fashion to cotton, the list of ways we consume fashion that have an adverse effect on our environment and other species is a long one. But is there hope? Apart from consuming less, recycling and repairing, can we find clothes and textiles which are more sustainable? And if you have to buy new clothes, which fabrics should you look out for? Here are six materials or innovations which could help in the drive for a more sustainable way of life.', 'tonibaracca@feda.it'),
    ('Moda', 'Moda sostenibile: e se il futuro fosse digitale? La industria della moda produce ogni anno 100 miliardi di abiti, dei quali si stima che 3 capi su 5 finiscano in discarica entro i primi 12 mesi.', 'filipponeri@feda.it'),
    ('Fashion Sustainability', 'Are Christmas jumpers bad for the environment?For many, it is just a harmless bit of fun, which helps you get into the party spirit, but what are the environmental implications for buying a garment that will go straight to the back of the cupboard in the New Year?', 'maurodaros@feda.it'),
    ('Winter Workout Look', 'Sometimes it seems that supermodels like Bella Hadid are impervious to the cold. After all, yesterday, Hadid stepped out in New York City wearing a teeny tiny sports bra and leggings en route to a workout. (She later posted an Instagram story of herself lifting weights.) One charming styling touch here was a pair of socks that were pulled-up above her spandex. And while the look was certainly midriff-baring, she was semi covered-up thanks to an XXL black The North Face jacket.', 'giacomo.brambilla2@feda.it');

-- Manage
INSERT INTO Tailor_feda.manage (product, employee) VALUES
    ('15796786-df9c-41e7-b78f-44c78a7a4e7a', 'mariorossi@feda.it'),
    ('205bad6e-b3ea-44c0-8719-abb8a5e80b4e', 'enricoverdi@feda.it'),
    ('9c659e9f-5a78-479b-bbb0-b1f3a391aae7', 'filipponeri@feda.it'),
    ('848fb568-70e5-4322-a414-54a2c60ace47', 'enricoverdi@feda.it'),
    ('0c75bbc0-c0b8-44b5-a36f-24f791533e75', 'maurodaros@feda.it'),
    ('11670643-94c2-4a93-918d-fa1b862b4209', 'giacomo.brambilla2@feda.it'),
    ('8597eea6-30a1-467a-bdde-f005c45cf6e2', 'enricoverdi@feda.it');
    
-- Work
INSERT INTO Tailor_feda.work (employee, shop) VALUES
    ('mariorossi@feda.it', '6c4dbb99-ab18-4891-a190-3bf125324177'),
    ('mariorossi@feda.it', '0fe07604-0115-4bdf-89b9-56eb327a7c69'),
    ('mariorossi@feda.it', '750f3f46-9c95-4b8c-b1fc-54c1c55daf5d'),
    ('filipponeri@feda.it', '6c4dbb99-ab18-4891-a190-3bf125324177'),
    ('enricoverdi@feda.it', '0fe07604-0115-4bdf-89b9-56eb327a7c69'),
    ('tonibaracca@feda.it', '750f3f46-9c95-4b8c-b1fc-54c1c55daf5d'),
    ('maurodaros@feda.it', '744456aa-e5a2-4886-8e14-abeaa28f7026'),
    ('giacomo.brambilla2@feda.it', '31959806-fd52-49d5-9dbd-eff7f1ed56f0');
    
-- Supplier
INSERT INTO Tailor_feda.supplier(VAT_number,name, email) VALUES
    ('0344352075A' ,'Stoffe&Tessuti', 'sandt@gmail.com'),
    ('8942794996T', 'Manfrotto Tessuti', 'info@manfrotto.it'),
    ('2834718347V', 'Nihao s.p.a.', 'contact@nihao.com'),
    ('6372526655D', 'Giesse Scampoli', 'info@giesse.it'),
    ('0352075A443' ,'Cloth House', 'clo.house@gmail.com');

-- Order_supplier
INSERT INTO Tailor_feda.order_supplier (total_price, employee, supplier) VALUES
    (138.50 ,'mariorossi@feda.it', '0344352075A'),
    (212.70,'enricoverdi@feda.it', '8942794996T'),
    (85.00, 'filipponeri@feda.it' ,'0344352075A'),
    (38.00, 'filipponeri@feda.it', '8942794996T'),
    (1380.00, 'tonibaracca@feda.it', '2834718347V'),
    (500.00, 'maurodaros@feda.it', '6372526655D'),
    (650, 'giacomo.brambilla2@feda.it', '0352075A443');

-- Review
INSERT INTO Tailor_feda.review(description, reply, customer, product) VALUES
    ('Fits very well and very affordable. Great job by tailor shop','Thank you very much!','paolorossi@gmail.com','15796786-df9c-41e7-b78f-44c78a7a4e7a'),
    ('The dress is really nice, good job', 'Thank you fot the comment', 'tiziocaio@libero.it','9c659e9f-5a78-479b-bbb0-b1f3a391aae7'),
    ('The jumper is really trendy, I like it very much!', NULL, 'mary.poppins@hotmail.com', '848fb568-70e5-4322-a414-54a2c60ace47'),
    ('Good material','Thank you so much','massimo.gualtieri@gmail.com', '11670643-94c2-4a93-918d-fa1b862b4209');
    
-- Request_quote
INSERT INTO Tailor_feda.request_quote(model, customer, quote) VALUES
    ('T-shirt summer 2020', 'tiziocaio@libero.it', 14.99),
    ('Shorts', 'paolorossi@gmail.com', 4.99),
    ('Bride dress', 'mary.poppins@hotmail.com', 2900.00),
    ('Casual Blazer', 'kingjames@gmail.com', 149.99),
    ('Gold Curtain', 'massimo.gualtieri@gmail.com', 99.99);