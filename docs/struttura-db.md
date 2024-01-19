# Database

## Velivolo
- ID: PK 
- Produttore
- Modello
- Specifica
- numero passeggeri massimo
- range 
- classe (dimensione velivolo)
- numero assistenti volo

## Istanza velivolo
- targa: PK
- id_velivolo -> velivolo.id

## Aereoporto
- icao: PK
- classe
- Nome
- Nazione
- Città

## Personale
- id: PK
- Nome
- Cognome

## Ufficiale di volo (personale)
- numero_licenza: PK
- tipo_aereo
- ruolo

## Assistente di volo (personale)

## Percorso
- id: PK
- icao_partenza -> aereoporto.icao
- icao_scalo -> aereporto.icao (opt)
- icao_arrivo -> aereoporto.icao
- distanza
- durata

## Tratta
- percorso -> percorso.id
- velivolo -> istanza_velivolo.targa
- ora_partenza (stimata)
- numero passeggeri effettivi
- comandante -> [ufficiale_di_volo.numero_licenza]
- primo_ufficiale -> [ufficiale_di_volo.numero_licenza]
- assistenti_di_volo -> [assistente_di_volo.id]

## Stazionamento
- velivolo -> istanza_velivolo.targa
- aereoporto -> aereoporto.icao

### !! Lo scalo non conta come stazionamento
