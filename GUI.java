import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class GUI {
	public static JTextArea output;
	public static Map<Integer,Point> points;
	public static ArrayList<Integer> drawArray;
	public static JFrame frame;

	public static void main(String[] args)  {
		frame = new JFrame("Genetic Algorithm for TSP");
		frame.setSize(600,700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new BorderLayout(5,10));
		
		//create a panel to hold the text fields and buttons
		JPanel pOne = new JPanel();
		pOne.setSize(new Dimension(200,200));

		//Label and text field for the number of child to generate
		JLabel genLabel = new JLabel("Number of Generations");
		pOne.add(genLabel);
		//Label and text field for the number of child to generate
		JTextField genText = new JTextField(10);
		pOne.add(genText);

		//The amount of nodes to use in the cross over
		JLabel sizeCrossLabel = new JLabel("Amount of nodes in CrossOver");
		pOne.add(sizeCrossLabel);

		JTextField sizeCrossText = new JTextField(10);
		pOne.add(sizeCrossText);

		JLabel sizeOfPop = new JLabel("Population Size");
		pOne.add(sizeOfPop);

		JTextField sizePopText = new JTextField(10);
		pOne.add(sizePopText);

		JLabel lblMutateRate = new JLabel("Mutation Rate");
		pOne.add(lblMutateRate);

		JTextField txtMutateRate = new JTextField(10);
		pOne.add(txtMutateRate);




		//Create a button
		JButton submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(75,25));
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("hit");
				String[] para = new String[5];
				para[0] = args[0];
				para[1] = genText.getText();
				para[2] = sizeCrossText.getText();
				para[3] = sizePopText.getText();
				para[4] = txtMutateRate.getText();
				String fun = "";
		        Genetic g = new Genetic();
		        try {
		        	//g.main(para);
		        	fun = g.main(para);
		        } catch (IOException ex){}
		        //System.out.println(fun);
		       output.setText(fun);

		       //Draw the points on MyPanel
		       points = g.getPoints();
		       drawArray = g.getDrawArray();
		       //System.out.println(points.keySet());
				MyPanel pointsPanel = new MyPanel();
				pointsPanel.setPrefferredSize(new Dimension(600,400));
				frame.add(pointsPanel,BorderLayout.NORTH);

				SwingUtilities.updateComponentTreeUI(frame);

		       //frame.repaint();
		    }
		});
		

		pOne.add(submit,BorderLayout.SOUTH);
		frame.add(pOne,BorderLayout.CENTER);

		//Output Panel
		JPanel panOutput = new JPanel();
		//panOutput.setPreferredSize(new Dimension(600,100));
		output = new JTextArea();
		output.setPreferredSize(new Dimension(500,175));
		JScrollPane scroll = new JScrollPane (output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(450,175));
		panOutput.add(scroll);
		//panOutput.add(output);
		frame.add(panOutput,BorderLayout.SOUTH);



		//Panel used to display points
		/*MyPanel pointsPanel = new MyPanel();
		pointsPanel.setPrefferredSize(new Dimension(600,300));
		frame.add(pointsPanel,BorderLayout.NORTH);*/

		frame.setVisible(true);
	}

	public static class MyPanel extends JPanel{
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;

			for(int i=0;i< points.size();i++){
				//System.out.println(drawArray.get(i)+": " + (int)points.get(drawArray.get(i)).x + " " + (int)points.get(drawArray.get(i)).y);
				int scale = 3;
				if(i+1>=points.size()){
					//System.out.println("here");
					//System.out.println((int)points.get(drawArray.get(i)).x + " " + (int)points.get(drawArray.get(i)).y + " " + (int)points.get(drawArray.get(0)).x + " " + (int)points.get(drawArray.get(0)).y);
					//g2.drawLine((int)points.get(drawArray.get(i)).x * 4,(int)points.get(drawArray.get(i)).x * 4, (int)points.get(drawArray.get(0)).x * 4, (int)points.get(drawArray.get(0)).y * 4);	
					//draw the point and the line
					g2.drawOval((int)points.get(drawArray.get(drawArray.size()-1)).x *scale, (int)points.get(drawArray.get(drawArray.size()-1)).y *scale, 5, 5);
					g2.drawLine( (int)points.get(drawArray.get(0)).x *scale, (int)points.get(drawArray.get(0)).y*scale,(int)points.get(drawArray.get(drawArray.size()-1)).x*scale,(int)points.get(drawArray.get(drawArray.size()-1)).y*scale);
				}
				else {
					//System.out.println((int)points.get(drawArray.get(i)).x + " " + (int)points.get(drawArray.get(i)).y + " " + (int)points.get(drawArray.get(i+1)).x + " " + (int)points.get(drawArray.get(i+1)).y);
					//draw the point and the line
					g2.drawOval((int)points.get(drawArray.get(i)).x *scale, (int)points.get(drawArray.get(i)).y *scale, 5, 5);
					g2.drawLine( (int)points.get(drawArray.get(i)).x *scale, (int)points.get(drawArray.get(i)).y*scale,(int)points.get(drawArray.get(i+1)).x*scale,(int)points.get(drawArray.get(i+1)).y*scale);
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