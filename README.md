# Expense Tracker

A desktop expense tracker I built in Java Swing to manage personal and business spending. Add expenses, filter and sort them, see spending averages, and save everything to a file to pick up where you left off.

<img width="1481" height="888" alt="expense_overview" src="https://github.com/user-attachments/assets/bd1d92e2-4c7f-4abc-93d0-5533c0bb7bb3" />

## Features
- **Add, update, and delete expenses** with a name, cost, date (calendar picker), type, and category
- **Personal and business categories**: choosing a type loads its own category list (e.g. Housing and Food, or Payroll and Advertising)
- **Filtering and sorting** by type, category, cost range, and date range, plus sorting by cost or date
- **Spending summary** showing the total plus daily, weekly, monthly, and yearly averages, all recalculated from whatever the current filters show
- **Currency selection**: totals display in USD, EUR, GBP, JPY, and more
- **Save and open files** so your data persists between sessions (New, Open, Save, Save As)

## Built With
Java 21, Swing, Maven, [JCalendar](https://toedter.com/jcalendar/) (date picker), Java Time API, Java Serialization

## How It's Organized
The project uses a layered, MVC-style structure so the UI never handles data logic directly:

- `model/`: data classes (`Expense`, `ExpenseFilter`, `CurrencyOption`, `ExpenseFileData`)
- `service/`: business logic for expenses, filtering and sorting, totals and averages, currency, and saving/loading files
- `controller/`: `ExpenseController` connects the UI to the services
- `ui/` and `screens/`: Swing components, dialogs, and the main windows
- `app/`: `ExpenseTrackerApp`, the entry point

## Run It
Requires JDK 21+ and Maven.

```bash
git clone https://github.com/Jsn-Le/Expense-Tracker-App.git
cd Expense-Tracker-App
mvn compile exec:java -Dexec.mainClass=app.ExpenseTrackerApp
```

Or open the project in IntelliJ or VS Code and run `app/ExpenseTrackerApp.java`.

## Screenshots


| Start Screen | Choose Currency | Create Expense |
|---|---|---|
| <img width="260" alt="Start screen" src="https://github.com/user-attachments/assets/7a4ceb30-cd68-4e50-a20a-784d7d3449f8" /> | <img width="260" alt="Currency screen" src="https://github.com/user-attachments/assets/b225894b-9cea-47c7-b10a-5f3f6fb47334" /> | <img width="260" alt="Create expense dialog" src="https://github.com/user-attachments/assets/6606ec09-e1b3-47e9-81da-48c6addf8d7d" /> |

## What I'd Add Next
- Real currency conversion (right now currency only changes how amounts are displayed)
- Charts for spending by category
- Unit tests for the filter and total services with JUnit
- Save to a database or JSON instead of Java serialization
