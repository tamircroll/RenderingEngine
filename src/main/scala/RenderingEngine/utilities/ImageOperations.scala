package RenderingEngine.utilities

import java.awt.geom.AffineTransform
import java.awt.image.{AffineTransformOp, BufferedImage}
import RenderingEngine.layer.Position

object ImageOperations
{
    def getRotatedImage(image : BufferedImage, rotation : Int, position : Position) : BufferedImage =
    {
        val rotationRequired = Math.toRadians(rotation)
        val locationX = image.getWidth / 2
        val locationY = image.getHeight / 2
        val at = new AffineTransform()
        at.rotate(rotationRequired, locationX, locationY)
        val op : AffineTransformOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC)
        val outputImage : BufferedImage = new BufferedImage(image.getWidth, image.getHeight, BufferedImage.TYPE_3BYTE_BGR);   //  could not resample from BGR24 to YUV420P for picture of type BGR24
        outputImage.getGraphics.drawImage(op.filter(image, null), 0, 0, null)
        outputImage
    }
    
    def getUniformScaledImage(image : BufferedImage, scaleFactor : Int) : BufferedImage =
    {
        val scaledIMageWidth = (image.getWidth * scaleFactor) / 100.0
        val scaledImageHeight = (image.getHeight * scaleFactor) / 100.0
        var scaledImage : BufferedImage = new BufferedImage(scaledIMageWidth.toInt, scaledImageHeight.toInt, BufferedImage.TYPE_INT_ARGB);
        val at : AffineTransform = new AffineTransform();
        at.scale(scaleFactor / 100.0, scaleFactor / 100.0);
        val scaleOp : AffineTransformOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaledImage = scaleOp.filter(image, null);  // NOTE:: TCr - maybe instead of 'null' use  scaledImage
        scaledImage
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
