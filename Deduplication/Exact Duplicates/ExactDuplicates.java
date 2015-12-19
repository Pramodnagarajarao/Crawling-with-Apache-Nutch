import java.security.MessageDigest;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Groups{
	String urls = "";
	int counter = 0;
}

public class ExactDuplicates {
	static int count = 0, duplicate= 0;
	static String line, record_number, url, text, cmd;

	public static void main(String[] args) throws IOException {
		FileInputStream in = null;
		FileOutputStream out = null;
		HashMap hash_cmd = new HashMap();
		HashMap hash_pmd = new HashMap();
		System.out.println(System.getProperty("user.dir"));

		try {
			File file = new File("result_out/dump");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
         	        
			StringBuffer stringBuffer = new StringBuffer();
			int flag = 0;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				
				//gets the URL and text
				if((stringBuffer.toString()).contains("Recno:: "))
					record_number = stringBuffer.toString();
				else if((stringBuffer.toString()).contains("URL:: ")){
					url = stringBuffer.toString();
				//Check if URL is an image				
					if((url.lastIndexOf("/") < url.lastIndexOf(".jpg")) || (url.lastIndexOf("/") < url.lastIndexOf(".gif")) || (url.lastIndexOf("/") < url.lastIndexOf(".png")) || (url.lastIndexOf("/") < url.lastIndexOf(".ico")) || (url.lastIndexOf("/") < url.lastIndexOf(".bmp")) || (url.lastIndexOf("/") < url.lastIndexOf(".jpeg")) || (url.lastIndexOf("/") < url.lastIndexOf(".tiff")) ){
						flag = 1;
					}


				}
				//Taking Content Metadata (You can comment this section if you do not want to include Content Metadata)				
				else if((stringBuffer.toString()).contains("Content Metadata:") && flag==1){
					text = stringBuffer.toString();
					System.out.println(url);
					System.out.println(text);
					int text_len, start_x, end_x; 
					if(text.length() != 1){
						try {
				//Removing Unwanted fields							
							start_x = text.indexOf("nutch.fetch.time=");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);
							}

							start_x = text.indexOf("ETag");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);}


							start_x = text.indexOf("nutch.content.digest=");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);}


							start_x = text.indexOf("Connection=");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);}

							start_x = text.indexOf("Server=");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);}

							start_x = text.indexOf("nutch.segment.name=");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);}
							
							start_x = text.indexOf("nutch.crawl.score=");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);}

							start_x = text.indexOf("_fst_=");
							if(start_x > 0){
							end_x = text.indexOf(" ",start_x + 1);
							text = text.substring(0,start_x) + text.substring(end_x);}	

							System.out.println(text);
								
							String md5 = text;
							java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
							byte[] array = md.digest(md5.getBytes());
							StringBuffer sb = new StringBuffer();
							for (int i = 0; i < array.length; ++i) {
								sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
							}

							cmd = sb.toString();
					    	}catch (Exception e){
							System.out.println("Error! ::: ");
							e.printStackTrace();
						}
					}	
				}
				
				//Taking Parse Metadata
				else if((stringBuffer.toString()).contains("Parse Metadata:") && flag==1){
					flag = 0;
					text = stringBuffer.toString();
					System.out.println(text);
					count++;
					if(text.length() != 1){
						try {
							String md5 = text;
							java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
							byte[] array = md.digest(md5.getBytes());
							StringBuffer sb = new StringBuffer();
							for (int i = 0; i < array.length; ++i) {
								sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
							}
							
							//Comment nextline if you are using only Parsed Metadata
							//Uncomment the line after it 
							if(hash_pmd. containsKey(sb.toString()) && hash_cmd. containsKey(cmd)){
//							if(hash_pmd. containsKey(sb.toString())){    
							//adds URL and increases the counter if duplicate is found
								duplicate++;
								//gets the grp class from hashmap
								Groups grpDup = (Groups)hash_pmd.get(sb.toString());
								grpDup.urls = grpDup.urls + ""+ url;
								grpDup.counter++;
							}
							else{
							//adds to the HashMap if the Hash value doesn't exist in HashMap
								Groups grp = new Groups();
								grp.urls = url;
								grp.counter++;
								hash_pmd.put(sb.toString(), grp);
								hash_cmd.put(cmd, grp);
							}
					    	}catch (Exception e){
							System.out.println("Error!");
						}
					}	
				}
				stringBuffer.delete(0,stringBuffer.length());
			}
			fileReader.close();
		}catch(Exception e){
			System.out.println("Error!");
		}finally {
			//Printing the statistics
			Set<String> keys = hash_pmd.keySet();
			int i=1;
			for(String s: keys){
				Groups g = (Groups)hash_pmd.get(s);
				if(g.counter != 1){
					System.out.println("Group "+(i++));				
					System.out.println("Number of URL's : " + g.counter);
					System.out.println(g.urls+"\n");
				}
			}
			System.out.println("Ratio of exact duplication : "+((duplicate*1.0)/count));	
			System.out.println("Duplicate URL's : "+duplicate+"\nTotal URL's     : "+count);		
		}
	}
}

