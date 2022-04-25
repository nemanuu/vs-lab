CREATE database IF NOT EXISTS `productsdb`;
CREATE database IF NOT EXISTS `categoriesdb`;
CREATE database IF NOT EXISTS `webshop`;

CREATE USER IF NOT EXISTS 'webshopuser'@'%' IDENTIFIED BY '240b2c6d58ff2ce2f508b49f';
GRANT ALL PRIVILEGES ON *.* TO 'webshopuser'@'%';
FLUSH PRIVILEGES;