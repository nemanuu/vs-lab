mysql -u root -p$MYSQL_ROOT_PASSWORD --execute \
"CREATE database IF NOT EXISTS productsdb;
CREATE database IF NOT EXISTS categoriesdb;
CREATE database IF NOT EXISTS webshop;

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';

CREATE USER IF NOT EXISTS 'webshopuser'@'%' IDENTIFIED BY '$MYSQL_WEBSHOPUSER_PASSWORD';
GRANT ALL PRIVILEGES ON webshop.* TO 'webshopuser'@'%';

CREATE USER IF NOT EXISTS 'productuser'@'%' IDENTIFIED BY '$MYSQL_PRODUCTS_PASSWORD';
GRANT ALL PRIVILEGES ON productsdb.* TO 'productuser'@'%';

CREATE USER IF NOT EXISTS 'categoryuser'@'%' IDENTIFIED BY '$MYSQL_CATEGORIES_PASSWORD';
GRANT ALL PRIVILEGES ON categoriesdb.* TO 'categoryuser'@'%';
FLUSH PRIVILEGES;"