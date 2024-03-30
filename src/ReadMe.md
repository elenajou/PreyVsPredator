
## Changes from A2a to A2b

---
In the previous version of the program (A2a), the logic for interactions between entities (Herbivores, Plants) was encapsulated within the `World` class. This design sufficed when dealing with only two types of organisms. However, with the introduction of Carnivores and Omnivores, the existing approach becomes repetitive and lacks modularity.

In the updated version (A2b), the main change is the decentralization of logic. Rather than having all interaction logic in the `World` class, each individual organism class (Herbivore, Plant, Carnivore, Omnivore) now contains its own behavior logic. This change leads to a more modular and scalable design, where each entity is responsible for its own actions and interactions.

Here's how the changes in the `World` class reflect this:

1. **Method Segregation**: Previously, the `runWorld()` method in the `World` class handled all actions and interactions for all types of entities. In A2b, this method is simplified to only handle iterating over each land and delegating the action to the resident of that land.

2. **Neighbor Handling**: The `runWorld()` method now creates a 3x3 subgrid centered around each land, which serves as the neighborhood for interactions. This subgrid is passed to the `action()` method of each resident, allowing them to determine their actions based on their local environment.

3. **Resident Action**: The `action()` method of each resident (implemented in their respective classes) defines their behavior based on their surroundings. This decentralization allows for custom behavior for each type of organism, promoting code reuse and modularity.

4. **Future Resident Update**: After all actions are performed, the `runWorld()` method updates each land's resident based on their future state, ensuring that all changes take effect simultaneously.

Overall, by moving the logic from the `World` class to individual organism classes, the program becomes more modular, maintainable, and extensible, allowing for easier addition of new organism types and behaviors in future updates.


# Program Overview:

---
The **PreyVsPredators** program simulates the behavior of preys and predators within a grid-based ecosystem. Each entity in the simulation is represented by a distinct color and possesses basic functionalities common to all living things.

### Runs Program
1. `Game`
2. `Launcher`
3. `GUI`

### Simulated Environment
1. `World`
2. `Land`

### Simulated Entities (Also known as residents)
1. `Plants`
2. `Herbivores`
3. `Carnivores`
4. `Omnivores`
## Runs Program

---
### Launcher Class Overview

The `Launcher` class is responsible for starting the Preys Vs Predators game. When the program is executed, the `main` method of the `Launcher` class is invoked automatically. It creates a new instance of the `Game` class, which in turn initializes the graphical user interface and begins the simulation.

#### Usage

To start the Plants Vs Herbivores game, simply execute the `Launcher` class. This can be done from the command line or within an integrated development environment (IDE) by running the program. Upon execution, the game window will appear, and the simulation will begin automatically.

---
### Game Class Overview

The `Game` class serves as the entry point for the simulation. It creates and manages the `World` object, which contains the logic for the simulation. The graphical user interface allows users to interact with the simulation by clicking on the window.

When the user clicks on the window, the `Game` class triggers a repaint, which updates the graphical representation of the world to reflect any changes that may have occurred due to the simulation logic.

---
### GUI Class Overview

The `GUI` class displays a grid-based representation of the simulation world. Each cell in the grid represents a location in the world, and its color indicates the type of resident (plant, herbivore, carnivore, or omnivore) occupying that location.

Users can interact with the GUI by clicking on the panel. When a click event occurs, the `GUI` class triggers the `runWorld` method of the `World` object, causing the simulation to advance by one step. The GUI is then updated to reflect any changes in the world state.

#### Usage

To use the `GUI` class, instantiate it with the desired number of rows, columns, and a `World` object representing the simulation world. The GUI will automatically render the world and respond to user interactions by updating the world state.
## Simulated Entities

---
### Common Properties:
All entities in the simulation inherit from the `LivingThing` abstract class, which defines the following properties:
- `color`: A string indicating the color of the entity.
- *`turnsToDie`: An integer representing the remaining lifespan of the entity.
- `isDead`: A boolean indicating whether the entity has died.

### Common Methods:
1. `getColor(): String`: Retrieves the color of the entity.
2. `die(): boolean`: Marks the entity as deceased and resets its remaining lifespan.
3. `checkIfDead(): boolean`: Checks whether the entity is alive or dead.
4. *`reduceLife()`: Reduces the remaining turns until the organism dies.
4. `reproduce(Land[][] neighbors)`: Reproduces by generating new entities in vacant areas of the grid under specific conditions.
5. `action(Land[][] neighbors)`: Governs the entity's behavior, including movement, interaction with surroundings, and reproduction.
6. `countInstances(Land[][] neighbors): int`: Counts the number of instances of the entity within the given grid.
7. `eat(Land[][] neighbors)`: Manages feeding behavior, including locating food sources and adjusting lifespan accordingly.  
   ***Note:*** *This method is not applicable to plants, as they do not consume other entities.*

