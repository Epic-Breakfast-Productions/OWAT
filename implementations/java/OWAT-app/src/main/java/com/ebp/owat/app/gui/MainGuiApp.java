package com.ebp.owat.app.gui;

import com.ebp.owat.app.config.Globals;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 * Main class for the GUI.
 * <p>
 * Partially generated using Intellij.
 * Guides:
 * https://stackoverflow.com/questions/12775170/how-do-i-create-a-new-swing-app-in-intellij-idea-community-edition
 * https://stackoverflow.com/questions/3899525/how-to-use-gui-form-created-in-itellij-idea
 */
public class MainGuiApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainGuiApp.class);

	private JPanel mainPanel;
	private JButton processStartButton;
	private JProgressBar processProgressBar;
	private JTabbedPane modeSelect;
	private JPanel scramblePanel;
	private JSplitPane scrambleOptionsPane;
	private JPanel scrambleDataInputPanel;
	private JLabel inputsScrambleTitle;
	private JTabbedPane inputModeScramble;
	private JTextArea scrambleDataDirectInput;
	private JTextField scrambleDataFileInput;
	private JButton chooseScrambleDataFileButton;
	private JPanel scrambleDataOutputPanel;
	private JLabel outputsScrambleTitle;
	private JTextField outputScrambledDataFile;
	private JButton chooseScrambledDataOutputFileButton;
	private JTextField keyFileOutput;
	private JButton chooseKeyOutputFileButton;
	private JPanel deScramblePanel;
	private JSplitPane deScrambleOptionsPane;
	private JPanel enterScrambledDataAndKeyPanel;
	private JTextField keyFileInput;
	private JButton chooseDeScrambleKeyFileButton;
	private JTabbedPane deScrambleDataInputModePane;
	private JTextArea scrambledDataDirectInput;
	private JTextField scrambledDataFileInput;
	private JButton chooseScrambledDataFileInputButton;
	private JPanel enterDeScrambledDataOutputPanel;
	private JTabbedPane deScrambleDataOutputModePane;
	private JTextField deScrambledDataOutputFileInput;
	private JButton chooseDeScrambledDataOutputFileButton;
	private JPanel infoPanel;
	private JTextPane infoPane;
	private JTabbedPane outputScrambledDataModePane;
	private JTextArea scrambledDataDirectOutput;
	private JTabbedPane outputScrambleKeyModePane;
	private JTextArea keyDirectOutput;
	private JTextArea deScrambledDirectOutput;
	private JTabbedPane deScrambleKeyInputModePane;
	private JTextArea keyDirectInput;
	private JButton resetButton;

	private JFrame frame;

	private static final String TITLE_FORMAT = "%s v%s %s";

	private static final String appTitle = String.format(
		TITLE_FORMAT,
		Globals.getProp(Globals.PropertyKey.APP_NAME_PROP_KEY),
		Globals.getProp(Globals.PropertyKey.APP_VERSION_PROP_KEY),
		Globals.getProp(Globals.PropertyKey.APP_VERSION_NAME_PROP_KEY)
	);

	/* ****************************************************************
	 * Helpful Workers
	 ******************************************************************/

	private void showMessage(int type, String title, String message) {
		LOGGER.info("Displaying message: {}", message);
		JOptionPane.showMessageDialog(this.mainPanel, "Clearing all inputs.", title, type);
	}

	private void showMessage(String message) {
		this.showMessage(INFORMATION_MESSAGE, "Message", message);
	}

	private boolean confirmActon(String action) {
		LOGGER.debug("Confirming action with user: {}", action);
		boolean returned = 0 == JOptionPane.showConfirmDialog(this.mainPanel, action + "\nAre you sure?", "Confirm Action", JOptionPane.OK_CANCEL_OPTION);
		LOGGER.debug("User returned: {}", returned);
		return returned;
	}

	private String chooseFile(String title, int mode, String extention) {
		LOGGER.debug("Prompting user for file: {}", title);
		FileDialog dialog = new FileDialog(this.frame, title, mode);

		dialog.setFile(extention);
		dialog.setVisible(true);
		dialog.setMultipleMode(false);

		String dir = dialog.getDirectory();
		String returned = dialog.getFile();

		if (returned == null) {
			LOGGER.debug("User cancelled selection.");
			return "";
		}
		LOGGER.debug("User chose: {}{}", dir, returned);
		return dir + returned;
	}

	/* ****************************************************************
	 * Clear input methods.
	 ******************************************************************/

	private void resetProgress() {
		this.resetButton.setEnabled(true);
		this.processStartButton.setEnabled(false);
		this.processStartButton.setText("Go");
		this.processProgressBar.setValue(0);
		this.processProgressBar.setString("");
	}

	private void resetScrambleInputs() {
		this.scrambleDataDirectInput.setText("");
		this.scrambleDataFileInput.setText("");
	}

	private void resetScrambleKeyOutputs() {
		this.keyDirectOutput.setText("");
		this.keyFileOutput.setText("");
	}

	private void resetScrambleDataOutputs() {
		this.scrambledDataDirectOutput.setText("");
		this.outputScrambledDataFile.setText("");
	}

	private void resetScrambleOutputs() {
		this.resetScrambleKeyOutputs();
		this.resetScrambleDataOutputs();
	}

	private void resetScramble() {
		this.resetScrambleInputs();
		this.resetScrambleOutputs();
	}

	private void resetDeScrambleKeyInputs() {
		this.keyDirectInput.setText("");
		this.keyFileInput.setText("");
	}

	private void resetDeScrambleDataInputs() {
		this.scrambledDataDirectInput.setText("");
		this.scrambledDataFileInput.setText("");
	}

	private void resetDeScrambleInputs() {
		this.resetDeScrambleKeyInputs();
		this.resetDeScrambleDataInputs();
	}

	private void resetDeScrambleOutputs() {
		this.deScrambledDirectOutput.setText("");
		this.deScrambledDataOutputFileInput.setText("");
	}

	private void resetDeScramble() {
		this.resetDeScrambleInputs();
		this.resetDeScrambleOutputs();
	}

	private void resetAllInputs() {
		LOGGER.debug("Resetting all inputs.");
		this.resetProgress();
		this.resetScramble();
		this.resetDeScramble();
	}

	/* ****************************************************************
	 * Validate input methods.
	 ******************************************************************/

	private void validateForGO() {
		boolean goodToGO = false;
		//TODO:: based on what tabs are open, validate that the inputs are valid.
	}

	/* ****************************************************************
	 * Form enabler/disablers
	 ******************************************************************/

	//TODO:: methods to enable/disable forms and switching between forms while scrambling/descrambling happens.

	/* ****************************************************************
	 * Run scramble/descramble
	 ******************************************************************/

	//TODO:: run a scramble/descramble

	/* ****************************************************************
	 * Setup
	 ******************************************************************/

	public MainGuiApp(JFrame frame) {
		this.frame = frame;
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//TODO:: not working/waiting for double click
				if(e.getClickCount() == 2){
					if (confirmActon("Reset All Forms")) {
						resetAllInputs();
					}
				}

				if (modeSelect.getSelectedComponent() == scramblePanel) {
					if (confirmActon("Reset Scramble Form")) {
						resetScramble();
					}
				}
				if (modeSelect.getSelectedComponent() == deScramblePanel) {
					if (confirmActon("Reset DeScramble Form")) {
						resetDeScramble();
					}
				}
				super.mouseClicked(e);
			}

		});
		chooseScrambleDataFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrambleDataFileInput.setText(chooseFile("Choose a file to scramble", FileDialog.LOAD, "*"));
				super.mouseClicked(e);
			}
		});
		chooseKeyOutputFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyFileOutput.setText(chooseFile("Choose where to save the data key", FileDialog.SAVE, "*.obfk"));
				super.mouseClicked(e);
			}
		});
		chooseScrambledDataOutputFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				outputScrambledDataFile.setText(chooseFile("Choose where to save the scrambled data", FileDialog.SAVE, "*.obf"));
				super.mouseClicked(e);
			}
		});
		chooseDeScrambleKeyFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				keyFileInput.setText(chooseFile("Choose key file to use", FileDialog.LOAD, "*.obfk"));
				super.mouseClicked(e);
			}
		});
		chooseScrambledDataFileInputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrambledDataFileInput.setText(chooseFile("Choose the scrambled data file", FileDialog.LOAD, "*.obf"));
				super.mouseClicked(e);
			}
		});
		chooseDeScrambledDataOutputFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deScrambledDataOutputFileInput.setText(chooseFile("Choose where to save the descrambled data", FileDialog.SAVE, "*"));
				super.mouseClicked(e);
			}
		});
	}

	public static void main(String[] args) {
		LOGGER.info("Starting GUI.");
		JFrame frame = new JFrame(appTitle);
		frame.setContentPane(new MainGuiApp(frame).mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
		mainPanel.setMinimumSize(new Dimension(500, 500));
		mainPanel.setName("");
		mainPanel.setPreferredSize(new Dimension(500, 500));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
		mainPanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 55), new Dimension(-1, 55), new Dimension(-1, 55), 0, false));
		processStartButton = new JButton();
		processStartButton.setEnabled(false);
		processStartButton.setText("Go");
		panel1.add(processStartButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		processProgressBar = new JProgressBar();
		processProgressBar.setString("");
		processProgressBar.setStringPainted(true);
		panel1.add(processProgressBar, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 50), new Dimension(-1, 50), new Dimension(-1, 50), 0, false));
		final Spacer spacer1 = new Spacer();
		panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(2, -1), new Dimension(2, -1), new Dimension(2, -1), 0, false));
		final Spacer spacer2 = new Spacer();
		panel1.add(spacer2, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(2, -1), new Dimension(2, -1), new Dimension(2, -1), 0, false));
		resetButton = new JButton();
		resetButton.setText("Reset");
		panel1.add(resetButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		modeSelect = new JTabbedPane();
		mainPanel.add(modeSelect, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		scramblePanel = new JPanel();
		scramblePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		scramblePanel.setMaximumSize(new Dimension(-1, -1));
		scramblePanel.setMinimumSize(new Dimension(-1, -1));
		scramblePanel.setPreferredSize(new Dimension(-1, -1));
		modeSelect.addTab("Scramble", null, scramblePanel, "Scramble some data");
		scrambleOptionsPane = new JSplitPane();
		scramblePanel.add(scrambleOptionsPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		scrambleDataInputPanel = new JPanel();
		scrambleDataInputPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		scrambleDataInputPanel.setMaximumSize(new Dimension(250, -1));
		scrambleDataInputPanel.setMinimumSize(new Dimension(250, -1));
		scrambleDataInputPanel.setPreferredSize(new Dimension(250, -1));
		scrambleOptionsPane.setLeftComponent(scrambleDataInputPanel);
		inputsScrambleTitle = new JLabel();
		Font inputsScrambleTitleFont = this.$$$getFont$$$(null, Font.BOLD, 20, inputsScrambleTitle.getFont());
		if (inputsScrambleTitleFont != null) inputsScrambleTitle.setFont(inputsScrambleTitleFont);
		inputsScrambleTitle.setText("Inputs");
		scrambleDataInputPanel.add(inputsScrambleTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		inputModeScramble = new JTabbedPane();
		scrambleDataInputPanel.add(inputModeScramble, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel2.setMaximumSize(new Dimension(-1, -1));
		panel2.setMinimumSize(new Dimension(-1, -1));
		panel2.setPreferredSize(new Dimension(-1, -1));
		panel2.setToolTipText("");
		inputModeScramble.addTab("Enter Text", null, panel2, "This is to enter your own data to be scrambled.");
		scrambleDataDirectInput = new JTextArea();
		Font scrambleDataDirectInputFont = this.$$$getFont$$$("Monospaced", -1, -1, scrambleDataDirectInput.getFont());
		if (scrambleDataDirectInputFont != null) scrambleDataDirectInput.setFont(scrambleDataDirectInputFont);
		scrambleDataDirectInput.setLineWrap(true);
		panel2.add(scrambleDataDirectInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		final JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		inputModeScramble.addTab("File", null, panel3, "This is to specify a file to be scrambled.");
		scrambleDataFileInput = new JTextField();
		panel3.add(scrambleDataFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer3 = new Spacer();
		panel3.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseScrambleDataFileButton = new JButton();
		chooseScrambleDataFileButton.setText("Choose File");
		panel3.add(chooseScrambleDataFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label1 = new JLabel();
		label1.setText("Data to Scramble:");
		scrambleDataInputPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		scrambleDataOutputPanel = new JPanel();
		scrambleDataOutputPanel.setLayout(new GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
		scrambleDataOutputPanel.setMinimumSize(new Dimension(-1, -1));
		scrambleDataOutputPanel.setPreferredSize(new Dimension(-1, 238));
		scrambleOptionsPane.setRightComponent(scrambleDataOutputPanel);
		outputsScrambleTitle = new JLabel();
		Font outputsScrambleTitleFont = this.$$$getFont$$$(null, Font.BOLD, 20, outputsScrambleTitle.getFont());
		if (outputsScrambleTitleFont != null) outputsScrambleTitle.setFont(outputsScrambleTitleFont);
		outputsScrambleTitle.setText("Outputs");
		scrambleDataOutputPanel.add(outputsScrambleTitle, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label2 = new JLabel();
		label2.setText("Key:");
		scrambleDataOutputPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label3 = new JLabel();
		label3.setText("Scrambled data output to:");
		scrambleDataOutputPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		outputScrambledDataModePane = new JTabbedPane();
		scrambleDataOutputPanel.add(outputScrambledDataModePane, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		final JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambledDataModePane.addTab("Direct output", panel4);
		scrambledDataDirectOutput = new JTextArea();
		scrambledDataDirectOutput.setEditable(false);
		Font scrambledDataDirectOutputFont = this.$$$getFont$$$("Monospaced", -1, -1, scrambledDataDirectOutput.getFont());
		if (scrambledDataDirectOutputFont != null) scrambledDataDirectOutput.setFont(scrambledDataDirectOutputFont);
		scrambledDataDirectOutput.setLineWrap(true);
		scrambledDataDirectOutput.setText("");
		panel4.add(scrambledDataDirectOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		final JPanel panel5 = new JPanel();
		panel5.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambledDataModePane.addTab("File", panel5);
		chooseScrambledDataOutputFileButton = new JButton();
		chooseScrambledDataOutputFileButton.setText("Choose File");
		panel5.add(chooseScrambledDataOutputFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer4 = new Spacer();
		panel5.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		outputScrambledDataFile = new JTextField();
		outputScrambledDataFile.setText("");
		panel5.add(outputScrambledDataFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		outputScrambleKeyModePane = new JTabbedPane();
		scrambleDataOutputPanel.add(outputScrambleKeyModePane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		final JPanel panel6 = new JPanel();
		panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambleKeyModePane.addTab("Direct Output", panel6);
		keyDirectOutput = new JTextArea();
		keyDirectOutput.setEditable(false);
		Font keyDirectOutputFont = this.$$$getFont$$$("Monospaced", -1, -1, keyDirectOutput.getFont());
		if (keyDirectOutputFont != null) keyDirectOutput.setFont(keyDirectOutputFont);
		keyDirectOutput.setLineWrap(true);
		panel6.add(keyDirectOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		final JPanel panel7 = new JPanel();
		panel7.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambleKeyModePane.addTab("File", panel7);
		keyFileOutput = new JTextField();
		panel7.add(keyFileOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer5 = new Spacer();
		panel7.add(spacer5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseKeyOutputFileButton = new JButton();
		chooseKeyOutputFileButton.setText("Choose File");
		panel7.add(chooseKeyOutputFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		deScramblePanel = new JPanel();
		deScramblePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		modeSelect.addTab("Descramble", null, deScramblePanel, "Descramble previously scrambled data");
		deScrambleOptionsPane = new JSplitPane();
		deScramblePanel.add(deScrambleOptionsPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		enterScrambledDataAndKeyPanel = new JPanel();
		enterScrambledDataAndKeyPanel.setLayout(new GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleOptionsPane.setLeftComponent(enterScrambledDataAndKeyPanel);
		final JLabel label4 = new JLabel();
		Font label4Font = this.$$$getFont$$$(null, Font.BOLD, 20, label4.getFont());
		if (label4Font != null) label4.setFont(label4Font);
		label4.setText("Inputs");
		enterScrambledDataAndKeyPanel.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label5 = new JLabel();
		label5.setText("Key:");
		enterScrambledDataAndKeyPanel.add(label5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		deScrambleDataInputModePane = new JTabbedPane();
		enterScrambledDataAndKeyPanel.add(deScrambleDataInputModePane, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		final JPanel panel8 = new JPanel();
		panel8.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataInputModePane.addTab("Direct Input", panel8);
		scrambledDataDirectInput = new JTextArea();
		Font scrambledDataDirectInputFont = this.$$$getFont$$$("Monospaced", -1, -1, scrambledDataDirectInput.getFont());
		if (scrambledDataDirectInputFont != null) scrambledDataDirectInput.setFont(scrambledDataDirectInputFont);
		scrambledDataDirectInput.setLineWrap(true);
		panel8.add(scrambledDataDirectInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		final JPanel panel9 = new JPanel();
		panel9.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataInputModePane.addTab("From File", panel9);
		scrambledDataFileInput = new JTextField();
		panel9.add(scrambledDataFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer6 = new Spacer();
		panel9.add(spacer6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseScrambledDataFileInputButton = new JButton();
		chooseScrambledDataFileInputButton.setText("Choose File");
		panel9.add(chooseScrambledDataFileInputButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label6 = new JLabel();
		label6.setText("Scrambled Data:");
		enterScrambledDataAndKeyPanel.add(label6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		deScrambleKeyInputModePane = new JTabbedPane();
		enterScrambledDataAndKeyPanel.add(deScrambleKeyInputModePane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		final JPanel panel10 = new JPanel();
		panel10.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleKeyInputModePane.addTab("Direct Input", panel10);
		keyDirectInput = new JTextArea();
		Font keyDirectInputFont = this.$$$getFont$$$("Monospaced", -1, -1, keyDirectInput.getFont());
		if (keyDirectInputFont != null) keyDirectInput.setFont(keyDirectInputFont);
		keyDirectInput.setLineWrap(true);
		panel10.add(keyDirectInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		final JPanel panel11 = new JPanel();
		panel11.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleKeyInputModePane.addTab("File", panel11);
		keyFileInput = new JTextField();
		panel11.add(keyFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer7 = new Spacer();
		panel11.add(spacer7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseDeScrambleKeyFileButton = new JButton();
		chooseDeScrambleKeyFileButton.setText("Choose File");
		panel11.add(chooseDeScrambleKeyFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		enterDeScrambledDataOutputPanel = new JPanel();
		enterDeScrambledDataOutputPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleOptionsPane.setRightComponent(enterDeScrambledDataOutputPanel);
		final JLabel label7 = new JLabel();
		Font label7Font = this.$$$getFont$$$(null, Font.BOLD, 20, label7.getFont());
		if (label7Font != null) label7.setFont(label7Font);
		label7.setText("Outputs");
		enterDeScrambledDataOutputPanel.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		deScrambleDataOutputModePane = new JTabbedPane();
		enterDeScrambledDataOutputPanel.add(deScrambleDataOutputModePane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		final JPanel panel12 = new JPanel();
		panel12.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataOutputModePane.addTab("Display", panel12);
		deScrambledDirectOutput = new JTextArea();
		deScrambledDirectOutput.setEditable(false);
		Font deScrambledDirectOutputFont = this.$$$getFont$$$("Monospaced", -1, -1, deScrambledDirectOutput.getFont());
		if (deScrambledDirectOutputFont != null) deScrambledDirectOutput.setFont(deScrambledDirectOutputFont);
		deScrambledDirectOutput.setLineWrap(true);
		panel12.add(deScrambledDirectOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		final JPanel panel13 = new JPanel();
		panel13.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataOutputModePane.addTab("To File", panel13);
		deScrambledDataOutputFileInput = new JTextField();
		panel13.add(deScrambledDataOutputFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer8 = new Spacer();
		panel13.add(spacer8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseDeScrambledDataOutputFileButton = new JButton();
		chooseDeScrambledDataOutputFileButton.setText("Choose File");
		panel13.add(chooseDeScrambledDataOutputFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		infoPanel.setToolTipText("Info about this program");
		modeSelect.addTab("*Info*", null, infoPanel, "Information about this program");
		infoPane = new JTextPane();
		infoPane.setEditable(false);
		infoPane.setText("OWAT-J\n\nJava implementation of the OWAT protocol.");
		infoPanel.add(infoPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
	}

	/**
	 * @noinspection ALL
	 */
	private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
		if (currentFont == null) return null;
		String resultName;
		if (fontName == null) {
			resultName = currentFont.getName();
		} else {
			Font testFont = new Font(fontName, Font.PLAIN, 10);
			if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
				resultName = fontName;
			} else {
				resultName = currentFont.getName();
			}
		}
		return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
	}

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return mainPanel;
	}
}
