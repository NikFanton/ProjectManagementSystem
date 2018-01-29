-- 2.sql --
SELECT sum(salary), project_name FROM projects
LEFT JOIN developers_projects ON projects.id = developers_projects.id_project
LEFT JOIN developers ON developers_projects.id_developer = developers.developer_id
GROUP BY project_name
ORDER BY sum(salary) DESC
LIMIT 1;