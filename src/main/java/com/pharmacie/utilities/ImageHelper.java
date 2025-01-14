package com.pharmacie.utilities;

import javafx.scene.image.Image;

public class ImageHelper {
    public static Image loadImageFromResources(String imagePath) {
        try {
            // Obtenir l'URL de la ressource
            var resource = ImageHelper.class.getResource("/com/pharmacie/images/medecines/" + imagePath);

            if (resource == null) {
                System.err.println("Erreur : L'image n'a pas été trouvée dans resources/images/medicines/" + imagePath);
                return null;
            }

            // Charger l'image
            Image image = new Image(resource.toString());

            return image;
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            return null;
        }
    }
}
