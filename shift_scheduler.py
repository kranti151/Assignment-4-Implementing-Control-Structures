import random

DAYS = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
SHIFTS = ["Morning", "Afternoon", "Evening"]

employees = {
    "Alice": {"preferences": ["Morning"], "days_worked": 0},
    "Bob": {"preferences": ["Afternoon"], "days_worked": 0},
    "Charlie": {"preferences": ["Evening"], "days_worked": 0},
    "David": {"preferences": ["Morning", "Afternoon"], "days_worked": 0},
    "Ella": {"preferences": ["Afternoon", "Evening"], "days_worked": 0},
    "Frank": {"preferences": ["Morning", "Evening"], "days_worked": 0},
}

schedule = {day: {shift: [] for shift in SHIFTS} for day in DAYS}

for day in DAYS:
    assigned_today = set()
    for shift in SHIFTS:
        available = [
            name for name, data in employees.items()
            if shift in data["preferences"]
            and data["days_worked"] < 5
            and name not in assigned_today
        ]
        random.shuffle(available)
        while len(schedule[day][shift]) < 2:
            if available:
                emp = available.pop()
            else:
                candidates = [
                    n for n, d in employees.items()
                    if d["days_worked"] < 5 and n not in assigned_today
                ]
                if not candidates:
                    break
                emp = random.choice(candidates)
            schedule[day][shift].append(emp)
            employees[emp]["days_worked"] += 1
            assigned_today.add(emp)

print("=== Weekly Schedule ===")
for day, shifts in schedule.items():
    print(f"\n{day}")
    for shift, names in shifts.items():
        print(f"  {shift}: {', '.join(names)}")
