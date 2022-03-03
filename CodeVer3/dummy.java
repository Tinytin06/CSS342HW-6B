package homeworkSixB;

public class dummy {
	public static void main(String[] theArgs) {
		int[] arrays = {5, 2, 3, 1};
		sortArray(arrays);
	}
	
	public int[] sortArray(int[] nums) {
		if (nums.length == 1) {
			return nums;
		} else if (nums.length == 2) {
			if (nums[0] < nums[1]) {
				return nums;
			}
		} else {
			sortArray(nums);
			sortArray(nums);
		}
		return nums;
	}
}
