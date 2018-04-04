
public class SubtitleSeqIm implements SubtitleSeq {
	public static LinkedList<Subtitle> sub;

	public SubtitleSeqIm() {
		sub = new LinkedList<Subtitle>();
	}

	public void addSubtitle(Subtitle st) {
		if (sub.empty()) {
			sub.insert(st);
			return;
		}
		if (!sub.full()) {
			int addAtIndex = 0;
			sub.findFirst();
			int startTimeOfSt = ((TimeIm) st.getStartTime()).timeToMS();
			int startTimeOfCurrent = 0;
			while (!sub.last()) {
				if((inTime((TimeIm)st.getStartTime()) || inTime(((TimeIm)st.getEndTime()))))
					return;
				startTimeOfCurrent = ((TimeIm) sub.retrieve().getStartTime()).timeToMS();
				if (startTimeOfSt < startTimeOfCurrent) {
					break;
				}
				addAtIndex++;
				sub.findNext();
			}
			startTimeOfCurrent = ((TimeIm) sub.retrieve().getStartTime()).timeToMS();
			if (startTimeOfSt > startTimeOfCurrent) {
				addAtIndex++;
			}

			sub.findFirst();
			if ((addAtIndex == 0)) { // if it should be added at the first
				Subtitle tmp = sub.retrieve();
				sub.update(st);
				sub.insert(tmp);
			} else {
				for (int i = 1; i < addAtIndex; i++) {
					sub.findNext();
				}
				sub.insert(st);
			}

		}
	}

	@Override
	public List<Subtitle> getSubtitles() {
		if (!sub.empty()) { // you can't find first when the list is empty
							// because of the requirement "Return all subtitles
							// in their chronological order.
		} // sub.empty must return a list even if the list is empty
		return sub;
	}

	@Override
	public Subtitle getSubtitle(Time time) {
		if (sub.empty())
			return null; // if the list is empty, return null ,requirment for
							// findFirst too
		sub.findFirst(); // need to start from the fist subtitle

		while (!sub.last()) {
			if (inTime((TimeIm) time)) // returns true if the given time match
										// the time in the current
				return sub.retrieve();
			sub.findNext();
		}
		if (inTime((TimeIm) time)) // checking the last element
			return sub.retrieve();
		return null;
	}

	private boolean inTime(TimeIm time) { // created to make code cleaner and
											// might need to use the some method
											// again

		TimeIm start = (TimeIm) sub.retrieve().getStartTime();
		TimeIm end = (TimeIm) sub.retrieve().getEndTime();
		double startt = start.timeToMS();
		double endt = end.timeToMS();
		double givent = time.timeToMS();
		if (givent >= startt && givent < endt)
			return true;
		return false;
	}

	@Override
	public List<Subtitle> getSubtitles(Time startTime, Time endTime) {

		if (sub.empty())
			return sub;
		LinkedList<Subtitle> subWithOrder = new LinkedList<Subtitle>(); // collection
		int startTimeOFIntrval = ((TimeIm) startTime).timeToMS();
		int endTimeOFIntrval = ((TimeIm) endTime).timeToMS();

		sub.findFirst();
		subWithOrder.findFirst();

		while (!sub.last()) {
			if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= startTimeOFIntrval
					&& ((TimeIm) sub.retrieve().getEndTime()).timeToMS() >= startTimeOFIntrval) {
				subWithOrder.insert(sub.retrieve());
				continue;
			}

			else if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= endTimeOFIntrval
					&& ((TimeIm) sub.retrieve().getStartTime()).timeToMS() >= startTimeOFIntrval) {
				subWithOrder.insert(sub.retrieve());
				continue;
			}
						
