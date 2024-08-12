

![[Pasted image 20240810180152.png]]


<p align="center"> UNIVERSITÀ DEGLI STUDI DI FIRENZE <br>
		DIPARTIMENTO DI INGEGNERIA DELL'INFORMAZIONE </p>
		<hr>

<p align="center"><b> RELAZIONE DI PROGETTO, seconda tipologia </b></p>

<hr>



Autori:
Loris Vettori
Corrado Duccio Piccioni
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
Siccome non si identificano ridondanze, gerarchie tra le tabelle, attributi multivalore e gli identificatori sono già stati definiti per ciascuna tabella, possiamo considerare la fase di ristrutturazione come già implicitamente completata.

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
IR: sull'attributo indicato vige un vincolo di Integrità Referenziale
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

### CLI.java

<p align="justify">La classe `CLI` (Command Line Interface) è resposabile dell'interazione con l'utente, fornendo le possibilità di navigazione all'interno del sistema, per poter consultare le informazioni relative a voli e personale.
Contiene diversi metodi, andremo ad esaminarli uno ad uno comprendendone a pieno il funzionamento.</p>
<h5> Metodo Costruttore </h5>
<p align="justify">Prende in input un oggetto di tipo ManagementSystem, che sarà poi consultato per la lettura dei dati, e istanzia un oggetto del tipo CredentialsManager, il quale sarà invece impiegato per autenticare gli utenti e quindi fornire i corretti privilegi di accesso nelle varie sezioni del sistema.</p>

//TODO Spiegazione Privilegi

<h5> void run(); </h5>
Predispone un menù iniziale che permette di accedere a diverse categorie di informazioni in base al numero da 1 a 6 che viene inserito:

1 - Dati degli impiegati 
2 - Dati degli aeromobili
3 - Dati delle rotte disponibili
4 - Dati dei voli disponibili
5 - Area del personale
6 - Esci

<h5>void  waitUntilEnter(); </h5>
<p align="justify">Predispone l'attesa del comando "Invio" una volta inserito il numero</p>

<h5> (1) void accessEmployeesData(); </h5>
<h5> (2) void accessAircraftDetails(); </h5>
<h5> (3) void accessRoutesDetails();</h5>
<p align="justify">Stampa i dettagli sulle rotte disponibili in archivio prelevandole dall'oggetto ManagementSystem
Il metodo eseguito è getRouteDetails(); </p>
<h5> (4) void accessFlightSchedule();</h5>
<h5> (5) void accessPersonalArea(); </h5>

<h5> (6) void quit(); </h5>
<p align="justify">Consente di uscire dal programma</p>


### FlightRouteDaoPg
### EmployeeDaoPg

### PgDB
Classe che si usa per interfacciarsi con il database, chiamata anche dal DAO per fare le query


## Domain Model
### Aircraft
### Airport

### Credentials

### Dimension Class

### Employee

### EmployeeRole

### Flight

### FlightRoute

## Business logic

### CredentialsManager
### FlightManager
### FlightSchedule
### ManagementSystem
### SimulatedClock
### SchedulingStrategy
Pattern strategy
### SimpleSchedule