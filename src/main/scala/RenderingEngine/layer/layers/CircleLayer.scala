package RenderingEngine.layer.layers

import java.awt.Color
import RenderingEngine.layer.{LayerEvent, LayerScale, Position}
import RenderingEngine.utilities.ShapeOperations

class CircleLayer(radius: Int, color: Color, defPosition: Position, events: List[LayerEvent], defScale: LayerScale = LayerScale(100), visibility: Boolean = true, defOpacity: Int = 0, defRotation: Int = 0)
    extends ShapeLayer(events, defPosition, defScale, visibility, defOpacity, defRotation)
{
    override def getInitialLayer = ShapeOperations.createCircle(radius, color, defPosition)
}