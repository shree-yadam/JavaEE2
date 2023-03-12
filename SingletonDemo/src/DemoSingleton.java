
public class DemoSingleton {
	public static void main(String[] args) {
		SingletonClass sObj1 = SingletonClass.getObject();
		SingletonClass sObj2 = SingletonClass.getObject();
		// SingletonClass sObj3 = new SingletonClass(); // The is error because constructor is private
		
		System.out.println("Obj1 data: " + sObj1.getData());
		System.out.println("Obj2 data: " + sObj2.getData());
		
		sObj1.setData("How are you??");
		
		System.out.println("Obj1 data: " + sObj1.getData());
		System.out.println("Obj2 data: " + sObj2.getData());
	}
}
