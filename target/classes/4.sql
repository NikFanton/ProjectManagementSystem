-- 4.sql --
ALTER TABLE projects
ADD cost INT;

UPDATE projects AS t1
LEFT JOIN (SELECT id, project_name, id_customer, id_company, sum(salary) AS salary_sum FROM projects
    LEFT JOIN developers_projects ON projects.id = developers_projects.id_project
    LEFT JOIN developers ON developers_projects.id_developer = developers.developer_id
    GROUP BY project_name
    ORDER BY id) AS t2 ON t1.id = t2.id
SET t1.cost = t2.salary_sum;
COMMIT;

SELECT * FROM projects;
