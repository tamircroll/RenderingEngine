package RenderingEngine.layer.layers

import java.awt.Shape
import RenderingEngine.layer.{FadeEvent, IsVisibleEvent, Layer, LayerEvent, LayerScale, Position, PositioningEvent, RotateEvent, ScaleEvent}
import RenderingEngine.utilities.ShapeOperations

abstract class ShapeLayer(events : List[LayerEvent], defPosition : Position, defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends Layer[Shape](events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    var currentShape : Shape = getInitialLayer
    var isVisible : Boolean = true
    
    ShapeOperations.rotateShape(currentShape, defRotation)
    ShapeOperations.uniformScaleShape(currentShape, defScale.percentage)
    ShapeOperations.positionShape(currentShape, defScale.percentage)
    ShapeOperations.fadeShape(currentShape, defPosition.x, defPosition.y)
    
    
    override def getRenderedLayer() : Option[Shape] =
    {
        if(isVisible)
        {
            Some(currentShape)
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
                        ShapeOperations.rotateShape(currentShape, defRotation)
                    }
                    case _ @ ScaleEvent(_, angle) =>
                    {
                        ShapeOperations.uniformScaleShape(currentShape, defScale.percentage)
                    }
                    
                    case _ @ PositioningEvent(_, position @ Position(x, y, _)) =>
                    {
                        currentPosition = position
                        ShapeOperations.positionShape(currentShape, defScale.percentage)
                    }
                    case _ @ FadeEvent(_, fadePercent) =>
                    {
                        ShapeOperations.fadeShape(currentShape, defPosition.x, defPosition.y)
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
    
    override def onCriticalTimeStamp(timeStamp : Long) = ???
}
