package projoop;

import java.util.*;
import java.io.*;

/* Interface */
interface Displayable {
    void display();
}

/* Abstract Class */
abstract class Item implements Displayable {
    protected String id, name, category;
    protected int quantity;
    protected String expiry;

    public Item(String id, String name, String category, int quantity, String expiry) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.expiry = expiry;
    }

    public String getId() { return id; }
    public int getQuantity() { return quantity; }

    public abstract void display();
}

/* Part */
class Part extends Item {

    public Part(String id, String name, String category, int quantity, String expiry) {
        super(id, name, category, quantity, expiry);
    }

    public void display() {
        System.out.println("ID: " + id + " | Name: " + name +
                " | Category: " + category + " | Qty: " + quantity +
                " | Expiry: " + expiry);
    }
}

/* Inventory */
class Inventory {

    ArrayList<Part> parts = new ArrayList<>();

    public void addPart(Part p) {
        parts.add(p);
    }

    public void showInventory() {
        if (parts.isEmpty()) {
            System.out.println("Inventory Empty");
            return;
        }
        for (Part p : parts) p.display();
    }

    public Part search(String id) {
        for (Part p : parts)
            if (p.getId().equals(id)) return p;
        return null;
    }

    // ✅ STRICT CHECKER
    public boolean hasEnoughStock(String id, int qty) {
        Part p = search(id);

        if (p == null) {
            System.out.println("Part not found");
            return false;
        }

        if (p.quantity >= qty) return true;

        System.out.println("Not enough stock! Available: " + p.quantity);
        return false;
    }

    public boolean reduceStock(String id, int qty) {
        Part p = search(id);

        if (p == null) return false;

        if (p.quantity >= qty) {
            p.quantity -= qty;
            return true;
        }

        System.out.println("Stock changed! Not enough now.");
        return false;
    }

    public void sortByQuantity() {
        parts.sort(Comparator.comparingInt(Part::getQuantity));
        System.out.println("\nSorted by Quantity:");
        showInventory();
    }

    public void showByCategory() {
        HashMap<String, ArrayList<Part>> map = new HashMap<>();

        for (Part p : parts) {
            map.putIfAbsent(p.category, new ArrayList<>());
            map.get(p.category).add(p);
        }

        for (String cat : map.keySet()) {
            System.out.println("\nCategory: " + cat);
            for (Part p : map.get(cat)) p.display();
        }
    }

    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("inventory.txt");

            for (Part p : parts) {
                fw.write(p.id + "," + p.name + "," + p.category + "," +
                        p.quantity + "," + p.expiry + "\n");
            }

            fw.close();
            System.out.println("Inventory saved to file.");
        } catch (Exception e) {
            System.out.println("File error: " + e);
        }
    }
}

/* Flight */
class Flight {
    String id, state, position, previousTrip, futureSchedule, comment;
    ArrayList<String> partsUsed = new ArrayList<>();

    String airline, aircraftType;

public Flight(String id, String state, String position,
              String prev, String future, String comment,
              String airline, String aircraftType) {

    this.id = id;
    this.state = state;
    this.position = position;
    this.previousTrip = prev;
    this.futureSchedule = future;
    this.comment = comment;
    this.airline = airline;
    this.aircraftType = aircraftType;
}

    public void assignPart(String partId, int qty) {
        partsUsed.add(partId + " (x" + qty + ")");
    }

    public void display() {
        System.out.println("\nFlight: " + id);
        System.out.println("State: " + state);
        System.out.println("Position: " + position);
        System.out.println("Previous Trip: " + previousTrip);
        System.out.println("Next Schedule: " + futureSchedule);
        System.out.println("Work Done: " + comment);
        System.out.println("Parts Used: " + partsUsed);
        System.out.println("Airline: " + airline);
        System.out.println("Aircraft Type: " + aircraftType);
    }
    
    public void updateState(String newState) {
    this.state = newState;
    System.out.println("Flight status updated to: " + newState);
}
}

/* Transfer Record */
class TransferRecord {
    String partId, flightId;
    int qty;

    public TransferRecord(String p, String f, int q) {
        partId = p;
        flightId = f;
        qty = q;
    }

    public void display() {
        System.out.println("Part " + partId + " -> Flight " + flightId + " | Qty: " + qty);
    }
}

/* Emergency Stack */
class EmergencyStack {

    static class Request {
        String id;
        int qty;

        Request(String id, int qty) {
            this.id = id;
            this.qty = qty;
        }
    }

    Stack<Request> stack = new Stack<>();

    public void push(String id, int qty, Inventory inv) {
        if (inv.hasEnoughStock(id, qty)) {
            stack.push(new Request(id, qty));
            System.out.println("Emergency Request Added: " + id + " x" + qty);
        } else {
            System.out.println("Request Rejected");
        }
    }

    public void process(Inventory inv) {
        if (stack.isEmpty()) {
            System.out.println("No emergency requests");
            return;
        }

        Request r = stack.pop();

        if (inv.reduceStock(r.id, r.qty))
            System.out.println("Emergency Processed: " + r.id + " x" + r.qty);
    }
}

/* Regular Queue */
class RequestQueue {

    static class Request {
        String id;
        int qty;

        Request(String id, int qty) {
            this.id = id;
            this.qty = qty;
        }
    }

    Queue<Request> queue = new LinkedList<>();

