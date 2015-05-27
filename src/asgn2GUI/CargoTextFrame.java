package asgn2GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.InvalidCodeException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * The main window for the Cargo Manifest Text application.
 *
 * @author CAB302 Than Nhat Huy Nguyen - 8781583
 */
public class CargoTextFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnNewManifest;

    private CargoTextArea canvas;

    private JPanel pnlControls;
    private JPanel pnlDisplay;

    private CargoManifest cargo;

    /**
     * Constructs the GUI.
     *
     * @param title The frame title to use.
     * @throws HeadlessException from JFrame.
     */
    public CargoTextFrame(String frameTitle) throws HeadlessException {
        super(frameTitle);
        constructorHelper();
        disableButtons();
        setVisible(true);
        setLayout(new BorderLayout());

        
    }

    /**
     * Initialises the container display area.
     *
     * @param cargo The <code>CargoManifest</code> instance containing necessary state for display.
     */
    private void setCanvas(CargoManifest cargo) {
        if (canvas != null) {
            pnlDisplay.remove(canvas);
            pnlDisplay.repaint();
        }
        if (cargo == null) {
            disableButtons();
        } else {
            canvas = new CargoTextArea(cargo);
            //Kian
            canvas.setSize(500, 250);
            pnlDisplay = new JPanel();
            pnlDisplay.add(canvas);
            add(pnlDisplay, BorderLayout.WEST);
            enableButtons();
            canvas.updateDisplay();
        } 
    }

    /**
     * Enables buttons for user interaction.
     */
    private void enableButtons() {
    	//Kian 
    	btnNewManifest.setEnabled(true);
    	btnFind.setEnabled(true);
    	btnLoad.setEnabled(true);
    	btnUnload.setEnabled(true);
    }

    /**
     * Disables buttons from user interaction.
     */
    private void disableButtons() {
    	//Kian 
    	btnFind.setEnabled(false);
    	btnLoad.setEnabled(false);
    	btnUnload.setEnabled(false);
    }

    /**
     * Initialises and lays out GUI components.
     */
    private void constructorHelper() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnLoad = createButton("Load", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                    	resetCanvas();
                        CargoTextFrame.this.doLoad();
                    	
                    }
                };
                SwingUtilities.invokeLater(doRun);
            }
        });
        btnUnload = createButton("Unload", new ActionListener() {
        	//Kian 
        	@Override
            public void actionPerformed(ActionEvent e) {
                Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                    	resetCanvas();
                        CargoTextFrame.this.doUnload();
                    	
                    }
                };
                SwingUtilities.invokeLater(doRun);
            }
        });
        btnFind = createButton("Find", new ActionListener() {
        	//Kian 
        	@Override
            public void actionPerformed(ActionEvent e) {
                Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                        CargoTextFrame.this.doFind();
                    }
                };
                SwingUtilities.invokeLater(doRun);
            }
        });
        btnNewManifest = createButton("New Manifest", new ActionListener() {
        	//Kian 
        	@Override
            public void actionPerformed(ActionEvent e) {
                Runnable doRun = new Runnable() {
                    @Override
                    public void run() {
                    	CargoTextFrame.this.setNewManifest();
                        CargoTextFrame.this.setCanvas(cargo);
                        
                        
                    }
                };
                SwingUtilities.invokeLater(doRun);
            }
        });

      //Kian 
        pnlControls = createControlPanel();
        pnlControls.setLayout(new FlowLayout());
        add(pnlControls, BorderLayout.SOUTH);
        repaint();
    }

    /**
     * Creates a JPanel containing user controls (buttons).
     *
     * @return User control panel.
     */
    private JPanel createControlPanel() {
    	//Kian 
    	JPanel controls = new JPanel();
    	controls.add(btnNewManifest);
    	controls.add(btnLoad);
    	controls.add(btnUnload);
    	controls.add(btnFind);
    	
    	return controls;
    }

    /**
     * Factory method to create a JButton and add its ActionListener.
     *
     * @param name The text to display and use as the component's name.
     * @param btnListener The ActionListener to add.
     * @return A named JButton with ActionListener added.
     */
    private JButton createButton(String name, ActionListener btnListener) {
        JButton btn = new JButton(name);
        btn.setName(name);
        btn.addActionListener(btnListener);
        return btn;
    }

    /**
     * Initiate the New Manifest dialog which sets the instance of CargoManifest to work with.
     */
    private void setNewManifest() {
		//Kian 
    	try {
    		CargoManifest newManifest = ManifestDialog.showDialog(this);
        	cargo = newManifest;
        	if ( cargo.toString() != null ) {
        		enableButtons();
        	}
        	redraw();
		} catch (Exception e) { }
    	
    }
    
    /**
     * Turns off container highlighting when an action other than Find is initiated.
     */
    private void resetCanvas() {
    	//Kian 
    	canvas.setToFind(null);
    }

    /**
     * Initiates the Load Container dialog.
     */
    private void doLoad() {
    	//Kian 
    	try {
    		FreightContainer newContainer = LoadContainerDialog.showDialog(this);
    		try {
    			cargo.loadContainer(newContainer);
    			redraw();
    		} catch (ManifestException e) {
    			JOptionPane.showMessageDialog(this, e.getMessage());
    		}
		} catch (Exception e) { }
    	
    }

    /**
     * Initiates the Unload Container dialog.
     */
    private void doUnload() {
    	//Kian 
    	try {
    		ContainerCode newContainerCode = ContainerCodeDialog.showDialog(this);
        	try {
    			cargo.unloadContainer(newContainerCode);
    			redraw();
    		} catch (ManifestException e) {
    			JOptionPane.showMessageDialog(this, e.getMessage());
    		}
		} catch (Exception e) { }
    	
    }

    /**
     * Initiates the Find Container dialog.
     */
    private void doFind() {
    	//Kian 
    	try {
        	ContainerCode newContainerCode = ContainerCodeDialog.showDialog(this);
    		canvas.setToFind(newContainerCode);
		} catch (Exception e) { }

    }

    /**
     * 
     * Updates the display area.
     *
     */
    private void redraw() {
    	//Kian 
    	canvas.updateDisplay();
    }
}
