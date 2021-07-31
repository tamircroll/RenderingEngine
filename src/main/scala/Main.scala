import java.awt.Toolkit
import RenderingEngine.layer.{ImageLayer, Layer, LayerEvent, Position, RotateEvent, ScaleEvent}
import RenderingEngine.{Composition, RenderingEngine, Resolution}

object Main
{
    def main(args : Array[String]) : Unit =
    {
        //        new TEMPPPPCompositionToMovie().makeVideo()
        val comp = createComposition
        new RenderingEngine().render(comp, "file:///c:/Images/myMovie.mp4")
//        println(s"TAMIR: HERE: progrem ends. t.main(Main.scala:9)")
    }
    
    private def createComposition =
    {
        val Layers : List[Layer] = createLayers()
        val screenSize = Toolkit.getDefaultToolkit.getScreenSize
        val width = screenSize.getWidth.toInt
        val height = screenSize.getHeight.toInt
        new Composition(Resolution(width, height), Layers)
    }
    
    private def createLayers() : List[Layer] =
    {
        val rotate1 = RotateEvent(1000, 20)
        val rotate2 = RotateEvent(2000, 40)
        val rotate3 = RotateEvent(3000, 40)
        val rotate4 = RotateEvent(4000, 40)
        val rotate5 = RotateEvent(5000, 100)
        val rotate6 = RotateEvent(6000, 20)
        
        val scale1 = ScaleEvent(1000, 20)
        val scale2 = ScaleEvent(2000, 40)
        val scale3 = ScaleEvent(3000, 140)
        val scale4 = ScaleEvent(4000, 40)
        val scale5 = ScaleEvent(5000, 100)
        val scale6 = RotateEvent(6000, 20)
        
        
        val events : List[LayerEvent] = List(rotate1, rotate2, rotate3, rotate4, rotate5, rotate6)
        val events2 : List[LayerEvent] = List(scale1, scale2, scale3, scale4, scale5, scale6)
        
        val layer1 = new ImageLayer(s"""file:///c:/Images/1.jpg""", new Position(50, 50, 4), events)
        val layer2 = new ImageLayer(s"""file:///c:/Images/2.jpg""", new Position(50, 50, 2), events2)
        
        val lst = List(layer1, layer2)
        lst
    }
}