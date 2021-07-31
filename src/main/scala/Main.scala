import java.awt.Toolkit
import RenderingEngine.layer.{ImageLayer, Layer, LayerEvent, Position, PositioningEvent, ScaleEvent}
import RenderingEngine.{Composition, RenderingEngine, Resolution}

object Main
{
    def main(args : Array[String]) : Unit =
    {
        //        new TEMPPPPCompositionToMovie().makeVideo()
        val comp = createComposition
        new RenderingEngine().render(comp, s"""C:/Images/movies.mp4""")
        println(s"TAMIR: HERE: program ends. t.main(Main.scala:9)")
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
        val rotate1 = PositioningEvent(1000, Position(0, 0, 1))
        val rotate2 = PositioningEvent(2000, Position(10, 0, 1))
        val rotate3 = PositioningEvent(3000, Position(20, 0, 1))
        val rotate7 = PositioningEvent(3000, Position(30, 0, 1))
        val rotate4 = PositioningEvent(4000, Position(40, 0, 1))
        val rotate5 = PositioningEvent(5000, Position(50, 0, 1))
        val rotate6 = PositioningEvent(6000, Position(60, 0, 1))

//        val rotate1 = RotateEvent(1000, 20) //
//        val rotate2 = RotateEvent(2000, 40) //
//        val rotate3 = RotateEvent(3000, 40) //
//        val rotate7 = PositioningEvent(3000, Position(0,0,5)) //
//        val rotate4 = RotateEvent(4000, 40) //
//        val rotate5 = RotateEvent(5000, 100) //
//        val rotate6 = RotateEvent(6000, 20) //
        
        val scale1 = ScaleEvent(1000, 90)
        val scale2 = ScaleEvent(2000, 90)
        val scale3 = ScaleEvent(3000, 90)
        val scale4 = ScaleEvent(8000, 90)
        val scale5 = ScaleEvent(9000, 90)
        val scale6 = ScaleEvent(10000, 150)

//        val scale1 =  RotateEvent(1000, 20) // ScaleEvent(1000, 120)
//        val scale2 =  RotateEvent(2000, 40) // ScaleEvent(2000, 40)
//        val scale3 =  RotateEvent(3000, 40) // ScaleEvent(3000, 140)
//        val scale4 =  RotateEvent(4000, 40) // ScaleEvent(8000, 140)
//        val scale5 =  RotateEvent(5000, -100) // ScaleEvent(9000, 150)
//        val scale6 = RotateEvent(60000, -40) // RotateEvent(6000, 20)
        
        
        val events : List[LayerEvent] = List(rotate1, rotate2, rotate3, rotate4, rotate5, rotate6/*, rotate7*/)
        val events2 : List[LayerEvent] = List(scale1, scale2, scale3, scale4, scale5, scale6)
        
        
        val layer1 = new ImageLayer(s"""C:/Images/1.jpg""", Position(50, 50, 2), events)
        val layer2 = new ImageLayer(s"""C:/Images/2.jpg""", Position(0, 0, 4), events2)
        
        val lst = List(layer1, layer2)
        lst
    }
}