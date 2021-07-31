package RenderingEngine

import java.io.File
import scala.collection.immutable

case class Resolution(width : Int, height : Int)

case class Composition(file : File, resolution : Resolution, layers : List[Layer]) extends CriticalTimeStampObserver
{
    val allCriticalTimeStamps : List[Long] = layers.flatMap(layer => (layer.getCriticalTimeStamps()))
    
    override def onCriticalTimeStamp(timeStamp : Long) : File =
    {
        ???
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
