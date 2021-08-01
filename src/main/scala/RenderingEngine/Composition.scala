package RenderingEngine

import java.awt.image.BufferedImage
import RenderingEngine.layer.{Layer, Position}

case class Resolution(width : Int, height : Int)

abstract class Composition(resolution : Resolution, layers : List[Layer])
{
    val allCriticalTimeStampsSorted : List[Long] = layers.flatMap(layer => layer.getCriticalTimeStamps).sorted
    def getResolution() : Resolution = resolution

    def onCriticalTimeStamp(timeStamp : Long) : BufferedImage
}

class BasicComposition(resolution : Resolution, layers : List[Layer]) extends Composition(resolution, layers)
{
    val mainFrame : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR);
    
    
    def generateFrame() : BufferedImage =
    {
        val sortedLayersImage : List[(BufferedImage, Position)] = layers.sortBy(_.getCurrentZ).map(layer => (layer.getCurrentImage(), layer.currentPosition))
        
        val frame : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR)
        sortedLayersImage.foreach
        {
            case (image : BufferedImage, position : Position) =>
            {
                frame.getGraphics.drawImage(image, position.x, position.y, null)
            }
            
            case _ =>
        }
        frame
    }
    
    override def onCriticalTimeStamp(timeStamp : Long) : BufferedImage =
    {
        layers.filter(_.criticalTimeStamp.contains(timeStamp)).foreach(_.onCriticalTimeStamp(timeStamp))
        generateFrame()
    }
}
