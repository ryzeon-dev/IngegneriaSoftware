

![[Pasted image 20240810180152.png]]


<p align="center"> UNIVERSITÀ DEGLI STUDI DI FIRENZE <br>
		DIPARTIMENTO DI INGEGNERIA DELL'INFORMAZIONE </p>
		<hr>

<p align="center"><b> RELAZIONE DI PROGETTO, seconda tipologia </b></p>

<hr>



Autori: \
Loris Vettori \
Corrado Duccio Piccioni \
Vincenzo Marturano

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>















[[#Statement]] 

[[#Il database]] 

[[#Dizionario dei dati]]
* [[#Entità dello schema ER]]
* [[#Relazioni dello schema]]
* [[#Regole di vincolo]]

[[#Schema Entity-Relationships]]
[[#Ristrutturazione dello schema E-R]]
[[#Schema Logico]]

[[#Analisi dei requisiti]]

[[#Use cases e templates]]

* [[#Use case e template 1]]
* [[#Use case e template 2]]
* [[#Use case 3]]
* [[#Use case 4]]
* [[#Use case 5]]

[[#Progettazione]]

* [[#Class diagrams]]
* [[#Aspetti da attenzionare]]

[[#Class diagrams]]

[[#CLI]]
[[#FlightRouteDaoPg]]
[[#EmployeeDaoPg]]
[[#PgDB]]

[[#Domain Model]]
* [[#Aircraft]]
* [[#Airport]]
* [[#Credentials]]
* [[#Dimension Class]]
* [[#Employee]]
* [[#EmployeeRole]]
* [[#Flight]]
* [[#FlightRoute]]

[[#Business logic]]
* [[#CredentialsManager]]
* [[#FlightManager]]
* [[#FlightSchedule]]
* [[#ManagementSystem]]
* [[#SimulatedClock]]
* [[#SchedulingStrategy]]
* [[#SimpleSchedule]]



# Statement 

La compagnia aerea ITA Airways necessita di un software per la gestione giornaliera dell’impiego dei suoi velivoli nelle rotte offerte. Si ha esigenza di includere l’assegnazione degli aeromobili adeguati e del personale alle tratte, compatibilmente con la distanza dalla destinazione e con il numero di passeggeri, in ottemperanza della capacità di un aeroporto di accogliere aerei di una determinata classe.

I velivoli, di varia tipologia, devono essere adatti alla tratta, con criteri quali: numero di biglietti prenotati (numero di posti maggiore o uguale alle prenotazioni), range dell’aereo (autonomia dichiarata dalla casa produttrice, che deve consentire l’esecuzione senza scali della tratta), classe degli aeroporti di arrivo, scalo e partenza che devono essere in grado di accogliere quella specifica categoria di aeromobili.

A ciascun volo viene assegnato un aeromobile, in base al tipo del quale l’ENAC (Ente Nazionale Aviazione Civile), oltre a comandante e primo ufficiale, prevede un numero minimo di assistenti di volo per poter autorizzare il decollo. Altra norma dell’ aviazione è che, per un volo superiore alle nove ore, ci siano due equipaggi presenti in cabina di pilotaggio.

L’abilitazione dei piloti è strettamente legata al modello di aereo. Eccezione risiede nella famiglia Airbus A320: un pilota abilitato per tale modello, può infatti mettersi ai comandi anche di A318, A319, A320 e A321, dovuto al fatto che sono aerei molto simili tra loro. Nel caso in cui un pilota si abiliti per un nuovo velivolo,è previsto il decadimento del brevetto precedente. 

Ciascun velivolo è tracciato; in particolare è necessario conoscere, in ogni momento, se stia effettuando una tratta o se si trovi parcheggiato in qualche aeroporto. E’ il pilota a comunicare alla compagnia il distacco dal gate e l’atterraggio. Tramite un orologio simulato è possibile interagire con la funzione di monitoraggio degli aerei, conoscendo lo stato dei voli in ogni momento.

---

# Il database
## Dizionario dei dati

### Entità dello schema ER

| Entità            | Descrizione                        | Attributi                                                                      | Identificatore |
| ----------------- | ---------------------------------- | ------------------------------------------------------------------------------ | -------------- |
| Aircraft          | Tipologia di aeromobile            | id, manufacturer, model, specification, range, assistants_number, class, seats | id             |
| Aircraft_instance | Particolare aeromobile             | plate                                                                          | plate          |
| Airport           | Aeroporto nel mondo                | icao, class, name, nation, city                                                | icao           |
| Route             | Tragitto collegato dalla compagnia | id, distance, duration                                                         | id             |
| Flight            | Specifico volo                     | id, departure_time, passengers_number                                          | id             |
| Personal          | Operatori della compagnia          | id, name, lastName, role, abilitation                                          | id             |
|                   |                                    |                                                                                |                |
|                   |                                    |                                                                                |                |


### Relazioni dello schema

| Nome          | Descrizione                                                                   | Entità coinvolte e cardinalità         | Attributi dell'associazione |
| ------------- | ----------------------------------------------------------------------------- | -------------------------------------- | --------------------------- |
| belong*       | Associa a ciascun modello di aeromobile le istanze possedute dalla compagnia  | Aircraft(0,N) - Aircraft_instance(1,1) |                             |
| details_dep*  | Associa a ciascun aeroporto di partenza tutti i dettagli                      | Route(1,1)-Airport(0,N)                |                             |
| details_step* | Associa a ciascun aeroporto di scalo tutti i dettagli                         | Route(0,1)-Airport(0,N)                |                             |
| details_arr*  | Associa a ciascun aeroporto di arrivo tutti i dettagli                        | Route(1,1)-Airport(0,N)                |                             |
| wait*         | Associa a ciascun aeromobile l'aeroporto dove è ubicato                       | Airport(0,N) - Aircraft_instance(0,N)  |                             |
| concern*      | Associa ciascun volo specifico alla corrispondente rotta                      | Flight(1,1)-Route(0,N)                 |                             |
| command*      | Assegna comandante (-i nel caso di voli di grande durata) a ciascun volo      | Flight(1,2)-Personal(0,N)              |                             |
| monitor*      | Assegna primo ufficiale (-i nel caso di voli di grande durata) a ciascun volo | Flight(1,2)-Personal(0,N)              |                             |
| assist*       | Assegna a ciascun volo steward/hostess                                        | Flight(N,N)-Personal(0,N)              |                             |
| operate       | Assegna a ciascun volo un velivolo                                            | Flight(1,1)-Aircraft_instance(0,N)     |                             |

### Regole di vincolo

Aircraft_instance:
* L'istanza deve far riferimento ad un modello di aereo esistente.

Route:
* L'aeroporto di partenza di una rotta deve esistere tra gli aeroporti del database
* L'aeroporto di scalo (se diverso da null) deve esistere tra gli aeroporti del database
* L'aeroporto di partenza di una rotta deve esistere tra gli aeroporti del database

Flight:
* Il comandante/i di un volo deve esistere tra il personale della Compagnia
* Il/I primo ufficiale/i di un volo deve esistere tra il personale della Compagnia
* Gli assistenti di volo devono esistere tra il personale della compagnia
* Un volo deve riguardare una delle rotte offerte dalla compagnia
* L'aeromobile che opera il volo deve comparire tra gli aerei in possesso.




## Schema Entity-Relationships

![[Screenshot 2024-03-16 015649.png]]


### Ristrutturazione dello schema E-R
Dato che non si identificano ridondanze, gerarchie tra le tabelle, attributi multivalore e gli identificatori sono già stati definiti per ciascuna tabella, possiamo considerare la fase di ristrutturazione come già implicitamente completata.

## Schema Logico

Aircraft (<u>id</u>, manufacturer, model, specification, range, assistants_number, class, seats);

Aircraft_instance (<u>plate</u>, aircraft IR *)
Airport(<u>icao</u>, class, name, nation, city)

Route(<u>id</u>, distance, duration, departure IR* * , stepover IR, arrival IR *)

Parking (<u>aircraft_instance IR, airport IR</u>)

Flight (<u>id </u>, departure_time, passenger_number, route IR *, aircraft_instance IR *)

Command(<u>personal IR, flight IR</u> )

Monitor(<u>personal IR, flight IR</u> )

Assist (<u>personal IR, flight IR</u>)


Legenda:
- IR: sull'attributo indicato vige un vincolo di Integrità Referenziale
"*" attributo not null


# Analisi dei requisiti

## Use cases e templates 

### Use case e template 1

### Use case e templates 2

### Use case 3

### Use case 4

### Use case 5

# Progettazione 

## Class diagrams 

## Aspetti da attenzionare 

# Classi principali

## CLI.java

La classe `CLI` (Command Line Interface) è resposabile dell'interazione con l'utente, fornendo le possibilità di navigazione all'interno del sistema, per poter consultare le informazioni relative a voli e personale.
Contiene diversi metodi, andremo ad esaminarli uno ad uno comprendendone a pieno il funzionamento.

####  Metodo Costruttore 
Prende in input un oggetto di tipo `ManagementSystem`, che sarà poi consultato per la lettura dei dati, e istanzia un oggetto del tipo `CredentialsManager`, il quale sarà invece impiegato per autenticare gli utenti e quindi fornire i corretti privilegi di accesso nelle varie sezioni del sistema.

//TODO Spiegazione Privilegi

#### `void run()`
Predispone un menù iniziale che permette di accedere a diverse categorie di informazioni in base al numero da 1 a 6 che viene inserito:

1. Dati degli impiegati 
2. Dati degli aeromobili 
3. Dati delle rotte disponibili 
4. Dati dei voli disponibili 
5. Area del personale 
6. Esci

#### `void  waitUntilEnter()`
<p align="justify">Predispone l'attesa del comando "Invio" una volta inserito il numero</p>

#### (1) `void accessEmployeesData()`

Stampa le informazioni riguardanti tutti gli impiegati che lavorano in ITA Airways. Per accedere a quest'area è richiesto un login, ci sono due modalità:


- Modalità admin (digitando username "admin", in questo caso sarà possibile accedere a tutti i dati disponibili nel sistema.
Sarà mostrato un ulteriore menù che consente l'accesso a diverse informazioni in base al numero che viene digitato
  - Visualizza tutti gli impiegati, "View all employees"
    - Stampa i dati riguardanti tutti gli impiegati; questi sono contenuti nel managementSystem e vengono estratti attraverso il metodo `getAllEmployees()`
  - Visualizza tutti i comandanti, "View all commanders"
    - Stampa i dati sui comandanti della compagnia; questi sono contenuti in managementSystem e vengono estratti mediante il metodo `getCommanders()`

  - Visualizza tutti i primi ufficiali, "View all first officers"
    - Stampa i dati sui primi ufficiali della compagnia; questi sono contenuti in managementSystem e vengono estratti mediante il metodo `getFirstOfficers()`
  - Visualizza tutti gli assistenti di volo , "View all flight assistants"
    - Stampa i dati sugli assistenti di volo della compagnia; questi sono contenuti in managementSystem e vengono estratti mediante il metodo `getFlightAssistants()`

  - Visualizza uno specifico impiegato, "View a specific employee"
    - Dopo aver inserito il suo id, verifica che sia valido (nel caso in cui non lo fosse notifica l'errore e chiede all'utente di riprovare) e stampa poi i suoi dettagli, sempre contenuti in managementSystem, mediante il metodo `getEmployeeById(requestedId)`

  - Indietro, "back"
    - Consente di tornare al menù principale

- Modalità user (inserendo username e password personali)
  - A seguito di un controllo della correttezza dei dati di login, verranno mostrati i dettagli sull'utente che ha effettuato; i dati, sempre contenuti in `ManagementSystem`, sono ricavati mediante il metodo `getEmployeeById(requestedId)`
		


#### (2) `void accessAircraftDetails()`
Stampa i dettagli di tutti gli aeromobili della compagnia.
L'accesso a queste informazioni è consentito esclusivamente a personale che possegga i privilegi di admin. Le informazioni desiderate, sempre contenute nell'oggetto `ManagementSystem`, vengono estratte mediante il metodo `getAircraftDetails()`


#### (3) `void accessRoutesDetails()`
Stampa i dettagli sulle rotte disponibili in archivio prelevandole dall'oggetto ManagementSystem
Il metodo eseguito è getRouteDetails(); 

#### (4) `void accessFlightSchedule()`

Stampa tutte le informazioni relative all'operativo voli. 
Per accedere a questa sezione è richiesto l'accesso (sono correttamente predisposte  tutte le procedure di riprova e/o negazione dell'accesso nel caso in cui un utente non risulti nel sistema)
Sono previste due modalità distinte in base al soggetto che utilizza il programma: 
- Modalità admin, consente l'accesso a tutti i voli previsti
- Modalità user, consente al comandante di verificare solo il suo programma, rivedere questa parte del codice.


#### (5) `void accessPersonalArea()`

//FIXME differenza con flight schedule??
#### (6) `void quit()`
Consente di uscire dal programma
 
---

## db

### PgDB
Classe che si usa per interfacciarsi con il database, chiamata anche dal DAO per fare le query

## DAO


Il DAO (Data Access Object) è a tutti gli effetti un pattern architetturale usato nelle applicazioni con lo scopo di separare la logica di accesso al Database dal resto delle responsabilità. Non facendo questa divisione si andrebbe a scrivere del codice  poco manutenibile. Volendo citare anche un altro pattern, si noti come ogni classe di questo tipo sia a tutti gli effetti una Factory la quale produce le istanze necessarie dei relativi modelli.

### Package Interfaces

Questo sub-package contiene le interfacce astratte che verranno poi implementate nelle classi fisiche appartenenti al pacchetto DAO. L'utilizzo di quest'ultime, oltre ad essere una buona pratica di programmazione, agevola anche la fase di unit testing, rendendola più semplice ed efficace.
Di seguito andiamo a riportare il codice per una sola di queste, dato che è uguale per tutte le classi riportarlo per ciascuna di esse sarebbe verboso e ridondante.</p>
_L'identificatore `<Type>` rappresenta una qualsiasi delle implementazioni dell'interfaccia_

![[IntelliJ Snippet (1).png]]

### Classi `*DaoPg`

Le varie classi `*DaoPg` implementano la relativa interfaccia (specificata nel sub-package *interfaces*)

#### Metodo `<Type>buildFromRow(Vector [String] row)`
Questo metodo si occupa di generare effettivamente le istanze, accedendo alle caselle opportune della riga prelevata dal database mediante il metodo row.get(integer); e costruendo opportunamente l'oggetto ritornandone uno di tipo &lt;Type&gt;

*A titolo di esempio è riportato lo snippet del metodo relativo alla classe Employee*
![[IntelliJ Snippet 1.png]]

#### Metodo `Vector<Type> getAll()`
Questo metodo stabilisce, usando l'oggetto db della classe PgDB, una connessione con il database. Facendo uso di una query costante estrae i dati richiesti e costruisce le necessarie strutture dati (un vettore di &lt;Type&gt;). Come già osservato, l'istanziazione effettiva degli oggetti è delegata al metodo buildFromRow(row); appunto richiamato su ciascuna delle righe estratte dal Database.

*A titolo di esempio è riportato lo snippet del metodo relativo alla classe Employee*
![[IntelliJ Snippet (2).png]]

#### Eventuali metodi getter

## Domain Model
### Aircraft
Modella gli aeromobili specificandone tutti i dettagli necessari per lo scheduling dei voli

#### Metodo costruttore 
Inizializza un oggetto di tipo Aircraft con i valori che vengono forniti in input. Gli attributi usati sono: 
- `plate`, di tipo Stringa
	Talvolta chiamato erroneamente codice di registrazione o targa, in realtà ne eseguono la medesima funzione; sono una stringa di caratteri alfanumerici, impressi e ben visibili tipicamente vicino alla coda e sulle ali di un aeromobile, che lo identificano univocamente 
- `manufacturer`, di tipo Stringa
	Contiene il nome del produttore dell'aereo. In aviazione commerciale i più comuni sono: Airbus, Boeing, Embraer e Bombardier. 
- `model`, di tipo Stringa
	Indica il modello dell'aereo (es. A320, 737...)
- `specification`, di tipo Stringa
	Di ogni modello di aereo ne vengono realizzate diverse specifiche: la struttura generale dell'aereo è la medesima ma cambia la lunghezza della fusoliera (blocchi in meno o in più di sedili, motori/sistemi/controlli diversi). Ad esempio del Boeing 737 ne esistono moltissime specifiche: dal 737-100 che risale al 1967, passando per 200,300,400...fino alla versione più recente, il 737-MAX 10 in produzione dal 2017.
- `dimensionClass`
	*Vedi sezione appositamente dedicata*
- `assistantsNumber`, di tipo Integer
	Contiene il numero di assistenti di volo che sono richiesti dalla normativa per poter operare voli commerciali con quel velivolo. Una rule of the thumb, che ne impone un limite minimo, è quella di avere uno steward/hostess ogni 50 passeggeri. Ci sono poi aeromobili che per altri motivi (numero di porte, dimensione della fusoliera,...) ne possono richiedere anche un numero ad essa maggiore.
- `range`, di tipo Integer
	Autonomia del velivolo. Questo parametro, misurato in condizioni ideali (assenza di vento contrario, volo alla IAS (Indicated Air Speed) e altitudine ideali garantendo all'aereo la più grande autonomia possibile), non è da considerarsi in maniera precisa ma indicativa; ci sono infatti moltissimi parametri durante il volo che possono andare a ridurre il valore effettivo della distanza percorribile.
- `seats`, di tipo Integer
	Numero di passeggeri che un aeromobile può ospitare
- `busy`, di tipo Boolean 
	Attributo di tipo booleano, indica se l'aeromobile è impiegato in un volo (true) oppure è fermo e pronto ad operarne uno (false).
- `position`, di tipo String
	Contiene il codice icao dell'aeroporto in cui è localizzato l'aeromobile (secondo la simulazione oraria scandita da `SimulatedClock`)
	
#### Metodo `boolean canGo(Airport airport)`
Dato in input un aeroporto, restituisce vero se la dimensione dell'aereo è tale da poter atterrare in `airport`, falso se l'aeroporto è invece sottodimensionato rispetto all'aeromobile, che quindi non potrà operarvici.

#### Metodo `String fullData()`
Restituisce una stringa contenente, sotto forma di elenco "attributo: valore", tutti i dati di un aeromobile.

#### Classici metodi getter, setter e metodo `toString()`
### Airport
Modella tutti gli aeroporti usati dalla compagnia; per ognuno di essi sono specificate chiaramente solo le caratteristiche che si rivelano utili per lo scheduling.
#### Metodo costruttore
Inizializza un oggetto di tipo Airport con i valori che gli vengono forniti in input. Gli attributi sono:


-  `ICAO`, di tipo Stringa. \
	Contiene il codice alfabetico , assegnato dall'International Civil Aviation Organization, che identifica univocamente ciascun aeroporto (ad esempio l'aeroporto di Firenze Peretola ha ICAO LIRQ)
- `dimensionClass`
    *Vedi sezione appositamente dedicata*
- `name`, di tipo Stringa \
    Contiene il nome commerciale dell'aeroporto (esempio Aeroporto di Firenze Amerigo Vespucci)
- `nation`, di tipo Stringa \
    Stato in cui l'aeroporto è ubicato (esempio: Aeroporto di Firenze -->Italia)
- `city`, di tipo Stringa \
    Città servita dall'aeroporto (da specificare che spesso questo non coincide con l'esatta ubicazione della struttura aeroportuale; se di grande dimensione si trovano infatti fuori città: l'aeroporto di Milano Malpensa, che serve Milano, si trova in realtà nei pressi di Busto Arsizio, a 35km dal centro di Milano)


#### Classico Override dei metodi `hashCode()`, `equals(Object obj)` e `toString()`

### `DimensionClass`
Modella la dimensione di un aeroporto, rappresentata da un numero da 1 a 4 che indica la lunghezza maggiore tra tutte le piste di decollo/atterraggio (da 1, la minore possibile, inferiore a 800m, a 4, la maggiore, superiore a 1800m) e una lettera che si riferisce alle dimensioni degli aeromobile che l'aeroporto può ospitare (da A a F, in ordine crescente) 


#### `boolean IsCompatible()`
Questo metodo esegue il confronto tra due oggetti di dimensionClass e stabilisce se quella corrente (tipicamente dell'aeromobile) sia compatibile con other (tipicamente quella dell'aeroporto). Se sia il numero che la lettera di this sono inferiori a quelli di other restituisce true, altrimenti false.

### `Credentials`
//TODO

#### `EmployeeRole`
Altro non è che un'enumerazione di tutti i ruoli (tra quelli di interesse nel nostro sistema) dei dipendenti dell'azienda. Questi sono "Commander", "FirstOfficer" e "FlightAssistant".
#### Override metodo `toString()`
Ritorna una stringa corrispondente al ruolo

### `Employee`
Modella il personale dell'azienda memorizzandone, oltre ai dettagli classici, anche quelli inerenti alle mansioni lavorative. Viene implementato anche un attributo per tenerne traccia della posizione in tempo reale.
#### Metodo Costruttore
Inizializza un oggetto di tipo Employee  con i valori che gli vengono forniti in input. Gli attributi sono:

- `id`, di tipo Integer \
Un identificatore univoco 
- `name`, di tipo String \
Nome del dipendente
- `lastName`, di tipo String  \
Cognome del dipendente 
- `role`, di tipo EmployeeRole \
Contiene il ruolo, uno tra quelli specificati sopra, del soggetto
- `abilitation`, di tipo String \
    Contiene, nel caso in cui il ruolo sia `Commander` o `FirstOfficer`, il velivolo sul quale sono abilitati a volare. Le regole dell'aviazione commerciale impongono infatti che si sia abilitati soltanto per una tipologia di velivolo alla volta: l'ottenimento del brevetto per un nuovo modello di aereo (o il cosiddetto passaggio macchina che rinnova una precedente abilitazione) va ad annullare la possibilità di prestare servizio sul velivolo sul quale si è prestato fino a quel momento. Questo non vale per steward e assistenti di volo i quali, a patto di vari corsi, possono essere contemporaneamente abilitati per quanti modelli sia necessario. 
- `position`, di tipo String \
Inizializzato a vuoto dal costruttore, conterrà poi il codice ICAO dell'aeroporto nel quale il dipendente della compagnia si trova. 

####  Classico override del metodo `toString()` e setter per l'attributo `position`
#### `String getFullData()`
Realizza e restituisce una lista puntata con "nome dell'attributo" : "valore"

### `FlightRoute`
Modella le rotte che sono effettuate dalla compagnia (si parla di tragitti generici, per il volo specifico vedremo un'altra entità specifica)
#### Metodo Costruttore 
Inizializza un oggetto di tipo `FlightRoute` con i valori che gli vengono forniti in input. Gli attributi sono:

- `id`, di tipo Integer \
Un identificatore univoco 
- `distance`, di tipo Integer \
Espressa in chilometri, indica la distanza in linea d'aria tra l'aeroporto di partenza e quello di destinazione (nel caso sia previsto uno scalo saranno sommate le distanze tra, rispettivamente, l'aeroporto di partenza e quello di scalo e quella tra l'aeroporto di scalo e quello di destinazione). Importante notare che queste distanze sono puramente teoriche: un volo può in realtà essere più lungo per motivi meteorologici, particolari procedure di partenza e/o avvicinamento, chiusura spazi aerei...
- `duration`, di tipo Integer \
Espressa in minuti, indica la durata schedulata del volo tra l'aeroporto di partenza e quello di arrivo; valgono le stesse considerazioni fatte sopra in merito alla distanza.</li>
- `departure`, di tipo Stringa \
Contiene il codice ICAO dell'aeroporto di partenza 
- `stepover`, di tipo Stringa \
Contiene, se previsto dal piano di volo, il codice ICAO dell'aeroporto di scalo 
- `arrival`, di tipo Stringa \
Contiene il codice ICAO dell'aeroporto di arrivo 

#### Classico Override dei metodi `hashCode()`, `equals(Object obj)` e `toString()`

### `Flight`

## Business logic (system)

### `CredentialsManager`
### `FlightManager`
### `FlightSchedule`
### `ManagementSystem`
### `SimulatedClock`

Questa classe implementa un orologio simulato, il quale consente di eseguire la simulazione dei voli eseguiti e delle posizioni dei velivoli e del personale in ogni momento. La classe SimulatedClock estende la classe Thread. Include un counter che scandisce lo scorrere dei secondi: per mantenerne sincronizzato il valore ed evitare disallineamenti si ricorre al semaforo mutex. 
Il metodo principale è il metodo `run()`; secondari sono poi i metodi `getTime()` e `getCounter()`  
#### Metodo costruttore di default
#### Metodo `void run()`
Questo metodo fa partire il SimulatedClock.
Dopo 1000 millisecondi (1 secondo) verrà acquisito, attraverso `mutex.acquire()` il valore del counter che sarà incrementato di uno (questo valore indica sostanzialmente il numero di secondi trascorsi fino a quel momento). Una volta fatto ciò il counter viene rilasciato.
L'utilizzo del metodo `sleep()` impone la gestione dell'eccezione InterruptedException, che è comunque lasciata di default.

![[SimulatedClockrun.png]]

#### Metodo `String getTime()`
Restituisce una stringa composta "ore trascorse : minuti trascorsi".
Usando sempre nelle stesse modalità descritte precedentemente il semaforo mutex, questo metodo assegna il valore del contatore all'intero lungo current.
A partire da questo valore vengono poi calcolate:
- le ore, String hour
	Tale valore è dato dal quoziente intero della divisione tra current e timeCoefficient, un integer uguale a 60. La conversione a String viene poi eseguita con il metodo `valueOf($string$)`
- i minuti, String minutes
	Tale valore è dato dal resto della divisione tra current e timeCoefficient, un integer uguale a 60. La conversione a String viene poi eseguita come sopra

*Quella riportata è solo una sezione del metodo descritto sopra*
![[SimulatedClockgetTime.png]]

#### Metodo `long getCounter()`
Semplice metodo getter che però accede alla risorsa condivisa per mezzo del semaforo mutex e la restituisce in output.

## scheduling

### `AirportWeighted`
Questa classe istanzia oggetti che saranno di ausilio per la realizzazione del grafo degli aeroporti.
#### Metodo costruttore
Inizializza un oggetto di tipo `AirportWeighted` con i valori che gli vengono forniti in input. Gli attributi di questa classe sono:
- airport, di tipo Airport
- weight, di tipo int
	Questo valore associa all'aeroporto in questione un "peso", pari alla distanza tra l'aeroporto  in questione ed un altro. Ad esempio sia l'aeroporto corrente A e la rotta considerata quella tra A e un secondo aeroporto che indichiamo con B, il peso è la distanza tra A e B.
- routeId, di tipo Int
	Identifica univocamente la tratta cui la classe si riferisce
- route, di tipo FlightRoute
	Una tratta che ha origine nell'aeroporto in questione	
	Questa viene prelavata dal Database per mezzo del FlightRouteDaoPg `routedao` chiamandoci il metodo `getRouteById(String.valueOf(this.routeId))`
- duration, di tipo Int
	Contiene la durata del volo
#### Classico Override dei metodi `hashCode()`e `equals(Object obj)`

### `AirportGraph`
Questa classe rappresenta il grafo contenente gli aeroporti e le loro interconnessioni. 
I metodi principali sono quelli per l'aggiunta dei nodi e degli archi

#### Metodo costruttore
Il costruttore crea una HashMap, implementazione più comune della classe Map. La mappa è  struttura dati nella quale i dati sono memorizzati come coppie chiave-valore; nel nostro caso è chiamata `adjacencyList` ed ha come chiave un oggetto di tipo `Airport` e come valore una lista collegata di `AirportWeighted`

#### Metodo `void addVertex(Airport airport)`
Dopo aver effettuato grazie al metodo `containsKey(airport)` la verifica che l'aeroporto non sia già contenuto nella mappa, utilizza il metodo `put(...)` per inserircelo.

#### Metodo `void addEdge(Airport a1,Airport a2,int weight,int routeId,int routeDuration)`
La prima operazione di questo metodo è il controllo che ambedue gli aeroporti inseriti tra i parametri siano già presenti in `adjacencyList`; in caso contrario viene richiamato il metodo sovrastante `addVertex(airport)` che si occupa di eseguire l'aggiunta.
L'operazione successiva è l'addizione dell'arco tra a1 e a2. L'ordine dei due parametri è molto importante in quanto, trattandosi di un grafo diretto, si crea esclusivamente l'arco a1-->a2 e non il viceversa. In sostanza, alla lista collegata di `AiportWeighted` dell'aeroporto a1 si va ad aggiungere un nuovo oggetto che rappresenti la connessione con l'aeroporto a2 e che abbia come altri parametri quelli forniti in input al metodo.

![[AirportGraphAddEdge.png]]

#### Metodo `Set<Airport> getVertexList()`
Restituisce semplicemente, usando il metodo `adjacencyList.keySet()`, la lista di tutti gli aeroporti contenuti nel grafo.

#### Metodo `boolean containsVertex(Airport r)`
Ritorna vero se l'aeroporto r è contenuto in `adjacencyList`, falso altrimenti

#### Metodo `LinkedList<AirportWeighted> getAdjacentVertex(Airport airport)`

Restituisce la lista collegata di `AirportWeighted` associata all'aeroporto airport


### `SchedulingStrategy`
Interfaccia che implementa il design pattern Strategy. Questo design pattern di tipo comportamentale (facente parte dei design pattern della Gang of Four GoF, un gruppo di quattro soggetti che misero assieme 23 design pattern ponendo a tutti gli effetto la base dell'ingegneria del software) consente di definire una famiglia di algoritmi rendendoli intercambiabili; in base alle necessità e al contesto specifico si seleziona a runtime un algoritmo piuttosto che un altro. Questa classe definisce tramite il metodo `Vector<Flight> run()` il comportamento generico che devono implementare tutto gli algoritmi concreti implementati in classi separate. L'utilizzo di questo design pattern consente di cambiare con facilità l'algoritmo che viene effettivamente impiegato per operare lo scheduling.
### `SimpleSchedule`
Classe che si occupa effettivamente dello scheduling dei voli.
Viene implementato il metodo `Vector<Flight> run()` dichiarato nell'interfaccia; è questo il metodo che effettua concretamente il core dell'applicazione.
//FIXME descrivi meglio in generale la classe e le sue funzioni

#### Metodo costruttore
//TODO
#### Metodo `AirportGraph buildGraphFromFlightRoute()`
Questo metodo si occupa concretamente della creazione del grafo degli aeroporti.
Viene infatti dichiarato un oggetto `graph` di tipo `AirportGraph`. 
Il passo successivo è la creazione di due vettori: il primo, `routes`, contenente tutte le tratte presenti nel database e ricavate mediante l'uso di un `flightRouteDao`, il secondo, `airports`, contenente gli aeroporti, ricavati mediante un `airportDao`
Si inseriscono poi tutti gli aeroporti nella mappa `airportDict`, avente come chiave l'icao dell'aeroporto e come valore un oggetto di tipo `airport`; questo è importante per effettuare quella che in base di dati si direbbe una "join" tra i dati contenuti in `routes` e quelli contenuti in `airports`. Infine, si scorrono tutte le rotte andando ad aggiungere per ognuna di esse un arco del grafo e passandole come parametri i due aeroporti (partenza e arrivo), l'id della rotta, la distanza e la durata. Si restituisce in output il grado appena creato.

![[SimpleSchedulebuildGraphFromFlightRoute.png]]

#### Metodo `boolean canFly(Aircraft aircraft,AirportWeighted airportWeighted)`

Il compito di questo metodo è la verifica che un aereo `aircraft` sia in grado di volare verso un aeroporto `airportWeighted`: si deve controllare sia la compatibilità dimensionale di aeromobile  e aeroporto che la distanza tra due, garantendo che il `range` del velivolo sia ad essa inferiore.
Per evitare il rischio di un emergenza carburante a bordo dovuta al margine troppo ridotto tra i due valori soprastanti (ad esempio aereo con range di 6500 km si trova a percorrere una rotta di 6495 km; è sufficiente la più piccola deviazione per i più banali motivi per causare un emergenza carburante e di conseguenza un atterraggio di emergenza nell'aeroporto più vicino) è stata introdotta una tolleranza, `RangeTollerance`, pari al 15% della distanza della rotta.
Viene quindi effettuata la verifica aggiuntiva `(aircraft.range-airportWeighted.weight) < airportWeighted.weight*RangeTollerance)`

![[SimpleSchedulecanFly.png]]

#### Gestione del personale
#### Metodo `makeEmployeesVectors()`
Si occupa di prelevare i dati di tutti i dipendenti operanti sui velivoli della compagnia e suddividerli in diverse strutture dati in base ai loro effettivi ruoli. In dettaglio, in prima battuta vengono prelevati dal database, per mezzo di `employeedao.getall(),` tutti i dipendenti della compagnia e inseriti nel vettore di dipendenti `employees`. Viene poi fatto partire un ciclo `for` che scorre tutto il vettore andando ad aggiungere alla lista collegata corrispondente con il suo valore di `role`.
Le tre strutture dati sono:
- `Queue<Employee> commanders`
- `Queue<Employee> firstOfficers`
- `Queue<Employee> flightAssistants`

#### Metodo `Vector<Employee> getFlightAssistants(int assistantNumber,Airport source,Airport destination)`
Questo metodo esegue l'assegnazione degli assistenti di volo ad uno specifico volo.
Al suo interno, oltre a quanto dichiarato e già descritto precedentemente, viene usata la mappa `flightAssistantsLocation`che ha come chiave un `aiport` e come valore una lista di impiegati; in realtà, per quanto sottolineato sopra, la lista è di soli assistenti di volo.
Si dichiara un vettore di impiegati, `currentFlightAssistants`, che sarà la struttura nel quale andremo mano a mano ad aggiungere le hostess/steward associati a quel volo.
Viene innanzitutto fatto partire un ciclo `for`, con un numero di iterazioni pari ad `assistantNumber`, numero persone da assegnare; ad ogni ciclo verrà infatti compiuto un assegnamento.

I primi assistenti di volo ad essere assegnati sono quelli già disponibili all'aeroporto di partenza (le condizioni imposte sulla mappa perchè questa condizione sia soddisfatta sono che la chiave esista e che la lista collegata ad essa associata non sia vuota). Si procede quindi con la rimozione dalla mappa con il metodo `remove()` e l'aggiunta a `currentFlightAssistants` con il metodo `add(flightAssistant).

Se il personale disponibile in aeroporto non dovesse essere sufficiente, verrà prelevato dalla lista generale `flightAssistants`, effettuando rimozione e aggiunta usando i metodi sopra descritti. Volendola spiegare da un punto di vista pratico, verrà chiesto (con l'opportuno preavviso) agli assistenti di volo di presentarsi in un aeroporto per effettuare il servizio.

Infine verrà aggiunta, se mancante, la destinazione del volo (lista collegata compresa) alla mappa `flightAssistantsLocation` e proprio in questa lista vengono aggiunti tutti gli assistenti di volo in  `currentFlightAssistants`. In quest'ultimo passaggio è come se fossimo andati a simulare l'effettuarsi del volo e il conseguente spostamento del personale di cabina nell'aeroporto di destinazione.

Si ritorna infine il vettore `currentFlightAssistants`.

![[SimpleSchedulegetFlightAssistants.png]]
#### Metodo `Boolean isAbilitationValid(String abilitation,String aircraftModel)`
#### Metodo `Vector<Employee> getCommanders(int neededCommanders, Airport source, Airport destination,String aircraftModel)`

#### Metodo `Vector<Employee> getFirstOfficers(int neededFirstOfficers, Airport source, Airport destination,String aircraftModel)`

#### Metodo `boolean isFlightValid(Flight f,Aircraft a,int neededCommanders)`