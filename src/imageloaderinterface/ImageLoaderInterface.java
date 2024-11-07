package imageloaderinterface;

import Exeptions.ResourcesFileErrorException;
import java.awt.image.BufferedImage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class ImageLoaderInterface extends VBox {

         @FXML
         private Button ImageLoaderButton;

         private ImageView preview;

         private BufferedImage image_out;

         public ImageLoaderInterface() {
                  try {
                           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ImageLoaderInterface.fxml"));
                           if (fxmlLoader == null) {
                                    throw new ResourcesFileErrorException();
                           }
                           fxmlLoader.setRoot(this);
                           fxmlLoader.setController(this);

                           fxmlLoader.load();

                           preview = (ImageView) ImageLoaderButton.getGraphic();
                           preview.setPreserveRatio(true);

                           preview.setFitHeight(200);
                           preview.setFitWidth(200);

                  } catch (IOException | ResourcesFileErrorException | IllegalArgumentException ex) {
                           Logger.getLogger(ImageLoaderInterface.class.getName()).log(Level.SEVERE, null, ex);
                  }
         }

         @FXML
         private void loaderClicked() {
                  FileChooser fileChooser = new FileChooser();
                  fileChooser.setTitle("Choisissez votre image");
                  fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
                  File selectedFile = fileChooser.showOpenDialog(ImageLoaderButton.getScene().getWindow());
                  if (selectedFile != null) {
                           Image image = new Image("file:" + selectedFile.getAbsolutePath());
                           preview.setImage(image);
                           image_out = SwingFXUtils.fromFXImage(image, null);
                  }
         }

         @FXML
         private void dragDropped(DragEvent event) {
         }

         @FXML
         private void drageEntered(DragEvent event) {
         }

         @FXML
         private void dragExited(DragEvent event) {
         }

         public BufferedImage getImage_out() {
                  return image_out;
         }
         
         
         
}
