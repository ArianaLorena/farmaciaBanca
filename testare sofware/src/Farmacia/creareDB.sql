-- Creare database + tables
CREATE database farmacia;
USE farmacia;

CREATE TABLE comanda(
	id INT NOT NULL auto_increment,
    farmacia VARCHAR(255) NOT NULL,
    data_comanda DATE NOT NULL,
    PRIMARY KEY(id)
);
 
CREATE TABLE produs(
	id INT NOT NULL auto_increment,
    nume VARCHAR(255) NOT NULL,
    analgezice BOOLEAN NOT NULL,
    anabolizante BOOLEAN NOT NULL,
    vitamine BOOLEAN NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE detalii_comanda(
    comanda_id INT NOT NULL,
    produs_id INT NOT NULL,
    cantitate INT NOT NULL,
    FOREIGN KEY(comanda_id) REFERENCES comanda(id),
    FOREIGN KEY(produs_id) REFERENCES produs(id)
);

-- Random Data for comanda
USE farmacia;
INSERT INTO comanda(farmacia, data_comanda) VALUES ('Dona', '2021-08-20');
INSERT INTO comanda(farmacia, data_comanda) VALUES ('Dona', '2021-08-21');
INSERT INTO comanda(farmacia, data_comanda) VALUES ('Dona', '2021-08-22');
INSERT INTO comanda(farmacia, data_comanda) VALUES ('Dona', '2021-09-01');
INSERT INTO comanda(farmacia, data_comanda) VALUES ('Dona', '2021-09-02');
INSERT INTO comanda(farmacia, data_comanda) VALUES ('Vlad', '2021-09-01');
INSERT INTO comanda(farmacia, data_comanda) VALUES ('Vlad', '2021-09-02');

SELECT * FROM comanda;

-- Random Data For produs
USE farmacia;
INSERT INTO produs(nume, analgezice, anabolizante, vitamine) VALUES('AnalgeziceA', TRUE, FALSE, FALSE);
INSERT INTO produs(nume, analgezice, anabolizante, vitamine) VALUES('AnabolizanteA', FALSE, TRUE, FALSE);
INSERT INTO produs(nume, analgezice, anabolizante, vitamine) VALUES('VitamineA', FALSE, FALSE, TRUE);
INSERT INTO produs(nume, analgezice, anabolizante, vitamine) VALUES('AnalgeziceAnabolizante', TRUE, TRUE, FALSE);
INSERT INTO produs(nume, analgezice, anabolizante, vitamine) VALUES('AnabolizanteVitamine', FALSE, TRUE, TRUE);
INSERT INTO produs(nume, analgezice, anabolizante, vitamine) VALUES('VitamineAnalgezice', TRUE, FALSE, TRUE);
INSERT INTO produs(nume, analgezice, anabolizante, vitamine) VALUES('VitamineAnalgeziceAnabolizante', TRUE, TRUE, TRUE);

SELECT * FROM produs;

-- Random Data for detalii_comanda 
USE farmacia;
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (1, 1, 20);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (1, 2, 15);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (2, 3, 4);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (3, 3, 10);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (3, 7, 27);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (4, 1, 20);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (5, 4, 6);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (6, 5, 20);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (6, 6, 37);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (7, 1, 28);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (7, 2, 8);
INSERT INTO detalii_comanda(comanda_id, produs_id, cantitate) VALUES (7, 6, 15);

SELECT * FROM detalii_comanda;
 