#### Additional Notes:
- Methods marked with asterisks (*) denote functionalities specific organisms other than `Plant`.
- The program follows a grid-based approach, where entities interact with neighboring cells based on specific rules and conditions.

---
### Plant Class
The `Plant` class represents a plant in the ecosystem. Plants serve as food sources for herbivores and omnivores.

### Herbivore Class
The `Herbivore` class represents a herbivorous living thing in the ecosystem. Herbivores primarily consume plants and serve as prey for carnivores.

### Carnivore Class
The `Carnivore`class represents a carnivorous living thing in the ecosystem. Carnivores primarily consume other living organisms, such as herbivores and omnivores.

### Omnivore Class
The `Omnivore` class represents an omnivorous living thing in the ecosystem. Omnivores can eat plants, herbivores, and carnivores, making them predators of these classes.


## Simulated Environment

---
### World Class Overview

The `World` class serves as the environment where interactions between different organisms occur. It creates and manages the grid of lands where organisms reside and facilitates their actions and behaviors.

#### Constructors

1. **`World(int w, int h)`**: Initializes a new world with the specified width and height. Upon initialization, it populates the world with a variety of organisms based on predefined probabilities.

#### Methods

1. **`createWorld()`**: Generates the world by populating it with organisms (Herbivores, Plants, Carnivores, Omnivores) based on predefined probabilities. Each land in the world is assigned an organism (or resident), or left empty based on these probabilities.

2. **`getWorld(): Land[][]`**: Returns the 2D array representing the grid of lands in the world, along with the organisms residing on each land.

3. **`runWorld()`**: Executes a single iteration of the simulation, during which each organism performs its actions based on its surroundings. It iterates over each land in the world, constructs a 3x3 subgrid centered around each land, and passes this subgrid to the resident organism's `action()` method. After all actions are performed, it updates each land's resident based on their future state.

4. **Helper Methods**:
    - **`getWidth(): int`**: Returns the width of the world grid.
    - **`getHeight(): int`**: Returns the height of the world grid.

#### Behavior

The `World` class encapsulates the behavior of the simulated ecosystem, orchestrating interactions between organisms and facilitating their actions within the environment. It provides a structured framework for running the simulation and observing the dynamics of the ecosystem over time.

#### Usage

To use the `World` class, instantiate a `World` object with the desired width and height. Then, call the `runWorld()` method to execute the simulation for a single iteration. Repeat this process as needed to observe the evolution of the ecosystem over multiple iterations.

---
### Land Class Overview

The `Land` class represents a single unit of space within the world grid where organisms reside. It encapsulates the information about the current resident organism, the future resident organism (after performing actions), and provides methods to interact with these residents.

#### Constructors

1. **`Land(LivingThing resident)`**: Initializes a land with the specified resident organism. Both the current resident and future resident are set to the provided organism.

#### Methods

1. **Accessors and Mutators**:
    - **`getResident(): LivingThing`**: Returns the current resident organism on the land.
    - **`setResident()`: void**: Updates the current resident to match the future resident, effectively applying changes after actions have been performed.
    - **`getFutureResident(): LivingThing`**: Returns the future resident organism that will occupy the land after actions are applied.
    - **`setFutureResident(LivingThing futureResident)`: void**: Sets the future resident organism for the land.

2. **Helper Methods**:
    - **`setFutureResident()`: void**: Resets the future resident of the land to `null`, indicating that no organism will occupy the land in the future.
    - **`countEmptyLands(Land[][] array): int`**: Counts the number of empty lands (lands without a resident) in the provided grid of lands.
    - **`countMovableLands(Land[][] array): int`**: Counts the number of lands where the future resident is different from the current resident, indicating potential movement.

#### Behavior

The `Land` class manages the occupancy of individual grid cells within the world. It tracks the current and future residents of each land, allowing organisms to move, reproduce, or die based on their actions. Additionally, it provides utility methods to analyze the state of lands within the world grid.

#### Usage

To use the `Land` class, create instances of `Land` objects and assign resident organisms as needed. Call the appropriate accessor and mutator methods to retrieve or update information about the residents. Use the helper methods to perform analyses on the grid of lands, such as counting empty or movable lands.