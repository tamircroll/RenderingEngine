package RenderingEngine.layer.layers

import RenderingEngine.layer.{LayerEvent, LayerScale, Position}
import RenderingEngine.utilities.ShapeOperations

class CircleLayer(events : List[LayerEvent], defPosition : Position, defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0)
    extends ShapeLayer(events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    override def getInitialLayer = ShapeOperations.createCircle()
}