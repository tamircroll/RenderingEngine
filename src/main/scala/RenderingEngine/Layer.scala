package RenderingEngine

import java.awt.Image
import java.awt.image.{BufferedImage, ImageObserver}
import java.io.File
import java.net.URL
import RenderingEngine.utilities.ImageOperations
import javax.imageio.ImageIO


case class Position(x : Int, y : Int, z : Int)
{
    if(z < 0)
    {
        throw new IndexOutOfBoundsException(s"z Position arguments must be bigger or equal to 0. received: z=$z")
    }
}

case class LayerScale(percentage : Int)
{
    if(percentage <= 0)
    {
        throw new IndexOutOfBoundsException(s"Scale must be bigger then 0. received: scale: scalePercentage=$percentage")
    }
}

trait CriticalTimeStampObserver
{
    def onCriticalTimeStamp(timeStamp : Long) : File
}

abstract class Layer(defPosition : Position, defScale : LayerScale, defVisibility : Boolean, defOpacity : Int = 0, defRotation : Int = 0) extends CriticalTimeStampObserver
{
    var currentOutput : BufferedImage
    
    def getCriticalTimeStamps() : List[Long]
    
    def getCurrentRenderedFile() : File
    
    def getZ() : Int = defPosition.z
}

class ImageLayer(imageURL : String, defPosition : Position, defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0) extends Layer(defPosition, defScale, visibility, defOpacity, defRotation)
{
    val image : BufferedImage = ImageIO.read(new URL(imageURL))
    override var currentOutput = image
    
    ImageOperations.rotateImage(image, defRotation, defPosition)
    ImageOperations.uniformScale(image, defScale.percentage)
    
//    var events =
    
    override def onCriticalTimeStamp(timeStamp : Long) : File =
    {
        ???
    }
    
    override def getCriticalTimeStamps() : List[Long] = ???
    
    override def getCurrentRenderedFile() : File = ???
}

class Two2DImage(imageURL : String, defPosition : Position, defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0) extends Layer(defPosition, defScale, visibility, defOpacity, defRotation)
{
    val image : BufferedImage = ImageIO.read(new URL(imageURL))
    override var currentOutput = image
    
    override def getCriticalTimeStamps() = ???
    
    override def getCurrentRenderedFile() = ???
    
    override def onCriticalTimeStamp(timeStamp : Long) = ???
    
}