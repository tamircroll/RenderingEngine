package RenderingEngine.layer

import java.awt.image.BufferedImage


case class Position(x : Int, y : Int, z : Int)
{
    if(z < 0)
    {
        throw new IndexOutOfBoundsException(s"z Position arguments must be bigger or equal to 0. received: z=$z")
    }
}

case class LayerScale(percentage : Int)
{
    if(percentage <= 0)
    {
        throw new IndexOutOfBoundsException(s"Scale must be bigger then 0. received: scale: scalePercentage=$percentage")
    }
}

trait CriticalTimeStampObserver
{
    def onCriticalTimeStamp(timeStamp : Long) : BufferedImage
}

abstract class Layer(events : List[LayerEvent], defPosition : Position, defScale : LayerScale, defVisibility : Boolean, defOpacity : Int = 0, defRotation : Int = 0) extends CriticalTimeStampObserver
{
    val criticalTimeStamp : List[Long] = events.map(_.getTimeStamp()).distinct.sorted
    var currentPosition : Position = defPosition
    
    def getCriticalTimeStamps : List[Long] =
    {
        criticalTimeStamp
    }
    
    def getCurrentImage() : BufferedImage
    
    def getCurrentZ() : Int = currentPosition.z
}