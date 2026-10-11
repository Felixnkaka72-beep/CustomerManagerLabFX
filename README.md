# Customer Manager Lab (CustomerManagerLabFX)

ICT261 Lecture 3 classroom lab, Mulungushi University.
A small JavaFX desktop app for adding and deleting customers.

## What the app does

- Form with a *Name* field and a *Province* list (the 10 provinces of Zambia)
- Customers are stored in an ObservableList<Customer> and shown in a TableView with Name and Province columns
- *Add* validates the input first:
    - name must not be empty
    - name must be at least 2 characters
    - a province must be selected
- *Delete* asks for confirmation (Yes/No) before removing the selected customer
- Keyboard access: Tab to move between fields, Enter to add, Alt+A for Add, Alt+D for Delete

## Requirements

- JDK 21
- JavaFX SDK 21 (download from https://gluonhq.com/products/javafx/ and unzip it)
- IntelliJ IDEA (any recent version)

## How to run in IntelliJ

1. Clone or download this repository and open it as a project in IntelliJ.
2. Set the project JDK to 21 (File > Project Structure > Project).
3. Add the JavaFX library: File > Project Structure > Libraries > + > Java, then select the lib folder inside your JavaFX SDK.
4. Open Run > Edit Configurations, create an *Application* configuration with main class CustomerManagerLab, then Modify options > Add VM options and paste the line below. *Change the path to match where you unzipped the SDK on your computer.*


--module-path "C:\javafx\javafx-sdk-21.0.12\lib" --add-modules javafx.controls


5. Click the green Run button.

## Test cases

| Action | Expected result |
|---|---|
| Click Add with everything empty | "Name is required." |
| Name A with a province selected | "Name must be at least 2 characters." |
| Name Mary, no province | "Please select a province." |
| Name Mary, province Lusaka | Row appears in the table, form clears |
| Select a row, Delete, choose No | Row stays |
| Select a row, Delete, choose Yes | Row is removed |

## Project structure


CustomerManagerLabFX/
src/
CustomerManagerLab.java   (whole app, including the Customer class)
README.md


## Common problems

- *"JavaFX runtime components are missing"*: the VM options in step 4 are missing or the SDK path is wrong.
- *"Module javafx.controls not found"*: the JavaFX lib folder was not added in step 3.

## Author

Felix Nkaka,Computer Science Student, Mulungushi University