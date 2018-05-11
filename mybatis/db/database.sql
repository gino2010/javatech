-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS demo_user;
CREATE TABLE demo_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  age int(11) DEFAULT NULL,
  address varchar(200) DEFAULT NULL,
  create_time timestamp NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
