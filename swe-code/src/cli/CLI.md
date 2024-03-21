# Classe `CLI` 
- la classe `CLI` (Command Line Interface) è resposabile dell'interazione con l'utente, fornendo le possibilità di navigazione all'interno del sistema, per poter consultare le informazioni relative a voli e personale
- al suo interno contiene un oggetto di tipo `ManagementSystem`, che viene fornito in ingresso al costruttore, e che verrà poi consultato per la lettura dei dati
- contiene inoltre un oggetto di tipo `CredentialsManager`, che viene istanziato all'interno del costrutture, che sarà poi impiegato per autenticare gli utenti e quindi fornire i corretti privilegi di accesso nelle varie sezioni del sistema
	- in particolare, l'utente `admin` ha i privilegi di `super-user`, quindi può accedere a tutte le informazioni, relative a tutti i voli e a tutti gli impiegati
	- a contrario, gli impiegati "semplici", hanno accesso unicamente ai dati che li riguardano direttamente
- 