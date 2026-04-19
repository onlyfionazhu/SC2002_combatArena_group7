# SC2002_combatArena_group7

**Course:** SC2002 | **Tutorial Group:** FDAA | **Group:** 7

**Members:** Zhu Feiyang, Yan Linyan, Phan Thao Van


This project is a combat arena game that uses a command line. The player can choose either a Warrior or a Wizard, each with different abilities, and fight against waves of enemies (Goblins and Wolves) across three difficulty levels. It is implemented in Java using object-oriented principles and requires the player to select actions strategically to survive.




## Game overview

**Game Setup:** The player selects a character class, two items and a difficulty level. The system validates all inputs and spawns the initial enemy wave accordingly.

**Battle Flow:** Core game logic includes managing the sequence of turns, processing player and enemy actions, applying status effects, and updating combat states such as health, cooldowns, and item usage. The design ensures that these mechanics are handled consistently within the battle flow.

**Game End:** A victory or defeat screen displays relevant statistics and options to replay, start a new game or exit. 




## Project structure

```
Main/           entry point
combatants/     Combatant interface, AbstractCombatant, Player, Enemy, and all four character classes
actions/        Action interface + BasicAttack, Defend, Item, and SpecialSkill actions
effects/        StatusEffect interface + Stun, Defend, SmokeBomb, ArcaneBlast effects
items/          Item interface + Potion, PowerStone, SmokeBomb
engine/         BattleEngine, Level, Difficulty, TurnOrderStrategy
ui/             BattleUI interface, GameCLI (all I/O lives here), GameSession
```




## Design

Built around four main objectives for the course:

- **Object‑Oriented Principles:** Identify abstractions, encapsulation, inheritance, and polymorphism to model combatants, actions, items, and status effects.

- **SOLID Design:** Provide concrete evidence that the system follows the Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion principles.

- **UML Documentation:** Produce a detailed class diagram and a sequence diagram that models a specific combat scenario.

- **Extensibility:** Ensure that new actions, items, status effects, or turn‑order strategies can be added without affecting the core battle engine.




## Submission contents

- Report (with signed declaration forms)
- UML class diagram
- UML sequence diagram
- Commit history
- Source code (this repository)
