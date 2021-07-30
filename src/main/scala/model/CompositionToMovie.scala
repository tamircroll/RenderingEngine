package model

import java.awt.image
import com.xuggle.mediatool.{IMediaViewer, IMediaWriter, ToolFactory}
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.io.File
//import java.io.File
import scala.xml.dtd.DEFAULT
import scala.concurrent.duration.MILLISECONDS
import com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT

class CompositionToMovie(composition : Composition)
{
    // Timing
//    val nextFrameTime = 1
    
    // video parameters
    
    val videoStreamIndex = 0
    val videoStreamID = 0
    val frameRate = DEFAULT_TIME_UNIT.convert(500, MILLISECONDS)
//    val width = 320
//    val height = 200
    
    // audio parameters
    
    val audioStreamIndex = 1
    val audioStreamID = 0
    val channelCount = 1
    val sampleRate = 44100
    val sampleCount = 1000
    
    def makeVideo(composition : Composition)
    {
        val writer : IMediaWriter = ToolFactory.makeWriter(composition.file.getPath)
        
        addExitOnCloseListener(writer)
        
        writer.addVideoStream(videoStreamIndex, videoStreamID, composition.resolution.width, composition.resolution.height)
//        writer.addAudioStream(audioStreamIndex, audioStreamID, width, height)
        
        val dir = new File(s"c:/MovieMaker/thisMovie")  // should be all the files to render
    
    
        composition.allCriticalTimeStamps.foreach
        {
            criticalTimestamp =>
            {
                val frame : BufferedImage = ImageIO.read(composition.onCriticalTimeStamp(criticalTimestamp))
                writer.encodeVideo(videoStreamIndex, frame, criticalTimestamp, DEFAULT_TIME_UNIT)
            }
        }
        
        
//        for(file <- dir.listFiles())
//        {
//            val frame : BufferedImage = ImageIO.read(file)
//
//            writer.encodeVideo(videoStreamIndex, frame, nextFrameTime, DEFAULT_TIME_UNIT)
//        }
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
