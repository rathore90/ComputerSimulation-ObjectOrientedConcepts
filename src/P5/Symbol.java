package P5;

public class Symbol {
	private String name;
	private int location;
	private Integer value = null;
	
	//constructor
	public Symbol() {
		//this(name,location,value);
		this.name = "";
		this.location = 0;
		this.value = null;
	}
	public Symbol(String name,int location,Integer value) {
		this.name = name;
		this.location = location;
		this.value = value;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public String toString() {
		return "(name=" + name + ", location=" + location + ", value=" + value + ")\n";
	}
}
