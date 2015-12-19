import java.io.*;
import java.util.*;
class FetchedRecord
{
    int recno;
    String url;
    String parsetext;
    List<String> hashstrings;
    List <String> similarURLs;
    FetchedRecord()
    {
    }

    FetchedRecord(int rn,String u,String pt)
    {
        recno = rn;
        url = new String(u);
        parsetext = new String(pt);
        hashstrings = new ArrayList<String>();
        similarURLs = new ArrayList<String>();
    }
}