package hypercube;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class HyperCubeMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	JMenu fileMenu = new JMenu("Plik");
	JMenuItem save = new JMenuItem("Zapisz obraz");
	JMenuItem load = new JMenuItem("Dodaj obiekt (ewentualnie, ale raczej nie)");
	
	JMenu colorMenu = new JMenu("Obraz");
	JMenuItem changeBG = new JMenuItem("Zmie� kolor t�a");
	JMenuItem changeLine = new JMenuItem("Zmie� kolor linii");
	JMenuItem changeThickness = new JMenuItem("Zmie� grubo�� linii");
	
	JMenu optionsMenu = new JMenu("Opcje");
	JMenuItem changeSize = new JMenuItem("Rozmiar hipersze�cianu");
	JMenuItem changeDistance = new JMenuItem("Odleg�o�� obserwatora");
	
	public HyperCubeMenuBar(ProjectionPanel projectionPanel) {
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = new BufferedImage(projectionPanel.getWidth(),projectionPanel.getHeight(),BufferedImage.TYPE_USHORT_555_RGB);
				Graphics2D g2 = image.createGraphics();
				projectionPanel.paintComponent(g2);
				try {
					ImageIO.write(image,"png", new File(JOptionPane.showInputDialog("Podaj nazw� pliku, kt�ry chcesz zapisa�:","Untitled")+"."+"png"));
				} catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		
		
		changeBG.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.bgColor = JColorChooser.showDialog(null, "Wybierz kolor", null);
			}
		});
				
		changeLine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.lineColor = JColorChooser.showDialog(null, "Wybierz kolor", null);
			}
		});
				
		changeThickness.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.lineThickness = Integer.parseInt(JOptionPane.showInputDialog("Podaj grubo�� linii", 1));				 
			}
		});
				
		changeSize.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.size = Integer.parseInt(JOptionPane.showInputDialog("Podaj d�ugo�� kraw�dzi:", 100))/2;
			}
		});
				
		changeDistance.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				projectionPanel.distance = Integer.parseInt(JOptionPane.showInputDialog("Podaj odleg�o�� obserwatora:", 200));
			}
		});
				
		fileMenu.add(save);
		fileMenu.add(load);
		colorMenu.add(changeBG);
		colorMenu.add(changeLine);
		colorMenu.add(changeThickness);
		optionsMenu.add(changeSize);
		optionsMenu.add(changeDistance);
		
		add(fileMenu);
		add(colorMenu);
		add(optionsMenu);
	}
}