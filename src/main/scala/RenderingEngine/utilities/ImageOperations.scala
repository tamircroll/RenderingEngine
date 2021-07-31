package RenderingEngine.utilities

import java.awt.geom.AffineTransform
import java.awt.image.{AffineTransformOp, BufferedImage}
import RenderingEngine.layer.Position

object ImageOperations
{
    def getRotatedImage(image : BufferedImage, rotation : Int, currentPosition : Position) : BufferedImage =
    {
        val sin = Math.abs(Math.sin(Math.toRadians(rotation)))
        val cos = Math.abs(Math.cos(Math.toRadians(rotation)))
        val w = image.getWidth
        val h = image.getHeight
        val neww = Math.floor(w * cos + h * sin).toInt
        val newh = Math.floor(h * cos + w * sin).toInt
        val RotatedImage = new BufferedImage(neww, newh, image.getType)
        val g = RotatedImage.createGraphics
        g.rotate(Math.toRadians(rotation), w / 2, h / 2)
        g.drawRenderedImage(image, null)
        g.dispose()
        
        RotatedImage
    }
    
    def getUniformScaledImage(image : BufferedImage, scaleFactor : Int) : BufferedImage =
    {
        val targetWidth = ((image.getWidth * scaleFactor) / 100.0).toInt
        val targetHeight = ((image.getHeight * scaleFactor) / 100.0).toInt
        
        val resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB)
        val graphics2D = resizedImage.createGraphics
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null)
        graphics2D.dispose()
        resizedImage
    }
    
    def getPositioningImage(image : BufferedImage, x : Int, y : Int) =
    {
        var scaledImage : BufferedImage = new BufferedImage(image.getWidth, image.getHeight, BufferedImage.TYPE_INT_ARGB);
        val at : AffineTransform = new AffineTransform();
        at.translate(x, y);
        val scaleOp : AffineTransformOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaledImage = scaleOp.filter(image, null);  // NOTE:: TCr - maybe instead of 'null' use  scaledImage
        scaledImage
    }
}
