import temp.TEMPPPPCompositionToMovie
import scala.tools.nsc.Main

object Main
{
    def main(args : Array[String]) : Unit =
    {
        new TEMPPPPCompositionToMovie().makeVideo()
        println(s"TAMIR: HERE: progrem ends. t.main(Main.scala:9)")
    }
}