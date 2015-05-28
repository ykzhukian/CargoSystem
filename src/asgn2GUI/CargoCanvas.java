package asgn2GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a JPanel in which graphical components are laid out to represent the cargo manifest.
 *
 * @author CAB302 Yunkai (Kian) Zhu n9253921
 */
public class CargoCanvas extends JPanel {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 50;
    private static final int HSPACE = 10;
    private static final int VSPACE = 20;

    private final CargoManifest cargo;

    private ContainerCode toFind;


    /**
     * Constructor
     *
     * @param cargo The <code>CargoManifest</code> on which the graphics is based so that the
     * number of stacks and height can be adhered to.
     */
    public CargoCanvas(CargoManifest cargo) {
        this.cargo = cargo;
        setName("Canvas");
    }

    /**
     * Highlights a container.
     *
     * @param code ContainerCode to highlight.
     */
    public void setToFind(ContainerCode code) {
    	// Kian
    	toFind = code;
    }

    /**
     * Draws the containers in the cargo manifest on the Graphics context of the Canvas.
     *
     * @param g The Graphics context to draw on.
     */
    @Override
    public void paint(Graphics g) {
    	//Kian 
    	super.paintComponents(g);
    	
    	g.setColor(Color.DARK_GRAY);
		g.fillRect(50, 0, 100, 15);
		
		g.setColor(new Color(188, 91, 73)); // red
		g.fillRect(200, 0, 10, 10);
		
		g.setColor(new Color(232, 183, 78)); // yellow
		g.fillRect(300, 0, 10, 10);
		
		g.setColor(new Color(90, 131, 166)); // blue
		g.fillRect(380, 0, 10, 10);
		
		g.setColor(new Color(122, 122, 122)); // gray
		g.fillRect(500, 0, 10, 10);
		
		g.setColor(Color.WHITE);
		g.drawString("The Bridge", 65, 11);
		g.setColor(Color.BLACK);
		g.drawString("Dangerous", 220, 10);
		g.drawString("General", 320, 10);
		g.drawString("Refrigerated", 400, 10);
		g.drawString("Find", 520, 10);
    	
    	for (int i = 0; i < cargo.getStackNumber(); i++) {
    		g.setColor(Color.GRAY);
    		g.fillRect(3, i*60 + 20, 3, 50);
    		try {
				FreightContainer[] containers = cargo.toArray(i);
				for (int j = 0; j < containers.length; j++) {
					drawContainer(g, containers[j], j*110 + 20, i*60 + 20);
				}
			} catch (ManifestException e) { }
    		
		}
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }

    /**
     * Draws a container at the given location.
     *
     * @param g The Graphics context to draw on.
     * @param container The container to draw - the type determines the colour and ContainerCode is
     *            used to identify the drawn Rectangle.
     * @param x The x location for the Rectangle.
     * @param y The y location for the Rectangle.
     */
    private void drawContainer(Graphics g, FreightContainer container, int x, int y) {
    	//Kian 
    	//Feel free to use some other method structure here, but this is the basis for the demo. 
    	//Obviously you need the graphics context and container as parameters. 
    	//But you can also use images if you wish. 
    	if (container.getType().equals("DangerousGoodsContainer")) {
    		g.setColor(new Color(188, 91, 73));// red
        	g.fillRect(x, y, 100, 50);
    	} else if (container.getType().equals("GeneralGoodsContainer")) {
    		g.setColor(new Color(232, 183, 78));	// yellow
        	g.fillRect(x, y, 100, 50);
    	} else if (container.getType().equals("RefrigeratedContainer")) {
    		g.setColor(new Color(90, 131, 166));  // blue
        	g.fillRect(x, y, 100, 50);
    	}
    	
    	if (toFind != null) {
    		if (container.getCode().toString().equals(toFind.toString())) {
        		g.setColor(new Color(122, 122, 122)); // gray
            	g.fillRect(x, y, 100, 50);
        	}
    	}
    	g.setColor(Color.WHITE);
    	g.drawString(container.getCode().toString(), x + 5, y + 20);
    }
}
