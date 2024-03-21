# Classe DBConfig
- classe atta a leggere i dati necessari per accedere al database PostgreSQL, contenuti all'interno dell'apposito file `db.properties`
- la configurazione della classe è puramente statica, e ha la funzione di leggere e racchiudere i dati 
- sono forniti appositi metodi dedicati ad ogni parametro di interesse presente nel file di configurazione
	- `String getUrl()` restituisce l'indirizzo IP statico del server (in formato di una stringa)
	- `String getUsername()` restituisce l'username da impiegare per accedere al database (in formato di una stringa)
	- `String getPasswd()` restituisce la password da impiegare per accedere al database (in formato di una stringa)

# Classe PgDB
- classe di interazione fisica col DB
- nel costruttore, chiamando i metodi statici appartenenti alla classe `DBConfig`, stabilisce una connessione fisica col server, accedendo al Database
- fornisce tre metodi fondamentali:
	- `Vector<Vector<String>> runAndFetch(String query)` che permette di eseguire una query e ricevere in risposta i dati, che vengono restituiti nel formato di una matrice di stringhe 
	- `boolean run(String query)`, che si limita ad eseguire la query specificata e restituire un booleano, che rappresenta l'esito di esecuzione della query stessa
	- `void close()` che chiude la connessione col Database

# Classe ConstantQueries
- classe contenente alcune query di interesse, atte alla costruzione delle strutture dati in derivazione dal database
- ogni stringa è statica, quindi comune ad ogni istanza della classe
- questa struttura dati è da vedere unicamente come un container, in quanto non fornisce nessun metodo o funzionalità, ma si limita a raggruppare delle stringhe