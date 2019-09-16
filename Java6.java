import java.lang.reflect.InvocationHandler;

public class Java6<TickectBuyer> {
	private static final int MyInvocationHandler = 0;

	public void buyTicket() {
	    // 在调用委托类的方法前，加入其他逻辑处理
	    beforeMethod();

	    Java6 passenger = null;
		// 调用委托类（乘客类）的方法
	    passenger.buyTicket();
	}
	// 创建一个与代理对象相关联的InvocationHandler
	InvocationHandler passengerHandler = MyInvocationHandler<MyInvocationHandler>(passenger);
	
	
	// 创建一个代理对象passengerProxy，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
	
	TickectBuyer passenger= (TickectBuyer)Proxy.newProxyInstance(TickectBuyer.class.getClassLoader(), new Class<?>[]{TickectBuyer.class}, passengerHandler);
	public interface TickectBuyer {

	    // 买车票
	    void buyTicket();
	    public class Passenger implements TickectBuyer {

	        private String name;

	        Passenger(String name) {
	            this.name = name;
	        }

	        @Override
	        public void buyTicket() {
	            try {
	                // 假设买一张票要5秒
	                Thread.sleep(5000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            System.out.println("乘客【" + name + "】买了一张车票。");
	        }
	    }
	    public class MonitorUtil {

	        private static ThreadLocal<Long> tl = new ThreadLocal<>();

	        public static void start() {
	            tl.set(System.currentTimeMillis());
	        }

	        // 结束时打印耗时
	        public static void finish(String methodName) {
	            long finishTime = System.currentTimeMillis();
	            System.out.println(methodName + "方法耗时" + (finishTime - tl.get()) + "ms");
	        }
	    }
	    public abstract class PassengerInvocationHandler<T> implements InvocationHandler {
	        // InvocationHandler持有的被代理对象
	        private T target;

	        public PassengerInvocationHandler(T target) {
	            this.target = target;
	        }

	        /**
	         * proxy:代表动态代理对象
	         * method：代表正在执行的方法
	         * args：代表调用目标方法时传入的实参
	         */
	        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	            System.out.println("代理执行" + method.getName() + "方法");

	            // 代理过程中插入监测方法，计算该方法耗时
	            MonitorUtil.start();
	            Object result = method.invoke(target, args);
	            MonitorUtil.finish(method.getName());
	            return result;
	        }
	    }
	    public class PassengerProxyTest2 {

	        public static void main(String[] args) {
	            
	            // 创建一个实例对象，这个对象是被代理的对象
	            TickectBuyer zhangsan = new Passenger("张三");

	            // 创建一个与代理对象相关联的InvocationHandler
	            InvocationHandler passengerHandler = new PassengerInvocationHandler<TickectBuyer>(zhangsan);

	            // 创建一个代理对象passengerProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
	            TickectBuyer passengerProxy = (TickectBuyer) Proxy.newProxyInstance(TickectBuyer.class.getClassLoader(),
	                    new Class<?>[]{TickectBuyer.class}, passengerHandler);

	            // 代理执行买车票的方法
	            passengerProxy.buyTicket();
	        }
	    }

	}

	private void beforeMethod() {
		// TODO 自动生成的方法存根
		
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
