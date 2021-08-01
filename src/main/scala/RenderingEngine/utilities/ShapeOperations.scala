package RenderingEngine.utilities

import java.awt.{Color, Shape}
import java.awt.geom.Ellipse2D
import RenderingEngine.layer.Position

object ShapeOperations
{
    def createCircle(radius : Int, color : Color, position : Position) : Shape =
    {
        new Ellipse2D.Double(position.x, position.y, radius, radius)
    }
    
    def rotateShape(shape : Shape, rotation : Int) =
    {
    
    }
    
    def uniformScaleShape(currentShape : Shape, percentage : Int) =
    {}
    
    def positionShape(currentShape : Shape, percentage : Int) =
    {}
    
    def fadeShape(currentShape : Shape, x : Int, y : Int) =
    {}
}
