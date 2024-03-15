
## DIZIONARIO DEI DATI

### Entità dello schema ER

| Entità                        | Descrizione                                     | Attributi                                                                                   | Identificatore     |
| ----------------------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------- | ------------------ |
| Aircraft                      | Tipologia di aeromobile                         | id, manufacturer, model, specification, range, assistants_number, class, seats //FIXME      | id                 |
| Aircraft_instance             | Particolare aeromobile                          | plate,aircraft                                                                              | plate              |
| Airport                       | Aeroporto nel mondo                             | icao, class, name, nation, city                                                             | icao               |
| Route                         | Tragitto collegato dalla compagnia              | id, distance, duration, departure, stepover, arrival                                        | id                 |
| Parking (FASE SUCC.)          | Ultima posizione a terra di un aeromobile       | aircraft_instance, airport                                                                  | aircraft, airport  |
| Flight                        | Specifico volo                                  | id, departure_time, passengers_number, route, aircraft, commander, firstOfficer, assistants | id                 |
| Personal                      | Operatori della compagnia                       | id, name, lastName, role, abilitation                                                       | id                 |
| FlightAssistants (FASE SUCC.) | Steward/Hostess assegnate ad uno specifico volo | flight, assistants                                                                          | flight, assistants |

### Relazioni dello schema

| Nome              | Descrizione                                                                   | Entità coinvolte e cardinalità         | Attributi dell'associazione |
| ----------------- | ----------------------------------------------------------------------------- | -------------------------------------- | --------------------------- |
| belong            | Associa a ciascun modello di aeromobile le istanze possedute dalla compagnia  | Aircraft(1,1) - Aircraft_instance(0,N) |                             |
| details_dep       | Associa a ciascun aeroporto di partenza tutti i dettagli                      | Route(0,N)-Airport(1,1)                |                             |
| details_step      | Associa a ciascun aeroporto di scalo tutti i dettagli                         | Route(0,N)-Airport(0,1)                |                             |
| details_arr       | Associa a ciascun aeroporto di arrivo tutti i dettagli                        | Route(0,N)-Airport(1,1)                |                             |
| wait              | Associa a ciascun aeromobile l'aeroporto dove è ubicato                       | Airport(0,N) - Aircraft_instance(0,N)  |                             |
| concern           | Associa ciascun volo specifico alla corrispondente rotta                      | Flight(0,N)-Route(1,1)                 |                             |
| comm_flight       | Assegna comandante (-i nel caso di voli di grande durata) a ciascun volo      | Flight(0,N)-Personal(1,2)              |                             |
| FO_flight         | Assegna primo ufficiale (-i nel caso di voli di grande durata) a ciascun volo | Flight(0,N)-Personal(1,2)              |                             |
| Assistants_flight | Assegna a ciascun volo steward/hostess                                        | Flight(0,N)-Personal(N,N)              |                             |
| operate           | Assegna a ciascun volo un velivolo                                            | Flight(0,N)-Aircraft_instance(1,1)     |                             |

### Regole di vincolo

Aircraft_instance:
* L'attributo aircraft deve contenere un id di un modello in Aircraft

Route:
* L'attributo departure deve contenere il codice icao di un aeroporto in Airport
* L'attributo stepover deve contenere (se diverso da null) il codice icao di un aeroporto in Airport
* L'attributo arrival deve contenere il codice icao di un aeroporto in Airport

Flight:
* L'attributo commander deve contenere l'id di un commander in Personal
* L'attributo firstOfficer deve contenere l'id di un firstOfficer in Personal
* L'attributo assistants deve contenere l'id di uno steward/hostess in Personal
* L'attrubuto route deve contenere l'id di un tragitto in Route
* L'attributo aircraft deve contenere il valore di un plate in Aircraft_instance




### Schema Entity-Relationships



RISTRUTTURAZIONE DELLO SCHEMA E-R

TRADUZIONE DA SCHEMA E-R A LOGICO

SCHEMA LOGICO