 
class inbasket { //DTO order_detail
	int ja_num;
	int jb_pay;
	int jo_count;
	int je_time;
	int jp_num;
	int jo_num;
	public inbasket(int ja_num,int jb_pay,int jo_count,int je_time,int jp_num, int jo_num) {
		this.ja_num = ja_num;
		this.jb_pay = jb_pay;
		this.jo_count = jo_count;
		this.je_time = je_time;
		this.jp_num = jp_num;
		this.jo_num = jo_num;
	}
	public int getJA_num() {
		return ja_num;
	}
	public void setJA_num(int ja_num) {
		this.ja_num = ja_num;
	}
	public int getJB_pay() {
		return jb_pay;
	}
	public void setJB_pay(int jb_pay) {
		this.jb_pay = jb_pay;
	}
	public int getJO_count() {
		return jo_count;
	}
	public void setJO_count(int jo_count) {
		this.jo_count = jo_count;
	}
	public int getJE_time() {
		return je_time;
	}
	public void setJE_time(int je_time) {
		this.je_time = je_time;
	}
	public int getJP_num() {
		return jp_num;
	}
	public void setJP_num(int jp_num) {
		this.jp_num = jp_num;
	}
	public int getJO_num() {
		return jo_num;
	}
	public void setJO_num(int jo_num) {
		this.jo_num = jo_num;
	}
	
}
