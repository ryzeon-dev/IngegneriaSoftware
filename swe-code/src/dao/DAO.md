# Package `dao`
- il package dao racchiude le classi (e le relative interfacce) responsabili della comunicazione diretta (in lettura) del database, isolando questa resposabilità dal resto del codice appartenente al sistema
- da notarsi che ogni classe è a tutti gli effetti una `Factory`, che produce le istanze necessarie dei relativi modelli

# Package `interfaces`
- il sub-package interfaces contiene le interfacce astratte che verranno poi implementate nelle classi fisiche appartenenti al pacchetto DAO
- la presenza delle interfacce rende più semplice ed efficace la fase di unit testing
- ogni interfaccia `DAO` ha un'implementazione affine, come segue
- (l'identificatore `$Type$` rappresenta una qualsiasi delle implementazioni dell'interfaccia)
```java
public interface $Type$DaoI {
	public Vector<$Type$> getAll();
}
```

# Classi `*DaoPg` 
- le varie classi `*DaoPg` implementano la relativa interfaccia (specificata nel sub-package `interfaces`), definendo quindi il metodo `getAll()`
	- ogni metodo `getAll()` costruisce una connessione col DB, e facendo uso di una query costante, riceve i dati richiesti e costruisce le relative strutture dati (appartenenti al package `models`), comportandosi quindi come una `Factory` 
	- la generazione effettiva delle istanze è eseguita da metodo `buildFromRow(Vector<String> dbRow)` 
(considerando che l'identificatore `$Type$` rappresenti il modello relativo al `DaoPg`)
```java
public class $Type$DaoPg implements $Type$DaoI {
	public Vector<$Type$> getAll() {
		PgDB db = new PgDB();
		var result = db.runAndFetch(ConstantQueries.$RelativeConstantQuery$);
		
		Vector<$Type$> vector = new Vector<>();
		
		for (var row : result) {
			vector.add(this.buildFromRow(row));
		}
		
		db.close();
		return vector;
	}
	
	private $Type$ buildFromRow(Vector<String> dbRow) { ... }
}
```


