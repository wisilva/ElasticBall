import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class Ball extends ImageIcon{
	int size;
	int xPos, yPos;
	int dia, rad, centerX, centerY;
	public Ball() {
		
	}
	public Ball(int s) {
		size = s;
		xPos = ElasticBallPanel.WID / 2;
		yPos = ElasticBallPanel.LEN/2;
		dia = size;
		rad = dia/2;
		
		centerX = xPos + rad;
		centerY = yPos + rad;
		
	}
	//Copy ball constructor
	public Ball(Ball other) {
		
		this.size = other.size;
		this.xPos = other.xPos;
		this.yPos = other.yPos;
		dia = size;
		rad = dia/2;
	}
	public void copy(Ball other) {
		size = other.size;
		xPos=  other.xPos;
		yPos = other.yPos;
	}
	
	public int getSize() {
		return size;
	}
	public int getDiameter() {
		return dia;
	}
	public int getRadius() {
		return rad;
	}

	
	
	//All the calculation for inside the circle should be using center variables
	public int getCenterX() {
		return xPos + rad;
	}
	public int getCenterY() {
		return yPos + rad;
	}
	
	
	public void paintIcon(Component c, Graphics gr, int x, int y) {
		gr.fillOval(x,y, size, size);

	}
}

