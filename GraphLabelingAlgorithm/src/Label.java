import java.util.HashSet;


public class Label {

	public double v;
	public int mNum;
	public HashSet<Integer> mSet;
	
	public Label(double v, int mNum, HashSet<Integer> mSet) {
		this.v = v;
		this.mNum = mNum;
		this.mSet = mSet;
	}
	
	/**
	 * 
	 * @param h
	 * @return true if this lable is dominanted by lable h, otherwise, false.
	 */
	public boolean isDominantedBy(Label h) {
		if (h.v >= this.v && this.mSet.containsAll(h.mSet))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mSet == null) ? 0 : mSet.hashCode());
		long temp;
		temp = Double.doubleToLongBits(v);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Label other = (Label) obj;
		if (mSet == null) {
			if (other.mSet != null)
				return false;
		} else if (!mSet.equals(other.mSet))
			return false;
		if (Double.doubleToLongBits(v) != Double.doubleToLongBits(other.v))
			return false;
		return true;
	}
	
	public String toString() {
		return "value: " + this.v + ", set size: " + this.mNum;
	}
	
}
