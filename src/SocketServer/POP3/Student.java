package SocketServer.POP3;

public class Student {
	int id;
	String name;
	int bYear;
	double score;
	
	public Student(int id, String name, int bYear, double score) {
		this.id = id;
		this.name = name;
		this.bYear = bYear;
		this.score = score;
	}
	
	public String toString() {
		return "ID:"+id +", Name:" + name + ", Year:" + bYear + ", Grade:" + score;
	}
}