    public void addRequest(String id, int qty, Inventory inv) {
        if (inv.hasEnoughStock(id, qty)) {
            queue.add(new Request(id, qty));
            System.out.println("Regular Request Added: " + id + " x" + qty);
        } else {
            System.out.println("Request Rejected");
        }
    }

    public void process(Inventory inv) {
        if (queue.isEmpty()) {
            System.out.println("No regular requests");
            return;
        }

        Request r = queue.remove();

        if (inv.reduceStock(r.id, r.qty))
            System.out.println("Regular Processed: " + r.id + " x" + r.qty);
    }
}

/* MAIN */
public class projoop {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Inventory inv = new Inventory();
        EmergencyStack emergency = new EmergencyStack();
        RequestQueue regular = new RequestQueue();

        ArrayList<Flight> flights = new ArrayList<>();
        ArrayList<TransferRecord> records = new ArrayList<>();

        // sample data
        inv.addPart(new Part("P1","LandingGear","Mechanical",20,"2026"));
        inv.addPart(new Part("P2","FuelPump","Electrical",15,"2027"));

        int choice;

        do {
            System.out.println("\n===== AIRPORT INVENTORY MENU =====");
            System.out.println("1 Show Inventory");
            System.out.println("2 Add Regular Request");
            System.out.println("3 Add Emergency Request");
            System.out.println("4 Process Regular Request");
            System.out.println("5 Process Emergency Request");
            System.out.println("6 Sort Parts by Quantity");
            System.out.println("7 Search Part");
            System.out.println("8 Add New Part");
            System.out.println("9 Save Inventory to File");
            System.out.println("10 Add Flight");
            System.out.println("11 Show Flights");
            System.out.println("12 Transfer Part to Flight");
            System.out.println("13 Show Transfers");
            System.out.println("14 Show Categories");
            System.out.println("15 Update Flight Status");
            System.out.println("0 Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1: inv.showInventory(); break;

                case 2:
                    System.out.print("Part ID: ");
                    String rid = sc.next();
                    System.out.print("Quantity: ");
                    int rqty = sc.nextInt();
                    regular.addRequest(rid, rqty, inv);
                    break;

                case 3:
                    System.out.print("Part ID: ");
                    String eid = sc.next();
                    System.out.print("Quantity: ");
                    int eqty = sc.nextInt();
                    emergency.push(eid, eqty, inv);
                    break;

                case 4: regular.process(inv); break;
                case 5: emergency.process(inv); break;

                case 6: inv.sortByQuantity(); break;

                case 7:
                    System.out.print("Enter Part ID: ");
                    Part p = inv.search(sc.next());
                    if (p != null) p.display();
                    else System.out.println("Part Not Found");
                    break;

                case 8:
                    System.out.print("ID: ");
                    String id = sc.next();
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Category: ");
                    String cat = sc.next();
                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();
                    System.out.print("Expiry: ");
                    String exp = sc.next();
                    inv.addPart(new Part(id, name, cat, qty, exp));
                    break;

                case 9: inv.saveToFile(); break;

                case 10:
                    System.out.print("Flight ID: ");
                    String fid = sc.next();
                    sc.nextLine();
                    System.out.print("State: ");
                    String st = sc.nextLine();
                    System.out.print("Position: ");
                    String pos = sc.nextLine();
                    System.out.print("Previous Trip: ");
                    String prev = sc.nextLine();
                    System.out.print("Future Schedule: ");
                    String fut = sc.nextLine();
                    System.out.print("Work Comment: ");
                    String com = sc.nextLine();
                    System.out.print("Airline: ");
                    String arl = sc.nextLine();
                    System.out.print("Aircraft Type: ");
                    String artyp = sc.nextLine();
                    flights.add(new Flight(fid, st, pos, prev, fut, com, arl, artyp));
                    break;

                case 11:
                    for (Flight f : flights) f.display();
                    break;

                case 12:
                    System.out.print("Part ID: ");
                    String pid = sc.next();
                    System.out.print("Flight ID: ");
                    String flid = sc.next();
                    System.out.print("Quantity: ");
                    int tqty = sc.nextInt();

                    Flight found = null;
                    for (Flight f : flights)
                        if (f.id.equals(flid)) found = f;

                    if (found != null && inv.hasEnoughStock(pid, tqty)
                            && inv.reduceStock(pid, tqty)) {

                        found.assignPart(pid, tqty);
                        records.add(new TransferRecord(pid, flid, tqty));
                        System.out.println("Transferred successfully");
                    } else {
                        System.out.println("Transfer failed");
                    }

                    break;

                case 13:
                    for (TransferRecord t : records) t.display();
                    break;

                case 14:
                    inv.showByCategory();
                    break;
                
                case 15:

                    System.out.print("Enter Flight ID: ");
                    String fid_update = sc.next();

                    Flight target = null;

                    for (Flight f : flights) {
                        if (f.id.equals(fid_update)) {
                        target = f;
                    break;
                    }
    }

    if (target != null) {

        sc.nextLine(); // clear buffer

        System.out.print("Enter New State: ");
        String newState = sc.nextLine();

        target.updateState(newState);

    } else {
        System.out.println("Flight not found");
    }

    break;
                
                case 0:
                    System.out.println("Program Ended");
                    break;

                default:
                    System.out.println("Invalid Choice");
            }

        } while (choice != 0);

        sc.close();
    }
}