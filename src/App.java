import java.net.URL;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
    private double canvasWidth = 800;
    private double canvasHeight = 600;
    private double cam_z = 500;
    private Canvas canvas;
    Block block;
    private boolean movingFlag = false;

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
        block = new Block(-100, -300, 100, 50);
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
        Point3 s1 = block.getPoints()[0];
        Point3 s2 = block.getPoints()[7];
        if(!movingFlag) {
            switch (e.getCode()) {
                case RIGHT:
                    movingFlag = true;
                    if(s1.getX() > s2.getX()) {
                        if(s1.getY() > s2.getY()) {
                            tumbleZp(0, s1.getX(), s2.getY());
                        } else {
                            tumbleZp(0, s1.getX(), s1.getY());
                        }
                    } else {
                        if(s1.getY() > s2.getY()) {
                            tumbleZp(0, s2.getX(), s2.getY());
                        } else {
                            tumbleZp(0, s2.getX(), s1.getY());
                        }
                    }
                    break;
                case LEFT:
                    movingFlag = true;
                    if(s1.getX() < s2.getX()) {
                        if(s1.getY() > s2.getY()) {
                            tumbleZn(0, s1.getX(), s2.getY());
                        } else {
                            tumbleZn(0, s1.getX(), s1.getY());
                        }
                    } else {
                        if(s1.getY() > s2.getY()) {
                            tumbleZn(0, s2.getX(), s2.getY());
                        } else {
                            tumbleZn(0, s2.getX(), s1.getY());
                        }
                    };
                    break;
                case UP:
                    movingFlag = true;
                    if(s1.getZ() > s2.getZ()) {
                        if(s1.getY() > s2.getY()) {
                            tumbleXp(0, s1.getZ(), s2.getY());
                        } else {
                            tumbleXp(0, s1.getZ(), s1.getY());
                        }
                    } else {
                        if(s1.getY() > s2.getY()) {
                            tumbleXp(0, s2.getZ(), s2.getY());
                        } else {
                            tumbleXp(0, s2.getZ(), s1.getY());
                        }
                    }
                    drawBlock();
                    break;
                case DOWN:
                    movingFlag = true;
                    if(s1.getZ() < s2.getZ()) {
                        if(s1.getY() > s2.getY()) {
                            tumbleXn(0, s1.getZ(), s2.getY());
                        } else {
                            tumbleXn(0, s1.getZ(), s1.getY());
                        }
                    } else {
                        if(s1.getY() > s2.getY()) {
                            tumbleXn(0, s2.getZ(), s2.getY());
                        } else {
                            tumbleXn(0, s2.getZ(), s1.getY());
                        }
                    }
                default:
                    break;
            }
        }
    }

    private void tumbleZp(int i, double baseX, double baseY) {
        PauseTransition p = new PauseTransition(Duration.millis(10));
        p.setOnFinished(e->{
            block.rotateZ(Math.PI / 90);
            block.setPositionX(baseX - 25 * Math.sqrt(2) * Math.sin(Math.PI * (45 - i * 2)) / 180);
            block.setPositionY(baseY + 25 * Math.sqrt(2) * Math.cos(Math.PI * (45 - i * 2)) / 180);
            drawBlock();
            if(i < 44)  {
                tumbleZp(i+1, baseX, baseY);
            } else {
                movingFlag = false;
            }
        });
        p.play();
    }

    private void tumbleZn(int i, double baseX, double baseY) {
        PauseTransition p = new PauseTransition(Duration.millis(10));
        p.setOnFinished(e->{
            block.rotateZ(-Math.PI / 90);
            block.setPositionX(baseX + 25 * Math.sqrt(2) * Math.sin(Math.PI * (45 - i * 2)) / 180);
            block.setPositionY(baseY + 25 * Math.sqrt(2) * Math.cos(Math.PI * (45 - i * 2)) / 180);
            drawBlock();
            if(i < 44) {
                tumbleZn(i+1, baseX, baseY);
            } else {
                movingFlag = false;
            }
        });
        p.play();
    }

    private void tumbleXp(int i, double baseZ, double baseY) {
        PauseTransition p = new PauseTransition(Duration.millis(10));
        p.setOnFinished(e->{
            block.rotateX(-Math.PI / 90);
            block.setPositionZ(baseZ - 25 * Math.sqrt(2) * Math.sin(Math.PI * (45 - i * 2)) / 180);
            block.setPositionY(baseY + 25 * Math.sqrt(2) * Math.cos(Math.PI * (45 - i * 2)) / 180);
            drawBlock();
            if(i < 44) {
                tumbleXp(i+1, baseZ, baseY);
            } else {
                movingFlag = false;
            }
        });
        p.play();
    }

    private void tumbleXn(int i, double baseZ, double baseY) {
        PauseTransition p = new PauseTransition(Duration.millis(10));
        p.setOnFinished(e->{
            block.rotateX(Math.PI / 90);
            block.setPositionZ(baseZ + 25 * Math.sqrt(2) * Math.sin(Math.PI * (45 - i * 2)) / 180);
            block.setPositionY(baseY + 25 * Math.sqrt(2) * Math.cos(Math.PI * (45 - i * 2)) / 180);
            drawBlock();
            if(i < 44) {
                tumbleXn(i+1, baseZ, baseY);
            } else {
                movingFlag = false;
            }
        });
        p.play();
    }
}
