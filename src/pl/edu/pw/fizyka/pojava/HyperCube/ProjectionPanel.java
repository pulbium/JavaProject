package pl.edu.pw.fizyka.pojava.HyperCube;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.*;
public class ProjectionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Point4D> points = new ArrayList<Point4D>();
	Color lineColor = Color.black, bgColor = Color.white;
	int lineThickness = 2, distance = 200, size = 50;
	RotSlider[] sliders;
	JRadioButton twoDButton = new JRadioButton("2D");
	JRadioButton threeDButton = new JRadioButton("3D");
	JRadioButton fourDButton = new JRadioButton("4D");
	BufferedImage buffer;
	
	JRadioButton perspectiveButton = new JRadioButton(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("p1"));
	JRadioButton paralellButton = new JRadioButton(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("p2"));
	JButton resetButton = new JButton("Reset");
	
	public ProjectionPanel(RotSlider[] sliders) {
		super();
		setLayout(null);
		this.sliders=sliders;
		twoDButton.setSelected(true);
		twoDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paralellButton.setSelected(true);
				perspectiveButton.setEnabled(false);
				
				for(int i = 0;i<6;i++) {
					sliders[i].setValue(0);
					sliders[i].setEnabled(false);
					sliders[i].playButton.setEnabled(false);
					sliders[i].textField.setEnabled(false);
					sliders[i].textField.setText("000");
				}
				sliders[2].setEnabled(true);
				sliders[2].textField.setEnabled(true);
			}
		});
		
		threeDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				perspectiveButton.setEnabled(true);
				
				for(int i = 0;i < 3;i++) {
					sliders[i].setEnabled(true);
					sliders[i].playButton.setEnabled(true);
					sliders[i].textField.setEnabled(true);
				}
				for(int i = 3;i<6;i++) {
					sliders[i].setValue(0);
					sliders[i].setEnabled(false);
					sliders[i].playButton.setEnabled(false);
					sliders[i].textField.setEnabled(false);
					sliders[i].textField.setText("000");
				}
				
			}
		});
		
		fourDButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				perspectiveButton.setEnabled(true);
				for(int i = 0;i<6;i++) {
					sliders[i].setEnabled(true);
					sliders[i].playButton.setEnabled(true);
					sliders[i].textField.setEnabled(true);
				}
			}
		});
		
		fourDButton.setToolTipText(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("p3"));
		
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0;i < 6;i++)
					sliders[i].setValue(0);
				
			}
		});
		
		ButtonGroup dimensionButtons = new ButtonGroup();
		dimensionButtons.add(twoDButton);
		dimensionButtons.add(threeDButton);
		dimensionButtons.add(fourDButton);
		
		paralellButton.setSelected(true);
		
		ButtonGroup projectionButtons = new ButtonGroup();
		projectionButtons.add(perspectiveButton);
		perspectiveButton.setToolTipText(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("p4"));
		perspectiveButton.setEnabled(false);
		projectionButtons.add(paralellButton);
		paralellButton.setToolTipText(ResourceBundle.getBundle("LanguageBundle",HyperCubeFrame.locale).getString("p5"));

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(bgColor);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setStroke(new BasicStroke(lineThickness));
		g2.setColor(lineColor);
		g2.translate(getWidth()/2, getHeight()/2);
		
		double[][] rotation = new double[4][4];
		rotation = 
				Matrix.MatMulMat(
					Matrix.MatMulMat(
						Matrix.MatMulMat(
							Matrix.MatMulMat(
								Matrix.MatMulMat(
									Matrix.rotationMatrix(0,1,sliders[2].angle), 
									Matrix.rotationMatrix(1,2,sliders[0].angle)
												), 
								Matrix.rotationMatrix(0,2,sliders[1].angle)
										), 
							Matrix.rotationMatrix(0,3,sliders[3].angle)
								), 
						Matrix.rotationMatrix(1,3,sliders[4].angle)
						), 
					Matrix.rotationMatrix(2,3,sliders[5].angle)
				);
			
		if(twoDButton.isSelected()) {
			points.removeAll(points);
			points.add(new Point4D(-size,-size));
			points.add(new Point4D(size,-size));
			points.add(new Point4D(size,size));
			points.add(new Point4D(-size,size));
				
			double[][] rotated = new double[4][4];
			
			for(int i=0;i<4;i++) {				
				rotated[i]=Matrix.MatMulVec(rotation,points.get(i).toVec());
			}
			
			for(int j=0;j<4;j++) {
				g2.drawLine((int)rotated[j][0], (int)rotated[j][1], (int)rotated[(j+1)%4][0], (int)rotated[(j+1)%4][1]);
			}
			/*
			for(Point4D p: points) {
				remove(p.coords);
				p.coords.setText(p.getX() + "," + p.getY());
				p.coords.setSize(150, 50);
				p.coords.setLocation((int)p.getX(), (int)p.getY());
				//p.coords.setBounds((int)p.getX(), (int)p.getY(), (int)(p.getX()*1.1), (int)(p.getY()*1.1));
				add(p.coords);
			}*/
		}
		else if(threeDButton.isSelected()) {
			points.removeAll(points);
			points.add(new Point4D(-size,-size,-size));
			points.add(new Point4D(size,-size,-size));
			points.add(new Point4D(size,size,-size));
			points.add(new Point4D(-size,size,-size));
			points.add(new Point4D(-size,-size,size));
			points.add(new Point4D(size,-size,size));
			points.add(new Point4D(size,size,size));
			points.add(new Point4D(-size,size,size));

			double[][] rotated = new double[8][4];
			double[][] projected2D = new double[8][2];
			
			for(int i=0;i<8;i++) {
				
				rotated[i]=Matrix.MatMulVec(rotation,points.get(i).toVec());
				double z = rotated[i][2];
				if(perspectiveButton.isSelected())
					projected2D[i] = Matrix.MatMulVec(Matrix.projectionFrom4DTo2D(distance, z), rotated[i]);
				else {
					projected2D[i][0] = rotated[i][0];
					projected2D[i][1] = rotated[i][1];
				}
			}
			
			for(int j=0;j<4;j++) {
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[(j+1)%4][0],  (int)projected2D[(j+1)%4][1]);
				g2.drawLine((int)projected2D[j+4][0],(int)projected2D[j+4][1],(int)projected2D[(j+1)%4+4][0],(int)projected2D[(j+1)%4+4][1]);
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[j+4][0],      (int)projected2D[j+4][1]);
			}
		}
		else if(fourDButton.isSelected()) {
			points.removeAll(points);
			points.add(new Point4D(-size,-size,-size,size));
			points.add(new Point4D(size,-size,-size,size));
			points.add(new Point4D(size,size,-size,size));
			points.add(new Point4D(-size,size,-size,size));
			points.add(new Point4D(-size,-size,size,size));
			points.add(new Point4D(size,-size,size,size));
			points.add(new Point4D(size,size,size,size));
			points.add(new Point4D(-size,size,size,size));

			points.add(new Point4D(-size,-size,-size,-size));
			points.add(new Point4D(size,-size,-size,-size));
			points.add(new Point4D(size,size,-size,-size));
			points.add(new Point4D(-size,size,-size,-size));
			points.add(new Point4D(-size,-size,size,-size));
			points.add(new Point4D(size,-size,size,-size));
			points.add(new Point4D(size,size,size,-size));
			points.add(new Point4D(-size,size,size,-size));

			double[][] rotated = new double[16][4];
			double[][] projected3D = new double[16][3];
			double[][] projected2D = new double[16][2];
			
			
			for(int i = 0;i<16;i++) {
				rotated[i] = Matrix.MatMulVec(rotation, points.get(i).toVec());
				if(perspectiveButton.isSelected()) {
					double w = rotated[i][3];
					projected3D[i] = Matrix.MatMulVec(Matrix.projectionFrom4DTo3D(distance, w), rotated[i]);
					double z = projected3D[i][2];
					projected2D[i] = Matrix.MatMulVec(Matrix.projectionFrom3DTo2D(distance, z), projected3D[i]);
				}
				else {
					projected2D[i][0] = rotated[i][0];
					projected2D[i][1] = rotated[i][1];
					
				}
			}
			
			for(int j = 0;j<4;j++) {
				
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[(j+1)%4][0],  (int)projected2D[(j+1)%4][1]);
				g2.drawLine((int)projected2D[j+4][0],(int)projected2D[j+4][1],(int)projected2D[(j+1)%4+4][0],(int)projected2D[(j+1)%4+4][1]);
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[j+4][0],      (int)projected2D[j+4][1]);

				g2.drawLine((int)projected2D[j+8][0],  (int)projected2D[j+8][1],  (int)projected2D[(j+1)%4+8][0],  (int)projected2D[(j+1)%4+8][1]);
				g2.drawLine((int)projected2D[j+4+8][0],(int)projected2D[j+4+8][1],(int)projected2D[(j+1)%4+4+8][0],(int)projected2D[(j+1)%4+4+8][1]);
				g2.drawLine((int)projected2D[j+8][0],  (int)projected2D[j+8][1],  (int)projected2D[j+4+8][0],      (int)projected2D[j+4+8][1]);
				
				g2.drawLine((int)projected2D[j][0],  (int)projected2D[j][1],  (int)projected2D[j+8][0], (int)projected2D[j+8][1]);
				g2.drawLine((int)projected2D[j+4][0],(int)projected2D[j+4][1],(int)projected2D[j+12][0],(int)projected2D[j+12][1]);

			}
		}
		repaint();
	}
}
