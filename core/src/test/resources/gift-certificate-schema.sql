CREATE TABLE gift_certificate
(
    id               INT           NOT NULL AUTO_INCREMENT,
    name             VARCHAR(40)   NOT NULL,
    description      VARCHAR(100)   NOT NULL,
    price            DECIMAL(9, 2) NOT NULL,
    duration         INT           NOT NULL,
    create_date      TIMESTAMP     NOT NULL,
    last_update_date TIMESTAMP     NOT NULL,
    PRIMARY KEY (id)
);