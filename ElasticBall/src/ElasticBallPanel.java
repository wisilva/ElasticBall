import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.Timer;
public class ElasticBallPanel extends JPanel{
	private Ball b, prev;
	private Timer timer;
	private double accel = 0.0;
	private int speed; 
	private double xSpeed, ySpeed;
	static final int WID=1000, LEN=600, DELAY=10;
	private JLabel label;
	private boolean released;
	private JButton reset;
	private JSlider slider;

	
	public ElasticBallPanel(Ball ball) {
		label = new JLabel("This will display speed");
		slider = new JSlider(-100,100);
		slider.addChangeListener(new SliderListener());
		reset = new JButton("Reset");
		reset.addActionListener(new ButtonListener());
		b = ball;
		timer = new Timer(DELAY,new TimerListener());
		prev = new Ball(b);
		xSpeed = 0.0;
		ySpeed = 0.0;
		this.addMouseMotionListener(new DragListener());
		this.addMouseListener(new ReleaseListener());
		this.setPreferredSize(new Dimension(WID,LEN));
		this.setBackground(Color.white);
		released = false;
		
		add(label);
		add(reset);
		add(slider);
		timer.start();
	}
	
	public boolean isInsideBall(MouseEvent event, Ball b) {
		//I think the circle starts at its rectangle's top left corner
		//Therefore, since size is diameter, just go down the radius and then right the radius to find the center.
	if( Math.sqrt(((event.getX()-b.getCenterX())*(event.getX()-b.getCenterX())) + ((event.getY()-b.getCenterY())*(event.getY()-b.getCenterY()))) <b.getRadius()) return true;
	return false;
		
	}
	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		b.paintIcon(this, page, b.xPos, b.yPos);
		}			
	private class ReleaseListener implements MouseListener{
		public void mouseReleased(MouseEvent event) {
			
			if(isInsideBall(event,b)) released = true;
		}
		
		public void mouseEntered(MouseEvent event) {}
		public void mousePressed(MouseEvent event) {}
		public void mouseClicked(MouseEvent event) {}
		public void mouseExited(MouseEvent event) {}
	}
	private class DragListener implements MouseMotionListener {
		public void mouseMoved(MouseEvent event) {
		}
		
		public void mouseDragged(MouseEvent event) {		
			released = false;
			if(isInsideBall(event,b)) {
				b.yPos = event.getY()-b.getRadius();
				b.xPos = event.getX()-b.getRadius();
			}
			
		if(event.getX() > WID || event.getY() > LEN|| event.getY() <0 || event.getX() <0) {
			b.xPos = WID/2;
				b.yPos = LEN/2;
			}
			repaint();	
		}
	}
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			xSpeed = 0;
			ySpeed = 0;
			speed = 0;
			prev.xPos = WID/2;
			prev.yPos = LEN/2;
			b.xPos = WID/2;
			b.yPos = LEN/2;
			released = false;
			slider.setValue(0);
		}
	}
	private class SliderListener implements ChangeListener{
		public void stateChanged(ChangeEvent event) {
			accel = (double) ((JSlider) event.getSource()).getValue()/100;
		}
	}
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			speed =(int) Math.sqrt((xSpeed*xSpeed)+(ySpeed*ySpeed));
			if((b.getCenterX()-prev.getCenterX()) != 0) xSpeed = (b.getCenterX()-prev.getCenterX());
			if( (b.getCenterY()-prev.getCenterY())!=0) ySpeed = (b.getCenterY()-prev.getCenterY());
			
			
			prev.copy(b);
			
			if(released) {
				
			
				if(ySpeed>0) ySpeed += (accel);
				else ySpeed -= accel;
				if(xSpeed>0) xSpeed += (accel);
				else xSpeed -= accel;
				if(b.xPos>=WID-b.size || b.xPos<=0) xSpeed *= -1;
				if(b.yPos>=LEN-b.size || b.yPos<=0) ySpeed *= -1;
				b.xPos += xSpeed;
				b.yPos += ySpeed;
			}
			label.setText("Speed: " + xSpeed + " | " + ySpeed);


			repaint();
		}

	}	
	
}
	
	
