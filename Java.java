
public class Java {
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������

}
	public interface TickectBuyer { //��Ʊ�˽ӿ�
		void buyTicket1(); void buyTicket();
		//��Ʊ
		public abstract class Passenger implements TickectBuyer {
		 // �˿���ʵ����Ʊ�˽ӿ�
			private String name;
		Passenger(String name) {
		        this.name = name;
		    }
		    @Override
		    public void buyTicket() {
		        System.out.println("�˿͡�" + name + "������һ�ų�Ʊ��");
		    }
		}
		public abstract class PassengerProxy implements TickectBuyer {
     //�˿ʹ�����ʵ����Ʊ�˽ӿ�
		    private String name;
		    private Passenger passenger;

		    PassengerProxy(String name, Passenger passenger) {
       //������ĳ˿���
		        this.name = name;
		        // ֻ����˿���
		        if (passenger.getClass() == Passenger.class) {
		            this.passenger = passenger;
		        }
		    }

		    @Override
		    public void buyTicket1() {
		        // ί���฽�ӵĲ���
		        System.out.print("�����ˡ�" + name + "����");

		        // ����ί���ࣨ�˿��ࣩ�ķ���
		        passenger.buyTicket();
		    }
		    {
	    // ֻ����˿���
        if (passenger.getClass() == Passenger.class) {
            this.passenger = passenger;
        }
    }

    @Override
    public void buyTicket() {
        // ί���฽�ӵĲ���
        System.out.print("�����ˡ�" + name + "����");

        // ����ί���ࣨ�˿��ࣩ�ķ���
        passenger.buyTicket();
    }
}
		public class PassengerProxyTest {
     //�˿ʹ��������
		    public static void main(String[] args) {
		        // �˿ͳ�С�����˿��ࣩ
		        Passenger passenger = Passenger("��С��");
		        // �����ˣ��˿ʹ����ࣩ
		        PassengerProxy carFollower = PassengerProxy("��С��", passenger);
		        // �ɸ����˴����С����Ʊ
		        carFollower.buyTicket();
		    }

	private static Passenger Passenger(String string) {
		// TODO �Զ����ɵķ������
		return null;
	}
		}
		static PassengerProxy PassengerProxy(String string, Passenger passenger) {
			// TODO �Զ����ɵķ������
			return null;}
		
	}
	
			
		


	
