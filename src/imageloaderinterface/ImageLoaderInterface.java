package imageloaderinterface;

import Exeptions.ResourcesFileErrorException;
import java.awt.image.BufferedImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.VBox;

public class ImageLoaderInterface extends VBox {

        @FXML
        private Button ImageLoaderButton;

        @FXML
        private Button downloadButton;

        @FXML
        private Button binButton;

        private ImageView preview;

        private BufferedImage image_out;

        private final BooleanProperty changed = new SimpleBooleanProperty(false);

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

                        disableButtons();
                        
                        // Configure drag and drop management
                        this.setOnDragOver(this::dragOver);
                        this.setOnDragEntered(this::dragEntered);
                        this.setOnDragExited(this::dragExited);
                        this.setOnDragDropped(this::dragDropped);

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
                        loadImage(selectedFile);
                        enableButtons();
                }
        }

        /**
         * This function delete the current image and actualise everything
         */
        @FXML
        private void deleteImage() {
                preview.setImage(null);

                image_out = null;

                disableButtons();
                this.setChanged(true);
        }

        private void dragOver(DragEvent event) {
                if (event.getDragboard().hasFiles()) {
                        event.acceptTransferModes(TransferMode.COPY);
                }
                event.consume();
        }

        private void dragEntered(DragEvent event) {
                if (event.getDragboard().hasFiles()) {
                        this.setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
                }
                event.consume();
        }

        private void dragExited(DragEvent event) {
                this.setStyle("-fx-border-color: transparent;");
                event.consume();
        }

        private void dragDropped(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                        success = true;
                        File file = db.getFiles().get(0);
                        loadImage(file);
                      enableButtons();
                }
                event.setDropCompleted(success);
                event.consume();
        }

        @FXML
        private void downloadImage() {
                if (image_out != null) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Enregistrer l'image");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("PNG Files", "*.png"),
                                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                                new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg")
                        );
                        File file = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());
                        if (file != null) {
                                try {
                                        ImageIO.write(image_out, "png", file);
                                } catch (IOException ex) {
                                        Logger.getLogger(ImageLoaderInterface.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                }
        }

        public BufferedImage getImage_out() {
                return image_out;
        }

        public BooleanProperty isChanged() {
                return changed;
        }

        public void setChanged(boolean value) {
                this.changed.set(value);
        }

        public void loadImage(File imageFile) {
                Image image = new Image("file:" + imageFile.getAbsolutePath());
                preview.setImage(image);
                image_out = SwingFXUtils.fromFXImage(image, null);
                changed.set(true);
        }

        public void loadImage(BufferedImage bufferedImage) {
                if (bufferedImage != null) {
                        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                        preview.setImage(image);
                        image_out = bufferedImage;
                        enableButtons();
                        changed.set(true);
                } else {
                        System.err.println("Error: BufferedImage is null");
                       disableButtons();
                }
        }
        
        
        private void disableButtons(){
                 downloadButton.setDisable(true);
                  binButton.setDisable(true);
        }
        
        private void enableButtons(){
                 downloadButton.setDisable(false);
                  binButton.setDisable(false);
        }
}
