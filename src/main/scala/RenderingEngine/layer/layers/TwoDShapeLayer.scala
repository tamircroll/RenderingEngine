package RenderingEngine.layer.layers

import java.awt.Shape
import RenderingEngine.layer.{FadeEvent, IsVisibleEvent, Layer, LayerEvent, LayerScale, Position, PositioningEvent, RotateEvent, ScaleEvent}
import RenderingEngine.utilities.ShapeOperations

//
//object CircleLayer
//{
//    def createCircle(diameter : Int, color : Color, defPosition : Position) : BufferedImage =
//    {
//        val bufferedImage : BufferedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
//        val transparent : Color = new Color(0x00FFFFFF, true);
//
//        val graphics : Graphics2D = bufferedImage.getGraphics().asInstanceOf[Graphics2D];
//        //trying to make the bufferedImage transparent
//        graphics.setComposite(AlphaComposite.Src);
//        graphics.setColor(Color.blue);
//        graphics.setBackground(Color.pink);
//        //drawing the circle
//        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        graphics.setColor(color);
//        graphics.setStroke(new BasicStroke(50))
//        graphics.drawOval(1400, 1400, 300, 30)
//
////        graphics.drawOval(defPosition.x + (diameter / 2), defPosition.y + (diameter / 2), diameter, diameter);
//
//        bufferedImage;
//    }
//}

class CircleLayer(shape : Shape, defPosition : Position, events : List[LayerEvent], defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends Layer[Shape](events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    var currentShape : Shape = getInitialLayer
    var isVisible : Boolean = true
    
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
    
    override def getInitialLayer : Shape =
    {
        ShapeOperations.rotateShape(currentShape, defRotation)
        ShapeOperations.uniformScaleShape(currentShape, defScale.percentage)
        ShapeOperations.positionShape(currentShape, defScale.percentage)
        ShapeOperations.fadeShape(currentShape, defPosition.x, defPosition.y)
        
        shape
    }
    
    override def onCriticalTimeStamp(timeStamp : Long) = ???
}
