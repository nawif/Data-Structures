import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SubtitleSeqFactory {

	// Return an empty subtitles sequence
	public static SubtitleSeq getSubtitleSeq() {
		return new SubtitleSeqIm();
	}

	// Load a subtitle sequence from an SRT file. If the file does not exist or
	// is corrupted (incorrect format), null is returned.
	public static SubtitleSeq loadSubtitleSeq(String fileName) {
		SubtitleSeqIm seq = new SubtitleSeqIm();
		int seqNum = 1;
		String timeLinePattern = "(^(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d[,]\\d{3} --> (?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d[,]\\d{3}$)";
		String line = null;
		String timeLine, startTime = null, endTime = null;
		boolean flag = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while (((line = br.readLine()) != null)) { // .equals("")
				flag = false;
				String text = "";
				if (Integer.parseInt(line) == seqNum) {
					seqNum++;
					if ((line = br.readLine()).matches(timeLinePattern)) {
						timeLine = line;
						if ((!(line = br.readLine()).equals(""))) {
							text += line;
							try {
								while (!(line = br.readLine()).equals("")) {
									text += "\n";
									text += line;
								}
								if(line.trim().isEmpty()){
									flag = true;
								}
							} catch (NullPointerException e) {
								// this allow the last subtitle to have multiple
								// text lines.
							}
						} else {
							return null;
						}
					} else {
						return null;
					}
				} else {
					return null;
				}
				String[] timeArray = timeLine.split(" ");
				startTime = timeArray[0];
				endTime = timeArray[2];

				// add to the subtitleSeq
				Time startTimeObj = new TimeIm(Integer.parseInt(startTime.substring(0, 2)),
						Integer.parseInt(startTime.substring(3, 5)), Integer.parseInt(startTime.substring(6, 8)),
						Integer.parseInt(startTime.substring(9, 12)));
				Time endTimeObj = new TimeIm(Integer.parseInt(endTime.substring(0, 2)),
						Integer.parseInt(endTime.substring(3, 5)), Integer.parseInt(endTime.substring(6, 8)),
						Integer.parseInt(endTime.substring(9, 12)));
				SubtitleIm tmp = new SubtitleIm(startTimeObj, endTimeObj, text);
				if(((TimeIm)endTimeObj).timeToMS() < ((TimeIm)startTimeObj).timeToMS()){
					// End time precede start time, return null
					return null;
				}
				seq.addSubtitle(tmp);
			}

		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (NumberFormatException e) {
			return null; // this will return null if the first line of any subtitle is not an integer.
		}

		if (seq.getSubtitles().empty()) {
			return null; // if first line of file is empty this will handle it
		}
		if(flag){ // if last line is empty return null
			return null;
		}
		return seq;
	}

	public static void main(String[] args) {
		SubtitleSeqFactory s = new SubtitleSeqFactory();
		SubtitleSeq sub = s.loadSubtitleSeq("/Users/osama/Desktop/Phase-1 (2)/data/winnie-the-pooh-2011.srt");
		sub.getSubtitles().findFirst();
		int counter = 1;
		while(!sub.getSubtitles().last()){
			System.out.println(counter++);
			System.out.println("..... --> .....");
			System.out.println(sub.getSubtitles().retrieve().getText());
			System.out.println();
			sub.getSubtitles().findNext();
		}
		System.out.println(counter++);
		System.out.println("..... --> .....");
		System.out.println(sub.getSubtitles().retrieve().getText());

		
	}
}
