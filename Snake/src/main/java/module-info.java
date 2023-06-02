module com.snake.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.snake.snake to javafx.fxml;
    exports com.snake.snake;
}