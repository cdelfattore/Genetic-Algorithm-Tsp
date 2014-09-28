import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class GUI {

	public static void main(String[] args)  {
		JFrame frame = new JFrame("Genetic Algorithm for TSP");
		frame.setSize(600,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new BorderLayout(5,10));
		
		//create a panel to hold the text fields and buttons
		JPanel pOne = new JPanel(/*new GridLayout(2,1)*/);
		pOne.setSize(new Dimension(200,200));

		JLabel genLabel = new JLabel("Number of Generations");
		pOne.add(genLabel);

		JTextField genText = new JTextField(10);
		pOne.add(genText);

		JLabel sizeCrossLabel = new JLabel("Amount of nodes in CrossOver");
		pOne.add(sizeCrossLabel);

		JTextField sizeCrossText = new JTextField(10);
		pOne.add(sizeCrossText);



		//Create a button
		JButton submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(75,25));
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("hit");
				String[] para = new String[2];
				para[0] = args[0];
				para[1] = genText.getText();

		        
		        try {
		        	//g.main(para);
		        	Genetic g = new Genetic();
		        	g.main(para);
		        } catch (IOException ex){}
		       
		    }
		});
		

		pOne.add(submit,BorderLayout.SOUTH);
		frame.add(pOne,BorderLayout.CENTER);

		//Panel used to display points
		MyPanel pointsPanel = new MyPanel();
		pointsPanel.setPrefferredSize(new Dimension(600,300));
		frame.add(pointsPanel,BorderLayout.NORTH);

		frame.setVisible(true);
	}

	public static class MyPanel extends JPanel{
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			int scale = 6;

			for(int i=0;i< 10;i++){
				if(i+1>=10){
					g2.drawOval(i * scale, 0, 5, 5);
					g2.drawOval(0, i * scale, 5, 5);
					
				}
				else {
					g2.drawOval(i * scale, 0, 5, 5);
					g2.drawOval(0, i * scale, 5, 5);
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