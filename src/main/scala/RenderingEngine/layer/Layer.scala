package RenderingEngine.layer


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

abstract class Layer[T](events : List[LayerEvent], defPosition : Position, defScale : LayerScale, defVisibility : Boolean, defOpacity : Int = 0, defRotation : Int = 0)
{
    val criticalTimeStamp : List[Long] = events.map(_.getTimeStamp()).distinct.sorted
    var currentPosition : Position = defPosition
    
    def getInitialLayer : T
    
    def getCriticalTimeStamps : List[Long] =
    {
        criticalTimeStamp
    }
    
    def onCriticalTimeStamp(timeStamp : Long) : T
    
    def getRenderedLayer() : Option[T]
    
    def getCurrentZ() : Int = currentPosition.z
}