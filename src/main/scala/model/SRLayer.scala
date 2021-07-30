package model

import java.io.File


case class Position(x : Int, y : Int, z : Int)
{
    if(z < 0)
    {
        throw new IndexOutOfBoundsException(s"z Position arguments must be bigger or equal to 0. received: z=$z")
    }
}

case class LayerScale(width : Int, height : Int)
{
    if(width <= 0 || height <= 0)
    {
        throw new IndexOutOfBoundsException(s"Scale must be bigger then 0. received: scale: width=$width height=$height")
    }
}

case class Opacity(x : Int)

case class ShowTimeStamps(timeStamps : List[Long])


trait CriticalTimeStampObserver
{
    def onCriticalTimeStamp()
}

trait Layer extends CriticalTimeStampObserver
{
    def getName
    
    def getPosition : Position
    
    def getScale : LayerScale
    
    def getCriticalTimeStamps() : List[Long]
    
    def getCurrentRenderedFile() : File
}

class SRLayer(name : String, defPosition : Position, defScale : LayerScale, showTimeStamps : ShowTimeStamps, opacity : Int) extends Layer
{
    override def getName = name
    
    override def getPosition = defPosition
    
    override def getScale = defScale
    
    override def onCriticalTimeStamp()
    {
        ???
    }
    
    override def getCriticalTimeStamps() : List[Long] =
    {
        ???
    }
    
    override def getCurrentRenderedFile() : File =
    {
        ???
    }
    
}

