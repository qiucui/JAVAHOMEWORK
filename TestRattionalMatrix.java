
public class TestRattionalMatrix {
	public class TestArr {
	    public void main(String[] args) {
	        int m = 3; // m��
	        int n = 4; // n��
	        int[][] arr = new int[m][n]; // m��n�о��� = m������Ϊn������ = 3������Ϊ4������
	        System.out.println("arr.length = " + arr.length); // m = 3
	        System.out.println("arr[0].length = " + arr[0].length); // n = 4
	        int num = 0;
	        for (int i = 0; i < arr.length; i++) {
	            for (int j = 0; j < arr[0].length; j++) {
	                arr[i][j] = num++; // ��i�������jλ����i�е�j��
	            }
	        }
	        
	        System.out.println("��ӡ����1");
	        p(arr);
	 
	        System.out.println("��ӡ����2");
	        for (int i = 0; i < arr.length; i++) {
	            printArr(arr[i]); // ��i������
	        }
	        System.out.println("end");
	    }
	    
	   
	    private void p(int[][] arr) {
			// TODO �Զ����ɵķ������
			
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
	    //ʵ��GenericMatrix�е�add���󷽷�
	    protected Integer add(Integer o1, Integer o2 ){  
	        return o1+o2;  
	    }  



	public void main(String[] args) {
		// TODO �Զ����ɵķ������

	}


	public void p(int[][] arr) {
		// TODO �Զ����ɵķ������
		
	}

}
}