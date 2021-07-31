package RenderingEngine

import java.awt.image.BufferedImage
import RenderingEngine.layer.{CriticalTimeStampObserver, Layer}

case class Resolution(width : Int, height : Int)

class Composition(resolution : Resolution, layers : List[Layer]) extends CriticalTimeStampObserver
{
    val mainFrame : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR);
    val allCriticalTimeStampsSorted : List[Long] = layers.flatMap(layer => layer.getCriticalTimeStamps).sorted
    
    def getResolution() : Resolution = resolution
    
    def generateFrame() : BufferedImage =
    {
        val sortedLayersImage : List[BufferedImage] = layers.sortBy(_.getCurrentZ).map(_.getCurrentImage())
        
        val frame : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR)
        sortedLayersImage.foreach
        {
            image =>
            {
                frame.getGraphics.drawImage(image, image.getMinX, image.getMinY, null)
            }
        }
        frame
    }
    
    override def onCriticalTimeStamp(timeStamp : Long) : BufferedImage =
    {
        layers.filter(_.criticalTimeStamp.contains(timeStamp)).foreach(_.onCriticalTimeStamp(timeStamp))
        generateFrame()
    }
}
