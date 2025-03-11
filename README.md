# District Roleplay Projekt-Guidelines

## Ordnerstruktur

```
📂 District Plugin
├── 📂 api            # Alle Anfragen vom Client oder HTTP (Commands, Events, HttpRequests)
├── 📂 core           # Zentrale Logik des Projekts (Services, Entities)
│   ├── 📂 TYPE       # Alle nicht-implementierten Typen (Interfaces)
│   ├── 📂 TYPE/impl  # Implementierungen (Klassen, Entities, etc.)
├── 📂 data           # Datenbankaktionen (Repositories, CRUDs) 
                      # (Nicht genutzt, da TypeORM verwendet wird)
```

## Namenskonventionen

### Get vs. Find
- `get` gibt einen **gecachten Wert** oder `null` zurück.
- `find` durchsucht die **Datenbank**.  

**Beispiel:**  
```ts
getUser(id: number): CachedUser  // Holt den Nutzer aus dem Cache  
findUser(id: number): DbUser     // Sucht den Nutzer in der Datenbank  
```

### **Klassenstruktur**  

1️⃣ **Öffentlich-statische Konstante**  
   ```Java
   public const string PREFIX = "Prefix";
   ```  

2️⃣ **Privat-dynamische Konstante**  
   ```Java
   private const string prefix = "Prefix";
   ```  

3️⃣ **Öffentlich-statische Variable**  
   ```Java
   public static string PREFIX = "Prefix";
   ```  

4️⃣ **Privat-statische Variable**  
   ```Java
   private static string _PREFIX = "Prefix";
   ```  

5️⃣ **Öffentlich-dynamische Variable**  
   ```Java
   public string Prefix = "Prefix";
   ```  

6️⃣ **Privat-dynamische Variable**  
   ```Java
   private string _prefix = "Prefix";
   ```  

7️⃣ **Öffentlich-statische Methode**  
   ```Java
   public static string getPrefix() { ... }
   ```  

8️⃣ **Privat-statische Methode**  
   ```Java
   private static string getPrefix() { ... }
   ```  

9️⃣ **Öffentlich-dynamische Methode**  
   ```Java
   public string getPrefix() { ... }
   ```  

🔟 **Privat-dynamische Methode**  
   ```Java
   private string getPrefix() { ... }
   ```  

## Funktionen, Methoden & Klassen
### Initializer
Initializer werden genutzt um Cache-Daten (z.B. Locations) zu laden. Initializer werden in der `@onEnable` ausgelöst, nach dem die Datenbank/Hibernate initialisiert ist & eine Datenbankverbindung vorhanden ist. <br>

### Statische Klassen & Methoden
Klassen welche über keinerlei Cache-Daten verfügen (z.B. Utilities) sind statisch. <br>
Methoden wie equals, o.ä. welche über nicht-instanz-bedingte Logik verfügen sind statisch.
