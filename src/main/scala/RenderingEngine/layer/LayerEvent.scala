package RenderingEngine.layer

import RenderingEngine.EventType

abstract class LayerEvent(timeStamp : Long)
{
    def getTimeStamp() : Long =
    {
        timeStamp
    }
}
case class RotateEvent(timeStamp : Long, rotatePercentage : Int) extends LayerEvent(timeStamp)

case class ScaleEvent(timeStamp : Long, scaleAngle : Int) extends LayerEvent(timeStamp)

//case class RotateEvent(timeStamp : Long, rotatePercentage : Int) extends LayerEvent(eventType, timeStamp)
