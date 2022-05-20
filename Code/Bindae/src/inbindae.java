
class inbindae { //DTO product
	int jp_num;
	String jp_name;
	int jp_price;
	int jp_class;
	String jp_memo;
	int jp_time;
	public inbindae(int jp_num, String jp_name, int jp_price, int jp_class, String jp_memo, int jp_time) {
		this.jp_num =jp_num;
		this.jp_name =jp_name;
		this.jp_price = jp_price;
		this.jp_class = jp_class;
		this.jp_memo = jp_memo;
		this.jp_time = jp_time;
	}
	public int getJp_num() {
		return jp_num;
	}
	public void setJp_num(int jp_num) {
		this.jp_num = jp_num;
	}
	public String getJp_name() {
		return jp_name;
	}
	public void setJp_name(String jp_name) {
		this.jp_name = jp_name;
	}
	public int getJp_price() {
		return jp_price;
	}
	public void setJp_price(int jp_price) {
		this.jp_price = jp_price;
	}
	public int getJp_class() {
		return jp_class;
	}
	public void setJp_class(int jp_class) {
		this.jp_class = jp_class;
	}
	public String getJp_memo() {
		return jp_memo;
	}
	public void setJp_memo(String jp_memo) {
		this.jp_memo = jp_memo;
	}
	public int getJp_time() {
		return jp_time;
	}
	public void setJp_time(int jp_time) {
		this.jp_time = jp_time;
	}
	
}
