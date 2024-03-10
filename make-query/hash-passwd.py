import psycopg2
import hashlib as hash 

connection = psycopg2.connect(
    database='swedb',
    host='192.168.1.65',
    user='master',
    password='swe-db-dlv',
    port='5432'
)

cursor = connection.cursor()
cursor.execute('select * from credentials')
lines = cursor.fetchall()



query = ''
for line in lines:
    username = line[0]
    passwd = line[1]

    h = hash.sha512()
    h.update(passwd.encode())
    hashed = h.hexdigest()

    query += f"update credentials set passwd='{hashed}' where username='{username}';\n\n"

with open('hash-passwds.sql', 'w') as file:
    file.write(query)
