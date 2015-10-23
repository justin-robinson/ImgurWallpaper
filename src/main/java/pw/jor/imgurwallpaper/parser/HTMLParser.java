package pw.jor.imgurwallpaper.parser;

import pw.jor.imgurwallpaper.Main;

import java.util.ArrayList;
import java.util.Scanner;


public class HTMLParser extends ParserAbstract {
    public static final String HASH = "\"hash\":\"";
    public static final String POST_CLASS ="class=\"post\"";
    public static final String POSTS_CLASS = "class=\"posts\"";
    public static final String OPEN_DIV = "<div ";
    public static final String CLOSE_DIV = "</div>";
    public static final String ID="id=\"";

    public void parse ( String body ) {
        Main.gui.println("Getting Page...");
        String line="",s;
        int begin=0, end, index;
        Scanner input = new Scanner(body);
        //start searching for div or hashes
        while(input.hasNext()){
            line=input.nextLine().trim();
            index=line.indexOf(HASH);
            //process hashes if found
            if(index != -1){
                begin = index;
                while(begin != -1){
                    begin=begin+HASH.length();
                    end=line.indexOf("\"", begin);
                    s=line.substring(begin, end);
                    this.addHash(s);
                    begin = line.indexOf(HASH, begin);
                }
            }

            //process div tags if found
            index = line.indexOf(OPEN_DIV + POSTS_CLASS);
            if(index != -1){
                //start processing divs
                int level = 1;
                boolean entered = false;
                while(input.hasNext() && (level>0 || entered==false)){
                    line=input.nextLine().trim();
                    if(line.indexOf(OPEN_DIV) != -1){//if open div tag in line we denote level change
                        ++level;
                        entered=true;
                        if(line.indexOf(POST_CLASS)!=-1){ //div is for an image
                            //extract hash for image
                            begin=line.indexOf(ID)+ID.length();
                            end=line.indexOf("\"", begin);
                            s=line.substring(begin, end);
                            if(!ImageHashes.contains(s))
                                ImageHashes.add(s);
                        }
                    }
                    if(line.indexOf(CLOSE_DIV) != -1)//if close tage is in line we denote level change
                        --level;
                }
            }


        }
        input.close();
    }
}
