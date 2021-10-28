-- 1) Cate comenzi a plasat farmacia Dona in august?
--    Raspuns: 3 comenzi. 
USE farmacia; 
SELECT COUNT(*) FROM comanda WHERE farmacia = 'Dona' AND MONTH(data_comanda) = 08;

-- 1) Suma totala de produse in august pentru Dona?
--    Raspuns: 76 de produse.
USE farmacia; 
SELECT SUM(cantitate) FROM detalii_comanda
JOIN comanda ON (detalii_comanda.comanda_id = comanda.id) AND (comanda.farmacia = 'Dona') AND MONTH(comanda.data_comanda) = 08;

-- 1) Valoarea medie per comanda in august pentru Dona?
--    Raspuns: 15.20 produse.
USE farmacia; 
SELECT AVG(cantitate) FROM detalii_comanda
JOIN comanda ON (detalii_comanda.comanda_id = comanda.id) AND (comanda.farmacia = 'Dona') AND MONTH(comanda.data_comanda) = 08;

-- 2) Cate comenzi de analgezice a plasat farmacia Vlad in 2021?
--    Raspuns: 2 comenzi cu 80 de produse analgezice.
USE farmacia; 
SELECT COUNT(DISTINCT comanda_id) FROM detalii_comanda
JOIN comanda ON (detalii_comanda.comanda_id = comanda.id) AND (comanda.farmacia = 'Vlad') AND Year(comanda.data_comanda) = 2021
JOIN produs ON (detalii_comanda.produs_id = produs.id) AND (produs.analgezice = TRUE);

SELECT SUM(cantitate) FROM detalii_comanda
JOIN comanda ON (detalii_comanda.comanda_id = comanda.id) AND (comanda.farmacia = 'Vlad') AND Year(comanda.data_comanda) = 2021
JOIN produs ON (detalii_comanda.produs_id = produs.id) AND (produs.analgezice = TRUE);

-- 3) Care e farmacia care a comandat cel mai mult in 2021 , ca valoare absoluta?
--    Raspuns: Farmacia Vlad cu 108 produse.
USE farmacia; 
SELECT s.farmacia, s.suma FROM (SELECT farmacia, SUM(ABS(cantitate)) AS suma FROM detalii_comanda
							   JOIN comanda ON (detalii_comanda.comanda_id = comanda.id)
							   GROUP BY farmacia) s,
                               (SELECT MAX(s1.suma) AS maxSuma FROM (SELECT farmacia, SUM(ABS(cantitate)) AS suma FROM detalii_comanda
																	JOIN comanda ON (detalii_comanda.comanda_id = comanda.id)
                                                                    GROUP BY farmacia) s1) m
WHERE s.suma = m.maxSuma;
