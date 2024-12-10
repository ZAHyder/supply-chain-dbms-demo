Company (name, phone country, streetInfo)
CompanyEmail (c_name, email)
ZipCity (zip, city)
Produces (c_name, pid, capacity)
Product (pid, brand, description, name)
Transaction (c_name, pid, date, amount)

PRIMARY KEY (Company) = <name>
PRIMARY KEY (CompanyEmail) = <name, email>
PRIMARY KEY (ZipCity) = <zip>
PRIMARY KEY (Produces) = <c_name, pid>
PRIMARY KEY (Product) = <pid>
PRIMARY KEY (Transaction) = <c_name, pid>

FOREIGN KEY CompanyEmail (c_name) REFERENCES Company (name)
FOREIGN KEY Produces (c_name) REFERENCES Company (name)
FOREIGN KEY Produces (pid) REFERENCES Product (pid)
FOREIGN KEY Transaction (pid) REFERENCES Product (pid)
FOREIGN KEY Transaction (c_name) REFERENCES Company (name)