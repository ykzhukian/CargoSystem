package asgn2GUI;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import asgn2Codes.ContainerCode;
import asgn2Exceptions.InvalidCodeException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a JTextArea in which textual components are laid out to represent the cargo manifest.
 *
 * @author CAB302 Than Nhat Huy Nguyen - 8781583
 */
public class CargoTextArea extends JTextArea {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 50;
    private static final int HSPACE = 10;
    private static final int VSPACE = 20;

    private final CargoManifest cargo;

    private ContainerCode toFind;

    /**
     * Constructor initialises the JTextArea.
     *
     * @param cargo he <code>CargoManifest</code> on which the text area is based 
     * 
     */
    public CargoTextArea(CargoManifest cargo) {
        setFont(new Font("Calibri", Font.PLAIN, 12));
        setName("Cargo Text Area");
        setSize(WIDTH, HEIGHT);
        setEditable(false);
        this.cargo = cargo;
    }

    /**
     * Highlights a container.
     *
     * @param code ContainerCode to highlight.
     * @throws InvalidCodeException 
     */
    public void setToFind(ContainerCode code) {
    	//Kian
    	toFind = code;
    	updateDisplay();
    }

    /**
     * Outputs the container representation from the cargo manifest on the text area.
     *
     */
    public void updateDisplay() {
    	//Kian
    	setText(cargo.toString(toFind));
    }
}
