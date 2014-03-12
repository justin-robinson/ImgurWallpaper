import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class GUI {

	public JTextArea output;
	public JTextField textField;
	private static String imgurURLPrefix = "http://imgur.com/a/";
	private static String http = "http";
	MyThread worker = new MyThread();
	private ArrayList<String> dPages = new ArrayList<String>();
	
	String[] galleries ={
						"http://imgur.com/r/wallpaper",
						"http://imgur.com/r/wallpapers",
						"wCBYO", 
						"x6sRL",
						"UtTug",
						"Pd50T",
						"cSHht",
						"bxuoB",
						"atexU",
						"rdOxc",
						"UFMym",
						"D3vya",
						"CYLB5",
						"9ITy3",
						"GAx1r",
						"wBYOE",
						"Faz7d",
						"GCVlJ",
						"OKaDx",
						"chSms",
						"catwP",
						"U3PyN",
						"RvFYM",
						"dbELn",
						"6EPQG",
						"J2VG8",
						"ZETXp"
						};
	String userSelection = "user";
	String definedSelection = "defined";
	String selection;
	ButtonGroup radios;
	JComboBox<?> cb;
	JCheckBox chkbox;

	public GUI(){
		//text input for url
		textField = new JTextField(40);
		//combobox
		cb = new JComboBox<Object>(galleries);
		cb.setSelectedIndex(0);
		cb.setActionCommand(definedSelection);
		//panel for text and combobox
		JPanel inputPanel = new JPanel(new GridLayout(0,1));
		inputPanel.add(textField);
		inputPanel.add(cb);
		//Checkbox to download all files
		chkbox = new JCheckBox("Download All");

		//radio buttons and panel to select input or combobox
		JRadioButton user = new JRadioButton();
		user.setActionCommand(userSelection);
		user.setSelected(true);
		JRadioButton defined = new JRadioButton();
		defined.setActionCommand(definedSelection);

		radios = new ButtonGroup();
		radios.add(user);
		radios.add(defined);
		JPanel radioPanel = new JPanel(new GridLayout(0,1));
		radioPanel.add(user);
		radioPanel.add(defined);

		//buttons for start, pause, resume
		JButton submit = new JButton("Submit");
		//button action
		submit.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				if(worker.isAlive())
					worker.stop();
				worker =  new MyThread();
				worker.start();}});
		JButton pause = new JButton("Pause");
		pause.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				if(worker.isAlive()){
					worker.suspend();}}});
		JButton resume = new JButton("Resume");
		resume.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				if(worker.isAlive()){
					worker.resume();}}});


		//panels and buttons into top panel
		JPanel top = new JPanel();
		top.add(radioPanel);
		top.add(inputPanel);
		top.add(submit);
		top.add(pause);
		top.add(resume);
		top.add(chkbox);
		top.setBorder(setBorder("Enter the imgur gallery url"));

		//scrollable output
		output = new JTextArea();
		output.setMinimumSize(new Dimension(40, 600));
		JScrollPane scrollPane = new JScrollPane(output);
		scrollPane.setMinimumSize(new Dimension(40, 600));

		//Frame for everything
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(0, 1));
		frame.add(top);
		frame.add(scrollPane);
		frame.pack();
		frame.setSize(new Dimension(1000, 400));
		//frame.setAlwaysOnTop(true);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@SuppressWarnings("deprecation")
			public void windowClosing(WindowEvent winEvt) {
				if(worker.isAlive())
					worker.stop();
			}
		});
		//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

	public Border setBorder(String title){
		return BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(title),
				BorderFactory.createEmptyBorder(5,5,5,5));
	}

	public class MyThread extends Thread{

		public void main(String[] args) {
			MyThread worker = new MyThread();
			worker.setDaemon(true);
			worker.start();
		}
		public void run(){
			try {
				String request = "";
				selection = radios.getSelection().getActionCommand();
				int count=-1;
				int galleryPick=0;
				if(chkbox.isSelected()){
					count=galleries.length;
					request = galleries[galleryPick];
				}
				else if(selection.equals(userSelection)){
					request = textField.getText();
				}
				else if(selection.equals(definedSelection)){
					request = (galleries[cb.getSelectedIndex()].startsWith(http, 0) ?
								galleries[cb.getSelectedIndex()] : 
								imgurURLPrefix + galleries[cb.getSelectedIndex()]);
				}
				
				do{
					String page="";
					try{
						page = new Scanner(new URL(request).openStream(), "UTF-8").useDelimiter("\\A").next();
					}catch (MalformedURLException e){
						output.append("URL is invalid\n");
						output.setCaretPosition(output.getDocument().getLength());
					}catch(IOException e){
						output.append("404 Page Not Found\n");
						output.setCaretPosition(output.getDocument().getLength());
					}catch(IllegalArgumentException e){
						output.append("URL is invalid\n");
						output.setCaretPosition(output.getDocument().getLength());
					}
					if(page!=""){
						String hash = "\"hash\":\"";
						String postClass ="class=\"post\"";
						String postsClass = "class=\"posts\"";
						String openDiv = "<div ";
						String closeDiv = "</div>"; 
						String id="id=\"";
						ArrayList<String> hashes = new ArrayList<String>();
						output.append("Getting Page...\n");
						output.setCaretPosition(output.getDocument().getLength());
						String line="",s;
						int begin=0, end, index;
						Scanner input = new Scanner(page);
						//start searching for div or hashes
						while(input.hasNext()){
							line=input.nextLine().trim();
							index=line.indexOf(hash);
							//process hashes if found
							if(index != -1){
								begin = index;
								while(begin != -1){
									begin=begin+hash.length();
									end=line.indexOf("\"", begin);
									s=line.substring(begin, end);
									if(!hashes.contains(s))
										hashes.add(s);
									begin = line.indexOf(hash, begin);
								}
							}

							//process div tags if found
							index = line.indexOf(openDiv+postsClass);
							if(index != -1){
								//start processing divs
								int level = 1;
								boolean entered = false;
								while(input.hasNext() && (level>0 || entered==false)){
									line=input.nextLine().trim();
									if(line.indexOf(openDiv) != -1){//if open div tag in line we denote level change
										++level;
										entered=true;
										if(line.indexOf(postClass)!=-1){ //div is for an image
											//extract hash for image
											begin=line.indexOf(id)+id.length();
											end=line.indexOf("\"", begin);
											s=line.substring(begin, end);
											if(!hashes.contains(s))
												hashes.add(s);
										}
									}
									if(line.indexOf(closeDiv) != -1)//if close tage is in line we denote level change
										--level;
								}
							}


						}
						input.close();


						if(hashes.size() > 0){
							output.append(hashes.size()+" images found\n");
							File file;
							URL url;
							InputStream reader;
							FileOutputStream writer;
							String name;				
							byte[] buffer = new byte[153600];
							int bytesRead = 0;
							String userDir = System.getenv("USERPROFILE")+"\\Pictures\\Wallpapers\\";
							File dir = new File(userDir);
							dir.mkdir();
							String filePath;
							for(int i=0; i<hashes.size(); ++i){
								name=hashes.get(i)+".jpg";
								filePath = userDir+name;
								file=new File(filePath);
								if(!file.exists()){
									line="http://i.imgur.com/"+name;

									url = new URL(line);
									url.openConnection();
									reader = url.openStream();

									writer=new FileOutputStream(filePath);

									//System.out.print((i+1)+". Downloading "+ name);
									//String temp = gui.output.getText();
									output.append((i+1)+". Downloading "+ name);
									output.setCaretPosition(output.getDocument().getLength());
									while ((bytesRead = reader.read(buffer)) > 0){
										writer.write(buffer,0,bytesRead);
										buffer = new byte[153600];
										output.append(".");
									}
									//System.out.println();
									output.append("\n");

									writer.close();
									reader.close();
								}
								else{
									//System.out.println(i+". "+name+" already exists");
									output.append((i+1)+". "+name+" already exists\n");
									output.setCaretPosition(output.getDocument().getLength());
								}
							}
						}
						else{
							//System.out.println("No valid images found");
							output.append("No valid images found\n");
							output.setCaretPosition(output.getDocument().getLength());
						}
					}
					if(count != -1){
						request=(galleries[++galleryPick].startsWith(http, 0) ?
								galleries[galleryPick] : 
								imgurURLPrefix + galleries[galleryPick]);
					}
			}while(count != -1 && galleryPick < count);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
		}
	}

	/** Listens to the radio buttons. */
	public void actionPerformed(ActionEvent e) {
		selection = e.getActionCommand();
	}
	
	private void getLinks() throws MalformedURLException, IOException{
		InputStream url = new URL("http://dl.jrobcomputers.com/walls").openStream();
		Scanner pageScanner = new Scanner(url, "UTF-8");
		String page=pageScanner.useDelimiter("\\A").next();
		url.close();
		pageScanner.close();
		
		Scanner input = new Scanner(page);
		while(input.hasNext()){
			String line=input.nextLine().trim();
			dPages.add(line);
		}
	}
}
