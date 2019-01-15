# Spring-WMS
The spring-wms repository is part of group1113's (aka group 9) project to create a warehouse management system for the company Holland Trading Group. This project is realised partially for educational purposes, and within a school context.

De spring-wms repository is onderdeel van Groep1113's (aka groep 9) project om een warehouse management system te bouwen voor Holland Trading Group. Dit project wordt onder andere gerealiseerd voor educationele doeleinden.

### Backend AuthContext
Voor de GraphQL backend services wordt er gebruik gemaakt van een `CustomContextBuilder` class. Deze class zorgt ervoor dat bij elke request er een `AuthContext` object wordt gemaakt. Het `AuthContext` object bevat vervolgens een `User` object, of `null`, en is beschikbaar door middel van de methode `getUser()`.

Om een user te authentiseren leest de `CustomContextBuilder` bij elke request de `Authorization` header uit. Deze header moet van het volgende formaat zijn: `"Bearer someTokenHere"`.

Vanwege de flow in onze GraphQL opzet is vervolgens het `AuthContext` object beschikbaar in de `Query` en `Mutation` methoden. Deze methoden hoeven enkel een nieuwe parameter te accepteren: `DataFetchingEnvironment env`.

Om enkel en alleen te testen dat de ingelogde gebruiker geautoriseerd is om een bepaalde GraphQL actie uit te voeren kan een statische helper functie van de `AuthContext` class gebruikt worden. Deze methode neemt het `DataFetchingEnvironment env` object, waarin de `AuthContext` automatisch beschikbaar is, en gooit enkel een `GraphQLException` met het bericht `Unauthorized.` wanneer de gebruiker niet geautoriseerd is. Eventueel kan deze methode later ook uitgebreid worden om bijv. role-based permissions te implementeren.
```java
AuthContext.requireAuth(env);
```

Het kan handig zijn om de ingelogde user te gebruiken in je GraphQL methode. Doordat de `AuthContext` beschikbaar is in het `DataFetchingEnvironment` object kan je deze natuurlijk ook altijd zelf benaderen:
```java
AuthContext context = env.getContext();
User user = context.getUser();
```

### Gebruik van GraphiQL voor development
*Tip: linksboven wordt een geschiedenis bijgehouden, waarmee je bijvoorbeeld de login mutatie snel kan opzoeken, na deze 1 keer te hebben uitgetikt.*

Tijdens development kan de volgende backend url gebruikt worden: `http://localhost:{SERVERPORT}/graphiql`. De `SERVERPORT` variabele moet ingesteld worden in de `application.properties` file. Port 9000 is aangeraden.

Op deze pagina kan je links een GraphQL query of mutation typen, en rechts de resultaten zien. Zoals de code nu staat is enkel de `login` mutatie zonder authenticatie beschikbaar. Bij andere acties krijg je een foutmelding `Unauthorized.`. Om toch toegang te krijgen tot deze acties, binnen de GraphiQL pagina, kan je een Cookie genaamd `token` toevoegen. Deze kan je op de volgende manier toevoegen (voor Google Chrome):
  1. Open de developer tools van je browser (rechtsboven menu knop -> more tools -> developer tools)
  2. Ga naar de tab `Application`
  3. Klik aan de linkerzijde Cookies open en klik op de website url
  4. Dubbelklik op een leeg veld onder de bestaande cookies, en onder de kolom `name`
  5. Vul de naam: `token` in en voeg vervolgens als value een token, afkomstig van de login mutatie toe

De tokens worden nu bijgehouden in de database, enkel wordt er wel een nieuwe token gegenereerd voor elke keer dat de login mutatie aangesproken wordt. Als je dus op hetzelfde account in de frontend, en in de GraphiQL webinterface wilt inloggen is het handig om de token uit de `localStorage` van je frontend applicatie te plukken. Dit kan je op de volgende manier doen (Google Chrome):
  1. Login op de frontend
  2. Open de developer tools van je browser (rechtsboven menu knop -> more tools -> developer tools)
  3. Ga naar de tab `Application`
  4. Klik aan de linkerzijde Local Storage open en klik op de website url
  5. Kopieër de `authToken` waarde en gebruik deze vervolgens als `token` waarde voor de bovenstaande stappenlijst om een Cookie in te stellen.

### Frontend authenticatie flow
Om een goeie authenticatie flow te realiseren is het voor bijbehorende frontend applicaties van belang aan een aantal richtlijnen/regels te voldoen:
  * Stuur al je GraphQL queries en mutaties met een POST naar `backendHost/graphql`
  * Zorg ervoor dat de `Content-Type` header een waarde van `application/json` heeft
  * En hanteer vervolgens het volgende formaat in je request body:
```
{
    "operationName": "operationName" || null,
    "query": "Valid GraphQL queryString",
    variables: { "varKey": "varValue" } || {}
}
```
  * Roep eerst de login mutatie aan, en sla zodra je een valide token terugkrijgt deze lokaal op
  * Voeg vervolgens bij alle vervolg requests de `Authorization` header toe met een waarde: `Bearer tokenHere`
  
### Docker
Voordat je met Docker de applicatie kan starten, moet je eenmalig het volgende doen:
- Maak een bestand aan in de project root genaamd `.env`.
- Kopieer de inhoud van `.env.example` naar `.env`.
- Vul de gegevens vervolgens aan in `.env` naar eigen keuze, zoals wachtwoord, username en databasenaam.

Daarna kan je met [ Docker Compose ](https://docs.docker.com/compose/install/) de applicatie starten. De applicatie gebruikt vanuit Docker de poorten 3306, 8080 en 9000. Zorg dat je deze poorten vrij hebt voor je het opstart.
```bash
docker-compose up
```

Om alles weer correct af te sluiten.
```bash
docker-compose down
```

Andere handige commando's:

- Nieuwe images pullen van Docker Hub. 
```bash
docker-compose pull
```

- Alle images en containers van Docker verwijderen. Een reset-knop, eigenlijk.
```bash
docker system prune -a
```

- Alle containers bekijken.
```bash
docker ps -a
```
