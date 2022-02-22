# Dokumentation

## ER-Modell

TODO: Einfügen

## Datenbankschema

TODO: Einfügen

### History

History übernimmt 2 Funktionen und muss deshalb Zwei Verbindungen besitzen. Es dient einerseits zur Kaufpreisermittlung eines Investments, aber auch zum anlegen eines neuen Marktpreises, das ohne einem Investment eingetragen werden kann. Es wurde sich dafür entschieden um kein doppeltes Datum in der Datenbank zu halten und trotzdem ein Kauf bereits den ersten History-Point erstellt.
