/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author LECOURT Camille
 */
import imageloaderinterface.ImageLoaderInterface;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ImageLoaderInterfaceTest extends Application {

         @Override
         public void start(Stage primaryStage) {
                  

                  ImageLoaderInterface gradientInterface = new ImageLoaderInterface();
                  Scene scene = new Scene(gradientInterface);
                  primaryStage.setTitle("Gradient Interface Test");
                  primaryStage.setScene(scene);
                  primaryStage.show();
         }

         public static void main(String[] args) {
                  launch(args);
         }
}
