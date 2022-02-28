-- Connect to the database
\c feda_db

-- Select the features of the products related to a specific order by the customer Paolo Rossi
SELECT P_CODE, PICTURES, SIZE, COLOR, WORK_TYPE, MODEL FROM TAILOR_FEDA.PRODUCT AS P
  INNER JOIN TAILOR_FEDA.CONTAIN AS C
ON C.PRODUCT=P.P_CODE
  INNER JOIN TAILOR_FEDA.ORDER_CUSTOMER AS O
ON O.ID=C.ORDER_CUSTOMER
WHERE O.CUSTOMER='paolorossi@gmail.com' AND O.ID='f2571408-08d5-440e-bb16-283fe1c4c09b';

-- Count how many products are managed by each employee, with employees in alphabetic order
SELECT email AS emp_email, COUNT(email) AS managed_products
FROM Tailor_feda.product AS PROD
  INNER JOIN Tailor_feda.manage AS MAN
ON PROD.p_code = MAN.product
  INNER JOIN Tailor_feda.employee AS EMP
ON MAN.employee = EMP.email
GROUP BY email
ORDER BY email;

-- Count the employees working on at least one product in each shop
SELECT email AS shop, people_working FROM Tailor_feda.tailor_shop AS sh INNER JOIN (SELECT shop, COUNT(*) AS people_working
FROM Tailor_feda.manage AS m 
  INNER JOIN Tailor_feda.work AS w
ON m.employee = w.employee 
GROUP BY shop) AS counting ON sh.id = counting.shop;

-- Compute the average cost of an order by customers who wrote at least one review
SELECT O.customer, CAST(AVG(Tot_price) AS NUMERIC(8,2)) AS average_price 
FROM Tailor_feda.order_customer AS O
  INNER JOIN Tailor_feda.customer AS C 
ON C.email= O.customer 
  INNER JOIN Tailor_feda.review AS R 
ON C.Email = R.customer
GROUP BY O.customer;