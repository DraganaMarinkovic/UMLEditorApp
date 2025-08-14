# UML Diagram Editor

UML Diagram Editor is a Java-based desktop application for creating, editing, and managing UML diagrams.  
It provides a flexible platform for designing class diagrams, sequence diagrams, and other UML types,  
with a focus on intuitive interaction, maintainable architecture, and extensibility.

## Features
- **Create and Edit UML Diagrams** — Supports class, sequence, and other diagram types.
- **Drag-and-Drop UI** — Easily move and connect diagram elements.
- **Multiple Diagram Views** — Switch between different UML diagram types within the same project.
- **Undo/Redo Support** — Comprehensive history for non-destructive editing.
- **Export Options** — Save diagrams as image files or export as project data.

## Tech Stack
- **Language:** Java SE 17+ (or your version)
- **UI Framework:** Swing
- **Architecture & Patterns:**
  - **MVC (Model–View–Controller)** — Separation of state, UI, and user actions.
  - **Observer Pattern** — Dynamic updates between UI components and underlying data.
  - **Factory Pattern** — Centralized creation of UML elements (nodes, connectors, etc.).
  - **Subscriber/Publisher Pattern** — Event-based communication between components.
  - **Command Pattern** — Implementation of undo/redo functionality.
  - **Strategy Pattern** — Configurable behaviors for different UML element types.

## Requirements
- Java 13 or higher
- Gradle or Maven build system

## Installation
Clone the repository and build with Gradle:

```bash
git clone https://github.com/your-username/uml-diagram-editor.git
cd uml-diagram-editor
./gradlew build
