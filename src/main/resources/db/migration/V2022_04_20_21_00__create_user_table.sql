CREATE TABLE `user` (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(32) NOT NULL,
    details json NOT NULL
)