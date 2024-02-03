package RMIServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class ServerImp extends UnicastRemoteObject implements IServer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DAO dao;
	
	public ServerImp() throws RemoteException {
		super();
		dao = new DAO();
	}
	
	@Override
	public boolean checkUsername(String username) throws RemoteException, ClassNotFoundException, SQLException {
		return dao.checkUsername(username);
	}
	
	@Override
	public boolean checkLogin(String username, String password) throws RemoteException, ClassNotFoundException, SQLException {
		return dao.checkLogin(username, password);
	}

	@Override
	public List<Student> findById(int id) throws RemoteException, ClassNotFoundException, SQLException {
		return dao.findById(id);
	}

	@Override
	public List<Student> findByName(String name) throws RemoteException, ClassNotFoundException, SQLException {
		return dao.findByName(name);
	}

	@Override
	public List<Student> findByAge(int age) throws RemoteException, ClassNotFoundException, SQLException {
		return dao.findByAge(age);
	}

	@Override
	public List<Student> findByScore(double score) throws RemoteException, ClassNotFoundException, SQLException {
		return dao.findByScore(score);
	}

	@Override
	public void insert(Student student) throws RemoteException, ClassNotFoundException, SQLException {
		dao.insert(student);
	}

	@Override
	public void update(int id, Student student) throws RemoteException, ClassNotFoundException, SQLException {
		dao.update(id, student);
	}

	@Override
	public void delete(int id) throws RemoteException, ClassNotFoundException, SQLException {
		dao.delete(id);
	}
}
