# README #

A recently founded sartoria aims to be more competitive, by having an automated system to keep track of orders, goods and products and by entering the online market. In this way, this business would open to a broader audience, allowing more people to see and buy its products, and the processes needed to actually make the products would be more efficient.
In order to achieve this, the plan is to build a system that manages all these features, i.e. an e-commerce website with also management functionalities. Since the business is a pretty young activity, the system must be flexible enough to comply with its evolution, at least at the beginning.

### Setup the database ###
1.  After accessing pgAdmin create a new login/group role and set the following parameter:
	1. General:	
		- name: tailorshop
		- psw : tailorshop
	2. Privileges:
		- superuser
2. Create database named feda_db and assign it to the user tailorshop newly created
3. Create a new server
	1. General: 
		- name: TailorShop
	2. Connection
		- host name: localhost (or the hostname that you are using)
		- Manteinence DB: feda_db
		- username: tailorshop
		- psw : tailorshop

Once that all this operation are done, create the table using the db_schema.sql and populate them using the file insert_population.sql.


### How to try the REST part ###
You can find the commands used to try the REST part in the rest-commands.txt file.

### Some other notes ###
In the various DAO files, Servlets, etc. it is also possible to find methods that have not been used in the app developed so far. This is because we think it is useful to develop methods that could be useful in the future to make the application perform further functions. 
