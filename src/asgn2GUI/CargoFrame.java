package asgn2GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * The main window for the Cargo Manifest graphics application.
 *
 * @author CAB302 Yunkai (Kian) Zhu n9253921
 */
public class CargoFrame extends JFrame {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;

    private JButton btnLoad;
    private JButton btnUnload;
    private JButton btnFind;
    private JButton btnNewManifest;

    private CargoCanvas canvas;

    private JPanel pnlControls;
    private JPanel pnlDisplay;

    private CargoManifest cargo;
    

    /**
     * Constructs the GUI.
     *
     * @param title The frame title to use.
     * @throws HeadlessException from JFrame.
     */
    public CargoFrame(String title) throws HeadlessException {
        super(title);

        constructorHelper();
        disableButtons();
        redraw();
        setVisible(true);
    }

    /**
     * Initialises the container display area.
     *
     * @param cargo The <code>CargoManifest</code> instance containing necessary state for display.
     */
    private void setCanvas(CargoManifest cargo) {
        if (canvas != null) {
            pnlDisplay.remove(canvas);
        }
        if (cargo == null) {
            disableButtons();
        } else {
            canvas = new CargoCanvas(cargo);
          //Kian    
            pnlDisplay = new JPanel();
            pnlDisplay.add(canvas);
            add(pnlDisplay, BorderLayout.WEST);
            enableButtons();
            
        }
        redraw();
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
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.doLoad();
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
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.doUnload();
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
                        CargoFrame.this.resetCanvas();
                        CargoFrame.this.doFind();
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
                        CargoFrame.this.setNewManifest();
                        setCanvas(cargo);
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
    		try {
    			cargo = newManifest;
            	if ( cargo.toString() != null ) {
            		enableButtons();
            	}
    		} catch(Exception e){ }
		} catch (Exception e) { }
    }

    /**
     * Turns off container highlighting when an action other than Find is initiated.
     */
    private void resetCanvas() {
    	//Kian   
    	canvas.setToFind(null);
    	redraw();
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
    			JOptionPane.showMessageDialog(this, e.getMessage(),  "Invalid Loading", JOptionPane.ERROR_MESSAGE);
    		}
		} catch (Exception e) { }
    }

    private void doUnload() {
    	//Kian 
    	try {
    		ContainerCode newContainerCode = ContainerCodeDialog.showDialog(this);
        	try {
    			cargo.unloadContainer(newContainerCode);
    			redraw();
    		} catch (ManifestException e) {
    			JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid UnLoading", JOptionPane.ERROR_MESSAGE);
    		}
		} catch (Exception e) { }
    }

    private void doFind() {
    	//Kian 
    	try {
        	ContainerCode newContainerCode = ContainerCodeDialog.showDialog(this);
    		canvas.setToFind(newContainerCode);
    		redraw();
		} catch (Exception e) { }
    }

    private void redraw() {
        invalidate();
        validate();
        repaint();
    }
}
