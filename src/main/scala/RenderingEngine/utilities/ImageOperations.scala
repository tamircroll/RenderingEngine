package RenderingEngine.utilities

import java.awt.Image
import java.awt.geom.AffineTransform
import java.awt.image.{AffineTransformOp, BufferedImage}
import RenderingEngine.Position

object ImageOperations
{
    def rotateImage(image : BufferedImage, rotation : Int, position : Position)
    {
        val rotationRequired = Math.toRadians(rotation)
        val locationX = image.getWidth / 2
        val locationY = image.getHeight / 2
        val tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY)
        val op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR)
        
        image.getGraphics.drawImage(op.filter(image, null), position.x, position.y, null)
    }
    
    def uniformScale(originalImage : BufferedImage, scaleFactor : Int) =
    {
        val targetWidth = (originalImage.getWidth() * scaleFactor) / 100
        val targetHeight = (originalImage.getHeight() * scaleFactor) / 100
        val resultingImage : Image = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        originalImage.getGraphics().drawImage(resultingImage, 0, 0, null);
    }
    
}
