# Automation: The Game - IP2

A simplified deck-building card game inspired by Dominion, implemented in Java for COSI 103A Individual Project 2.

## Project Structure

```
src/main/java/edu/brandeis/cosi103a/ip2/
├── Card.java                 # Represents a single card
├── CardType.java             # Enum for 6 card types
├── CardSupply.java           # Manages the shared card supply
├── Player.java               # Represents a player and their deck
├── Game.java                 # Main game controller
└── AutomationGame.java       # Entry point with main() method

src/test/java/edu/brandeis/cosi103a/ip2/
├── CardTest.java             # 8 tests for Card class
├── CardSupplyTest.java       # 10 tests for CardSupply class
├── PlayerTest.java           # 15 tests for Player class
└── GameTest.java             # 15 tests for Game class
```

## Building and Running

### Compile the project:
```bash
cd ip1
mvn clean compile
```

### Run all tests (48 tests for IP2):
```bash
mvn clean test
```

### Run the game simulation:
```bash
mvn clean compile
java -cp target/classes edu.brandeis.cosi103a.ip2.AutomationGame
```

## Game Rules

### Card Types

**Cryptocurrency Cards** (provide coins for buying):
- Bitcoin: cost 0, value 1 coin (60 in supply)
- Ethereum: cost 3, value 2 coins (40 in supply)
- Dogecoin: cost 6, value 3 coins (30 in supply)

**Automation Cards** (provide Victory Points):
- Method: cost 2, value 1 AP (14 in supply)
- Module: cost 5, value 3 APs (8 in supply)
- Framework: cost 8, value 6 APs (8 in supply)

### Game Setup

1. Each player starts with 7 Bitcoins + 3 Methods
2. Shuffle starting deck and draw 5 cards
3. Starting player is chosen randomly

### Turn Structure

**Buy Phase:**
- Player plays all cryptocurrency cards in hand
- Calculate total coins available
- Buy up to 1 card from supply if affordable
- Bought card goes to discard pile

**Cleanup Phase:**
- Discard entire hand to discard pile
- Draw 5 new cards from draw pile
- If draw pile empty, reshuffle discard pile

### Game End

- Game ends when all 8 Framework cards are purchased
- Winner = player with most total APs in entire deck

### AI Strategy

Players use a simple greedy strategy:
1. Buy the most expensive affordable card
2. Priority: Framework > Module > Method > Dogecoin > Ethereum > Bitcoin

## Implementation Notes

- Clean, modular design with good encapsulation
- 48 comprehensive unit tests with meaningful assertions
- Simple player AI strategy (no randomness)
- Game completes in seconds with varied outcomes
- Meaningful variable and method names
- Comments for complex logic

## Sample Game Output

```
========================================
  Welcome to Automation: The Game!
========================================

Starting game with automated players...

=== GAME OVER ===
Total turns: 86

Alice's Deck:
  Bitcoin: 12
  Ethereum: 16
  Dogecoin: 10
  Method: 7
  Module: 4
  Framework: 4
Total APs: 43

Bob's Deck:
  Bitcoin: 12
  Ethereum: 20
  Dogecoin: 6
  Method: 7
  Module: 4
  Framework: 4
Total APs: 43

Tie! Both players have 43 APs
```
