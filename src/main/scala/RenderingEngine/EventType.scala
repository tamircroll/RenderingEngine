package RenderingEngine

case class EventType()

object EventType extends Enumeration
{
    type EventType = Value
    val VISIBILITY,
    OPACITY,
    ROTATE,
    SCALE,
    POSITION = Value
}
