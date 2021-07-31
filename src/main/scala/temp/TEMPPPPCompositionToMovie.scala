package temp

import java.awt.geom.AffineTransform
import java.awt.{Image, image}
import com.xuggle.mediatool.{IMediaViewer, IMediaWriter, ToolFactory}
import javax.imageio.ImageIO
import java.awt.image.{AffineTransformOp, BufferedImage, IndexColorModel}
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
    val width = 600
    val height = 400
    
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
//            val width = image.getWidth
//            val height = image.getHeight
            image = rotate(image)
            image = scale(image)
            
            val outputImage : BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            outputImage.getGraphics().drawImage(image, 0, 0, null);
            writer.encodeVideo(videoStreamIndex, outputImage, 0, DEFAULT_TIME_UNIT)

            val image1 = ImageIO.read(dir.listFiles()(1))
            val outputImage2 : BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            outputImage2.getGraphics().drawImage(image1, 0, 0, null);
            writer.encodeVideo(videoStreamIndex, outputImage2, DEFAULT_TIME_UNIT.convert(3000, MILLISECONDS), DEFAULT_TIME_UNIT)



//            outputImage1.getGraphics().drawImage(image1, 0, 0, null);
//
//            var outputImage2 : BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
//            val image2 = ImageIO.read(dir.listFiles()(2))
//            outputImage2.getGraphics().drawImage(image2, 0, 0, null);
//
//            var outputImage3 : BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
//            val image3 = ImageIO.read(dir.listFiles()(3))
//            outputImage3.getGraphics().drawImage(image3, 0, 0, null);

//            ImageIO.write(outputImage0, "jpg", new File("C:/Images/a1.jpg"));
//            ImageIO.write(image, "jpg", new File("C:/Images/a0.jpg"));
//            ImageIO.write(outputImage1, "jpg", new File("C:/Images/a1.jpg"));
//            ImageIO.write(image1, "jpg", new File("C:/Images/a2.jpg"));
//            ImageIO.write(outputImage2, "jpg", new File("C:/Images/b1.jpg"));
//            ImageIO.write(image2, "jpg", new File("C:/Images/b2.jpg"));
//            ImageIO.write(outputImage3, "jpg", new File("C:/Images/c1.jpg"));
//            ImageIO.write(image3, "jpg", new File("C:/Images/c2.jpg"));
            
            
            
            
            
            
            
//            writer.encodeVideo(videoStreamIndex, outputImage, 0, DEFAULT_TIME_UNIT)
//            writer.encodeVideo(videoStreamIndex, outputImage1, DEFAULT_TIME_UNIT.convert(2000, MILLISECONDS), DEFAULT_TIME_UNIT)
//            writer.encodeVideo(videoStreamIndex, outputImage2, DEFAULT_TIME_UNIT.convert(3000, MILLISECONDS), DEFAULT_TIME_UNIT)
//            writer.encodeVideo(videoStreamIndex, outputImage3, DEFAULT_TIME_UNIT.convert(4000, MILLISECONDS), DEFAULT_TIME_UNIT)

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
        val locationX = width/2
        val locationY = height/2
        
        val tx = new AffineTransform()
        tx.rotate(rotationRequired, locationX, locationY)
        val op : AffineTransformOp = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC)
        var image2 = op.filter(image, null)
        image2
    }
    
    private def scale(image : BufferedImage) : BufferedImage =
    {
        val scaleFactor = 20
        val w : Int = image.getWidth();
        val h : Int = image.getHeight();
        var scaledImage : BufferedImage = new BufferedImage(((w * scaleFactor) / 100.0).toInt, ((h * scaleFactor) / 100.0).toInt, BufferedImage.TYPE_INT_ARGB);
        val at : AffineTransform = new AffineTransform();
        at.scale(scaleFactor / 100.0, scaleFactor / 100.0);
        val scaleOp : AffineTransformOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaledImage = scaleOp.filter(image, null);
        scaledImage
    }
}

