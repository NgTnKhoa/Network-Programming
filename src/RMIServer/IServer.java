package RMIServer.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface IServer extends Remote {
	public boolean checkUsername(String username) throws RemoteException, ClassNotFoundException, SQLException;
	public boolean checkLogin(String username, String password) throws RemoteException, ClassNotFoundException, SQLException;
	public List<Student> findById(int id) throws RemoteException, ClassNotFoundException, SQLException;
	public List<Student> findByName(String name) throws RemoteException, ClassNotFoundException, SQLException;
	public List<Student> findByAge(int age) throws RemoteException, ClassNotFoundException, SQLException;
	public List<Student> findByScore(double score) throws RemoteException, ClassNotFoundException, SQLException;
	public void insert(Student student) throws RemoteException, ClassNotFoundException, SQLException;
	public void update(int id, Student student) throws RemoteException, ClassNotFoundException, SQLException;
	public void delete(int id) throws RemoteException, ClassNotFoundException, SQLException;
}
