package RenderingEngine.composition

import java.awt.Shape
import java.awt.image.BufferedImage
import RenderingEngine.layer.{Layer, Position}
import scala.swing.Graphics2D

case class Resolution(width : Int, height : Int)

abstract class Composition(resolution : Resolution, layers : List[Layer[_]])
{
    val allCriticalTimeStampsSorted : List[Long] = layers.flatMap(layer => layer.getCriticalTimeStamps).sorted
    
    def getResolution() : Resolution = resolution
    
    def onCriticalTimeStamp(timeStamp : Long) : BufferedImage
}

class BasicComposition(resolution : Resolution, layers : List[Layer[_]]) extends Composition(resolution, layers)
{
    val mainFrame : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR);
    
    def generateFrame() : BufferedImage =
    {
        val mainFrame : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR)
        
        val sortedLayersImage : List[(Option[_], Position)] = layers.sortBy(_.getCurrentZ).map(layer => (layer.getRenderedLayer(), layer.currentPosition))
        sortedLayersImage.foreach
        {
            case (_ @ Some(image : BufferedImage), position : Position) =>
            {
                mainFrame.getGraphics.drawImage(image, position.x, position.y, null)
            }
            
            case (_ @ Some(shape : Shape), _ : Position) =>
            {
                mainFrame.getGraphics.asInstanceOf[Graphics2D].draw(shape)
            }
            
            case _ =>
        }
        mainFrame
    }
    
    override def onCriticalTimeStamp(timeStamp : Long) : BufferedImage =
    {
        layers.filter(_.criticalTimeStamp.contains(timeStamp)).foreach(_.onCriticalTimeStamp(timeStamp))
        generateFrame()
    }
}
