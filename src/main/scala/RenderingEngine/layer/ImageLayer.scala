package RenderingEngine.layer

import java.awt.image.BufferedImage
import java.net.URL
import RenderingEngine.utilities.ImageOperations
import javax.imageio.ImageIO

class ImageLayer(imageURL : String, defPosition : Position, defScale : LayerScale = LayerScale(100), events : List[LayerEvent] = Nil, visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0) extends Layer(events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    val origImage : BufferedImage = ImageIO.read(new URL(imageURL))
    override var currentOutput = origImage
    var currentImage : BufferedImage = origImage
    
    ImageOperations.getRotatedImage(origImage, defRotation, defPosition)
    ImageOperations.getUniformScaledImage(origImage, defScale.percentage)
    
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
                }
            }
        }
        currentImage
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