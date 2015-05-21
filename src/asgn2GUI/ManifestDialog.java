package asgn2GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.fest.reflect.field.StaticFieldName;

import asgn2Exceptions.CargoException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a dialog box allowing the user to enter parameters for a new <code>CargoManifest</code>.
 *
 * @author CAB302
 */
public class ManifestDialog extends AbstractDialog {

    private static final int HEIGHT = 150;
    private static final int WIDTH = 250;

    private JTextField txtNumStacks;
    private JTextField txtMaxHeight;
    private JTextField txtMaxWeight;

    private CargoManifest manifest;

    /**
     * Constructs a modal dialog box that gathers information required for creating a cargo
     * manifest.
     *
     * @param parent the frame which created this dialog box.
     */
    private ManifestDialog(JFrame parent) {
        super(parent, "Create Manifest", WIDTH, HEIGHT);
        setName("New Manifest");
        setResizable(false);
        manifest = null;
    }

    /**
     * @see AbstractDialog.createContentPanel()
     */
    @Override
    protected JPanel createContentPanel() {

        txtNumStacks = createTextField(8, "Number of Stacks");
        txtMaxHeight = createTextField(8, "Maximum Height");
        txtMaxWeight = createTextField(8, "Maximum Weight");
        
        JPanel toReturn = new JPanel();
        toReturn.setLayout(new GridBagLayout());
       
        //Implementation here
        JLabel numStacks = new JLabel("Stacks Number:");
        JLabel maxHeight = new JLabel("Maximum Height:");
        JLabel maxWeight = new JLabel("Maximum Weight:");
        
        GridBagConstraints constraints = new GridBagConstraints();
        addToPanel(toReturn, numStacks, constraints, 1, 1, 10, 1);
        addToPanel(toReturn, maxHeight, constraints, 1, 3, 10, 1);
        addToPanel(toReturn, maxWeight, constraints, 1, 5, 10, 1);
        addToPanel(toReturn, txtNumStacks, constraints, 15, 1, 8, 1);
        addToPanel(toReturn, txtMaxHeight, constraints, 15, 3, 8, 1);
        addToPanel(toReturn, txtMaxWeight, constraints, 15, 5, 8, 1);
        return toReturn;
    }

    /*
     * Factory method to create a named JTextField
     */
    private JTextField createTextField(int numColumns, String name) {
        JTextField text = new JTextField();
        text.setColumns(numColumns);
        text.setName(name);
        return text;
    }

    @Override
    protected boolean dialogDone() {
        //Implementation here 
    	//Parameters and building a new manifest, all the while handling exceptions 
    	String numStacks = txtNumStacks.getText();
        String maxHeight = txtMaxHeight.getText();
        String maxWeight = txtMaxWeight.getText();
        try {
			manifest = new CargoManifest(Integer.parseInt(numStacks), Integer.parseInt(maxHeight), Integer.parseInt(maxWeight));
			return true;
		} catch (NumberFormatException e) {
			return false;
		} catch (ManifestException e) {
			return false;
		}
    }

    /**
     * Shows the <code>ManifestDialog</code> for user interaction.
     *
     * @param parent - The parent <code>JFrame</code> which created this dialog box.
     * @return a <code>CargoManifest</code> instance with valid values.
     */
    public static CargoManifest showDialog(JFrame parent) {
        //Implementation again
    	ManifestDialog manifestDialog = new ManifestDialog(parent);
    	manifestDialog.setVisible(true);
//    	while (!manifestDialog.dialogDone()) {
//    		manifestDialog.setVisible(true);
//    	} 
//    	manifestDialog.setVisible(false);
    	return manifestDialog.manifest;
    }
}
