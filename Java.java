
public class Java {
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

}
	public interface TickectBuyer { //买票人接口
		void buyTicket1(); void buyTicket();
		//买票
		public abstract class Passenger implements TickectBuyer {
		 // 乘客类实现买票人接口
			private String name;
		Passenger(String name) {
		        this.name = name;
		    }
		    @Override
		    public void buyTicket() {
		        System.out.println("乘客【" + name + "】买了一张车票。");
		    }
		}
		public abstract class PassengerProxy implements TickectBuyer {
     //乘客代理类实现买票人接口
		    private String name;
		    private Passenger passenger;

		    PassengerProxy(String name, Passenger passenger) {
       //被代理的乘客类
		        this.name = name;
		        // 只代理乘客类
		        if (passenger.getClass() == Passenger.class) {
		            this.passenger = passenger;
		        }
		    }

		    @Override
		    public void buyTicket1() {
		        // 委托类附加的操作
		        System.out.print("代买人【" + name + "】代");

		        // 调用委托类（乘客类）的方法
		        passenger.buyTicket();
		    }
		    {
	    // 只代理乘客类
        if (passenger.getClass() == Passenger.class) {
            this.passenger = passenger;
        }
    }

    @Override
    public void buyTicket() {
        // 委托类附加的操作
        System.out.print("代买人【" + name + "】代");

        // 调用委托类（乘客类）的方法
        passenger.buyTicket();
    }
}
		public class PassengerProxyTest {
     //乘客代理测试类
		    public static void main(String[] args) {
		        // 乘客陈小鸡（乘客类）
		        Passenger passenger = Passenger("陈小鸡");
		        // 跟车人（乘客代理类）
		        PassengerProxy carFollower = PassengerProxy("王小狗", passenger);
		        // 由跟车人代理陈小鸡买车票
		        carFollower.buyTicket();
		    }

	private static Passenger Passenger(String string) {
		// TODO 自动生成的方法存根
		return null;
	}
		}
		static PassengerProxy PassengerProxy(String string, Passenger passenger) {
			// TODO 自动生成的方法存根
			return null;}
		
	}
	
			
		


	
