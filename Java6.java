import java.lang.reflect.InvocationHandler;

public class Java6<TickectBuyer> {
	private static final int MyInvocationHandler = 0;

	public void buyTicket() {
	    // �ڵ���ί����ķ���ǰ�����������߼�����
	    beforeMethod();

	    Java6 passenger = null;
		// ����ί���ࣨ�˿��ࣩ�ķ���
	    passenger.buyTicket();
	}
	// ����һ�����������������InvocationHandler
	InvocationHandler passengerHandler = MyInvocationHandler<MyInvocationHandler>(passenger);
	
	
	// ����һ���������passengerProxy����������ÿ��ִ�з��������滻ִ��Invocation�е�invoke����
	
	TickectBuyer passenger= (TickectBuyer)Proxy.newProxyInstance(TickectBuyer.class.getClassLoader(), new Class<?>[]{TickectBuyer.class}, passengerHandler);
	public interface TickectBuyer {

	    // ��Ʊ
	    void buyTicket();
	    public class Passenger implements TickectBuyer {

	        private String name;

	        Passenger(String name) {
	            this.name = name;
	        }

	        @Override
	        public void buyTicket() {
	            try {
	                // ������һ��ƱҪ5��
	                Thread.sleep(5000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            System.out.println("�˿͡�" + name + "������һ�ų�Ʊ��");
	        }
	    }
	    public class MonitorUtil {

	        private static ThreadLocal<Long> tl = new ThreadLocal<>();

	        public static void start() {
	            tl.set(System.currentTimeMillis());
	        }

	        // ����ʱ��ӡ��ʱ
	        public static void finish(String methodName) {
	            long finishTime = System.currentTimeMillis();
	            System.out.println(methodName + "������ʱ" + (finishTime - tl.get()) + "ms");
	        }
	    }
	    public abstract class PassengerInvocationHandler<T> implements InvocationHandler {
	        // InvocationHandler���еı��������
	        private T target;

	        public PassengerInvocationHandler(T target) {
	            this.target = target;
	        }

	        /**
	         * proxy:����̬�������
	         * method����������ִ�еķ���
	         * args���������Ŀ�귽��ʱ�����ʵ��
	         */
	        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	            System.out.println("����ִ��" + method.getName() + "����");

	            // ��������в����ⷽ��������÷�����ʱ
	            MonitorUtil.start();
	            Object result = method.invoke(target, args);
	            MonitorUtil.finish(method.getName());
	            return result;
	        }
	    }
	    public class PassengerProxyTest2 {

	        public static void main(String[] args) {
	            
	            // ����һ��ʵ��������������Ǳ�����Ķ���
	            TickectBuyer zhangsan = new Passenger("����");

	            // ����һ�����������������InvocationHandler
	            InvocationHandler passengerHandler = new PassengerInvocationHandler<TickectBuyer>(zhangsan);

	            // ����һ���������passengerProxy������zhangsan����������ÿ��ִ�з��������滻ִ��Invocation�е�invoke����
	            TickectBuyer passengerProxy = (TickectBuyer) Proxy.newProxyInstance(TickectBuyer.class.getClassLoader(),
	                    new Class<?>[]{TickectBuyer.class}, passengerHandler);

	            // ����ִ����Ʊ�ķ���
	            passengerProxy.buyTicket();
	        }
	    }

	}

	private void beforeMethod() {
		// TODO �Զ����ɵķ������
		
	}

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������

	}

}
