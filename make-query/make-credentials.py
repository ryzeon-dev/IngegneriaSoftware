import psycopg2

connection = psycopg2.connect(
    database='swedb',
    host='192.168.1.65',
    user='master',
    password='swe-db-dlv',
    port='5432'
)

cursor = connection.cursor()
cursor.execute('select * from personal')
lines = cursor.fetchall()

query = ''
for line in lines:
    id = line[0]
    name = line[1].lower()[:3]

    lastName = line[2].lower()[:3]
    role = line[3].lower()[:3]
    
    query += f"insert into credentials values (\n    '{lastName}{name}', '{role}-{lastName}-{name}', {id}\n);\n\n"

with open('insert-credentials.sql', 'w') as file:
    file.write(query)
