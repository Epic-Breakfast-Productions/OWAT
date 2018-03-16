package com.ebp.owat.app.gui;

import com.ebp.owat.app.InputValidator;
import com.ebp.owat.app.config.Globals;
import com.ebp.owat.app.runner.DeScrambleRunner;
import com.ebp.owat.app.runner.OwatRunner;
import com.ebp.owat.app.runner.ScrambleRunner;
import com.ebp.owat.app.runner.utils.Step;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.*;

import static javax.swing.JOptionPane.*;

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
	private JTabbedPane inputModeScrambleSelect;
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
	private JTabbedPane outputScrambledDataModeSelectPane;
	private JTextArea scrambledDataDirectOutput;
	private JTabbedPane outputScrambleKeyModeSelectPane;
	private JTextArea keyDirectOutput;
	private JTextArea deScrambledDirectOutput;
	private JTabbedPane deScrambleKeyInputModePane;
	private JTextArea keyDirectInput;
	private JButton resetButton;
	private JPanel scrambleDataDirectInputPane;
	private JPanel scrambleDataFileInputPane;
	private JPanel scrambleKeyFileOutputPane;
	private JPanel scrambledDataFileOutputPane;
	private JPanel scrambledDataDirectOutputPane;
	private JPanel scrambleKeyDirectOutputPane;
	private JPanel scrambleKeyDirectInputPane;
	private JPanel scrambleKeyFileInputPane;
	private JPanel scrambledDataDirectInputPane;
	private JPanel scrambledDataFileInputPane;
	private JPanel deScrambledDataDirectOutputPanel;
	private JPanel deScrambledDataFileOutputPanel;
	private JLabel versionLabel;
	private JLabel gitHubLink;
	private JLabel ebpLink;
	private JTextPane thisProgramIsAnTextPane;

	private JFrame frame;

	private Desktop desktop = Desktop.getDesktop();
	private final URI gitHubUri = new URI("https://github.com/Epic-Breakfast-Productions/OWAT");
	private final URI ebpUri = new URI("http://epic-breakfast-productions.tech/");

	{
		if (!desktop.isSupported(Desktop.Action.BROWSE)) {
			this.desktop = null;
		}
	}

	private boolean keepRun = false;
	private boolean running = false;

	private synchronized boolean isRunning() {
		return this.running;
	}

	private synchronized void runningStopped() {
		LOGGER.trace("Running has stopped.");
		this.running = false;
		this.keepRun = false;
	}

	private synchronized void runStarted() {
		this.running = true;
		this.keepRun = true;
	}

	private synchronized void stopRunning() {
		LOGGER.trace("Running has been told to stop.");
		this.keepRun = false;
	}

	private synchronized boolean doKeepRunning() {
		return this.keepRun;
	}


	private static final String TITLE_FORMAT = "%s v%s %s";

	private static final int ICON_IMG_DIMENTIONS = 4;

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
		JOptionPane.showMessageDialog(this.mainPanel, message, title, type);
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

	private boolean validateTextInput(String text) {
		return !(text == null || text.equals(""));
	}

	private boolean validateFileInput(String loc, String description, boolean input) {
		if (!validateTextInput(loc)) {
			return false;
		}
		File file = null;
		try {
			//TODO:: put this validation in InputValidator
			Path path = Paths.get(loc).normalize();
			file = path.toFile();
		} catch (Exception e) {
			LOGGER.debug("File path to scramble was invalid.");
			this.showMessage(ERROR_MESSAGE, "Invalid file path given.", "Invalid " + description + " location given:\n" + loc);
			return false;
		}
		if (input) {
			try {
				InputValidator.ensureCanReadFromFile(file, description);
			} catch (IllegalArgumentException e) {
				LOGGER.debug("Input file given cannot be read. ({}) Error: {}", loc, e);
				this.showMessage(ERROR_MESSAGE, "File cannot be read.", "" + description + " cannot be read:\n" + loc);
				return false;
			}
		} else {
			try {
				InputValidator.ensureCanWriteToFile(file, description);
			} catch (IllegalArgumentException e) {
				LOGGER.debug("Input file given cannot be written to. ({}) Error: {}", loc, e);
				this.showMessage(ERROR_MESSAGE, "File cannot be written to.", "" + description + " cannot be written to:\n" + loc);
				return false;
			}
		}
		return true;
	}

	private void outputTimingData(OwatRunner runner) {
		StringBuilder sb = new StringBuilder("Timing data:\n");

		for (Map.Entry<Step, Long> curEntry : runner.getLastRunResults().getTimingMap().entrySet()) {
			sb.append("\t" + curEntry.getKey().stepNo + ") " + curEntry.getKey().stepName + ": " + ((double) curEntry.getValue() / 1000.0) + "s\n");
		}
		this.showMessage(INFORMATION_MESSAGE, "Success!", sb.toString());
	}

	private void openLinkOnDesktop(URI url) {
		if (this.desktop == null) {
			LOGGER.info("Opening a link on the desktop is apparently not supported.");
			this.showMessage(WARNING_MESSAGE, "Cannot open link", "Apparently we cannot open a link in a browser for you.");
			return;
		}
		try {
			desktop.browse(url);
		} catch (IOException e) {
			LOGGER.warn("Error opening link: ", e);
			this.showMessage(ERROR_MESSAGE, "Could not open link", "Could not open the link.");
		}
	}

	/* ****************************************************************
	 * Clear input methods.
	 ******************************************************************/

	private void resetProgress() {
		this.resetButton.setEnabled(true);
		this.processStartButton.setEnabled(false);
		this.processStartButton.setText("Go");
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

	private boolean inScrambleMode() {
		return this.modeSelect.getSelectedComponent() == this.scramblePanel;
	}

	private boolean inDeScrambleMode() {
		return this.modeSelect.getSelectedComponent() == this.deScramblePanel;
	}

	private boolean inRunnableMode() {
		return this.inScrambleMode() || this.inDeScrambleMode();
	}

	private boolean inputScrambleDataDirect() {
		return this.inputModeScrambleSelect.getSelectedComponent() == this.scrambleDataDirectInputPane;
	}

	private boolean inputScrambleDataFile() {
		return this.inputModeScrambleSelect.getSelectedComponent() == this.scrambleDataFileInputPane;
	}

	private boolean outputScrambleKeyDirect() {
		return this.outputScrambleKeyModeSelectPane.getSelectedComponent() == this.scrambleKeyDirectOutputPane;
	}

	private boolean outputScrambleKeyFile() {
		return this.outputScrambleKeyModeSelectPane.getSelectedComponent() == this.scrambleKeyFileOutputPane;
	}

	private boolean outputScrambledDataDirect() {
		return this.outputScrambledDataModeSelectPane.getSelectedComponent() == this.scrambledDataDirectOutputPane;
	}

	private boolean outputScrambledDataFile() {
		return this.outputScrambledDataModeSelectPane.getSelectedComponent() == this.scrambledDataFileOutputPane;
	}

	private boolean inputScrambleKeyDirect() {
		return this.deScrambleKeyInputModePane.getSelectedComponent() == this.scrambleKeyDirectInputPane;
	}

	private boolean inputScrambleKeyFile() {
		return this.deScrambleKeyInputModePane.getSelectedComponent() == this.scrambleKeyFileInputPane;
	}

	private boolean inputScrambledDataDirect() {
		return this.deScrambleDataInputModePane.getSelectedComponent() == this.scrambledDataDirectInputPane;
	}

	private boolean inputScrambledDataFile() {
		return this.deScrambleDataInputModePane.getSelectedComponent() == this.scrambledDataFileInputPane;
	}

	private boolean outputDeScrambledDataDirect() {
		return this.deScrambleDataOutputModePane.getSelectedComponent() == this.deScrambledDataDirectOutputPanel;
	}

	private boolean outputDeScrambledDataFile() {
		return this.deScrambleDataOutputModePane.getSelectedComponent() == this.deScrambledDataFileOutputPanel;
	}

	private boolean validateScrambleInputs() {
		if (this.inputScrambleDataDirect()) {
			return validateTextInput(this.scrambleDataDirectInput.getText());
		} else if (this.inputScrambleDataFile()) {
			return this.validateFileInput(
				this.scrambleDataFileInput.getText(),
				InputValidator.DESC_SCRAMBLE_DATA_INPUT,
				true
			);
		}
		return false;
	}

	private boolean validateScrambleKeyOutput() {
		if (this.outputScrambleKeyFile()) {
			boolean result = this.validateFileInput(
				this.keyFileOutput.getText(),
				InputValidator.DESC_KEY,
				false
			);
			if (!result) {
				return false;
			}
		}
		return true;
	}

	private boolean validateScrambledDataOutput() {
		if (this.outputScrambledDataFile()) {
			boolean result = this.validateFileInput(
				this.outputScrambledDataFile.getText(),
				InputValidator.DESC_SCRAMBLED_DATA_OUTPUT,
				false
			);
			if (!result) {
				return false;
			}
		}
		return true;
	}

	private boolean validateScrambleOutputs() {
		return this.validateScrambleKeyOutput() & this.validateScrambledDataOutput();
	}

	private boolean validateScrambleKeyInput() {
		if (this.inputScrambleKeyFile()) {
			boolean result = this.validateFileInput(
				this.keyFileInput.getText(),
				InputValidator.DESC_KEY,
				true
			);
			if (!result) {
				return false;
			}
		} else if (this.inputScrambleKeyDirect()) {
			boolean result = this.validateTextInput(this.keyDirectInput.getText());
			if (!result) {
				return false;
			}
		}
		return true;
	}

	private boolean validateScrambledDataInputs() {
		if (this.inputScrambledDataFile()) {
			boolean result = this.validateFileInput(
				this.scrambledDataFileInput.getText(),
				InputValidator.DESC_SCRAMBLED_DATA_INPUT,
				true
			);
			if (!result) {
				return false;
			}
		} else if (this.inputScrambledDataDirect()) {
			boolean result = this.validateTextInput(this.scrambledDataDirectInput.getText());
			if (!result) {
				return false;
			}
		}
		return true;
	}

	private boolean validateDeScrambleInputs() {
		return this.validateScrambleKeyInput() & this.validateScrambledDataInputs();
	}

	private boolean validateDeScrambleOutputs() {
		if (this.outputDeScrambledDataFile()) {
			boolean result = this.validateFileInput(
				this.deScrambledDataOutputFileInput.getText(),
				InputValidator.DESC_DESCRAMBLED_DATA_OUTPUT,
				false
			);
			if (!result) {
				return false;
			}
		}
		return true;
	}

	private boolean validateForGO() {
		if (this.isRunning()) {
			LOGGER.debug("Won't validate while running.");
			return true;
		}
		LOGGER.debug("Validating inputs.");

		boolean goodToGO = false;

		if (this.inRunnableMode()) {
			if (this.inScrambleMode()) {
				goodToGO = validateScrambleInputs() & validateScrambleOutputs();
			} else if (this.inDeScrambleMode()) {
				goodToGO = validateDeScrambleInputs() & validateDeScrambleOutputs();
			}
		}


		if (goodToGO) {
			LOGGER.debug("Validation passed. Enabling GO button.");
			this.enableGoButton();
		} else {
			LOGGER.debug("Validation failed. Disabling GO button.");
			this.disableGoButton();
		}
		return goodToGO;
	}

	private void enableGoButton() {
		this.processStartButton.setEnabled(true);
	}

	private void disableGoButton() {
		this.processStartButton.setEnabled(false);
	}

	/* ****************************************************************
	 * Form enabler/disablers
	 ******************************************************************/

	private void disEnAbleInputs(boolean enabled) {
		this.resetButton.setEnabled(enabled);
		this.modeSelect.setEnabled(enabled);

		this.inputModeScrambleSelect.setEnabled(enabled);
		this.scrambleDataDirectInput.setEnabled(enabled);
		this.scrambleDataFileInput.setEnabled(enabled);
		this.chooseScrambleDataFileButton.setEnabled(enabled);

		this.outputScrambleKeyModeSelectPane.setEnabled(enabled);
		this.keyDirectOutput.setEnabled(enabled);
		this.keyFileOutput.setEnabled(enabled);
		this.chooseKeyOutputFileButton.setEnabled(enabled);

		this.outputScrambledDataModeSelectPane.setEnabled(enabled);
		this.scrambledDataDirectOutput.setEnabled(enabled);
		this.outputScrambledDataFile.setEnabled(enabled);
		this.chooseScrambledDataOutputFileButton.setEnabled(enabled);

		this.deScrambleKeyInputModePane.setEnabled(enabled);
		this.keyDirectInput.setEnabled(enabled);
		this.keyFileInput.setEnabled(enabled);
		this.chooseDeScrambleKeyFileButton.setEnabled(enabled);

		this.deScrambleDataInputModePane.setEnabled(enabled);
		this.scrambledDataDirectInput.setEnabled(enabled);
		this.scrambledDataFileInput.setEnabled(enabled);
		this.chooseScrambledDataFileInputButton.setEnabled(enabled);

		this.deScrambleDataOutputModePane.setEnabled(enabled);
		this.deScrambledDirectOutput.setEnabled(enabled);
		this.deScrambledDataOutputFileInput.setEnabled(enabled);
		this.chooseDeScrambledDataOutputFileButton.setEnabled(enabled);

	}

	private void enableInputs() {
		this.disEnAbleInputs(true);
	}

	private void disableInputs() {
		this.disEnAbleInputs(false);
	}

	private void setupForRunning() {
		this.disableInputs();
		this.processStartButton.setText("STOP");
	}

	private void resetAfterRun() {
		this.enableInputs();
		this.processStartButton.setText("Go");
	}

	/* ****************************************************************
	 * Run scramble/descramble
	 ******************************************************************/

	private Exception runConcurrentProcess(OwatRunner runner) {
		ExecutorService exec = Executors.newSingleThreadExecutor();

		Future fut = exec.submit(OwatRunner.getThread(runner, false));

		Exception error = null;
		try {
			LOGGER.trace("Running.");
			this.runStarted();
			boolean isRunning = true;
			while (isRunning && !fut.isDone() && !fut.isCancelled()) {
				//LOGGER.trace("In the loop.");
				Step curStep = runner.getCurStep();

				int percent = (int) (((double) curStep.stepNo / (double) Step.NUM_STEPS_SCRAMBLING) * 100.0);

				this.processProgressBar.setValue(percent);
				this.processProgressBar.setString(percent + "% " + curStep.stepName);

				if (!this.doKeepRunning()) {
					fut.cancel(true);
					this.runningStopped();
				}

				try {
					Object get = fut.get(200, TimeUnit.MILLISECONDS);
					this.runningStopped();
				} catch (TimeoutException e) {
					//LOGGER.warn("Timeout exception.");
				}

				isRunning = this.isRunning();
			}
		} catch (InterruptedException | ExecutionException e) {
			error = e;
		} catch (CancellationException e) {

			error = e;
		}
		this.runningStopped();
		return error;
	}

	private void runScramble() throws IOException {
		ScrambleRunner.Builder builder = new ScrambleRunner.Builder();

		LOGGER.trace("Setting up data input...");
		if (this.inputScrambleDataDirect()) {
			builder.setDataInput(
				new ByteArrayInputStream(
					this.scrambleDataDirectInput.getText().getBytes(StandardCharsets.UTF_8)
				)
			);
		} else if (this.inputScrambleDataFile()) {
			LOGGER.debug("Data source file: {}", this.scrambleDataFileInput.getText());
			builder.setDataInput(new FileInputStream(this.scrambleDataFileInput.getText()));
		} else {
			throw new IllegalStateException();
		}

		LOGGER.trace("Setting up data output...");
		OutputStream keyOutput;
		OutputStream dataOutput;

		boolean directScrambledDataOutput = false;
		boolean directKeyDataOutput = false;

		if (this.outputScrambleKeyDirect()) {
			directKeyDataOutput = true;
			keyOutput = new ByteArrayOutputStream();
		} else if (this.outputScrambleKeyFile()) {
			keyOutput = new FileOutputStream(this.keyFileOutput.getText(), false);
		} else {
			throw new IllegalStateException();
		}

		if (this.outputScrambledDataDirect()) {
			directScrambledDataOutput = true;
			dataOutput = new ByteArrayOutputStream();
		} else if (this.outputScrambledDataFile()) {
			dataOutput = new FileOutputStream(this.outputScrambledDataFile.getText(), false);
		} else {
			throw new IllegalStateException();
		}
		builder.setKeyOutput(keyOutput);
		builder.setDataOutput(dataOutput);

		LOGGER.trace("Setting up Scramble execution...");
		ScrambleRunner runner = builder.build();

		Exception error = this.runConcurrentProcess(runner);
		LOGGER.trace("Done running.");

		if (error != null) {
			LOGGER.warn("There was an error during execution: ", error);
			if (error instanceof CancellationException) {
				this.showMessage(WARNING_MESSAGE, "Cancelled.", "Operation cancelled.");
			} else {
				this.showMessage(ERROR_MESSAGE, "ERROR", "There was an error during execution:\n" + error.getMessage());
			}
			return;
		}

		this.processProgressBar.setValue(100);
		this.processProgressBar.setString(100 + "% " + Step.DONE_SCRAMBLING.stepName);

		if (directKeyDataOutput) {
			this.keyDirectOutput.setText(keyOutput.toString());
		}
		keyOutput.close();

		if (directScrambledDataOutput) {
			this.scrambledDataDirectOutput.setText(dataOutput.toString());
		}
		dataOutput.close();

		this.outputTimingData(runner);
	}

	private void runDeScramble() throws IOException {
		DeScrambleRunner.Builder builder = new DeScrambleRunner.Builder();

		if (this.inputScrambleKeyDirect()) {
			builder.setKeyInput(new ByteArrayInputStream(this.keyDirectInput.getText().getBytes(StandardCharsets.UTF_8)));
		} else if (this.inputScrambleKeyFile()) {
			builder.setKeyInput(new FileInputStream(this.keyFileInput.getText()));
		} else {
			throw new IllegalStateException();
		}

		if (this.inputScrambledDataDirect()) {
			builder.setDataInput(new ByteArrayInputStream(this.scrambledDataDirectInput.getText().getBytes(StandardCharsets.UTF_8)));
		} else if (this.inputScrambledDataFile()) {
			builder.setDataInput(new FileInputStream(this.scrambledDataFileInput.getText()));
		} else {
			throw new IllegalStateException();
		}

		OutputStream os;
		boolean directDeScrambledDataOutput = false;
		if (this.outputDeScrambledDataDirect()) {
			directDeScrambledDataOutput = true;
			os = new ByteArrayOutputStream();
		} else if (this.outputDeScrambledDataFile()) {
			os = new FileOutputStream(this.deScrambledDataOutputFileInput.getText());
		} else {
			throw new IllegalStateException();
		}
		builder.setDataOutput(os);

		LOGGER.trace("Setting up Descramble execution...");
		DeScrambleRunner runner = builder.build();

		Exception error = this.runConcurrentProcess(runner);
		LOGGER.trace("Done running.");


		if (error != null) {
			LOGGER.warn("There was an error during execution: ", error);
			if (error instanceof CancellationException) {
				this.showMessage(WARNING_MESSAGE, "Cancelled.", "Operation cancelled.");
			} else {
				this.showMessage(ERROR_MESSAGE, "ERROR", "There was an error during execution:\n" + error.getMessage());
			}
			return;
		}

		this.processProgressBar.setValue(100);
		this.processProgressBar.setString(100 + "% " + Step.DONE_SCRAMBLING.stepName);

		if (directDeScrambledDataOutput) {
			this.deScrambledDirectOutput.setText(os.toString());
		}
		os.close();

		this.outputTimingData(runner);
	}

	private void runProcess() {
		this.setupForRunning();
		try {
			if (this.inScrambleMode()) {
				this.runScramble();
			} else if (this.inDeScrambleMode()) {
				this.runDeScramble();
			} else {
				this.showMessage(ERROR_MESSAGE, "Improper Mode", "Not in a scrambling or descrambling mode.");
			}
		} catch (IOException e) {
			LOGGER.warn("Error in file I/O during setup or cleanup of run. Error: ", e);
			this.showMessage(ERROR_MESSAGE, "File I/O Error", "There was a file I/O error while scrambling. Error: \n" + e.getMessage());
		} catch (IllegalStateException e) {
			LOGGER.error("Got into something we didn't really expect to see. Error: ", e);
			this.showMessage(ERROR_MESSAGE, "Some Other Error", "There was a strange error while scrambling. Error: \n" + e.getMessage());
		}
		this.resetAfterRun();
	}

	private void runConcurrently() {
		LOGGER.info("Button hit to run the process.");
		boolean goodToGO = this.validateForGO();
		if (!goodToGO) {
			LOGGER.info("Form did not pass validation. NOT running.");
			return;
		}
		LOGGER.info("Form PASSED validation. RUNNING...");

		ExecutorService exec = Executors.newSingleThreadExecutor();

		Future fut = exec.submit(new Runnable() {
			@Override
			public void run() {
				runProcess();
			}
		});
	}

	/* ****************************************************************
	 * Setup
	 ******************************************************************/
	public MainGuiApp(JFrame frame) throws URISyntaxException {
		this.frame = frame;
		$$$setupUI$$$();
		this.versionLabel.setText("Version " + Globals.getProp(Globals.PropertyKey.APP_VERSION_PROP_KEY) + " " + Globals.getProp(Globals.PropertyKey.APP_VERSION_NAME_PROP_KEY));
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (isRunning()) {
					return;
				}
				//TODO:: not working/waiting for double click
				if (e.getClickCount() == 2) {
					if (confirmActon("Reset ALL Forms")) {
						resetAllInputs();
					}
				}

				if (inScrambleMode()) {
					if (confirmActon("Reset Scramble Form")) {
						resetScramble();
					}
				}
				if (inDeScrambleMode()) {
					if (confirmActon("Reset DeScramble Form")) {
						resetDeScramble();
					}
				}
			}
		});
		chooseScrambleDataFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (isRunning()) {
					return;
				}
				scrambleDataFileInput.setText(chooseFile("Choose a file to scramble", FileDialog.LOAD, "*"));
			}
		});
		chooseKeyOutputFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (isRunning()) {
					return;
				}
				keyFileOutput.setText(chooseFile("Choose where to save the data key", FileDialog.SAVE, "*.obfdk"));
			}
		});
		chooseScrambledDataOutputFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (isRunning()) {
					return;
				}
				outputScrambledDataFile.setText(chooseFile("Choose where to save the scrambled data", FileDialog.SAVE, "*.obfd"));
			}
		});
		chooseDeScrambleKeyFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (isRunning()) {
					return;
				}
				keyFileInput.setText(chooseFile("Choose key file to use", FileDialog.LOAD, "*.obfdk"));
			}
		});
		chooseScrambledDataFileInputButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (isRunning()) {
					return;
				}
				scrambledDataFileInput.setText(chooseFile("Choose the scrambled data file", FileDialog.LOAD, "*.obfd"));
			}
		});
		chooseDeScrambledDataOutputFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (isRunning()) {
					return;
				}
				deScrambledDataOutputFileInput.setText(chooseFile("Choose where to save the descrambled data", FileDialog.SAVE, "*"));
			}
		});
		modeSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateForGO();
				super.mouseClicked(e);
			}
		});
		DocumentListener inputChangeListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				validateForGO();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validateForGO();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				validateForGO();
			}
		};
		this.scrambleDataDirectInput.getDocument().addDocumentListener(inputChangeListener);
		this.scrambleDataFileInput.getDocument().addDocumentListener(inputChangeListener);
		this.keyDirectOutput.getDocument().addDocumentListener(inputChangeListener);
		this.keyFileOutput.getDocument().addDocumentListener(inputChangeListener);
		this.scrambledDataDirectOutput.getDocument().addDocumentListener(inputChangeListener);
		this.outputScrambledDataFile.getDocument().addDocumentListener(inputChangeListener);
		this.keyDirectInput.getDocument().addDocumentListener(inputChangeListener);
		this.keyFileInput.getDocument().addDocumentListener(inputChangeListener);
		this.scrambledDataDirectInput.getDocument().addDocumentListener(inputChangeListener);
		this.scrambledDataFileInput.getDocument().addDocumentListener(inputChangeListener);
		this.deScrambledDirectOutput.getDocument().addDocumentListener(inputChangeListener);
		this.deScrambledDataOutputFileInput.getDocument().addDocumentListener(inputChangeListener);

		inputModeScrambleSelect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateForGO();
				super.mouseClicked(e);
			}
		});
		outputScrambleKeyModeSelectPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateForGO();
				super.mouseClicked(e);
			}
		});
		outputScrambledDataModeSelectPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateForGO();
				super.mouseClicked(e);
			}
		});
		deScrambleKeyInputModePane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateForGO();
				super.mouseClicked(e);
			}
		});
		deScrambleDataInputModePane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateForGO();
				super.mouseClicked(e);
			}
		});
		deScrambleDataOutputModePane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				validateForGO();
				super.mouseClicked(e);
			}
		});
		processStartButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isRunning()) {
					stopRunning();
				} else {
					runConcurrently();
				}
				super.mouseClicked(e);
			}
		});
		gitHubLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		gitHubLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				openLinkOnDesktop(gitHubUri);
			}
		});
		ebpLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ebpLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				openLinkOnDesktop(ebpUri);
			}
		});
	}

	public static Image getIcon() {
		BufferedImage img = new BufferedImage(ICON_IMG_DIMENTIONS, ICON_IMG_DIMENTIONS, BufferedImage.TYPE_INT_ARGB);
		OwatRandGenerator rand = new RandGenerator();
		for (int i = 0; i < ICON_IMG_DIMENTIONS; i++) {
			for (int j = 0; j < ICON_IMG_DIMENTIONS; j++) {
				int a = 255,
					r = rand.nextByte(),
					g = rand.nextByte(),
					b = rand.nextByte();
				int p = (a << 24) | (r << 16) | (g << 8) | b;
				img.setRGB(i, j, p);
			}
		}
		return img;
	}

	public static void main(String[] args) throws URISyntaxException {
		LOGGER.info("Starting GUI.");
		JFrame frame = new JFrame(appTitle);
		frame.setIconImage(getIcon());
		frame.setContentPane(new MainGuiApp(frame).mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	private void createUIComponents() {
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
		processProgressBar.setBorderPainted(true);
		Font processProgressBarFont = this.$$$getFont$$$(null, Font.BOLD, 18, processProgressBar.getFont());
		if (processProgressBarFont != null) processProgressBar.setFont(processProgressBarFont);
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
		inputModeScrambleSelect = new JTabbedPane();
		scrambleDataInputPanel.add(inputModeScrambleSelect, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		scrambleDataDirectInputPane = new JPanel();
		scrambleDataDirectInputPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		scrambleDataDirectInputPane.setMaximumSize(new Dimension(-1, -1));
		scrambleDataDirectInputPane.setMinimumSize(new Dimension(-1, -1));
		scrambleDataDirectInputPane.setPreferredSize(new Dimension(-1, -1));
		scrambleDataDirectInputPane.setToolTipText("");
		inputModeScrambleSelect.addTab("Enter Text", null, scrambleDataDirectInputPane, "This is to enter your own data to be scrambled.");
		scrambleDataDirectInput = new JTextArea();
		Font scrambleDataDirectInputFont = this.$$$getFont$$$("Monospaced", -1, -1, scrambleDataDirectInput.getFont());
		if (scrambleDataDirectInputFont != null) scrambleDataDirectInput.setFont(scrambleDataDirectInputFont);
		scrambleDataDirectInput.setLineWrap(true);
		scrambleDataDirectInputPane.add(scrambleDataDirectInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		scrambleDataFileInputPane = new JPanel();
		scrambleDataFileInputPane.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		inputModeScrambleSelect.addTab("File", null, scrambleDataFileInputPane, "This is to specify a file to be scrambled.");
		scrambleDataFileInput = new JTextField();
		scrambleDataFileInputPane.add(scrambleDataFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer3 = new Spacer();
		scrambleDataFileInputPane.add(spacer3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseScrambleDataFileButton = new JButton();
		chooseScrambleDataFileButton.setText("Choose File");
		scrambleDataFileInputPane.add(chooseScrambleDataFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
		outputScrambledDataModeSelectPane = new JTabbedPane();
		scrambleDataOutputPanel.add(outputScrambledDataModeSelectPane, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		scrambledDataDirectOutputPane = new JPanel();
		scrambledDataDirectOutputPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambledDataModeSelectPane.addTab("Direct output", scrambledDataDirectOutputPane);
		scrambledDataDirectOutput = new JTextArea();
		scrambledDataDirectOutput.setEditable(false);
		Font scrambledDataDirectOutputFont = this.$$$getFont$$$("Monospaced", -1, -1, scrambledDataDirectOutput.getFont());
		if (scrambledDataDirectOutputFont != null) scrambledDataDirectOutput.setFont(scrambledDataDirectOutputFont);
		scrambledDataDirectOutput.setLineWrap(true);
		scrambledDataDirectOutput.setText("");
		scrambledDataDirectOutputPane.add(scrambledDataDirectOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		scrambledDataFileOutputPane = new JPanel();
		scrambledDataFileOutputPane.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambledDataModeSelectPane.addTab("File", scrambledDataFileOutputPane);
		chooseScrambledDataOutputFileButton = new JButton();
		chooseScrambledDataOutputFileButton.setText("Choose File");
		scrambledDataFileOutputPane.add(chooseScrambledDataOutputFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer4 = new Spacer();
		scrambledDataFileOutputPane.add(spacer4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		outputScrambledDataFile = new JTextField();
		outputScrambledDataFile.setText("");
		scrambledDataFileOutputPane.add(outputScrambledDataFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		outputScrambleKeyModeSelectPane = new JTabbedPane();
		scrambleDataOutputPanel.add(outputScrambleKeyModeSelectPane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		scrambleKeyDirectOutputPane = new JPanel();
		scrambleKeyDirectOutputPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambleKeyModeSelectPane.addTab("Direct Output", scrambleKeyDirectOutputPane);
		keyDirectOutput = new JTextArea();
		keyDirectOutput.setEditable(false);
		Font keyDirectOutputFont = this.$$$getFont$$$("Monospaced", -1, -1, keyDirectOutput.getFont());
		if (keyDirectOutputFont != null) keyDirectOutput.setFont(keyDirectOutputFont);
		keyDirectOutput.setLineWrap(true);
		scrambleKeyDirectOutputPane.add(keyDirectOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		scrambleKeyFileOutputPane = new JPanel();
		scrambleKeyFileOutputPane.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		outputScrambleKeyModeSelectPane.addTab("File", scrambleKeyFileOutputPane);
		keyFileOutput = new JTextField();
		scrambleKeyFileOutputPane.add(keyFileOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer5 = new Spacer();
		scrambleKeyFileOutputPane.add(spacer5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseKeyOutputFileButton = new JButton();
		chooseKeyOutputFileButton.setText("Choose File");
		scrambleKeyFileOutputPane.add(chooseKeyOutputFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
		scrambledDataDirectInputPane = new JPanel();
		scrambledDataDirectInputPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataInputModePane.addTab("Direct Input", scrambledDataDirectInputPane);
		scrambledDataDirectInput = new JTextArea();
		Font scrambledDataDirectInputFont = this.$$$getFont$$$("Monospaced", -1, -1, scrambledDataDirectInput.getFont());
		if (scrambledDataDirectInputFont != null) scrambledDataDirectInput.setFont(scrambledDataDirectInputFont);
		scrambledDataDirectInput.setLineWrap(true);
		scrambledDataDirectInputPane.add(scrambledDataDirectInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		scrambledDataFileInputPane = new JPanel();
		scrambledDataFileInputPane.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataInputModePane.addTab("From File", scrambledDataFileInputPane);
		scrambledDataFileInput = new JTextField();
		scrambledDataFileInputPane.add(scrambledDataFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer6 = new Spacer();
		scrambledDataFileInputPane.add(spacer6, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseScrambledDataFileInputButton = new JButton();
		chooseScrambledDataFileInputButton.setText("Choose File");
		scrambledDataFileInputPane.add(chooseScrambledDataFileInputButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label6 = new JLabel();
		label6.setText("Scrambled Data:");
		enterScrambledDataAndKeyPanel.add(label6, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		deScrambleKeyInputModePane = new JTabbedPane();
		enterScrambledDataAndKeyPanel.add(deScrambleKeyInputModePane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		scrambleKeyDirectInputPane = new JPanel();
		scrambleKeyDirectInputPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleKeyInputModePane.addTab("Direct Input", scrambleKeyDirectInputPane);
		keyDirectInput = new JTextArea();
		Font keyDirectInputFont = this.$$$getFont$$$("Monospaced", -1, -1, keyDirectInput.getFont());
		if (keyDirectInputFont != null) keyDirectInput.setFont(keyDirectInputFont);
		keyDirectInput.setLineWrap(true);
		scrambleKeyDirectInputPane.add(keyDirectInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		scrambleKeyFileInputPane = new JPanel();
		scrambleKeyFileInputPane.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleKeyInputModePane.addTab("File", scrambleKeyFileInputPane);
		keyFileInput = new JTextField();
		scrambleKeyFileInputPane.add(keyFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer7 = new Spacer();
		scrambleKeyFileInputPane.add(spacer7, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseDeScrambleKeyFileButton = new JButton();
		chooseDeScrambleKeyFileButton.setText("Choose File");
		scrambleKeyFileInputPane.add(chooseDeScrambleKeyFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
		deScrambledDataDirectOutputPanel = new JPanel();
		deScrambledDataDirectOutputPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataOutputModePane.addTab("Display", deScrambledDataDirectOutputPanel);
		deScrambledDirectOutput = new JTextArea();
		deScrambledDirectOutput.setEditable(false);
		Font deScrambledDirectOutputFont = this.$$$getFont$$$("Monospaced", -1, -1, deScrambledDirectOutput.getFont());
		if (deScrambledDirectOutputFont != null) deScrambledDirectOutput.setFont(deScrambledDirectOutputFont);
		deScrambledDirectOutput.setLineWrap(true);
		deScrambledDataDirectOutputPanel.add(deScrambledDirectOutput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 50), null, 0, false));
		deScrambledDataFileOutputPanel = new JPanel();
		deScrambledDataFileOutputPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
		deScrambleDataOutputModePane.addTab("To File", deScrambledDataFileOutputPanel);
		deScrambledDataOutputFileInput = new JTextField();
		deScrambledDataFileOutputPanel.add(deScrambledDataOutputFileInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final Spacer spacer8 = new Spacer();
		deScrambledDataFileOutputPanel.add(spacer8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		chooseDeScrambledDataOutputFileButton = new JButton();
		chooseDeScrambledDataOutputFileButton.setText("Choose File");
		deScrambledDataFileOutputPanel.add(chooseDeScrambledDataOutputFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
		infoPanel.setToolTipText("Info about this program");
		modeSelect.addTab("*Info*", null, infoPanel, "Information about this program");
		final JLabel label8 = new JLabel();
		Font label8Font = this.$$$getFont$$$(null, Font.BOLD, 36, label8.getFont());
		if (label8Font != null) label8.setFont(label8Font);
		label8.setText("OWAT-J");
		infoPanel.add(label8, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		versionLabel = new JLabel();
		Font versionLabelFont = this.$$$getFont$$$(null, Font.ITALIC, 20, versionLabel.getFont());
		if (versionLabelFont != null) versionLabel.setFont(versionLabelFont);
		versionLabel.setText(" ");
		infoPanel.add(versionLabel, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		gitHubLink = new JLabel();
		gitHubLink.setText("Github: https://github.com/Epic-Breakfast-Productions/OWAT");
		infoPanel.add(gitHubLink, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		ebpLink = new JLabel();
		ebpLink.setText("Made by EBP: http://epic-breakfast-productions.tech");
		infoPanel.add(ebpLink, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		thisProgramIsAnTextPane = new JTextPane();
		thisProgramIsAnTextPane.setEditable(false);
		thisProgramIsAnTextPane.setText("This program is an implementation of the OWAT protocol.\n\nIt takes in some data, and obfuscates it by scrambling that data in itself and additional random data, generating a key in the process. This key is used to descramble the data later.\n\nFor more details, please feel free to visit the github page for this project.");
		infoPanel.add(thisProgramIsAnTextPane, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
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
