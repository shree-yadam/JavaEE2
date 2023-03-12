
public class SingletonClass {
	private static SingletonClass sObj = null;
	
	private String data = "Hello !!!";
	
	private SingletonClass() {
		
	}
	
	public static SingletonClass getObject() {
		if(sObj == null) {
			sObj = new SingletonClass();
		}
		return sObj;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
