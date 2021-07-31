package RenderingEngine.layer

import java.awt.image.BufferedImage
import java.net.URL
import RenderingEngine.utilities.ImageOperations
import javax.imageio.ImageIO

class ImageLayer(imageURL : String, defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0) extends Layer(events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    val origImage : BufferedImage = ImageIO.read(new URL(imageURL))
    override var currentOutput = origImage
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
                    case event : RotateEvent =>
                    {
                        currentImage = ImageOperations.getRotatedImage(currentImage, event.rotatePercentage, currentPosition)
                    }
                    case event : RotateEvent =>
                    {
                        currentImage = ImageOperations.getUniformScaledImage(currentImage, event.rotatePercentage)
                    }

                    case event : TransformEvent =>
                    {
                        currentImage = ImageOperations.getPositioningImage(currentImage, event.x, event.y)
                    }
                }
            }
        }
    }
    
    override def onCriticalTimeStamp(timeStamp : Long) =
    {
        if(criticalTimeStamp.contains(timeStamp))
        {
            regenerateCurrentImage(timeStamp)
        }
        
        currentImage
    }
}