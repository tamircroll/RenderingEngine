package RenderingEngine.layer


import java.awt.image.BufferedImage
import java.awt.{AlphaComposite, BasicStroke, Color, Graphics2D, RenderingHints}
import java.io.File
import javax.imageio.ImageIO


object CircleLayer
{
    def createCircle(diameter : Int, color : Color, defPosition : Position) : BufferedImage =
    {
        val bufferedImage : BufferedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        val transparent : Color = new Color(0x00FFFFFF, true);

        val graphics : Graphics2D = bufferedImage.getGraphics().asInstanceOf[Graphics2D];
        //trying to make the bufferedImage transparent
        graphics.setComposite(AlphaComposite.Src);
        graphics.setColor(Color.blue);
        graphics.setBackground(Color.pink);
        //drawing the circle
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(color);
        graphics.setStroke(new BasicStroke(50))
        graphics.drawOval(1400, 1400, 300, 30)

//        graphics.drawOval(defPosition.x + (diameter / 2), defPosition.y + (diameter / 2), diameter, diameter);

        bufferedImage;
    }
}

class CircleLayer(diameter : Int, color : Color, defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends ImageLayer(defPosition, events, defScale, visibility, defOpacity, defRotation)
{
    override def getInitialImage : BufferedImage = CircleLayer.createCircle(diameter, color, defPosition)
}


class ImageFromURLLayer(imageURL : String, defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends ImageLayer(defPosition, events, defScale, visibility, defOpacity, defRotation)
{
    override def getInitialImage : BufferedImage = ImageIO.read(new File(imageURL))
}
