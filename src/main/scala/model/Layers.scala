package model


case class Position(x : Int, y : Int, z : Int)
{
    if(x < 0 || y < 0 || z < 0)
    {
        throw new IndexOutOfBoundsException(s"Position's arguments must be bigger then 0. received: x=$x, y=$y, z=$z")
    }
}

case class LayerScale(scale : Int)
{
    if(scale < 0)
    {
        throw new IndexOutOfBoundsException(s"Scale must be bigger then 0. received: scale: $scale")
    }
}

case class Opacity(x : Int)

class Layers(name : String, position : Position, scale : LayerScale, opacity : Int)
{

}

