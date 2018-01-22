-- 1.sql --
ALTER TABLE developers
ADD salary INT;

UPDATE developers SET salary = 9000 WHERE developer_id = 1;
UPDATE developers SET salary = 1000 WHERE developer_id = 2;
UPDATE developers SET salary = 9000 WHERE developer_id = 3;
UPDATE developers SET salary = 2500 WHERE developer_id = 4;
UPDATE developers SET salary = 5000 WHERE developer_id = 5;
UPDATE developers SET salary = 9000 WHERE developer_id = 6;
UPDATE developers SET salary = 6500 WHERE developer_id = 7;

SELECT * FROM developers;

SELECT * FROM developers
JOIN developers_skills ON developers.developer_id = developers_skills.developer_id
JOIN skills ON developers_skills.skill_id = skills.skill_id;