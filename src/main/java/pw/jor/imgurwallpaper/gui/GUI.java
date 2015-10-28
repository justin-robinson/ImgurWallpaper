package pw.jor.imgurwallpaper.gui;

import pw.jor.imgurwallpaper.Downloader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

/**
 * GUI singleton for ImgurWallpaper
 *
 * @author jrobinson
 * @since 04/12/14
 */
public class GUI {

    public JTextArea output;
    public JTextField textField;
    public String selection;
    public ButtonGroup radios;
    public JComboBox<?> comboBox;
    public JCheckBox downloadAllCheckBox;
    public JFormattedTextField minWidth;
    public JFormattedTextField maxWidth;
    public JFormattedTextField minHeight;
    public JFormattedTextField maxHeight;
    public JFrame frame;

    public static String[] galleries;

    public static final String USER_SELECTION = "user";
    public static final String DEFINED_SELECTION = "defined";

    private static GUI instance = new GUI();
    private Worker worker = new Worker();

    /**
     * Gets the singleton
     *
     * @return GUI instance
     */
    public static GUI getInstance() {
        return instance;
    }

    /**
     * Private constructor
     */
    private GUI(){

        //Frame for everything
        frame = new JFrame();

        try {
            // get the prepopulated sources for wallpapers
            galleries = Downloader.getSourceURLs();

            // show the gui
            this.show();
        } catch ( Exception e ) {
            error(e.getMessage());
        }

    }

    /**
     * Creates and shows the gui
     */
    private void show() {

        //text input for url
        textField = new JTextField(40);
        //combobox
        comboBox = new JComboBox<Object>(galleries);
        comboBox.setSelectedIndex(0);
        comboBox.setActionCommand(DEFINED_SELECTION);
        //panel for text and combobox
        JPanel inputPanel = new JPanel(new GridLayout(0,1));
        inputPanel.add(textField);
        inputPanel.add(comboBox);
        //Checkbox to download all files
        downloadAllCheckBox = new JCheckBox("Download All");

        //radio buttons and panel to select input or combobox
        JRadioButton user = new JRadioButton();
        user.setActionCommand(USER_SELECTION);
        user.setSelected(true);
        JRadioButton defined = new JRadioButton();
        defined.setActionCommand(DEFINED_SELECTION);

        radios = new ButtonGroup();
        radios.add(user);
        radios.add(defined);
        JPanel radioPanel = new JPanel(new GridLayout(0,1));
        radioPanel.add(user);
        radioPanel.add(defined);

        //buttons for start, pause, resume
        JButton submit = new JButton("Submit");
        //button action
        submit.addActionListener((ActionEvent e) -> {
            worker.stop();
            worker = new Worker();
            worker.start();
        });

        JButton pause = new JButton("Pause");
        pause.addActionListener( e -> worker.suspend() );

        JButton resume = new JButton("Resume");
        resume.addActionListener( e -> worker.resume() );

        // width & height input fields
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);

        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        minWidth = new JFormattedTextField(formatter);
        minWidth.setValue(1920);
        minWidth.setColumns(8);

        maxWidth = new JFormattedTextField(formatter);
        maxWidth.setValue(0);
        maxWidth.setColumns(8);

        minHeight = new JFormattedTextField(formatter);
        minHeight.setValue(1080);
        minHeight.setColumns(8);

        maxHeight = new JFormattedTextField(formatter);
        maxHeight.setValue(0);
        maxHeight.setColumns(8);

        JPanel sizePanel = new JPanel(new GridBagLayout());
        sizePanel.add(new JLabel("Minimum"));
        sizePanel.add(minWidth);
        sizePanel.add(new JLabel("x"));
        sizePanel.add(minHeight);
        sizePanel.add(new JLabel("Maximum"));
        sizePanel.add(maxWidth);
        sizePanel.add(new JLabel("x"));
        sizePanel.add(maxHeight);


        //panels and buttons into top panel
        JPanel top = new JPanel();
        top.add(radioPanel);
        top.add(inputPanel);
        top.add(submit);
        top.add(pause);
        top.add(resume);
        top.add(downloadAllCheckBox);
        top.add(sizePanel);
        top.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Enter the imgur gallery url"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        //scrollable output
        output = new JTextArea();
        output.setMinimumSize(new Dimension(40, 600));
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setMinimumSize(new Dimension(40, 600));


        frame.setLayout(new GridLayout(0, 1));
        frame.add(top);
        frame.add(scrollPane);
        frame.pack();
        frame.setSize(new Dimension(1000, 400));

        // kill worker thread on application close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                if( worker.isAlive() ) {
                    worker.stop();
                }
            }
        });

        //frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Prints message to gui and stdout
     *
     * @param message message to print
     */
    public void print(String message ) {
        System.out.print(message);
        this.output.append(message);
        this.output.setCaretPosition(this.output.getDocument().getLength());
    }

    /**
     * Prints message and line break to gui and stdout
     *
     * @param message message to print
     */
    public void println(String message ) {
        message += System.lineSeparator();
        this.print(message);
    }

    /**
     * Shows and error dialog with a message
     *
     * @param message error message
     */
    public void error(String message ) {
        JOptionPane.showMessageDialog(
                frame,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

}
