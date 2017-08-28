Tur-applikasjon

Databasen var det første jeg begynte på, jeg tok utgangspunktet i dummydataene som vi fikk med Excel-arket som var lagt ved. I tillegg la jeg til en kolonne som heter Lengde. Til å holde på dataene om turen trengte jeg kun en tabell.
Kommunikasjonen mellom Databasen og klienten skjer igjennom API’et som vi har brukt i vår, api.php


Applikasjonen
Løsningen på applikasjonen består av følgende aktiviteter og klasser:

•	MainActivity -> Hovedaktivitet som ønsker bruker velkommen, her kan se registrerte turer i form av et ListView. I tillegg er det lagt til et adapterview som lar deg trykke på en av turene og tar med deg videre til nytt vindu, som viser hvor turen ligger, beskrivelse av turen, lengde og hvem som har registrert den.

  Den inneholder følgende elementer:
  
  -	Navn på tur
  -	Hvor målet for turen ligger i et kart
  -	Beskrivelse av turen
  -	Hvor lang den er, og hvem som har registrert den.


•	RegTurActivity - > Aktivitet hvor bruker kan skrive inn all nødvendig informasjon om turen. 

  -	Vis i kart knappen tar med seg den nåværende posisjonen og tar med deg videre til googlemaps sin egen applikasjon og viser deg hvor du   er.
  -	En egen spinner for å velge hva slags type tur det er
  -	En seekbar som lar deg velge hvor høyt turen ligger

•	TurerClass og RegTurClass -> Hjelpeklasse som kobler applikasjonen opp mot databasen, via api.php. Disse utfører handlinger opp mot databasen.


