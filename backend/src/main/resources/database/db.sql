SELECT 'CREATE DATABASE gruly ENCODING "utf8"'
WHERE NOT EXISTS
(SELECT FROM pg_database WHERE datname = 'gruly')\gexec

SELECT 'CREATE DATABASE gruly_test ENCODING "utf8"'
WHERE NOT EXISTS
(SELECT FROM pg_database WHERE datname = 'gruly_test')\gexec

