
DIZIONARIO DEI DATI

Entità dello schema ER

| Entità                        | Descrizione                                     | Attributi                                                                                                     | Identificatore     |
| ----------------------------- | ----------------------------------------------- | ------------------------------------------------------------------------------------------------------------- | ------------------ |
| Aircraft                      | Tipologia di aeromobile                         | id, manufacturer, model, specification, range, assistants_number, class, places                               | id                 |
| Aircraft_instance             | Particolare aeromobile                          | plate,aircraft                                                                                                | plate              |
| Airport                       | Aeroporto nel mondo                             | icao, class, name, nation, city                                                                               | icao               |
| Route                         | Tragitto collegato dalla compagnia              | id, distance, duration, departure, stepover, arrival                                                          | id                 |
| Parking (FASE SUCC.)          | Ultima posizione a terra di un aeromobile       | aircraft_instance, airport                                                                                    | aircraft, airport  |
| Flight                        | Specifico volo                                  | id, departure_time, passengers_number, route, aircraft, commander, firstOfficer  //FIXME gestione multivalore | id                 |
| Personal                      | Operatori della compagnia                       | id, name, lastName, role, abilitation                                                                         | id                 |
| FlightAssistants (FASE SUCC.) | Steward/Hostess assegnate ad uno specifico volo | flight, assistants                                                                                            | flight, assistants |

Relazioni dello schema

| Nome            | Descrizione                                                                   | Entità coinvolte e cardinalità                | Attributi dell'associazione |
| --------------- | ----------------------------------------------------------------------------- | --------------------------------------------- | --------------------------- |
| appartenenza    | Associa a ciascun modello di aeromobile le istanze possedute dalla compagnia  | Aircraft(0,N) - Aircraft_instance(1,1)        |                             |
| dettaglio_dep   | Associa a ciascun aeroporto di partenza tutti i dettagli                      | Route(1,1)-Airport(0,N)                       |                             |
| dettaglio_step  | Associa a ciascun aeroporto di scalo tutti i dettagli                         | Route(0,1)-Airport(0,N)                       |                             |
| dettaglio_arr   | Associa a ciascun aeroporto di arrivo tutti i dettagli                        | Route(1,1)-Airport(0,N)                       |                             |
| attesa          | Associa a ciascun aeromobile l'aeroporto dove è ubicato                       | Airport(0,N) - Aircraft_instance(1,1) //FIXME |                             |
| afferenza       | Associa ciascun volo specifico alla corrispondente rotta                      | Flight(1,1)-Route(0,N)                        |                             |
| com_volo        | Assegna comandante (-i nel caso di voli di grande durata) a ciascun volo      | Flight(1,2)-Personal(0,N)                     |                             |
| FO_volo         | Assegna primo ufficiale (-i nel caso di voli di grande durata) a ciascun volo | Flight(1,2)-Personal(0,N)                     |                             |
| Assistenti_volo | Assegna a ciascun volo steward/hostess                                        | Flight(N,N)-Personal(0,N)                     |                             |

Mancano Regole di vincolo, Regole di derivazione

SCHEMA ER

RISTRUTTURAZIONE DELLO SCHEMA E-R

TRADUZIONE DA SCHEMA E-R A LOGICO

SCHEMA LOGICO