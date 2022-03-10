package load.table.finalDS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import database.connectDB;
import database.dbCredentials;


public class loadData {
	public static void main(String[] args) {
        String csvFilePath = dbCredentials.getCsvfilepath();
 
        int batchSize = 20;
 
        Connection con = null;
 
        try {
            con = connectDB.getConnection();
            con.setAutoCommit(false);
 
            String query = "INSERT INTO "+dbCredentials.getTableName()+" (sl_no, business_code, cust_number,"
            				+" name_customer, clear_date, buisness_year, doc_id, posting_date,"
            				+" due_in_date, baseline_create_date, cust_payment_terms, converted_usd,"
            				+" aging_bucket) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
            String checkQuery = "select count(*) from final_ds_hrc";
            
            Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkQuery);
			
			rs.next();
			String total = rs.getString(1);
			if(total.equals("0")) {
	            int count = 0;
	 
	            lineReader.readLine(); // skip header line
	 
	            while ((lineText = lineReader.readLine()) != null) {
	                String[] data = lineText.split(",");
	                
	                int sl_no = Integer.parseInt(data[0]);	                
	                String business_code = data[1];
	                String cust_number = data[2];
	                String name_customer = data[3];
	                String clear_date = data[4];
	                int buisness_year = Integer.parseInt(data[5]);
	                long doc_id = Long.parseLong(data[6].substring(0, 10));
	                String posting_date = data[7];
	                String due_in_date = data[8];
	                String baseline_create_date = data[9];
	                String cust_payment_terms = data[10];
	                float converted_usd = Float.parseFloat(data[11]);
	                String aging_bucket = (data.length > 12) ? data[12] : "";
				
	                converter c = new converter();
	                
	                Timestamp clear_date_sql = c.convertClearDate(clear_date);
	                Timestamp posting_date_sql = c.convertClearDate(posting_date);
	                Timestamp due_in_date_sql = c.convertClearDate(due_in_date);
	                Timestamp baseline_create_date_sql = c.convertClearDate(baseline_create_date);
	                
	                pst.setInt(1, sl_no);
	                pst.setString(2, business_code);
	                pst.setString(3, cust_number);
	                pst.setString(4, name_customer);
	                pst.setTimestamp(5, clear_date_sql);
	                pst.setInt(6, buisness_year);
	                pst.setLong(7, doc_id);
	                pst.setTimestamp(8, posting_date_sql);
	                pst.setTimestamp(9, due_in_date_sql);
	                pst.setTimestamp(10, baseline_create_date_sql);
	                pst.setString(11, cust_payment_terms);
	                pst.setFloat(12, converted_usd);
	                pst.setString(13, aging_bucket);
	                
	                pst.addBatch();
	                count++;
	                
	                if (count % batchSize == 0) {
	                    pst.executeBatch();
	                    System.out.println(count);
	                }
	            }
	 
	            lineReader.close();
	 
	            // execute the remaining queries
	            pst.executeBatch();
	            System.out.println(count);
	            
	            con.commit();
	            con.close();
	            
	            System.out.println("Data inserted successfully");
			} else {
				System.out.println("!!! Alert some Data is already there. New data may create duplicate.");
			}
            
 
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
 
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
