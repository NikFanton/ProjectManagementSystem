CREATE DATABASE itsdb;

USE itsdb;

CREATE TABLE skills(
  skill_id INT AUTO_INCREMENT PRIMARY KEY,
  skill_name VARCHAR(128),
  `level` VARCHAR(64)
);

CREATE TABLE developers(
  developer_id INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(128),
  age INT,
  sex VARCHAR(16)
);

CREATE TABLE developers_skills(
  developer_id INT NOT NULL,
  skill_id INT NOT NULL,
  FOREIGN KEY (developer_id) REFERENCES developers(developer_id),
  FOREIGN KEY (skill_id) REFERENCES skills(skill_id),
  UNIQUE (developer_id, skill_id)
);

CREATE TABLE customers(
  id INT AUTO_INCREMENT PRIMARY KEY,
  `customer_name` VARCHAR(64)
);

CREATE TABLE companies(
  id INT AUTO_INCREMENT PRIMARY KEY,
  `company_name` VARCHAR(64),
  `year_of_foundation` INT
);

CREATE TABLE projects(
  id INT AUTO_INCREMENT PRIMARY KEY,
  `project_name` VARCHAR(64),
  id_customer INT NOT NULL,
  id_company INT NOT NULL,
  FOREIGN KEY (id_customer) REFERENCES customers(id),
  FOREIGN KEY (id_company) REFERENCES companies(id)
);

CREATE TABLE IF NOT EXISTS developers_projects(
  id_developer INT NOT NULL,
  id_project INT NOT NULL,
  FOREIGN KEY (id_developer) REFERENCES developers(developer_id),
FOREIGN KEY (id_project) REFERENCES projects(id),
UNIQUE (id_developer, id_project)
);