package RenderingEngine.layer

abstract class LayerEvent(timeStamp : Long)
{
    def getTimeStamp() : Long =
    {
        timeStamp
    }
}
case class RotateEvent(timeStamp : Long, rotatePercentage : Int) extends LayerEvent(timeStamp)

case class ScaleEvent(timeStamp : Long, scaleAngle : Int) extends LayerEvent(timeStamp)

case class TransformEvent(timeStamp : Long, x : Int, y : Int) extends LayerEvent(timeStamp)
