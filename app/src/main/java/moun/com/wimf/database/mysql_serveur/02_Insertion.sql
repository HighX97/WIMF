--source 02_Insertion.sql
--Table Utilisateur
SELECT * FROM Utilisateur
;
INSERT INTO Utilisateur
(nom,tel)
VALUES
("loic","0695504940")
;
INSERT INTO Utilisateur
(nom,tel)
VALUES
("mariam","0753369827")
;
INSERT INTO Utilisateur
(nom,tel)
VALUES
("jimmy","0789654004")
;
INSERT INTO Utilisateur
(nom,tel)
VALUES
("chahinaz","0783573458")
;
SELECT * FROM Utilisateur
;

--Table Amis
INSERT INTO Amis
(idU1,idU2)
VALUES
(1,2)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(1,3)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(1,4)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(2,1)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(2,3)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(2,4)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(3,1)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(3,2)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(3,4)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(4,1)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(4,2)
;
INSERT INTO Amis
(idU1,idU2)
VALUES
(4,3)
;
SELECT * FROM Amis
;

--Table Message
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",1,2)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",1,3)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",1,4)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",2,1)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",2,3)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",2,4)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",3,1)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",3,2)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",3,4)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",4,1)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",4,2)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",4,3)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",1,2)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",1,3)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",1,4)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",2,1)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",2,3)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",2,4)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",3,1)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",3,2)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",3,4)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",4,1)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",4,2)
;
INSERT INTO Message
(valeur,idU1,idU2)
VALUES
("Message",4,3)
;
SELECT * from Message;
