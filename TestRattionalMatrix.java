
public class TestRattionalMatrix {
	public class TestArr {
	    public void main(String[] args) {
	        int m = 3; // m行
	        int n = 4; // n列
	        int[][] arr = new int[m][n]; // m行n列矩阵 = m个长度为n的数组 = 3个长度为4的数组
	        System.out.println("arr.length = " + arr.length); // m = 3
	        System.out.println("arr[0].length = " + arr[0].length); // n = 4
	        int num = 0;
	        for (int i = 0; i < arr.length; i++) {
	            for (int j = 0; j < arr[0].length; j++) {
	                arr[i][j] = num++; // 第i个数组的j位，第i行第j列
	            }
	        }
	        
	        System.out.println("打印方法1");
	        p(arr);
	 
	        System.out.println("打印方法2");
	        for (int i = 0; i < arr.length; i++) {
	            printArr(arr[i]); // 第i个数组
	        }
	        System.out.println("end");
	    }
	    
	   
	    private void p(int[][] arr) {
			// TODO 自动生成的方法存根
			
		}


		@SuppressWarnings("unused")
		private void print(int[][] arr) {
	        for (int i = 0; i < arr.length; i++) {
	            for (int j = 0; j < arr[0].length; j++) {
	                System.out.print(arr[i][j]);
	                if (j !=  arr[0].length - 1) {
	                    System.out.print(" ");
	                } else {
	                    System.out.println();
	                }
	            }
	        }
	    }
	 
	    private void printArr(int[] arr) {
	        for (int i = 0; i < arr.length; i++) {
	            System.out.print(arr[i]);
	            if (i != arr.length - 1) {
	                System.out.print(" ");
	            } else {
	                System.out.println();
	            }
	        }
	    }
	}
	class IntegerMatrix extends GenericMatrix<Integer>{  
	    //实现GenericMatrix中的add抽象方法
	    protected Integer add(Integer o1, Integer o2 ){  
	        return o1+o2;  
	    }  



	public void main(String[] args) {
		// TODO 自动生成的方法存根

	}


	public void p(int[][] arr) {
		// TODO 自动生成的方法存根
		
	}

}
}