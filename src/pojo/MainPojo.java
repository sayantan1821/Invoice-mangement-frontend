package pojo;

public class MainPojo {
	private int sl_no;
	private String business_code;
	private String business_name;
	private String cust_number;
	private String name_customer;
	private String clear_date;
	private String business_year;
	private String doc_id;
	private String posting_date;
	private String document_create_date;
	private String document_create_date1;
	private String due_in_date;
	private String invoice_currency;
	private String document_type;
	private int posting_id;
	private String area_business;
	private double total_open_amount;
	private String baseline_create_date;
	private String cust_payment_terms;
	private int invoice_id;
	private int isOpen;
	private String aging_Bucket = "";
	private String is_deleted;
	private String predicted = "";

	public int getSl_no() {
		return sl_no;
	}
	public void setSl_no(int sl_no) {
		this.sl_no = sl_no;
	}
	public String getBusiness_code() {
		return business_code;
	}
	public void setBusiness_code(String business_code) {
		this.business_code = business_code;
	}
	public String getCust_number() {
		return cust_number;
	}
	public void setCust_number(String cust_number) {
		this.cust_number = cust_number;
	}
	public String getClear_date() {
		return clear_date;
	}
	public void setClear_date(String clear_date) {
		this.clear_date = clear_date;
	}
	public String getBusiness_year() {
		return business_year;
	}
	public void setBusiness_year(String business_year) {
		this.business_year = business_year;
	}
	public String getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	public String getPosting_date() {
		return posting_date;
	}
	public void setPosting_date(String posting_date) {
		this.posting_date = posting_date;
	}
	public String getDocument_create_date() {
		return document_create_date;
	}
	public void setDocument_create_date(String document_create_date) {
		this.document_create_date = document_create_date;
	}
	public String getDocument_create_date1() {
		return document_create_date1;
	}
	public void setDocument_create_date1(String document_create_date1) {
		this.document_create_date1 = document_create_date1;
	}
	public String getDue_in_date() {
		return due_in_date;
	}
	public void setDue_in_date(String due_in_date) {
		this.due_in_date = due_in_date;
	}
	public String getInvoice_currency() {
		return invoice_currency;
	}
	public void setInvoice_currency(String invoice_currency) {
		this.invoice_currency = invoice_currency;
	}
	public String getDocument_type() {
		return document_type;
	}
	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}
	public int getPosting_id() {
		return posting_id;
	}
	public void setPosting_id(int posting_id) {
		this.posting_id = posting_id;
	}
	public String getArea_business() {
		return area_business;
	}
	public void setArea_business(String area_business) {
		this.area_business = area_business;
	}
	public double getTotal_open_amount() {
		return total_open_amount;
	}
	public void setTotal_open_amount(double total_open_amount) {
		this.total_open_amount = total_open_amount;
	}
	public String getBaseline_create_date() {
		return baseline_create_date;
	}
	public void setBaseline_create_date(String baseline_create_date) {
		this.baseline_create_date = baseline_create_date;
	}
	public String getCust_payment_terms() {
		return cust_payment_terms;
	}
	public void setCust_payment_terms(String cust_payment_terms) {
		this.cust_payment_terms = cust_payment_terms;
	}
	public int getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(int invoice_id) {
		this.invoice_id = invoice_id;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public String getAging_Bucket() {
		return aging_Bucket;
	}
	public void setAging_Bucket(String aging_Bucket) {
		if(aging_Bucket != null) this.aging_Bucket = aging_Bucket;
	}
	public String getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}
	public String getName_customer() {
		return name_customer;
	}
	public void setName_customer(String name_customer) {
		this.name_customer = name_customer;
	}
	public String getBusiness_name() {
		return business_name;
	}
	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}
	public String getPredicted() {
		return predicted;
	}
	public void setPredicted(String predicted) {
		if(predicted != null) this.predicted = predicted;
	}
	
	
}
