package RenderingEngine.layer.layers

import java.awt.image.BufferedImage
import RenderingEngine.layer.{FadeEvent, IsVisibleEvent, Layer, LayerEvent, LayerScale, Position, PositioningEvent, RotateEvent, ScaleEvent}
import RenderingEngine.utilities.ImageOperations


abstract class ImageLayer(defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends Layer[BufferedImage](events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    var currentImage : BufferedImage = getInitialLayer
    var isVisible : Boolean = visibility
    
    currentImage = ImageOperations.getRotatedImage(currentImage, defRotation, defPosition)
    currentImage = ImageOperations.getUniformScaledImage(currentImage, defScale.percentage)
    currentImage = ImageOperations.getPositioningImage(currentImage, defPosition.x, defPosition.y)
    
    override def getRenderedLayer() : Option[BufferedImage] =
    {
        if(isVisible)
        {
            Some(currentImage)
        }
        else
        {
            None
        }
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
                    case _ @ IsVisibleEvent(_, visible) =>
                    {
                        isVisible = visible
                    }
                    
                    case _ =>
                }
            }
        }
    }
    
    override def onCriticalTimeStamp(timeStamp : Long) : BufferedImage =
    {
        if(criticalTimeStamp.contains(timeStamp))
        {
            regenerateCurrentImage(timeStamp)
        }
        
        currentImage
    }
}

