package BotFilter.botFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class mainTask {
	    public static final String JSON_FILE = "/Users/jchen/Documents/DataScience_Fixed_Bot_FIlter/Data/crawler-user-agents.json";
	    public static final String IP_FILE = "/Users/jchen/Documents/DataScience_Fixed_Bot_FIlter/Data/ip_range.tsv";
	    public static final String FILE_NAME = "/Users/jchen/Documents/search_term_with_bot.txt";
	    public static void main(String[] args) {
	    	
	        System.out.println("BotFilter Test:");
	        BotFilter filterObj = new BotFilter();
	        filterObj.buildPatternData(JSON_FILE);
	        filterObj.buildIpRange(IP_FILE);
	        String ip;
	        String userAgent;
	        String userAction;
	        try{
	        	File file = new File("/Users/jchen/Documents/SearchTerm_Clean.txt");
	        	if(!file.exists()){
	        		file.createNewFile();
	        	}
	        	BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
	        	FileWriter fw = new FileWriter(file.getAbsoluteFile());
	        	BufferedWriter bw = new BufferedWriter(fw);
	        	
	        	
	            String line;
	            while ((line = br.readLine()) != null) {
	                if (!(line.contains("START_USER_DATA")) && !(line.contains("END_USER_DATA"))){
	            	    String[] strs = line.split("\t");
	                    if (strs.length == 4) {
	        	                   ip = strs[0];
	                               userAgent = strs[3];
	                                userAction = strs[1];
	           
	                                if (!(filterObj.filter(ip, userAgent))) {
	            	                 
	                                	    //bw.write(userAction + strs[2]); 
	                                	    if (userAction.contains("searchResultsImp_new")) {
	            		                                       bw.write(strs[2]+"\n");
	                                	    }
	                                }
	                    }
	                }
	            }br.close();bw.close();
	        }catch (IOException e) {
				e.printStackTrace();
			}
	        //String User_agent_1 = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36"; 
	        //String User_agent_2 = "Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)";
	         
	        //String IP_1 = "140.112.30.142"; // # Valid IP
	       // String IP_2 = "216.3.110.165";  // # Internal QA IP
	         
	        //filterObj.ipL.get(0).print();
	         
	        /*System.out.println("Test (1,1): " + String.valueOf(filterObj.filter(IP_1, User_agent_1)));
	        System.out.println("Test (1,2): " + String.valueOf(filterObj.filter(IP_1, User_agent_2)));
	        System.out.println("Test (2,1): " + String.valueOf(filterObj.filter(IP_2, User_agent_1)));
	        System.out.println("Test (2,2): " + String.valueOf(filterObj.filter(IP_2, User_agent_2)));
	         
	        System.out.println("BotFilter Test ENDs.");*/
	       
	    }
	 
	}

