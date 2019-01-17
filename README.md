# ElasticBall
This Project is a Java GUI coded in Eclipse of one or more elastic balls that can be "thrown".

The program starts when one runs the Main class.

+ and minus buttons in the top corner add balls with a minimum one one ball and a maximum of 10.

The balls were created as extensions of the Java Object ImageIcon. Visually, they are just filled in circles.

Clicking and dragging a ball will cause the ball to move with the cursor, and letting go of it will cause it to move at the same speed
with which it was moving when it was released. This was done by using a second, invisible ball that corresponds to each visible one. The 
invisible ball has the same position the corresponding visible ball held 10 milliseconds before, and their distance determines the ball's 
speed.

Acceleration can be affected using the slider. Moving it to the left will cause the ball to decelerate, while moving it to the right will 
cause positive acceleration.

If a moving ball touches an unmoved ball or a ball that is currently being held, the moving ball will bounce in the opposite direction
(both X and Y speeds will reverse). This is a very simple collision.
