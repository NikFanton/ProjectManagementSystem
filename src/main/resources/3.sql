-- 3.sql --
SELECT sum(salary) FROM developers
  JOIN developers_skills ON developers.developer_id = developers_skills.developer_id
  JOIN skills ON developers_skills.skill_id = skills.skill_id
WHERE skill_name LIKE 'java';