package helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class dateConverter {
	public static Date toDate(String date) throws ParseException {
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);		
		return d;
	}
	public static void main(String[] args) throws ParseException {
		Date d = toDate("2019-01-22");
		System.out.println(d);
	}
}
