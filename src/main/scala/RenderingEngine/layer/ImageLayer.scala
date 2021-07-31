package RenderingEngine.layer

import java.awt.image.BufferedImage
import java.io.File
import RenderingEngine.utilities.ImageOperations
import javax.imageio.ImageIO


abstract class ImageLayer(/*imageURL : String, */ defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends Layer(events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    val origImage = getInitialImage
    var currentImage : BufferedImage = origImage

    def getInitialImage : BufferedImage
    
    currentImage = ImageOperations.getRotatedImage(currentImage, defRotation, defPosition)
    currentImage = ImageOperations.getUniformScaledImage(currentImage, defScale.percentage)
    currentImage = ImageOperations.getPositioningImage(currentImage, defPosition.x, defPosition.y)
    
    override def getCurrentImage() : BufferedImage =
    {
        currentImage
    }
    
    def regenerateCurrentImage(timeStamp : Long)
    {
        val currentEvents : List[LayerEvent] = events.filter(event => event.getTimeStamp == timeStamp)
        
        currentEvents.foreach
        {
            event =>
            {
                event match
                {
                    case _ @ RotateEvent(_, rotatePercentage) =>
                    {
                        currentImage = ImageOperations.getRotatedImage(currentImage, rotatePercentage, currentPosition)
                    }
                    case _ @ ScaleEvent(_, angle) =>
                    {
                        currentImage = ImageOperations.getUniformScaledImage(currentImage, angle)
                    }
                    
                    case _ @ PositioningEvent(_, position @ Position(x, y, _)) =>
                    {
                        currentPosition = position
                        currentImage = ImageOperations.getPositioningImage(currentImage, x, y)
                    }
                    case _ @ FadeEvent(_, fadePercent) =>
                    {
                        currentImage = ImageOperations.getFadedImage(currentImage, fadePercent)
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

