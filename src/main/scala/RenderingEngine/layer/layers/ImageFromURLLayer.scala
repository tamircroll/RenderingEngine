package RenderingEngine.layer.layers

import java.awt.image.BufferedImage
import java.io.File
import RenderingEngine.layer.{LayerEvent, LayerScale, Position}
import javax.imageio.ImageIO


class ImageFromURLLayer(imageURL : String, defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends ImageLayer(defPosition, events, defScale, visibility, defOpacity, defRotation)
{
    override def getInitialLayer : BufferedImage = ImageIO.read(new File(imageURL))
}
