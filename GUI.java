import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class GUI {

	public static void main(String[] args){
		JFrame frame = new JFrame("Genetic Algorithm for TSP");
		frame.setSize(600,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new BorderLayout(5,10));
		
		//create a panel to hold the text fields and buttons
		JPanel pOne = new JPanel(new GridLayout(5,2,10,2));
		pOne.setPreferredSize(new Dimension(300,300));

		JLabel genLabel = new JLabel("Number of Generations");
		genLabel.setPreferredSize(new Dimension(10,10));
		pOne.add(genLabel);

		JTextField genText = new JTextField(10);
		genText.setSize(new Dimension(10,10));
		pOne.add(genText);

		JLabel sizeCrossLabel = new JLabel("Amount of nodes in CrossOver");
		sizeCrossLabel.setPreferredSize(new Dimension(10,10));
		pOne.add(sizeCrossLabel);

		JTextField sizeCrossText = new JTextField(10);
		genText.setSize(new Dimension(10,10));
		pOne.add(sizeCrossText);

		//Create a button
		JButton submit = new JButton("Submit");
		submit.addActionListener(new click());
		

		pOne.add(submit);
		frame.add(pOne,BorderLayout.EAST);

		//Panel used to display points
		MyPanel pointsPanel = new MyPanel();
		pointsPanel.setPrefferredSize(new Dimension(300,300));
		frame.add(pointsPanel,BorderLayout.WEST);




		frame.setVisible(true);
	}

	public static class MyPanel extends JPanel{
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			int scale = 6;

			for(int i=0;i< 10;i++){
				//System.out.println(drawArray.get(i)+": " + (int)points.get(drawArray.get(i)).x + " " + (int)points.get(drawArray.get(i)).y);
				if(i+1>=10){
					//System.out.println("here");
					//System.out.println((int)points.get(drawArray.get(i)).x + " " + (int)points.get(drawArray.get(i)).y + " " + (int)points.get(drawArray.get(0)).x + " " + (int)points.get(drawArray.get(0)).y);
					//g2.drawLine((int)points.get(drawArray.get(i)).x * 4,(int)points.get(drawArray.get(i)).x * 4, (int)points.get(drawArray.get(0)).x * 4, (int)points.get(drawArray.get(0)).y * 4);	
					//draw the point and the line
					g2.drawOval(i * scale, 0, 5, 5);
					g2.drawOval(0, i * scale, 5, 5);
					//g2.drawLine( (int)points.get(drawArray.get(0)).x *4, (int)points.get(drawArray.get(0)).y*4,(int)points.get(drawArray.get(drawArray.size()-1)).x*4,(int)points.get(drawArray.get(drawArray.size()-1)).y*4);
				}
				else {
					//System.out.println((int)points.get(drawArray.get(i)).x + " " + (int)points.get(drawArray.get(i)).y + " " + (int)points.get(drawArray.get(i+1)).x + " " + (int)points.get(drawArray.get(i+1)).y);
					//draw the point and the line
					g2.drawOval(i * scale, 0, 5, 5);
					g2.drawOval(0, i * scale, 5, 5);
					//g2.drawLine( (int)points.get(drawArray.get(i)).x *4, (int)points.get(drawArray.get(i)).y*4,(int)points.get(drawArray.get(i+1)).x*4,(int)points.get(drawArray.get(i+1)).y*4);
				}
			}
		}	

		public void setPrefferredSize(Dimension d){
			super.setPreferredSize(d);
		}
	}

	public static class click implements ActionListener  {
		public void actionPerformed(ActionEvent e) { 
		    
		}
	} 
	
}