			sub.findNext();
			subWithOrder.findNext();
		}
		if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= startTimeOFIntrval
				&& ((TimeIm) sub.retrieve().getEndTime()).timeToMS() >= startTimeOFIntrval) {
			subWithOrder.insert(sub.retrieve());
		}

		else if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= endTimeOFIntrval
				&& ((TimeIm) sub.retrieve().getStartTime()).timeToMS() >= startTimeOFIntrval) {
			subWithOrder.insert(sub.retrieve());
		}
		return subWithOrder;
	}

	@Override
	public List<Subtitle> getSubtitles(String str) {
		LinkedList<Subtitle> sub2 = new LinkedList<Subtitle>();
		if (!sub.empty()) {// so we can use findfirst
			sub.findFirst();// have to start from the first element
			while (!sub.last()) {
				if (sub.retrieve().getText().contains(str)) // contains return
															// true if the left
															// string have a
															// sub-string of the
															// right side
					sub2.insert(sub.retrieve()); // need checking
													// ************************(regarding
													// the chronological order)
				sub.findNext();
			}
			if (sub.retrieve().getText().contains(str)) // checking the last
														// element
				sub2.insert(sub.retrieve());

		}
		return sub2;
	}

	@Override
	public void remove(String str) {
		if (sub.empty())
			return;
		sub.findFirst();
		while (!sub.last()) {
			if (sub.retrieve().getText().contains(str)) {
				sub.remove();
				continue;
			}
			sub.findNext();
		}
		if (sub.retrieve().getText().contains(str))
			sub.remove();

	}

	@Override
	public void replace(String str1, String str2) {
		if (sub.empty())
			return;
		sub.findFirst();
		while (!sub.last()) {
			if (sub.retrieve().getText().contains(str1)) {
				Subtitle temp = sub.retrieve();
				temp.setText(temp.getText().replace(str1, str2));
				sub.update(temp);
			}
			sub.findNext();
		}
		if (sub.retrieve().getText().contains(str1)) {
			Subtitle temp = sub.retrieve();
			temp.setText(temp.getText().replace(str1, str2));
			sub.update(temp);
		}
	}

	@Override
	public void shift(int offset) {
		if (sub.empty())
			return;
		sub.findFirst();
		while (!sub.last()) {
			Subtitle temp = sub.retrieve();
			((TimeIm) temp.getStartTime()).timeShifter(offset); // shifting the
																// start time by
																// the given
																// offset
			((TimeIm) temp.getEndTime()).timeShifter(offset); // shifting the
																// end time by
																// the given
																// offset
			sub.update(temp);
			sub.findNext();
		}
		if (sub.last()) { // checking the last element
			Subtitle temp = sub.retrieve();
			((TimeIm) temp.getStartTime()).timeShifter(offset); // shifting the
																// start time by
																// the given
																// offset
			((TimeIm) temp.getEndTime()).timeShifter(offset); // shifting the
																// end time by
																// the given
																// offset
			sub.update(temp);
		}

	}

	@Override
	public void cut(Time startTime, Time endTime) {

		if (sub.empty())
			return;
		int startTimeOFIntrval = ((TimeIm) startTime).timeToMS();
		int endTimeOFIntrval = ((TimeIm) endTime).timeToMS();
		sub.findFirst();
		if (endTimeOFIntrval < ((TimeIm) sub.retrieve().getStartTime()).timeToMS()) {
			return;
		}
		while (!sub.last()) {
// hi
			// ----|---|----
			// ----|-- |
			if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= startTimeOFIntrval
					&& ((TimeIm) sub.retrieve().getEndTime()).timeToMS() >= startTimeOFIntrval) {
				sub.remove();
				continue;
			}

			else if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= endTimeOFIntrval
					&& ((TimeIm) sub.retrieve().getStartTime()).timeToMS() >= startTimeOFIntrval) {
				sub.remove();
				continue;
			}
			sub.findNext();
		}
		if (((TimeIm) sub.retrieve().getEndTime()).timeToMS() < startTimeOFIntrval) {
			return;
		}

		if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= startTimeOFIntrval
				&& ((TimeIm) sub.retrieve().getEndTime()).timeToMS() >= startTimeOFIntrval) {
			sub.remove();

		}
		else if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() <= endTimeOFIntrval
				&& ((TimeIm) sub.retrieve().getStartTime()).timeToMS() >= startTimeOFIntrval) {
			sub.remove();
		}
		if (sub.empty())
			return;
		// if the cutt is done between any subtitle and to the last subtitle
		// no need to shift. return
		if (((TimeIm) sub.retrieve().getStartTime()).timeToMS() < startTimeOFIntrval)
			return;

		int shiftTime = (startTimeOFIntrval - endTimeOFIntrval);

		while (!sub.last()) {
			Subtitle temp = sub.retrieve();
			((TimeIm) temp.getStartTime()).timeShifter(shiftTime);

			((TimeIm) temp.getEndTime()).timeShifter(shiftTime);
			// sub.update(temp);
			sub.findNext();
		}
		Subtitle temp = sub.retrieve();
		((TimeIm) temp.getStartTime()).timeShifter(shiftTime);
		((TimeIm) temp.getEndTime()).timeShifter(shiftTime);
		// sub.update(temp);

	}

	public void print() {
		if (sub.empty())
			return;
		sub.findFirst();
		while (!sub.last()) {
			SubtitleIm a = (SubtitleIm) sub.retrieve();
			System.out.println(a.toString());
			sub.findNext();
		}
		SubtitleIm a = (SubtitleIm) sub.retrieve();
		System.out.println(a.toString());

	}

	public static void main(String[] args) {
		SubtitleSeqIm tmp = new SubtitleSeqIm();
		tmp.addSubtitle(new SubtitleIm(new TimeIm(00, 00, 01, 000), new TimeIm(00, 00, 05, 000), "PEW"));
		tmp.addSubtitle(new SubtitleIm(new TimeIm(00, 00, 06, 000), new TimeIm(00, 00, 10, 000), "odd"));
		tmp.addSubtitle(new SubtitleIm(new TimeIm(00, 00, 11, 000), new TimeIm(00, 00, 15, 000), "Boom"));
		tmp.addSubtitle(new SubtitleIm(new TimeIm(00, 00, 16, 000), new TimeIm(00, 00, 20, 000), "trgdm"));
		tmp.addSubtitle(new SubtitleIm(new TimeIm(00, 00, 21, 000), new TimeIm(00, 00, 25, 000), "Glack"));
		tmp.cut(new TimeIm(00, 00, 01, 000), new TimeIm(00, 00, 20, 000));

		sub.findFirst();
		while (!sub.last()) {
			System.out.println(sub.retrieve().getEndTime());
			System.out.println(sub.retrieve().getStartTime());
			
			sub.findNext();
		}
		System.out.println(sub.retrieve().getText());

	}
}
