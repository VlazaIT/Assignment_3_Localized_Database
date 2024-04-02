# Localized Database Application

The following JavaFX application demonstrates a multilingual user interface that interacts with a localized database, supporting English, Farsi, and Japanese.

## Features

- **Multilingual UI**: Dynamically updates UI labels, messages, and data storage based on the user's language selection.
- **Localized Data Storage**: Employs language-specific tables (`employee_en`, `employee_fa`, `employee_ja`) for data persistence.
- **User Interaction**: Enables adding new employee records to the database, with the interface and database operations adapting to the chosen language.

## Database Structure

The application relies on three tables, each corresponding to one of the supported languages, with columns for `id`, `first_name`, `last_name`, and `email`.

## Setup

Requires JDK 17+, MySQL Server, and Maven. Setup involves creating a `localization` database and initializing it with the specified tables.

## Dependencies

- **JavaFX**: For the graphical user interface.
- **MySQL Connector/J**: For database connectivity.
