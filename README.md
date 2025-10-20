<div align="center">

# 🎯 Number Guessing Game (CLI)


A fun and interactive command-line number guessing game built in Java. The computer picks a random number between 1 and 100 and the player has a limited number of attempts to guess it.
</div>

---

## ✨ Features

- **3 difficulty levels :**
  -	Easy → 10 attempts
  -	Medium → 5 attempts
  -	Hard → 3 attempts
-	⏱️ **Timer** — see how long you take to guess the number
-	🔢 **Attempts counter**
-	🏆 **High scores with JSON persistence** — best score per difficulty saved in `highScores.json`
-   🎨 **Colored console output** — ANSI colors for better visual experience
-	🔁 **Play again option** — replay as many times as you want

---

## 🧩 How to run

**1. Clone the repository:**

```bash
git clone https://github.com/celiahmm/Number-Guessing-Game.git
cd Number-Guessing-Game
```

**2. Build the project with Maven:**

```bash
mvn clean compile
```

**3. Run the game:**
```bash
mvn exec:java
```

---

## 💻 Example Gameplay

```

* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*               Welcome to the Number Guessing Game!                  *
*          I'm thinking of a number between 1 and 100.                *
*  You have a limited number of chances to guess the correct number   *
*                                                                     *
*               Please select the difficulty level:                   *
*                   1. Easy (10 chances)                              *
*                   2. Medium (5 chances)                             *
*                   3. Hard (3 chances)                               *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

Please select the difficulty level:
1. Easy (10 chances)
2. Medium (5 chances)
3. Hard (3 chances)

Enter your choice: 2
Great! You have selected the medium difficulty level.
Let's start the game!

Enter your guess: 50
Incorrect! The number is less than 50.

Enter your guess: 25
Incorrect! The number is greater than 25.

Enter your guess: 35
Congratulations! You guessed the correct number in 3/5 attempts
Time taken: 12 seconds

🏆 HIGH SCORES:
Easy: 7 attempts
Medium: 3 attempts
Hard: Not set yet

Play again? (yes/no): no

Thank you for playing! Goodbye!
```
---

## 🚀 Future Improvements
-	Add a hint system (e.g., “The number is even”)
-	Leaderboards with timestamps and player names

---

## 🧱 Technologies Used
-	Java 17+
-	Maven
-	org.json library
-	Command Line Interface (CLI)

---
## 🔗 About the Project

This project was built as part of the
_[roadmap.sh - Number Guessing Game](https://roadmap.sh/projects/number-guessing-game)_ challenge.