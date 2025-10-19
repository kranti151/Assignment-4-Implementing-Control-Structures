import java.util.*;

class Employee {
    String name;
    List<String> preferences;
    int daysWorked;

    Employee(String name, List<String> preferences) {
        this.name = name;
        this.preferences = preferences;
        this.daysWorked = 0;
    }
}

public class ShiftScheduler {
    static String[] DAYS = {"Monday","Tuesday","Wednesday","Thursday","Friday"};
    static String[] SHIFTS = {"Morning","Afternoon","Evening"};

    public static void main(String[] args) {
        Map<String, Employee> employees = new HashMap<>();
        employees.put("Alice", new Employee("Alice", Arrays.asList("Morning")));
        employees.put("Bob", new Employee("Bob", Arrays.asList("Afternoon")));
        employees.put("Charlie", new Employee("Charlie", Arrays.asList("Evening")));
        employees.put("David", new Employee("David", Arrays.asList("Morning","Afternoon")));
        employees.put("Ella", new Employee("Ella", Arrays.asList("Afternoon","Evening")));
        employees.put("Frank", new Employee("Frank", Arrays.asList("Morning","Evening")));

        Map<String, Map<String, List<String>>> schedule = new LinkedHashMap<>();
        for (String day : DAYS) {
            Map<String, List<String>> shifts = new LinkedHashMap<>();
            for (String shift : SHIFTS) shifts.put(shift, new ArrayList<>());
            schedule.put(day, shifts);
        }

        Random rand = new Random();
        for (String day : DAYS) {
            Set<String> assignedToday = new HashSet<>();
            for (String shift : SHIFTS) {
                List<String> available = new ArrayList<>();
                for (Employee e : employees.values()) {
                    if (e.preferences.contains(shift) && e.daysWorked < 5 && !assignedToday.contains(e.name)) {
                        available.add(e.name);
                    }
                }
                Collections.shuffle(available);
                while (schedule.get(day).get(shift).size() < 2) {
                    String emp = null;
                    if (!available.isEmpty()) emp = available.remove(0);
                    else {
                        List<String> candidates = new ArrayList<>();
                        for (Employee e : employees.values()) {
                            if (e.daysWorked < 5 && !assignedToday.contains(e.name))
                                candidates.add(e.name);
                        }
                        if (candidates.isEmpty()) break;
                        emp = candidates.get(rand.nextInt(candidates.size()));
                    }
                    schedule.get(day).get(shift).add(emp);
                    employees.get(emp).daysWorked++;
                    assignedToday.add(emp);
                }
            }
        }

        System.out.println("=== Weekly Schedule ===");
        for (String day : DAYS) {
            System.out.println("\n" + day);
            for (String shift : SHIFTS) {
                System.out.println("  " + shift + ": " + String.join(", ", schedule.get(day).get(shift)));
            }
        }
    }
}
