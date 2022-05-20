	/* -----------РќДо------------ */
class b_orderlist {
	int jo_num;
	int jp_num;
	int jo_count;
	public void b_orderlist(int jo_num,int jp_num,int jo_count) {
		this.jo_num = jo_num;
		this.jp_num = jp_num;
		this.jo_count = jo_count;
	}
	public int getJo_num() {
		return jo_num;
	}
	public void setJo_num(int jo_num) {
		this.jo_num = jo_num;
	}
	public int getJp_num() {
		return jp_num;
	}
	public void setJp_num(int jp_num) {
		this.jp_num = jp_num;
	}
	public int getJo_count() {
		return jo_count;
	}
	public void setJo_count(int jo_count) {
		this.jo_count = jo_count;
	}
}
