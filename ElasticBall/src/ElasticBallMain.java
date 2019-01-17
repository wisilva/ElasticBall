import javax.swing.*;
public class ElasticBallMain {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Elastic Circles");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane pane =new JTabbedPane();
		
		//Swap the order of these two to get to one quicker when rapidly experimenting with small changes
			pane.add("Test", new TestBallPanel());
			//pane.add("Ball",new ElasticBallPanel(new Ball(100)));
	
		
		
		frame.getContentPane().add(pane);

		
		frame.pack();
		frame.setVisible(true);
		
		
		
	}
}
