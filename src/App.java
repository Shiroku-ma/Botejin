import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    private double canvasWidth = 800;
    private double canvasHeight = 600;
    private double cam_z = 200;
    private Canvas canvas;
    Block block;

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL             location    = getClass().getResource( "App.fxml" );
        FXMLLoader      fxmlLoader  = new FXMLLoader( location );
        Pane    root        = (Pane) fxmlLoader.load();
        Scene   scene       = new Scene( root , canvasWidth , canvasHeight );

        canvas = new Canvas(canvasWidth, canvasHeight);
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setLineWidth(2.0);
        g.setStroke(Color.rgb(255,0, 255));
        block = new Block(-100, -100, 100, 50);
        drawBlock();

        root.getChildren().add(canvas);
        
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(this::keyPressed);
    }

    private void drawBlock() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvasWidth, canvasHeight);
        Point3[] points = block.getPoints();
        stroke3D(points[0], points[1]);
        stroke3D(points[0], points[2]);
        stroke3D(points[1], points[3]);
        stroke3D(points[2], points[3]);

        stroke3D(points[4], points[5]);
        stroke3D(points[4], points[6]);
        stroke3D(points[5], points[7]);
        stroke3D(points[6], points[7]);

        stroke3D(points[0], points[4]);
        stroke3D(points[1], points[5]);
        stroke3D(points[2], points[6]);
        stroke3D(points[3], points[7]);
    }

    private void stroke3D(Point3 p1, Point3 p2) {
        Point p1_2d = new Point(p1.getX() * cam_z / (p1.getZ() + cam_z), p1.getY() * cam_z / (p1.getZ() + cam_z));
        Point p2_2d = new Point(p2.getX() * cam_z / (p2.getZ() + cam_z), p2.getY() * cam_z / (p2.getZ() + cam_z));
        stroke(p1_2d, p2_2d);
    }

    private void stroke(Point p1, Point p2) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.strokeLine(p1.getX() + canvasWidth / 2, -p1.getY() + canvasHeight / 2, p2.getX() + canvasWidth / 2, -p2.getY() + canvasHeight / 2);
    }

    private void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case SPACE:
                block.rotateZ(Math.PI / 36);
                drawBlock();
                break;
            case RIGHT:
                block.rotateY(Math.PI / 36);
                drawBlock();
                break;
            case DOWN:
                block.rotateX(Math.PI / 36);
                drawBlock();
                break;
            default:
                break;
        }
    }
}
