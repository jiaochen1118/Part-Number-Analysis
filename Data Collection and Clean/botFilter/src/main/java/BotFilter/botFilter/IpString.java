package BotFilter.botFilter;
public class IpString {
    
    private int[] numbers;
     
    public IpString(String[] str) {
        numbers = new int[4];
        for (int i = 0; i < 4; i++) {
            numbers[i] = Integer.parseInt(str[i]);
        }
    }
     
    public IpString(String ip) {
        String[] str = ip.split("\\.");
        numbers = new int[4];
        for (int i = 0; i < 4; i++) {
        	try {
        		numbers[i] = Integer.parseInt(str[i]);
        	} catch (Exception e) {
        		System.err.println(ip);
        	}
        }
    }
 
    public void print() {
        System.out.println(String.valueOf(numbers[0]) + "."
                        + String.valueOf(numbers[1]) + "."
                        + String.valueOf(numbers[2]) + "."
                        + String.valueOf(numbers[3]));
    }
     
    public boolean greaterOrEqualTo(IpString ip) {
        for (int i = 0; i < 4; i++) {
            if (this.numbers[i] < ip.numbers[i]) return false;
        }
        return true;
    }
 
}