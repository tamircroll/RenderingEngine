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
    val criticalTimeStamp = events.map(_.getTimeStamp).toSet
    var currentPosition = defPosition
    var currentOutput : BufferedImage
    
    def getCriticalTimeStamps() : Set[Long] =
    {
        criticalTimeStamp
    }
    
    def getCurrentImage() : BufferedImage
    
    def getCurrentZ() : Int = currentPosition.z
}


//class Two2DImage(imageURL : String, defPosition : Position, defScale : LayerScale = LayerScale(100), visibility : Boolean = true, defOpacity : Int = 0, defRotation : Int = 0) extends Layer(defPosition, defScale, visibility, defOpacity, defRotation)
//{
//
//}