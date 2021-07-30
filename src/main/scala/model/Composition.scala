package model

import java.io.File
import scala.collection.immutable

case class Resolution(width : Int, height : Int)

case class Composition(file : File, resolution : Resolution, layers : List[SRLayer]) extends CriticalTimeStampObserver
{
    val allCriticalTimeStamps : List[Long] = layers.flatMap(layer => (layer.getCriticalTimeStamps()))
    
    override def onCriticalTimeStamp(timeStamp : Long) : File
    {
    
    }
}

