
public class test {

	public static void main(String[] args) {
		SubtitleSeqIm t = new SubtitleSeqIm();
		Subtitle a1 = new SubtitleIm(new Time1(0, 0, 10, 100), new Time1(0, 0, 13, 100), "a1");
		Subtitle a2 = new SubtitleIm(new Time1(0, 0, 15, 100), new Time1(0, 0, 17, 100), "a2");
		Subtitle a3 = new SubtitleIm(new Time1(0, 0, 18, 100), new Time1(0, 0, 22, 100), "a3");
		Subtitle a4 = new SubtitleIm(new Time1(0, 0, 25, 100), new Time1(0, 0, 30, 100), "a4");
		Subtitle a5 = new SubtitleIm(new Time1(0, 0, 34, 100), new Time1(0, 0, 40, 100), "a5");
		Subtitle a6 = new SubtitleIm(new Time1(0, 1, 0, 100), new Time1(0, 1, 10, 100), "a6");
		Subtitle a7 = new SubtitleIm(new Time1(0, 1, 11, 100), new Time1(0, 1, 40, 100), "a7");
		
		t.addSubtitle(a1);
		t.addSubtitle(a3);
		t.addSubtitle(a2);
		t.addSubtitle(a4);
		t.addSubtitle(a6);
		t.addSubtitle(a5);
		 t.print();
		
	}

}
