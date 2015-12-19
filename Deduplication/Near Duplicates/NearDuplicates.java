import java.io.*;
import java.util.*;
import java.security.*;
class NearDuplicates
{
    static File dump;
    static InputStreamReader disr;
    static BufferedReader dbr;
    static List<FetchedRecord> lfr;
    static File out;
    static PrintWriter pw;
    static int total_URLs,total_groups_dups,total_dup_URL;
    public static void main(String args[])throws Exception
    {
        dump = new File ("result_out/dump");
        disr = new InputStreamReader(new FileInputStream(dump));
        dbr = new BufferedReader(disr);
        out = new File("results_near.txt");
        pw = new PrintWriter(out);
        lfr = new ArrayList<FetchedRecord>();
        String line = new String();
        int rn;
        String u,pt;
        FetchedRecord fr;
        total_URLs = 0;
        while((line = dbr.readLine())!=null)
        {
            if(line.contains("Recno::")) //line has Recno:: hence new record started
            {
                total_URLs++;
                rn = Integer.parseInt(line.substring(7).trim());
                line = dbr.readLine(); //  next line in record is URL
                u = line.substring(5).trim();
		if((u.lastIndexOf("/") < u.lastIndexOf(".jpg")) || (u.lastIndexOf("/") < u.lastIndexOf(".gif")) || (u.lastIndexOf("/") < u.lastIndexOf(".png")) || (u.lastIndexOf("/") < u.lastIndexOf(".jpeg")) || (u.lastIndexOf("/") < u.lastIndexOf(".tiff")) || (u.lastIndexOf("/") < u.lastIndexOf(".ico")) || (u.lastIndexOf("/") < u.lastIndexOf(".bmp"))){
                	line = dbr.readLine(); //next line in record is empty
                
			while(!line.contains("Parse Metadata")){
				line = dbr.readLine();
			}
			pt = line.trim();
			if(pt.length() > 0)
                	{
                    		fr = new FetchedRecord(rn,u,pt);
                    		lfr.add(fr);
                    		computeShingleHash(fr);
                	}
		}
            }
        }
        System.out.println(lfr.size()+" URLs have parsetext");
        findSimilarity();
        pw.println("Total URLs Fetched: "+total_URLs);
        pw.println("Groups of URLs with at least one duplicate: "+total_groups_dups);
        pw.println("Total Duplicate URLs: "+total_dup_URL);
        /*System.out.println("Total URLs Fetched: "+total_URLs);
        System.out.println("Groups of URLs with at least one duplicate: "+total_groups_dups);
        System.out.println("Total Duplicate URLs: "+total_dup_URL);*/
        pw.close();
    }

    public static void computeShingleHash(FetchedRecord fr) throws Exception
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String tokens[] = fr.parsetext.split(" ");
        int i = 0,len = tokens.length;
        String shingle = new String();
        int k = 15-len%15;
        while((len-i)/k > 0)
        {
            shingle = "";
            for(int a=0;a<k;a++)
            {
                shingle += tokens[i+a];
            }
            byte digest[] =  md.digest(shingle.getBytes());
            int sum = 0;
            for(byte z : digest)
            {
                sum += z;
            }
            if(sum % k == 0)
            {
                fr.hashstrings.add(new String(digest));
            }
            i++;
        }

    }

    public static void findSimilarity()
    {
	//System.out.println("Reached Similarity");
        int len = lfr.size();
        FetchedRecord a,b;
        for(int i=0;i<len;i++)
        {
            a = lfr.get(i);
            for(int j=i+1;j<len;j++)
            {
                if(i != j)
                {
                    b = lfr.get(j);
                    List<String> intersect = new ArrayList<String>();
                    intersect.addAll(a.hashstrings);
                    intersect.retainAll(b.hashstrings);

                    List<String> a_min_b = new ArrayList<String>();
                    a_min_b.addAll(a.hashstrings);
                    a_min_b.removeAll(b.hashstrings);

                    List<String> union = new  ArrayList<String>();
                    union.addAll(a_min_b);
                    union.addAll(b.hashstrings);

                    a_min_b = null;

                    double jaccard_similarity = (intersect.size() * 1.0)/(union.size());
			//System.out.println("Jaccard Similarity ::: "+ jaccard_similarity);
                    if(jaccard_similarity > 0.99)
                    {
                        a.similarURLs.add(b.url);
                    }
                }
            }
            
            
            int t = a.similarURLs.size();
	    //System.out.println(t);
            if(t>0)
            {
                total_groups_dups++;
                total_dup_URL += t + 1;
            }
            pw.println("Group "+(i+1));
            pw.println("Number of URL(s) : "+(t+1));
            pw.println("URL:: ");
            pw.println(a.url);
            /*System.out.println("Group "+(i+1));
            System.out.println("Number of URL(s) : "+(t+1));
            System.out.println("URL:: ");
            System.out.println(a.url);*/
            for(int j=0;j<t;j++)
            {
                pw.println("URL:: ");
                pw.println(a.similarURLs.get(j));
                /*System.out.println("URL:: ");
                System.out.println(a.similarURLs.get(j));*/
            }
            
            
        }
        
    }
}
