package load.table.finalDS;


import java.sql.Timestamp;

public class converter {
	private String seconds_format = " 00:00:00";
	public Timestamp convertClearDate(String clear_date) {
		Timestamp sql_s = null;
		if(clear_date.length() > 0) sql_s = Timestamp.valueOf(clear_date);
		return sql_s;
	}

	public Timestamp convertPostingDate(String posting_date) {
		Timestamp sql_s = null;
		if(posting_date.length() > 0) sql_s = Timestamp.valueOf(posting_date + seconds_format);
		return sql_s;
	}

	public Timestamp convertCreateDate(String document_create_date) {
		document_create_date = document_create_date.substring(0, 4) +"-"+ document_create_date.substring(4, 6) +"-"+ document_create_date.substring(6, 8);
		Timestamp sql_s = null;
		if(document_create_date.length() > 0) sql_s = Timestamp.valueOf(document_create_date + seconds_format);
		return sql_s;
	}

	public long convertLong(String invoice_id) {
		long d = 0;
		if(invoice_id.length() > 0) d = (long)Float.parseFloat(invoice_id);
		return d;
	}
}

