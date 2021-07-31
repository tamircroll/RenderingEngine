package RenderingEngine.layer

import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import RenderingEngine.utilities.ImageOperations
import javax.imageio.ImageIO

class ImageLayer(imageURL : String, defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0) extends Layer(events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    val origImage : BufferedImage = ImageIO.read(new File(imageURL))
    var currentImage : BufferedImage = origImage
    
    currentImage = ImageOperations.getRotatedImage(currentImage, defRotation, defPosition)
    currentImage = ImageOperations.getUniformScaledImage(currentImage, defScale.percentage)
    currentImage = ImageOperations.getPositioningImage(currentImage, defPosition.x, defPosition.y)
    
    override def getCurrentImage() : BufferedImage =
    {
        currentImage
    }
    
    def regenerateCurrentImage(timeStamp : Long)
    {
        val currentEvents = events.filter(event => event.getTimeStamp == timeStamp)
        
        currentEvents.foreach
        {
            event =>
            {
                event match
                {
                    case event @ RotateEvent(_, rotatePercentage) =>
                    {
                        println(s"TAMIR: EVENT: RotateEvent. t.regenerateCurrentImage(ImageLayer.scala:35)")
                        currentImage = ImageOperations.getRotatedImage(currentImage, rotatePercentage, currentPosition)
                    }
                    case event @ ScaleEvent(_, angle) =>
                    {
                        println(s"TAMIR: EVENT: ScaleEvent. t.regenerateCurrentImage(ImageLayer.scala:35)")
                        currentImage = ImageOperations.getUniformScaledImage(currentImage, angle)
                    }

                    case event @ PositioningEvent(_, x, y, z) =>
                    {
                        println(s"TAMIR: EVENT: TransformEvent. t.regenerateCurrentImage(ImageLayer.scala:35)")
                        currentImage = ImageOperations.getPositioningImage(currentImage, event.x, event.y)
                    }
                }
            }
        }
    }
    
    override def onCriticalTimeStamp(timeStamp : Long) =
    {
        println(s"TAMIR: HERE: Layer CriticalTimeStamp: timeStamp:$timeStamp. t.onCriticalTimeStamp(ImageLayer.scala:53)")
        if(criticalTimeStamp.contains(timeStamp))
        {
            regenerateCurrentImage(timeStamp)
        }
        
        currentImage
    }
}