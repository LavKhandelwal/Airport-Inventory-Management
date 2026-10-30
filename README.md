# Airport Inventory Management System

## About the Project

The **Airport Inventory Management System (AIMS)** is a console-based Java application developed using Object-Oriented Programming principles. It helps manage aircraft spare parts, monitor inventory levels, handle regular and emergency requests, maintain flight records, and track the transfer of parts to flights.

The project demonstrates how OOP concepts and data structures can be applied to airport inventory and maintenance operations.

## Features

- **Inventory Management:** Add, display, and search for aircraft parts.
- **Stock Management:** Check stock availability and update quantities.
- **Sorting:** Sort inventory parts by quantity.
- **Category Management:** Organize parts according to their categories.
- **Regular Requests:** Process regular requests using FIFO (First In, First Out).
- **Emergency Requests:** Process emergency requests using LIFO (Last In, First Out).
- **Flight Management:** Add flights, display flight information, and update flight status.
- **Part Transfers:** Transfer available parts from inventory to flights.
- **Transfer Records:** Maintain records of transferred parts and quantities.
- **File Handling:** Save inventory information in `inventory.txt`.

## Technologies Used

- **Programming Language:** Java
- **Concepts:** Object-Oriented Programming
- **Data Structures:**
  - ArrayList
  - Stack
  - Queue
  - LinkedList
  - HashMap
- **Java Libraries:**
  - `java.util`
  - `java.io`

## OOP Concepts Implemented

### 1. Abstraction
The abstract class `Item` defines common attributes and an abstract `display()` method. The `Displayable` interface declares the display functionality.

### 2. Inheritance
The `Part` class extends the abstract `Item` class and inherits its common properties.

### 3. Encapsulation
Inventory item attributes are managed through class methods, including getter methods such as `getId()` and `getQuantity()`.

### 4. Polymorphism
The `display()` method is implemented in the `Part` class, demonstrating method overriding.

## System Modules

### Inventory Management
- Add new aircraft parts.
- Display available inventory.
- Search for parts using their IDs.
- Sort parts by quantity.
- Display parts category-wise.
- Validate stock before processing requests.

### Request Management
The system supports two types of requests:

| Request Type | Data Structure | Processing Order |
|---|---|---|
| Regular Request | Queue | FIFO |
| Emergency Request | Stack | LIFO |

Both request types check stock availability before accepting a request.

### Flight Management
- Create flight records.
- Store flight ID, state, position, previous trip, future schedule, airline, and aircraft type.
- Display flight information.
- Update flight status.
- Maintain a list of parts assigned to each flight.

### Transfer Management
- Select a part and destination flight.
- Check whether the flight exists.
- Validate available inventory.
- Reduce inventory after a successful transfer.
- Store transfer details using `TransferRecord`.

### File Handling
The application uses Java's `FileWriter` to save inventory information in `inventory.txt`.

The file stores records in CSV-style format:

`id,name,category,quantity,expiry`

This allows inventory information to be saved beyond the current program execution.

## Project Structure

```text
AirportInventoryManagement/
│
├── projoop/
│   └── projoop.java
│
├── inventory.txt
└── README.md
```

`inventory.txt` is generated when the inventory-saving functionality is executed.

## How to Run

### Prerequisites
- Java Development Kit (JDK) installed.
- A Java-compatible IDE or terminal.
- Basic knowledge of running Java programs.

### Steps

1. Clone the repository:

```bash
git clone <your-repository-url>
```

2. Open the project directory.

3. Ensure the source file is named `projoop.java` and is placed inside the `projoop` package folder, matching the package declaration and public class name in the uploaded source.

4. Open a terminal in the project directory.

5. Compile the program:

```bash
javac projoop/projoop.java
```

6. Run the application:

```bash
java projoop.projoop
```

## Application Menu

The application provides the following menu-driven operations:

| Option | Operation |
|---|---|
| 1 | Show Inventory |
| 2 | Add Regular Request |
| 3 | Add Emergency Request |
| 4 | Process Regular Request |
| 5 | Process Emergency Request |
| 6 | Sort Parts by Quantity |
| 7 | Search Part |
| 8 | Add New Part |
| 9 | Save Inventory to File |
| 10 | Add Flight |
| 11 | Show Flights |
| 12 | Transfer Part to Flight |
| 13 | Show Transfers |
| 14 | Show Categories |
| 15 | Update Flight Status |
| 0 | Exit |

## Sample Inventory Data

The application initializes with two sample parts:

| Part ID | Part Name | Category | Quantity | Expiry |
|---|---|---|---:|---|
| P1 | LandingGear | Mechanical | 20 | 2026 |
| P2 | FuelPump | Electrical | 15 | 2027 |

## Testing

The project report documents the following test cases:

| Test Case | Input / Operation | Expected Result | Status |
|---|---|---|---|
| TC1 | Add part | Part added | Pass |
| TC2 | Request quantity greater than stock | Error message | Pass |
| TC3 | Add emergency request | Stored in stack | Pass |
| TC4 | Process request | Stock reduced | Pass |
| TC5 | Transfer part | Transfer recorded | Pass |

## Future Enhancements

- Develop a graphical user interface (GUI).
- Integrate a database for persistent storage.
- Introduce user authentication.
- Implement real-time inventory and flight tracking.

## Learning Outcomes

- Understanding and implementation of OOP principles.
- Practical application of stacks, queues, lists, and maps.
- Experience with Java file handling.
- Understanding inventory validation and stock management.
- Designing modular software for a real-world use case.

## Disclaimer

This project is an academic demonstration of airport inventory and flight maintenance management concepts. It is not a production-ready airport operations or aircraft safety management system.
