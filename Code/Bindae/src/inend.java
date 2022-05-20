
class inend { // DTO order
	int jo_num;
	int ja_pay;
	int ja_time;
	public inend(int jo_num, int ja_pay, int ja_time) {
		this.jo_num = jo_num;
		this.ja_pay = ja_pay;
		this.ja_time = ja_time;
	}
	public int getJO_num() {
		return jo_num;
	}
	public void setJO_num(int jo_num) {
		this.jo_num = jo_num;
	}
	public int getJA_time() {
		return ja_time;
	}
	public void setJA_time(int ja_time) {
		this.ja_time = ja_time;
	}
	public int getJA_pay() {
		return ja_pay;
	}
	public void setJA_pay(int ja_pay) {
		this.ja_pay = ja_pay;
	}
	
}
