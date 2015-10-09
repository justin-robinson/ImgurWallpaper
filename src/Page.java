import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class Page {
	private String page="";
	private InputStream url = null;
	private Scanner pageScanner = null;
	private ArrayList<String> hashes = new ArrayList<String>();
	String hash = "\"hash\":\"";
	
	public void getPage(String input) throws MalformedURLException, IOException{
		url = new URL(input).openStream();
		pageScanner = new Scanner(url, "UTF-8");
		page = pageScanner.useDelimiter("\\A").next();
		url.close();
		pageScanner.close();
		searchPage();
	}
	
	public void searchPage() throws IOException{
		if(page!=""){
			String line="", s;
			int begin=0, end;
			Scanner input = new Scanner(page);
			while(input.hasNext()){
				line=input.nextLine().trim();
				begin = line.indexOf(hash, begin);
				if(begin != -1)
					break;
			}
			input.close();
			//file.delete();
			
			while(begin != -1){
				begin=begin+hash.length();
				end=line.indexOf("\"", begin);
				s=line.substring(begin, end);
				hashes.add(s);
				begin = line.indexOf(hash, begin);
			}
			
			if(hashes.size() > 0){
				File dir = new File("walls");
				dir.mkdir();
				File file;
				URL url;
				InputStream reader;
				FileOutputStream writer;
				String name;				
				byte[] buffer = new byte[153600];
				int bytesRead = 0;
				
				for(int i=0; i<hashes.size(); ++i){
					name=hashes.get(i)+".jpg";
					file=new File(".\\walls\\"+name);
					if(!file.exists()){
						line="http://i.imgur.com/"+name;
						
						url = new URL(line);
						url.openConnection();
						reader = url.openStream();
					
						writer=new FileOutputStream(".\\walls\\"+name);
						
						System.out.print((i+1)+". Downloading "+ name);
						//String temp = gui.output.getText();
						//gui.appendOutput("\n"+(i+1)+". Downloading "+ name);
						while ((bytesRead = reader.read(buffer)) > 0){
							writer.write(buffer,0,bytesRead);
							buffer = new byte[153600];
							System.out.print(".");
						}
						System.out.println();

						writer.close();
						reader.close();
					}
					else
						System.out.println((i+1)+". "+name+" already exists");
				}
			}
			else{
				System.out.println("No valid images found");
			}
		}
	}
}
