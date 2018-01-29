USE itsdb;

INSERT INTO customers(customer_name)
VALUES ('GoIT'), ('Big City'), ('Fire Wing'), ('Energy'), ('FutureLink'),
  ('Key Press'), ('Big Corn'), ('Loft'), ('Sofany'), ('Leader');

INSERT INTO companies(company_name, year_of_foundation)
VALUES ('Ubisoft', 1995),
  ('Epam Systems', 2005),
  ('Luxoft', 2002),
  ('GlobalLogic', 1996),
  ('Softline', 2006),
  ('SoftServe', 2005),
  ('Ciklum', 2002),
  ('DataArt', 1997);

INSERT INTO projects(project_name, id_customer, id_company)
VALUES ('Element', 1, 2),
  ('Warcraft', 1, 2),
  ('Man in space', 3, 4),
  ('Big boom', 1, 1),
  ('Secret room', 5, 5),
  ('Accounting', 10, 6),
  ('Last Stone', 6, 8),
  ('Interrupt', 5, 5);

INSERT INTO developers(name, age, sex)
VALUES ('Egor', 20, 'male'),
  ('Nikita', 18, 'male'),
  ('Kate', 30, 'female'),
  ('Jenifer', 21, 'female'),
  ('Brad', 19, 'male'),
  ('Ann', 25, 'female'),
  ('Peter', 23, 'male');

INSERT INTO skills (skill_name, level)
VALUES ('JS', 'middle'),
('Java', 'junior'),
('C++', 'senior'),
('JS', 'junior'),
('Java', 'middle'),
('Java', 'senior'),
('C#', 'junior'),
('Android', 'middle');

INSERT INTO developers_skills (developer_id, skill_id)
VALUES (1, 3), (1, 4), (1, 2), (2, 2), (2, 3), (2, 7),
(3, 1), (4, 2), (5, 8), (6, 4), (6, 3), (7, 1), (7, 8);


INSERT INTO developers_projects(id_developer, id_project)
VALUES (1, 2), (1, 8), (1, 3), (2, 1), (2, 3), (2, 4),
(3, 3), (4, 5), (5, 6), (6, 4), (6, 5), (7, 5), (7, 2), (3, 7), (6,7);

-- company and customer 1->oo project --
SELECT * FROM projects
  LEFT JOIN companies ON (projects.id_company = companies.id);

SELECT * FROM projects
  LEFT JOIN customers ON (projects.id_customer = customers.id);

SELECT
  projects.id,
  project_name,
  company_name,
  customer_name
FROM projects
  LEFT JOIN companies ON (projects.id_company = companies.id)
  LEFT JOIN customers ON (projects.id_customer = customers.id);

-- developers oo->oo skills--
SELECT
  developers.developer_id,
  developers.name,
  age,
  sex,
  skill_name,
  level
FROM developers
JOIN developers_skills ON (developers.developer_id = developers_skills.developer_id)
JOIN skills ON (developers_skills.skill_id = skills.skill_id);

SELECT
developer_id,
name,
age,
sex,
project_name,
company_name,
customer_name
FROM developers
JOIN developers_projects ON developers.developer_id = developers_projects.id_developer
JOIN projects ON developers_projects.id_project = projects.id
JOIN companies ON projects.id_company = companies.id
JOIN customers ON projects.id_customer = customers.id;