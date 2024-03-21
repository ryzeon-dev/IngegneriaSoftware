
<a href="/#dizionario-dei-dati" style="text-decoration: none; text-color: red">Dizionario dei Dati</a>

[Entità dello schema ER](#entità-dello-schema-er)

[Relazioni dello schema](#relazioni-dello-schema)

[Regole di vincolo](#regole-di-vincolo)

[Schema Entity-Relationship](#schema-entity-relationships)

[Ristrutturazione dello schema E-R](#ristrutturazione-dello-schema-er)

[Schema logico](#schema-logico)

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


<a id="item"></a>
### Relazioni dello schema

| Nome         | Descrizione                                                                   | Entità coinvolte e cardinalità         | Attributi dell'associazione |
| ------------ | ----------------------------------------------------------------------------- | -------------------------------------- | --------------------------- |
| belong       | Associa a ciascun modello di aeromobile le istanze possedute dalla compagnia  | Aircraft(0,N) - Aircraft_instance(1,1) |                             |
| details_dep  | Associa a ciascun aeroporto di partenza tutti i dettagli                      | Route(1,1)-Airport(0,N)                |                             |
| details_step | Associa a ciascun aeroporto di scalo tutti i dettagli                         | Route(0,1)-Airport(0,N)                |                             |
| details_arr  | Associa a ciascun aeroporto di arrivo tutti i dettagli                        | Route(1,1)-Airport(0,N)                |                             |
| wait         | Associa a ciascun aeromobile l'aeroporto dove è ubicato                       | Airport(0,N) - Aircraft_instance(0,N)  |                             |
| concern      | Associa ciascun volo specifico alla corrispondente rotta                      | Flight(1,1)-Route(0,N)                 |                             |
| command      | Assegna comandante (-i nel caso di voli di grande durata) a ciascun volo      | Flight(1,2)-Personal(0,N)              |                             |
| monitor      | Assegna primo ufficiale (-i nel caso di voli di grande durata) a ciascun volo | Flight(1,2)-Personal(0,N)              |                             |
| assist       | Assegna a ciascun volo steward/hostess                                        | Flight(N,N)-Personal(0,N)              |                             |
| operate      | Assegna a ciascun volo un velivolo                                            | Flight(1,1)-Aircraft_instance(0,N)     |                             |

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

![[Screenshot 2024-03-19 211010.png]]


### Ristrutturazione dello schema ER
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
