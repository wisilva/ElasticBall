import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.Timer;
//Everything except for collision physics works perfectly

public class TestBallPanel extends JPanel{
	private ArrayList<Ball> b, prev;
	private Timer timer;
	private double accel = 0.0;
	private int num,size; 
	private ArrayList<Double> xSpeed, ySpeed;
	static final int WID=1000, LEN=600, DELAY=10;
	private ArrayList<Boolean> released, collided;
	private JButton reset, add, sub;
	private JSlider slider;

	
	public TestBallPanel() {
		b= new ArrayList<Ball>();
		prev= new ArrayList<Ball>();
		collided =  new ArrayList<Boolean>();
		released = new ArrayList<Boolean>();
		xSpeed = new ArrayList<Double>();
		ySpeed = new ArrayList<Double>();
		size =100;
		
		//All the buttons and stuff
		slider = new JSlider(-100,100);
		slider.addChangeListener(new SliderListener());
		reset = new JButton("Reset");
		reset.addActionListener(new ButtonListener());
		add= new JButton("+");
		add.addActionListener(new ButtonListener());
		sub= new JButton("-");
		sub.addActionListener(new ButtonListener());
		timer = new Timer(DELAY,new TimerListener());
		
		

		b.add(new Ball(size));
		prev.add(new Ball(b.get(0)));
		xSpeed.add(0.0);
		ySpeed.add(0.0);
		
		
		this.addMouseMotionListener(new DragListener());
		this.addMouseListener(new ReleaseListener());
		this.setPreferredSize(new Dimension(WID,LEN));
		this.setBackground(Color.white);
		released.add(false);
		
		add(add);
		add(sub);
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
	public boolean areTouching(Ball b1, Ball b2) {
		return Math.sqrt(((b2.getCenterX()-b1.getCenterX())*(b2.getCenterX()-b1.getCenterX())) + ((b2.getCenterY()-b1.getCenterY())*(b2.getCenterY()-b1.getCenterY()))) < size;
	}
	public void changeNum() {
		b.clear();
		prev.clear();
		released.clear();
		xSpeed.clear();
		ySpeed.clear();
		for(int i =0; i<num;i++) {
			b.add(new Ball(size));
			b.get(i).xPos = size*i;
			prev.add(new Ball(b.get(i)));
			released.add(false);
			collided.add(false);
			xSpeed.add(0.0);
			ySpeed.add(0.0);
		}
		repaint();
	}
	public void paintComponent(Graphics page) {
		super.paintComponent(page);
		for(int i =0 ;i <num;i++) {
			
		b.get(i).paintIcon(this, page, b.get(i).xPos, b.get(i).yPos);
		}
		}			
	private class ReleaseListener implements MouseListener{
		public void mouseReleased(MouseEvent event) {
			for(int i =0;i<num;i++) {
			if(isInsideBall(event,b.get(i))) released.set(i,true);
			}
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
			for(int i =0;i<num;i++) {
			if(isInsideBall(event,b.get(i))) {
				released.set(i, false);
				b.get(i).yPos = event.getY()-b.get(i).getRadius();
				b.get(i).xPos = event.getX()-b.get(i).getRadius();
				
				}
		}
		//if(event.getX() > WID || event.getY() > LEN|| event.getY() <0 || event.getX() <0) {
				//b.xPos = WID/2;
				//b.yPos = LEN/2;
			//}
			repaint();	
		}
	}
	private class ButtonListener implements ActionListener{
	
		public void actionPerformed(ActionEvent event) {
			if(event.getSource()== reset) {
				slider.setValue(0);
			for(int i =0; i<num;i++) {
			xSpeed.set(i,0.0) ;
			
			ySpeed.set(i,0.0);
			b.get(i).xPos = size*i;
			b.get(i).yPos = LEN/2;
			released.set(i, false);
			}
			}
			else if(event.getSource() == add) {
				if(num*size<WID) {
				num++;
				changeNum();
			}
			}
			else if(event.getSource()==sub) {
				if(num>1) {
				num--;
				changeNum();
				}
				}
			repaint();
		}
	}
	private class SliderListener implements ChangeListener{
		public void stateChanged(ChangeEvent event) {
			if(slider.getValue()<7 && slider.getValue()>-7) slider.setValue(0);
			accel = (double) ((JSlider) event.getSource()).getValue()/100;
			
		}
	}
	private class TimerListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event) {
			for(int i =0;i<num;i++) {
			if((b.get(i).getCenterX()-prev.get(i).getCenterX()) != 0) xSpeed.set(i,(double)(b.get(i).getCenterX()-prev.get(i).getCenterX())); 
			if((b.get(i).getCenterY()-prev.get(i).getCenterY()) != 0) ySpeed.set(i,(double)(b.get(i).getCenterY()-prev.get(i).getCenterY()));
			
			
			prev.get(i).copy(b.get(i));
				
			if(released.get(i)) {
				
				
				if(ySpeed.get(i)>=0) ySpeed.set(i, ySpeed.get(i)+accel);
				else ySpeed.set(i, ySpeed.get(i)-accel);
				if(xSpeed.get(i)>=0) xSpeed.set(i, xSpeed.get(i)+accel);
				else xSpeed.set(i, xSpeed.get(i)-accel);
				
				//Bug patch below
				if(accel<0.0) {
					if(xSpeed.get(i) <=1 && xSpeed.get(i) >=-1.0) xSpeed.set(i,0.0);
					if(ySpeed.get(i) <=1 && ySpeed.get(i) >=-1.0) ySpeed.set(i, 0.0);
		
				}
				 
				if(b.get(i).xPos==WID-b.get(i).size || b.get(i).xPos==0) xSpeed.set(i, xSpeed.get(i)*-1);
				else if(b.get(i).xPos > WID-b.get(i).size) b.get(i).xPos = WID-b.get(i).size; 
				else if(b.get(i).xPos < 0)  b.get(i).xPos = 0;
				
				if(b.get(i).yPos>=LEN-b.get(i).size || b.get(i).yPos<=0) ySpeed.set(i, ySpeed.get(i)*-1);
				else if(b.get(i).yPos > LEN-b.get(i).size) b.get(i).yPos = LEN-b.get(i).size; 
				else if(b.get(i).yPos < 0)  b.get(i).yPos = 0;
				
				
				
				
//Right now, moving balls are supposed interact with untouched balls or the ones that are currently being held.
			
				
				if(num>1) {
				//Ball i is colliding with the stationary Ball j 
					for(int j=0;j<num;j++) {
						if(j!=i) {
						if(areTouching(b.get(i),b.get(j))) {
						//Simplest form of collision, reversing the ball's velocities
							if(released.get(i) && !released.get(j)) {
									ySpeed.set(i, ySpeed.get(i)*-1);
								
									xSpeed.set(i, xSpeed.get(i)*-1);
								
							}
							//  Moving ball collision
					//		if(released.get(i) && released.get(j)) {
					//			if(!collided.get(i) || !collided.get(j)) {
					//				double tempX = xSpeed.get(i);
					//				double tempY = ySpeed.get(i);
					//				ySpeed.set(i,ySpeed.get(j));
					//				xSpeed.set(i, xSpeed.get(j));
					//				ySpeed.set(j, tempY);
					//				xSpeed.set(j, tempX);
					//				collided.set(i,true);
					//				collided.set(j, true);
									
					//		} 
					//	}
						
						
						}
						}		
					}
					}	
				//Collision ends here
				 
				
				
				
				
				
				
				//This must be last for the movement of the balls, otherwise they can't move
				b.get(i).xPos += xSpeed.get(i);
				b.get(i).yPos += ySpeed.get(i);
				for(int j =0;j<num;j++) {
					collided.set(i,false);
				}
				
			}
		}
		
			repaint();
		}

	}	
	
}