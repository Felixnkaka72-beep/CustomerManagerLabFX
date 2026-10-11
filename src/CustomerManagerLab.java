import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * ICT261 Lecture 3 - Customer Manager classroom lab (single file).
 * Steps 1-6 of the slide are marked in the comments below.
 */
public class CustomerManagerLab extends Application {

    // STEP 2: Customer model class (JavaFX properties so TableView can read them)
    public static class Customer {
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty province = new SimpleStringProperty();

        public Customer(String name, String province) {
            this.name.set(name);
            this.province.set(province);
        }

        public String getName() { return name.get(); }
        public String getProvince() { return province.get(); }
        public StringProperty nameProperty() { return name; }
        public StringProperty provinceProperty() { return province; }
    }

    // STEP 2: the list that the TableView watches
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        // STEP 1: form with name field and province list
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter customer name");
        nameLabel.setLabelFor(nameField);

        Label provinceLabel = new Label("Province:");
        ComboBox<String> provinceBox = new ComboBox<>(FXCollections.observableArrayList(
                "Central", "Copperbelt", "Eastern", "Luapula", "Lusaka",
                "Muchinga", "Northern", "North-Western", "Southern", "Western"));
        provinceBox.setPromptText("Select province");
        provinceLabel.setLabelFor(provinceBox);

        Button addButton = new Button("_Add");        // Alt+A (keyboard access)
        addButton.setDefaultButton(true);             // Enter key triggers Add
        Button deleteButton = new Button("_Delete");  // Alt+D (keyboard access)

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #b00020;");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(provinceLabel, 0, 1);
        form.add(provinceBox, 1, 1);
        form.add(new HBox(10, addButton, deleteButton), 1, 2);
        form.add(messageLabel, 1, 3);

        // STEP 3: TableView with name and province columns
        TableView<Customer> table = new TableView<>(customers);
        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        TableColumn<Customer, String> provinceCol = new TableColumn<>("Province");
        provinceCol.setCellValueFactory(cell -> cell.getValue().provinceProperty());
        table.getColumns().add(nameCol);
        table.getColumns().add(provinceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        table.setPlaceholder(new Label("No customers yet"));

        // STEP 4: validate input, then add the customer
        addButton.setOnAction(e -> {
            String name = nameField.getText() == null ? "" : nameField.getText().trim();
            String province = provinceBox.getValue();

            if (name.isEmpty()) {
                messageLabel.setText("Name is required.");
                nameField.requestFocus();
                return;
            }
            if (name.length() < 2) {
                messageLabel.setText("Name must be at least 2 characters.");
                nameField.requestFocus();
                return;
            }
            if (province == null) {
                messageLabel.setText("Please select a province.");
                provinceBox.requestFocus();
                return;
            }

            customers.add(new Customer(name, province));
            messageLabel.setText("");
            nameField.clear();
            provinceBox.setValue(null);
            nameField.requestFocus();
        });

        // STEP 5: confirm deletion of the selected customer
        deleteButton.setDisable(true);
        deleteButton.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull());
        deleteButton.setOnAction(e -> {
            Customer selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete customer \"" + selected.getName() + "\"?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm delete");
            confirm.setHeaderText(null);
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                customers.remove(selected);
            }
        });

        VBox root = new VBox(15, form, table);
        root.setPadding(new Insets(20));

        stage.setTitle("Customer Manager");
        stage.setScene(new Scene(root, 480, 460));
        stage.show();
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}