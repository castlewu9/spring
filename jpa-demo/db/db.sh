docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=sung -e POSTGRES_DB=jpa-demo --name jpa-demo -d postgres

# psql -d jpa-demo -U sung