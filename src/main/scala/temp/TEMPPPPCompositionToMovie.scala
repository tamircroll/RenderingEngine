package temp

import java.awt.geom.AffineTransform
import java.awt.{Image, image}
import com.xuggle.mediatool.{IMediaViewer, IMediaWriter, ToolFactory}
import javax.imageio.ImageIO
import java.awt.image.{AffineTransformOp, BufferedImage}
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit
import scala.util.Random
//import java.io.File
import scala.xml.dtd.DEFAULT
import scala.concurrent.duration.MILLISECONDS
import com.xuggle.xuggler.Global.DEFAULT_TIME_UNIT

class TEMPPPPCompositionToMovie()
{
    // Timing
//    val nextFrameTime = 1
    
    // video parameters
    
    val videoStreamIndex = 0
    val secondVideoStreamIndex = 1
    val videoStreamID = 0
    val secondVideoStreamID = 1
    val frameRate = DEFAULT_TIME_UNIT.convert(40, MILLISECONDS)
    val width = 320
    val height = 200
    
    // audio parameters
    
    val audioStreamIndex = 1
    val audioStreamID = 0
    val channelCount = 1
    val sampleRate = 44100
    val sampleCount = 1000
    
    def makeVideo()
    {
        var writer : IMediaWriter = null
        try
        {
//            writer = ToolFactory.makeWriter(s"""C:/Images/movies${Random.nextInt}.mp4""")
            writer = ToolFactory.makeWriter(s"""C:/Images/movies.mp4""")
            
            addExitOnCloseListener(writer)
            
            writer.addVideoStream(videoStreamIndex, videoStreamID, width, height)
//        writer.addAudioStream(audioStreamIndex, audioStreamID, width, height)
            
            val dir = new File("""C:\Images""")  // should be all the files to render
            
            var image : BufferedImage = ImageIO.read(new File("""C:\Images\1.jpg"""))
            rotate2(image)
//            image = scale(image)
            
            writer.encodeVideo(videoStreamIndex, image, 0, DEFAULT_TIME_UNIT)
            writer.encodeVideo(videoStreamIndex, ImageIO.read(dir.listFiles()(1)), DEFAULT_TIME_UNIT.convert(2000, MILLISECONDS), DEFAULT_TIME_UNIT)
            writer.encodeVideo(videoStreamIndex, ImageIO.read(dir.listFiles()(2)), DEFAULT_TIME_UNIT.convert(3000, MILLISECONDS), DEFAULT_TIME_UNIT)
            writer.encodeVideo(videoStreamIndex, ImageIO.read(dir.listFiles()(3)), DEFAULT_TIME_UNIT.convert(4000, MILLISECONDS), DEFAULT_TIME_UNIT)

//            writer.encodeVideo(videoStreamIndex, frame, 2000, DEFAULT_TIME_UNIT)
//            writer.encodeVideo(videoStreamIndex, frame, 3000, DEFAULT_TIME_UNIT)
//            writer.encodeVideo(videoStreamIndex, frame, 10000, DEFAULT_TIME_UNIT)
//        }
//            writer.close()
        }
        catch
        {
            case e =>
            {
                println(s"TAMIR: HERE: FAILED: $e")
                e.printStackTrace()
            }
            
        }
        finally
        {
            if(writer != null)
            {
                writer.close
                println(s"TAMIR: HERE: CLOSED ! ! !. t.makeVideo(TEMPPPPCompositionToMovie.scala:79)")
            }
            else
            {
                println(s"TAMIR: HERE: failed to create writer. t.makeVideo(TEMPPPPCompositionToMovie.scala:73)")
            }
        }
    }
    
    def addExitOnCloseListener(mediaWriter : IMediaWriter) =
    {
        mediaWriter.addListener(
            ToolFactory.makeViewer(
                IMediaViewer.Mode.VIDEO_ONLY,
                true,
                javax.swing.WindowConstants.EXIT_ON_CLOSE)
        )
    }
    
    
    private def rotate(image : BufferedImage) : BufferedImage =
    {
        val rotationRequired = Math.toRadians(-30)
        val locationX = image.getWidth / 2
        val locationY = image.getHeight / 2
        
        val tx = new AffineTransform()
        tx.rotate(rotationRequired, locationX, locationY)
        val op : AffineTransformOp = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC)
        image.getGraphics.drawImage(op.filter(image, null), 0, 0, null)
        //        val outputImage : BufferedImage = new BufferedImage(width, height, 1);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 2);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 3);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 4);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 5);   //  could not resample from BGR24 to YUV420P for picture of type BGR24
//        val outputImage : BufferedImage = new BufferedImage(width, height, 6);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 7);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 8);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 9);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 10);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 11);
//        val outputImage : BufferedImage = new BufferedImage(width, height, 12);
val outputImage : BufferedImage = new BufferedImage(width, height, 13);
        outputImage.getGraphics.drawImage(op.filter(image, null), 0, 0, null)
        outputImage
    }
    
    private def scale(image : BufferedImage) : BufferedImage =
    {
        // scale
        val scaleFactor = 80
        val targetWidth = (image.getWidth() * scaleFactor) / 100
        val targetHeight = (image.getHeight() * scaleFactor) / 100
        val scaledImage : Image = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        val outputImage : BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        outputImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        outputImage
    }
    
    
    private def rotate2(image : BufferedImage) /*: BufferedImage*/ =
    {
        val rotationRequired = Math.toRadians(-30)
        val locationX = image.getWidth / 2
        val locationY = image.getHeight / 2
        val tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY)
        val op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR)
    
        image.getGraphics.drawImage(op.filter(image, null), locationX, locationY, null)
    }
    
    private def scale3(image : BufferedImage) : BufferedImage =
    {
        // scale
        val scaleFactor = 80
        val targetWidth = (image.getWidth() * scaleFactor) / 100
        val targetHeight = (image.getHeight() * scaleFactor) / 100
        val scaledImage : Image = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        val outputImage : BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        outputImage
    }
}

