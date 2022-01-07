DROP DATABASE IF EXISTS ThogaKade;
CREATE DATABASE IF NOT EXISTS ThogaKade;
SHOW DATABASES ;
USE ThogaKade;


DROP TABLE IF EXISTS Users;
CREATE TABLE IF NOT EXISTS Users(
    name VARCHAR(15) NOT NULL,
    password VARCHAR(45) NOT NULL,
    isAdmin tinyint(1) NOT NULL,
    CONSTRAINT PRIMARY KEY (name)
    );
SHOW TABLES ;
DESCRIBE Users;
INSERT INTO Users VALUES ('chama','123','1'),
			('chamath','1234','0');

SELECT * FROM Users;


DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    id VARCHAR(15),
    name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    address TEXT,
    province VARCHAR(20),
    postalCode VARCHAR(20),
    CONSTRAINT PRIMARY KEY (id)
    );
SHOW TABLES ;
DESCRIBE Customer;


DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    orderId VARCHAR(15),
    cId VARCHAR(15),
    orderDate DATE,
    time VARCHAR(15),
    cost DOUBLE,
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (cId) REFERENCES Customer(id) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order`;


DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    code VARCHAR(15),
    description TEXT,
    qtyOnHand INT DEFAULT 0,
    unitPrice DOUBLE DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (code)
    );
SHOW TABLES ;
DESCRIBE Item;


DROP TABLE IF EXISTS `Order Detail`;
CREATE TABLE IF NOT EXISTS `Order Detail`(
    itemCode VARCHAR(15),
    orderId VARCHAR(15),
    qty INT,
    price DOUBLE,
    CONSTRAINT PRIMARY KEY (itemCode, orderId),
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(code) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES `Order`(orderId) ON DELETE CASCADE ON UPDATE CASCADE
    );
SHOW TABLES ;
DESCRIBE `Order Detail`;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////


DROP TABLE IF EXISTS `Customer`;
CREATE TABLE `Customer` (
  `id` varchar(30) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
   `province` VARCHAR(20)DEFAULT NULL,
   `postalCode` VARCHAR(20)DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Item`;
CREATE TABLE `Item` (
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `qtyOnHand` int(10) DEFAULT NULL,
  `unitPrice` DOUBLE,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Orders`;
CREATE TABLE `Orders` (
  `oid` varchar(255) NOT NULL,
  `customerID` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` VARCHAR(15)DEFAULT NULL,
  `cost` DOUBLE,
  PRIMARY KEY (`oid`),
  KEY `customerID` (`customerID`),
  CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `Customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `OrderDetails`;
CREATE TABLE `OrderDetails` (
  `itemCode` varchar(255) NOT NULL,
  `oid` varchar(255) NOT NULL,
  `qty` int(10) DEFAULT NULL,
  `unitPrice` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`oid`,`itemCode`),
  KEY `itemCode` (`itemCode`),
  CONSTRAINT `OrderDetails_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `Orders` (`oid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `OrderDetails_ibfk_2` FOREIGN KEY (`itemCode`) REFERENCES `Item` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;