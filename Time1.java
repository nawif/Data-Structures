
public class Time1 implements Time {

	private int HH;
	private int MM;
	private int SS;
	private int MS;

	/*
	 * Setters and getters might need some validation later
	 * 
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see Time#getHH()
	 */

	// Constructor

	public Time1(int hH, int mM, int sS, int mS) { // Used to Create a "Time"
													// Object

		HH = hH;
		MM = mM;
		SS = sS;
		MS = mS;
	}

	public int getHH() {
		return HH;
	}

	public void setHH(int hH) {
		HH = hH;
	}

	public int getMM() {
		return MM;
	}

	public void setMM(int mM) {
		MM = mM;
	}

	public int getSS() {
		return SS;
	}

	public void setSS(int sS) {
		SS = sS;
	}

	public int getMS() {
		return MS;
	}

	public void setMS(int mS) {
		MS = mS;
	}

	public int timeToMS() {
		int ms = 0;
		ms +=(HH * 3600000);
		ms +=(MM * 60000);
		ms +=(SS * 1000);
		ms +=MS;

		return ms;
	}

	public void msToTime(int ms) {
		HH = (int) (ms / 1000) % 60;
		MM = (int) ((ms / (1000 * 60)) % 60);
		SS = (int) ((ms / (1000 * 60 * 60)) % 24);
		MS = ms - ( (HH * 3600000) + (MM * 60000) + (SS * 1000) );
		
	}
	public void timeShifter(int shift){
		int time = timeToMS();
		time += shift;
		msToTime(time);
		
	}

}
