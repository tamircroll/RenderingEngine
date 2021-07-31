package RenderingEngine

import java.awt.image.BufferedImage
import RenderingEngine.layer.{CriticalTimeStampObserver, Layer}

case class Resolution(width : Int, height : Int)

class Composition(resolution : Resolution, layers : List[Layer]) extends CriticalTimeStampObserver
{
    val backgroundImage : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR);
    val allCriticalTimeStamps : List[Long] = layers.flatMap(layer => layer.getCriticalTimeStamps())
    
    def getResolution() : Resolution =
    {
        resolution
    }
    
    def generateFrame() : BufferedImage =
    {
        val sortedLayersImage : List[BufferedImage] = layers.sortBy(_.getCurrentZ).map(_.getCurrentImage())
        val frame : BufferedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_3BYTE_BGR)
        sortedLayersImage.foreach
        {
            image =>
            {
                frame.getGraphics().drawImage(image, 0, 0, null)
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


//
//writer : IMediaWriter = ToolFactory.makeWriter(outputFile);
//writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, img.getSize().width, screenshot.getSize().height);
//long startTime = System.nanoTime();
//for (int i = 0; i < seconds*25; i++)
//{
//BufferedImage bgrScreen = Tools.convertToType(img, BufferedImage.TYPE_3BYTE_BGR);
//writer.encodeVideo(0, bgrScreen, System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
//try
//{
//  Thread.sleep((long) (1000 / 25));
//}
// catch (InterruptedException e) {
//// ignore
//}
//writer.close();
