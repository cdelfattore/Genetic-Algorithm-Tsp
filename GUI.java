import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class GUI {
	public static JTextArea output;

	public static void main(String[] args)  {
		JFrame frame = new JFrame("Genetic Algorithm for TSP");
		frame.setSize(600,600);
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
		        try {
		        	//g.main(para);
		        	Genetic g = new Genetic();
		        	fun = g.main(para);
		        } catch (IOException ex){}
		        //System.out.println(fun);
		       output.setText(fun);
		       frame.repaint();
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
		//scroll.setPreferredSize(new Dimension(450,175));
		panOutput.add(scroll);
		//panOutput.add(output);
		frame.add(panOutput,BorderLayout.SOUTH);



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

			for(int i=0;i< 300;i++){
				if(i+1>=300){
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