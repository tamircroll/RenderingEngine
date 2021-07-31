package RenderingEngine

import java.awt.image.BufferedImage
import com.xuggle.mediatool.{IMediaViewer, IMediaWriter, ToolFactory}
import com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT
import javax.imageio.ImageIO
import scala.concurrent.duration.MILLISECONDS

class RenderingEngine()
{
    // video parameters
    val videoStreamIndex = 0
    val videoStreamID = 0
    val frameRate = DEFAULT_TIME_UNIT.convert(40, MILLISECONDS)
    
//     audio parameters
//    val audioStreamIndex = 1
//    val audioStreamID = 0
//    val channelCount = 1
//    val sampleRate = 44100  // Hz
//    val sampleCount = 1000
    
    def render(composition : Composition, outputFilePath : String)
    {
        var writerOption : Option[IMediaWriter] = None
        try
        {
            writerOption = Some(ToolFactory.makeWriter(outputFilePath))
            writerOption.foreach
            {
                writer =>
                {
                    addExitOnCloseListener(writer)
                    
                    writer.addVideoStream(videoStreamIndex, videoStreamID, composition.getResolution().width, composition.getResolution().height)
//                  writer.addAudioStream(audioStreamIndex, audioStreamID, composition.getResolution().width, composition.getResolution().height)
                    
                    composition.allCriticalTimeStampsSorted.foreach
                    {
                        criticalTimestamp =>
                        {
                            val frame : BufferedImage = composition.onCriticalTimeStamp(criticalTimestamp)
                            writer.encodeVideo(
                                videoStreamIndex,
                                frame,
                                DEFAULT_TIME_UNIT.convert(criticalTimestamp, MILLISECONDS),
                                DEFAULT_TIME_UNIT)
//                          writer.encodeVideo(
//                          audioStreamIndex,
//                          frame, DEFAULT_TIME_UNIT.convert(criticalTimestamp, MILLISECONDS),
//                          DEFAULT_TIME_UNIT)
                        }
                    }
                }
            }
        }
        catch
        {
            case e : Exception =>
            {
                println(s"Failed to make video: $e. t.render(RenderingEngine.scala:62)")
                throw e
            }
        }
        finally
        {
            writerOption.foreach(_.close())
        }
    }
    
    private def addExitOnCloseListener(mediaWriter : IMediaWriter) =
    {
        mediaWriter.addListener(
            ToolFactory.makeViewer(
                IMediaViewer.Mode.VIDEO_ONLY,
                true,
                javax.swing.WindowConstants.EXIT_ON_CLOSE)
        )
    }
}
