package BotFilter.botFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class BotFilter {
    private Vector<String> pattern;
    private Vector<IpString> ipR;
    private Vector<IpString> ipL;
     
    public BotFilter() {
        pattern = new Vector<String> ();
        ipR = new Vector<IpString>();
        ipL = new Vector<IpString>();
    }
     
    public void buildPatternData(String filename) {
         
        // get patterns from .json file
        JSONParser parser = new JSONParser();
        try {
            JSONArray a = (JSONArray) parser.parse(new FileReader(filename));
            for (int i = 0; i < a.size(); i++) {
                JSONObject o = (JSONObject) a.get(i);
                String s = (String) o.get("pattern");
                if (s != null)  pattern.add(s);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
    public void buildPatternDataTxt(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                pattern.add(line.trim());
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
    public void buildIpRange(String filename){
        // get ip ranges
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
            }
            br.close();
            if (ipL.size() != ipR.size()){
                System.err.println("Invalid IP file");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
     
    public boolean filter(String ip, String userAgent) {
        return (filterIp(ip) || filterPattern(userAgent));
    }
 
    private boolean filterPattern(String userAgent) {
        for (int i = 0; i < pattern.size(); i++) {
            if (userAgent.toLowerCase().contains(pattern.get(i).toLowerCase())) return true;
        }
        return false;
    }
 
    private boolean filterIp(String ip) {
        IpString userIp = new IpString(ip);
        for (int i = 0; i < ipL.size(); i++) {
            if (userIp.greaterOrEqualTo(ipL.get(i)) && ipR.get(i).greaterOrEqualTo(userIp)) return true;
        }
        return false;
    }
 
    private void parseLine(String line) {
        String[] strs = line.split("\t");
        ipL.add(toIpString(strs[0]));
        ipR.add(toIpString(strs[1]));
    }
 
    public IpString toIpString(String string) {
        String s = string.trim();
        String[] numbers = s.split("\\.");
        if (numbers.length != 4) {
            System.out.println(string);
            System.err.println("Invalid IP format");
        }
         
        IpString ip = new IpString(numbers);
        return ip;
    }
 
    public boolean filterZone(String zone, int[] dsaZone) {
        try {
            int zoneID = Integer.parseInt(zone);
            return (Arrays.binarySearch(dsaZone, zoneID) >= 0);
        } catch (NumberFormatException e) {
            return false;
        }
    }
 
    public boolean filterIntIp(String ip) {
        IpString userIp = new IpString(ip);
        for (int i = 0; i < 4; i++) {
            if (userIp.greaterOrEqualTo(ipL.get(i)) && ipR.get(i).greaterOrEqualTo(userIp)) return true;
        }
        return false;
    }
 
}